package com.sso.jwt.utility;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.sso.jwt.constant.PropertiesEnum;
import com.sso.jwt.model.User;
import com.sso.jwt.repository.UserRepo;
import com.sso.jwt.security.UserPrincipal;

@Service
public class JsonWebTokenGenerator {

	private PermissionRelated permissionRelated;
	private UserRepo userRepository;

	public JsonWebTokenGenerator(UserRepo userRepository, PermissionRelated permissionRelated) {

		this.userRepository = userRepository;
		this.permissionRelated = permissionRelated;
	}

	public String jwtAccessTokenGenerate(UserPrincipal principal) {

		User loadUser = userRepository.findByUsername(principal.getUsername());

		String accessToken = JWT.create().withSubject(principal.getUsername())
				.withArrayClaim("ALLOWED_RESOURCES", permissionRelated.getEndPoints(loadUser.getRoles()))
				.withExpiresAt(new Date(System.currentTimeMillis()
						+ Integer.parseInt(PropertiesEnum.EXPIRATION_TIME_ACCESS_TOKEN.toString())))
				.sign(HMAC512(PropertiesEnum.JWT_SECRET_TO_ENCRYPT_DECRYPT.toString().getBytes()));

		return accessToken;

	}

	public String jwtRefreshTokenGenerate(UserPrincipal principal) {

		User loadUser = userRepository.findByUsername(principal.getUsername());

		String refreshToken = JWT.create().withSubject(principal.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()
						+ Integer.parseInt(PropertiesEnum.EXPIRATION_TIME_REFRESH_TOKEN.toString())))
				.sign(HMAC512(PropertiesEnum.JWT_SECRET_TO_ENCRYPT_DECRYPT.toString().getBytes()));

		loadUser.setRefreshToken(refreshToken);
		userRepository.save(loadUser);

		return refreshToken;

	}

	public String jwtIdTokenGenerate(UserPrincipal principal) {

		String IdToken = JWT.create().withSubject(principal.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()
						+ Integer.parseInt(PropertiesEnum.EXPIRATION_TIME_ID_TOKEN.toString())))
				.sign(HMAC512(PropertiesEnum.JWT_SECRET_TO_ENCRYPT_DECRYPT.toString().getBytes()));

		return IdToken;

	}

}
