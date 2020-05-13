package com.sso.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextListener;


@SpringBootApplication
@ComponentScan(basePackages = {"com.sso.jwt"})
public class SsoJwtSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoJwtSpringSecurityApplication.class, args);
	}
 
}
