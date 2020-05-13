package com.sso.jwt.token.filters;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.sso.jwt.constant.PropertiesEnum;
import com.sso.jwt.model.User;
import com.sso.jwt.repository.UserRepo;
import com.sso.jwt.scope.UserDataRequestScope;
import com.sso.jwt.security.UserPrincipal;
import com.sso.jwt.utility.JsonWebTokenGenerator;

public class JsonWebTokenAuthorizationFilter extends BasicAuthenticationFilter {

	private UserDataRequestScope userDataRequestScope;
	private UserRepo userRepository;
	private JsonWebTokenGenerator jsonWebTokenGenerator;

	public JsonWebTokenAuthorizationFilter(AuthenticationManager authenticationManager, UserRepo userRepository,
			UserDataRequestScope userDataRequestScope, JsonWebTokenGenerator jsonWebTokenGenerator) {
		super(authenticationManager);
		this.userRepository = userRepository;
		this.userDataRequestScope = userDataRequestScope;
		this.jsonWebTokenGenerator = jsonWebTokenGenerator;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(PropertiesEnum.HEADER_STRING_ID_TOKEN.toString());

		if (header == null || !header.startsWith(PropertiesEnum.TOKEN_PREFIX.toString())) {
			chain.doFilter(request, response);
			return;
		}

		Authentication authentication = getUsernamePasswordAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}

	private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
		String IdToken = request.getHeader(PropertiesEnum.HEADER_STRING_ID_TOKEN.toString())
				.replace(PropertiesEnum.TOKEN_PREFIX.toString(), "");
		String accessToken = request.getHeader(PropertiesEnum.HEADER_STRING_ACCESS_TOKEN.toString());
		String refreshToken = request.getHeader(PropertiesEnum.HEADER_STRING_REFRESH_TOKEN.toString());

		if (accessToken != null) {

			userDataRequestScope.setAccessToken(accessToken);
			userDataRequestScope.setRequestedUri(request.getRequestURI()
					.substring(request.getRequestURI().lastIndexOf("/") + 1, request.getRequestURI().length()));
		}

		if (IdToken != null) {

			if (refreshToken != null) {

				String userNameLoadFromIdToken = JWT
						.require(HMAC512(PropertiesEnum.JWT_SECRET_TO_ENCRYPT_DECRYPT.toString().getBytes())).build()
						.verify(IdToken).getSubject();
				String userNameLoadFromRefreshToken = JWT
						.require(HMAC512(PropertiesEnum.JWT_SECRET_TO_ENCRYPT_DECRYPT.toString().getBytes())).build()
						.verify(refreshToken).getSubject();

				if (userNameLoadFromIdToken.equals(userNameLoadFromRefreshToken)) {
					User userLoadByIdTokenSubject = userRepository.findByUsername(userNameLoadFromIdToken);

					if (userLoadByIdTokenSubject.getRefreshToken() == null) {
						return null;
					} else {
						userDataRequestScope.setUsername(userLoadByIdTokenSubject.getUsername());
						boolean requestedURIAvailability = requestedURIValidate();

						if (userNameLoadFromIdToken != null && requestedURIAvailability) {

							User user = userRepository.findByUsername(userNameLoadFromIdToken);
							UserPrincipal principal = new UserPrincipal(user);
							UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
									userNameLoadFromIdToken, null, principal.getAuthorities());
							jsonWebTokenGenerator.jwtRefreshTokenGenerate(principal);
							return auth;
						}

						return null;
					}
				}
			}
		}
		return null;

	}

	public boolean requestedURIValidate() {
		Claim claim = JWT.require(HMAC512(PropertiesEnum.JWT_SECRET_TO_ENCRYPT_DECRYPT.toString().getBytes())).build()
				.verify(userDataRequestScope.getAccessToken()).getClaim("ALLOWED_RESOURCES");
		ArrayList<String> claimList = new ArrayList<>(claim.asList(String.class));

		for (String string : claimList) {
			if (string.equals(userDataRequestScope.getRequestedUri())) {
				return true;
			}
		}
		return false;

	}

}
