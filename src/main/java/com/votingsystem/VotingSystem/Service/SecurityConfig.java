package com.votingsystem.VotingSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final AuthenticationFailureHandler customAuthenticationFailureHandler;
	private final VoterService userService;

	@Autowired
	public SecurityConfig(AuthenticationFailureHandler customAuthenticationFailureHandler, VoterService userService) {
		this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
		this.userService = userService;
	}

	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	                .csrf(AbstractHttpConfigurer::disable)
	                .authorizeHttpRequests(authorize -> authorize
	                        .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
	                        .requestMatchers("/admin/**").hasRole("ADMIN")
	                        .anyRequest().authenticated()
	                )
	                .formLogin(form -> form
	                        .loginPage("/login")
	                        .defaultSuccessUrl("/index", true)
	                        .failureHandler(customAuthenticationFailureHandler)
	                )
	                .logout(logout -> logout
	                        .logoutSuccessUrl("/login?logout=true")
	                )
	                .httpBasic(Customizer.withDefaults())
	                .exceptionHandling(exception -> exception
	                        .accessDeniedPage("/403")
	                );

	        return http.build();
	    }

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
}
