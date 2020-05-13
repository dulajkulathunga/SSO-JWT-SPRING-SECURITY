package com.sso.jwt.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="\"user\"")
public class User {

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name ="UserName")
    private String username;

    @Column(name="Password")
    private String password;

    private String roles;

    private String authority;
    
    private String refreshToken;

    public User(String username, String password, String roles, String authority,String refreshToken){
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.authority = authority;
        this.refreshToken=refreshToken;
        
    }

    public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	protected User(){}

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    

    public String getRoles() {
        return roles;
    }

    public String getPermissions() {
        return authority;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.authority.length() > 0){
            return Arrays.asList(this.authority.split(","));
        }
        return new ArrayList<>();
    }
	
	
}
