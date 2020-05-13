package com.sso.jwt.utility;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sso.jwt.model.AuthorityProvider;
import com.sso.jwt.model.User;
import com.sso.jwt.repository.AuthorityProviderRepo;
import com.sso.jwt.repository.UserRepo;
@Service
public class DatabseInitializer implements CommandLineRunner {

	 private UserRepo userRepository;
	    private PasswordEncoder passwordEncoder;
	    private   AuthorityProviderRepo authorityProviderRepo;

	    public DatabseInitializer(UserRepo userRepository, PasswordEncoder passwordEncoder,AuthorityProviderRepo authorityProviderRepo) {
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	        this.authorityProviderRepo=authorityProviderRepo;
	    }

	    
	    @Override
	    public void run(String... args) {
	 
	        this.userRepository.deleteAll();
	        this.authorityProviderRepo.deleteAll();

	        
	        User dan = new User("dulaj",passwordEncoder.encode("dulaj123"),"USER","USER","refresh_token");
	        User admin = new User("admin",passwordEncoder.encode("admin123"),"ADMIN","ADMIN","refresh_token");
	        User manager = new User("manager",passwordEncoder.encode("manager123"),"MANAGER","MANAGER","refresh_token");
	 
	        
	        AuthorityProvider access1=new AuthorityProvider("prof1","ADMIN");
	        AuthorityProvider access2=new AuthorityProvider("prof2","ADMIN");
	        AuthorityProvider access3=new AuthorityProvider("prof3","MANAGER");
	        AuthorityProvider access4=new AuthorityProvider("prof2","MANAGER");
	        AuthorityProvider access5=new AuthorityProvider("logout","MANAGER");
	        AuthorityProvider access6=new AuthorityProvider("logout","ADMIN");
	        AuthorityProvider access7=new AuthorityProvider("prof2","USER");
	        List<User> users = Arrays.asList(dan,admin,manager);
	         List<AuthorityProvider> authorityProviders = Arrays.asList(access1,access2,access3,access4,access5,access6,access7);
	 
	        this.userRepository.saveAll(users);
	        this.authorityProviderRepo.saveAll(authorityProviders);
	    }

}
