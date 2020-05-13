package com.sso.jwt.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority_provider")
public class AuthorityProvider {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String endPoint;
	private String role;

	public AuthorityProvider() {
	}

	public AuthorityProvider(String endPoint, String role) {
		super();
		this.endPoint = endPoint;
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public String getEndPoint() {
		return endPoint;
	}

	@Override
	public String toString() {
		return role;
	}

	public String getCount() {
		return role;
	}

}
