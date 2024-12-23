package com.start.spring.springboot_tutorial.security;

import com.start.spring.springboot_tutorial.jwt.AuthEntryPointJwt;
import com.start.spring.springboot_tutorial.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigJdbcBased {
    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }


     @Bean
     public PasswordEncoder passwordEncoder() {
     return  new BCryptPasswordEncoder();
     }



//DOES NOT WORK! Need to use the commandlineRunner-based below
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.withUsername("user1")
//                .password(passwordEncoder().encode("password1"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("adminpass"))
//                .roles("ADMIN")
//                .build();
//
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//        manager.createUser(user1);
//        manager.createUser(admin);
//        return manager;
//    }


    @Bean
//    Created here by IOC container
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }


//    Not for industry-scale application and has to be replaced by a dedicated service
    @Bean
    public CommandLineRunner initData (UserDetailsService userDetailsService) {
        return args -> {
            JdbcUserDetailsManager manager = (JdbcUserDetailsManager)  userDetailsService;
            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
            if (!userDetailsManager.userExists("user1")) {
                UserDetails user1 = User.withUsername("user1")
                        .password(passwordEncoder().encode("password1"))
                        .roles("USER").build();
                userDetailsManager.createUser(user1);
            }

            if (!userDetailsManager.userExists("admin")) {
                UserDetails admin = User.withUsername("admin")
                        .password(passwordEncoder().encode("adminpass"))
                        .roles("ADMIN").build();
                userDetailsManager.createUser(admin);
            }
        };
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(requests ->
                requests.requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/signin/**").permitAll()
                        .requestMatchers("/signup/**").permitAll()
                        .requestMatchers("/departments/**").hasRole("USER")
//                        .requestMatchers("/departments/**").permitAll()

//  For actuator
//                        .requestMatchers(new AntPathRequestMatcher("/manager/**")).permitAll()
//                        .requestMatchers("/manager/**").permitAll()
                        .requestMatchers("/manager/**").hasRole("ADMIN")

// For Swagger
                        .requestMatchers(
                                "/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger.html",
                                "/openapi/**"
                                )
                        .hasRole("ADMIN")
//                      .permitAll()
//Also works
//                        .requestMatchers(new AntPathRequestMatcher("/openapi/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/sagger-ui/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/sagger-ui.html")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/api-docs/**")).permitAll()


//  Somehow the following separate patterns do not work. Error message:
//  Full authentication is required to access this resource
//                        .requestMatchers("/openapi/**").permitAll()
//                        .requestMatchers("/sagger-ui/**").permitAll()
//                        .requestMatchers("/sagger-ui.html").permitAll()
//                        .requestMatchers("/api-docs/**").permitAll()

                        .anyRequest().authenticated());

//        For JWT, the request has to be stateless
        httpSecurity.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        httpSecurity.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

//TODO test      httpSecurity.formLogin(form -> form.defaultSuccessUrl("/hello", true));
//        httpSecurity.httpBasic(withDefaults());

//        Also works
//        httpSecurity.headers(headers ->
//                headers.frameOptions((frameOptions -> frameOptions.sameOrigin()));
//        httpSecurity.csrf(csrf -> csrf.disable());

        httpSecurity.headers(headers ->
                headers.frameOptions((HeadersConfigurer.FrameOptionsConfig::sameOrigin)));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

//      Do the JWT token filtering before authenticating user's credentials
        httpSecurity.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}

