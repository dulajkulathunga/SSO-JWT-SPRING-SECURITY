package com.sso.jwt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sso.jwt.model.User;
import com.sso.jwt.repository.UserRepo;
import com.sso.jwt.scope.UserDataRequestScope;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "api")
@Slf4j
public class DhashBoard {

	@Autowired
	UserDataRequestScope userDataRequestScope;
	@Autowired
	UserRepo userRepo;

	@GetMapping(value = "/test")
	public static String test() {
		return "test is okay !";
	}

	@GetMapping(value = "/unauth")
	public static String unauth() {

		return "Access Denied !";
	}

	@GetMapping(value = "/prof")
	public static String prof() {

		return "i am dulaj";
	}

	@GetMapping(value = "/prof1")
	public static String prof1() {

		return "i am udayanga";
	}

	@GetMapping(value = "/prof2")
	public String prof2() {

	 

		return "i am bandara";
	}

	@GetMapping(value = "/prof3")
	public String prof3() {
		 

		return "Hello im good !";
	}

	@GetMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		User user = userRepo.findByUsername(userDataRequestScope.getUsername());
		user.setRefreshToken(null);
		userRepo.save(user);
		return "ok Log Out";
	}
}
