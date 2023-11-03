package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	return http.authorizeHttpRequests(auth -> {
	    auth.requestMatchers("/**").permitAll();
	    auth.requestMatchers("/resources/**", "/css/**").permitAll().anyRequest().authenticated();
	}).formLogin(Customizer.withDefaults()).csrf(csrf -> {
	    csrf.disable();
	}).logout(logout -> {
	    logout.logoutUrl("/logout").permitAll();
	    logout.logoutSuccessUrl("/login?logout").invalidateHttpSession(true).deleteCookies("JSESSIONID",
		    "remember-me");
	}).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
	    throws Exception {
	AuthenticationManagerBuilder authenticationManagerBuilder = http
		.getSharedObject(AuthenticationManagerBuilder.class);
	authenticationManagerBuilder.userDetailsService(customUserDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
	return authenticationManagerBuilder.build();
    }
}