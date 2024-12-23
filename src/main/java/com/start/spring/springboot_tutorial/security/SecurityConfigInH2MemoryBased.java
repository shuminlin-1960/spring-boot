package com.start.spring.springboot_tutorial.security;

import com.start.spring.springboot_tutorial.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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


//@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigInH2MemoryBased {

    @Autowired
    DataSource dataSource;

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

    @Bean
//    Created here by IOC container
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public CommandLineRunner initData (UserDetailsService userDetailsService) {
        return args -> {
            JdbcUserDetailsManager manager = (JdbcUserDetailsManager)  userDetailsService;
            UserDetails user1 = User.withUsername("user1")
                    .password(passwordEncoder().encode("password1"))
                    .roles("USER").build();
            UserDetails admin = User.withUsername("admin")
                    .password(passwordEncoder().encode("adminpass"))
                    .roles("ADMIN").build();

            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
            userDetailsManager.createUser(user1);
            userDetailsManager.createUser(admin);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(requests ->
                requests.requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/signin").permitAll()
                        .anyRequest().authenticated());

//        Need to be stateless to force JWT token passed between server and clent
        httpSecurity.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );


//TODO test      httpSecurity.formLogin(form -> form.defaultSuccessUrl("/hello", true));
//        If enabled, any path like /user, /admin, /hello instead POST-based /signin
//        can be used to invoke the basic or form login.
//        Otherwise, the only way to sign in is via the POST-based "/signin"
//        httpSecurity.httpBasic(withDefaults());
//        httpSecurity.formLogin(withDefaults());

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