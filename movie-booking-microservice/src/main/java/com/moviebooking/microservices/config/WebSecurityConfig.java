package com.moviebooking.microservices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${movie.booking.microservice.user}")
    private String userName;
    @Value("${movie.booking.microservice.password}")
    private String userPwd;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Customize security configuration
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        InMemoryUserDetailsManager userDetailsManagerConfigurer = new InMemoryUserDetailsManager();
        UserDetails userDetails = User.withUsername(userName)
                .password(bCryptPasswordEncoder.encode(userPwd)).authorities("read").build();
        userDetailsManagerConfigurer.createUser(userDetails);
        auth.userDetailsService(userDetailsManagerConfigurer).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable basic authentication
        http.httpBasic();
        // All requests should be authenticated else throw 401:Unauthorized
        http.authorizeRequests().anyRequest().authenticated();

        /*
            Disable CSRF Protection because it is enabled by default in spring security and POST request throws 403:Forbidden
            CSRF: Cross-site request forgery (also known as CSRF) is a web security vulnerability
            that allows an attacker to induce users to perform actions that they do not intend to perform.
        */
        http.cors().and().csrf().disable();

        /*
        Enable H2 Console access: http://localhost:8000/h2-console
        by permitting h2-console path
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll();
         */

    }

}