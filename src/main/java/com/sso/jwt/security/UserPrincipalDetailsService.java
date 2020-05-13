package com.sso.jwt.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sso.jwt.model.User;
import com.sso.jwt.repository.UserRepo;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	private UserRepo userRepository;

	public UserPrincipalDetailsService(UserRepo userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(s);
		UserPrincipal userPrincipal = new UserPrincipal(user);

		return userPrincipal;
	}

}
