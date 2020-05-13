package com.sso.jwt.scope;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Data;
 
@RequestScope
@Component
@Data
public class UserDataRequestScope {
	
	private String username;
	private String userRole;
	private String accessToken;
	private String refreshToken;
	private String requestedUri;
	
	
	

}
