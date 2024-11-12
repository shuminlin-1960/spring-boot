package com.start.spring.springboot_tutorial.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


import static org.apache.commons.lang3.BooleanUtils.and;

//@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig_OAuth2 {

//    @Autowired
//    DataSource dataSource;

//    @Autowired
//    private AuthEntryPointJwt unauthorizedHandler;

//    @Bean
//    public AuthTokenFilter authenticationJwtTokenFilter() {
//        return new AuthTokenFilter();
//    }


//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
//        return builder.getAuthenticationManager();
//    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
//        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(authorize -> authorize
//                        .requestMatchers("/signin", "/login", "/oauth2").permitAll()
                .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults());
//                .formLogin(form -> form.defaultSuccessUrl("/hello", true));
                  .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/hello", true)
                  );



//        Need to be stateless to force JWT token passed between server and clent
//        http.sessionManagement(session ->
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
//
//        http.headers(headers ->
//                headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));


//      Do the JWT token filtering before authenticating user's credentials
//        http.addFilterBefore(authenticationJwtTokenFilter(),
//                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    Created here by IOC container
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

/**
//     @Bean
//    Injected here
//    public CommandLineRunner initData (UserDetailsService userDetailsService) {
//        return args -> {
//            JdbcUserDetailsManager manager = (JdbcUserDetailsManager)  userDetailsService;
//            UserDetails user1 = User.withUsername("user1")
//                    .password(passwordEncoder().encode("user1"))
//                    .roles("USER").build();
//            UserDetails admin = User.withUsername("admin")
//                    .password(passwordEncoder().encode("admin"))
//                    .roles("ADMIN").build();
//            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//            userDetailsManager.createUser(user1);
//            userDetailsManager.createUser(admin);
//        };
//    }
**/
}
