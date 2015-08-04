package com.unitedvision.sangihe.ehrm.security;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.unitedvision.sangihe.ehrm.OutOfDateEntityException;
import com.unitedvision.sangihe.ehrm.UnauthenticatedAccessException;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private CustomUserDetailsService userDetailService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        CustomUser user;
        if (username.equals("superuser")) {
        	user = userDetailService.loadAdmin(username, password);

        } else {
    		try {
    			user = userDetailService.loadUserByToken(password);
    		} catch (PersistenceException e) {
    			user = userDetailService.loadUserByUsername(username);
    		} catch (UnauthenticatedAccessException | OutOfDateEntityException e) {
                throw new BadCredentialsException(e.getMessage());
			}
        }
 
       	System.out.println(String.format("CustomAuthenticationProvide.java: Generated User: %s:%s", user.getUsername(), user.getPassword())); // LOG
       	System.out.println(String.format("CustomAuthenticationProvide.java: Requested User: %s:%s", username, password)); // LOG
        if (!(username.equals(user.getUsername())) || !(password.equals(user.getPassword())))
            throw new BadCredentialsException("Kombinasi Username dan Password Salah");
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
	
}
