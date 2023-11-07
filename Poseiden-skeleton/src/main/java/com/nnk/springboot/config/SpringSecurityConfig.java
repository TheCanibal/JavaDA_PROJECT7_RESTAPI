package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
	    auth.requestMatchers("/").permitAll();
	    auth.requestMatchers("/resources/**", "/css/**").permitAll();
	    auth.requestMatchers("/user/**").hasRole("ADMIN");
	    auth.requestMatchers("/bidList/**").hasAnyRole("USER", "ADMIN");
	    auth.requestMatchers("/curvePoint/**").hasAnyRole("USER", "ADMIN");
	    auth.requestMatchers("/rating/**").hasAnyRole("USER", "ADMIN");
	    auth.requestMatchers("/ruleName/**").hasAnyRole("USER", "ADMIN");
	    auth.requestMatchers("/trade/**").hasAnyRole("USER", "ADMIN");
	    auth.requestMatchers("/403").hasAnyRole("USER", "ADMIN");
	    auth.anyRequest().authenticated();
	}).csrf(csrf -> {
	    csrf.disable();
	}).formLogin(form -> {
	    form.defaultSuccessUrl("/bidList/list");
	    form.loginPage("/login").permitAll();
	    form.usernameParameter("username");
	    form.passwordParameter("password");
	    form.failureUrl("/login?error=true").permitAll();
	}).logout(logout -> {
	    logout.logoutUrl("/app-logout").permitAll();
	    logout.logoutSuccessUrl("/login?logout").invalidateHttpSession(true).deleteCookies("JSESSIONID");
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
