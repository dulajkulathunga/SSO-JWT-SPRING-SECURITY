package com.sso.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.sso.jwt.repository.UserRepo;
import com.sso.jwt.scope.UserDataRequestScope;
import com.sso.jwt.token.filters.JsonWebTokenAuthenticationFilter;
import com.sso.jwt.token.filters.JsonWebTokenAuthorizationFilter;
import com.sso.jwt.utility.JsonWebTokenGenerator;
import com.sso.jwt.utility.PermissionRelated;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private UserPrincipalDetailsService userPrincipalDetailsService;
	private UserRepo userRepository;
	private PermissionRelated permissionRelated;
	private UserDataRequestScope userDataRequestScope;
	private JsonWebTokenGenerator jsonWebTokenGenerator;

	public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, UserRepo userRepository,
			PermissionRelated permissionRelated, UserDataRequestScope userDataRequestScope,
			JsonWebTokenGenerator jsonWebTokenGenerator) {
		this.userPrincipalDetailsService = userPrincipalDetailsService;
		this.userRepository = userRepository;
		this.permissionRelated = permissionRelated;
		this.userDataRequestScope = userDataRequestScope;
		this.jsonWebTokenGenerator = jsonWebTokenGenerator;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilter(new JsonWebTokenAuthenticationFilter(authenticationManager(), jsonWebTokenGenerator))
				.addFilter(new JsonWebTokenAuthorizationFilter(authenticationManager(), userRepository,
						userDataRequestScope, jsonWebTokenGenerator))
				.authorizeRequests().antMatchers("/api/prof").hasAnyRole("ADMIN").antMatchers("/api/prof1")
				.hasAnyAuthority(permissionRelated.getAuthorizedRoles("prof1")).antMatchers("/api/prof2")
				.hasAnyAuthority(permissionRelated.getAuthorizedRoles("prof2")).antMatchers("/api/prof3")
				.hasAnyAuthority(permissionRelated.getAuthorizedRoles("prof3")).antMatchers("/api/logout").permitAll()
				.anyRequest().authenticated();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	PasswordEncoder passwodEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwodEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

		return daoAuthenticationProvider;
	}

}
