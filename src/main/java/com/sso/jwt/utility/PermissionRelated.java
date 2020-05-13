package com.sso.jwt.utility;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import com.sso.jwt.model.AuthorityProvider;
import com.sso.jwt.repository.AuthorityProviderRepo;

 
@Service

public class PermissionRelated {
	 private   AuthorityProviderRepo authorityProviderRepo;
	 
	
public PermissionRelated(AuthorityProviderRepo authorityProviderRepo) {
	this.authorityProviderRepo=authorityProviderRepo;
	 
}
	 
	public   String [] getAuthorizedRoles(String param) {
		 
   	 List<AuthorityProvider> hh=authorityProviderRepo.findByEndPoint(param);
        String[] array = new String[hh.size()];
        int i = 0;
        for (Iterator<AuthorityProvider> iterator = hh.iterator(); iterator.hasNext(); i++) {
            array[i] = iterator.next().getRole();
        }
   	return array;
	}
	public   String [] getEndPoints(String param) {
		System.out.println("Role parser --"+param);
	   	 List<AuthorityProvider> hh=authorityProviderRepo.findByRole(param);
	        String[] array = new String[hh.size()];
	        int i = 0;
	        for (Iterator<AuthorityProvider> iterator = hh.iterator(); iterator.hasNext(); i++) {
	            array[i] = iterator.next().getEndPoint();
	        }
	   	return array;
		}
	 
	}
