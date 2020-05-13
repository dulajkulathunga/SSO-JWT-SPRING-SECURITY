package com.sso.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sso.jwt.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	 User findByUsername(String username);
	 
	 @Modifying
	 @Query("update User u set u.refreshToken =:refreshToken where u.username =:username")
	 void updateRefreshToken(String refreshToken, String username);

}
