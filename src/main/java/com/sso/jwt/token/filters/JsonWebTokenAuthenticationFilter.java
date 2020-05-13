package com.sso.jwt.token.filters;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sso.jwt.constant.PropertiesEnum;
import com.sso.jwt.security.UserPrincipal;
import com.sso.jwt.utility.JsonWebTokenGenerator;
import com.sso.jwt.viewmodel.LoginViewModel;

public class JsonWebTokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JsonWebTokenGenerator jsonWebTokenGenerator;

	public JsonWebTokenAuthenticationFilter(AuthenticationManager authenticationManager,
			JsonWebTokenGenerator jsonWebTokenGenerator) {
		this.authenticationManager = authenticationManager;
		this.jsonWebTokenGenerator = jsonWebTokenGenerator;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		LoginViewModel credentials = null;
		try {
			credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), credentials.getPassword(), new ArrayList<>());

		Authentication auth = authenticationManager.authenticate(authenticationToken);
		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

		response.addHeader(PropertiesEnum.HEADER_STRING_ACCESS_TOKEN.toString(),
				jsonWebTokenGenerator.jwtAccessTokenGenerate(principal));
		response.addHeader(PropertiesEnum.HEADER_STRING_REFRESH_TOKEN.toString(),
				jsonWebTokenGenerator.jwtRefreshTokenGenerate(principal));
		response.addHeader(PropertiesEnum.HEADER_STRING_ID_TOKEN.toString(),
				PropertiesEnum.TOKEN_PREFIX.toString() + jsonWebTokenGenerator.jwtIdTokenGenerate(principal));
	}

}
