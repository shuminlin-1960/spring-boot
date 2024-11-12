package com.start.spring.springboot_tutorial.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig_BasicAndInMemory {

//Use In memory authentication via UserDetailsService
//to enable application to add in-memory users instead of using
// spring.security.user/password
    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//          .username("user1")
        UserDetails user = User.withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build();

        UserDetails user1 = User.withUsername("user1")
                .password("{noop}password1")
                .roles("USER")
                .build();
//        UserDetails admin1 = User.withDefaultPasswordEncoder()
//                        .username("admin1")
        UserDetails admin = User.withUsername("admin")
                .password("{noop}adminpass")
                .roles("ADMIN")
                .build();


        return new InMemoryUserDetailsManager(user, user1, admin);
    }

//    private static final String[] AUTH_WHITELIST = {
//            "/",
//            "/hello"
//    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizedRequests -> authorizedRequests.anyRequest().authenticated());
//        httpSecurity.authorizeHttpRequests(authorizedRequests -> authorizedRequests.requestMatchers(AUTH_WHITELIST).authenticated());
        httpSecurity.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        httpSecurity.httpBasic(Customizer.withDefaults());
//      Both also work
//      httpSecurity.formLogin(Customizer.withDefaults());
//      httpSecurity.formLogin(form -> form.defaultSuccessUrl("/hello", true));
        httpSecurity.csrf(csrf -> csrf.disable());
        return httpSecurity.build();
    }


}
