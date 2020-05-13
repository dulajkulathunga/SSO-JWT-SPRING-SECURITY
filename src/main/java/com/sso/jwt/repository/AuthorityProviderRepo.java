package com.sso.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sso.jwt.model.AuthorityProvider;

@Repository
public interface AuthorityProviderRepo extends JpaRepository<AuthorityProvider, Long> {
	List<AuthorityProvider> findByEndPoint(String endPoint);
	List<AuthorityProvider> findByRole(String role);	 
}
