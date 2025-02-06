package com.bankapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.bankapp.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {



	@Autowired
	AccountService accountService;

	/**
	 * Creates an instance of the password encrypt
	 * @return
	 */
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new  BCryptPasswordEncoder();
	}

	/**
	 * Controls the access the  dashboard ,register, login, logout
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/register").permitAll()
				.anyRequest().authenticated()
		);
		http.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/dashboard", true)
				.permitAll()
		);
		http.logout(logout -> logout
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll()
		);
		http.headers(header -> header
				.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
		);
		return http.build();
	}

	/**
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
	}

}
