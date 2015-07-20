package com.unitedvision.sangihe.ehrm.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.unitedvision.sangihe.ehrm.ApplicationException;
import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

/**
 * Class that provides authentication mechanism based on Spring's {@code Authentication} class.
 * @author Deddy Christoper Kakunsi
 *
 */
@Component
public class SpringAuthenticationBasedAuthorizationProvider extends AuthorizationProvider {
	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	private CustomUser getUserDetails() throws ApplicationException {
		Object principal = getAuthentication().getPrincipal();
		
		if (principal instanceof CustomUser)
			return (CustomUser) principal;
		
		throw new ApplicationException("Silahkan Login Terlebih Dahulu!");
	}

	/**
	 * Returns user's role depends on {@code Authentication}.<br />
	 * User's role was defined in {@code Role} class.
	 * @param authentication
	 * @return User's {@code Role}.
	 */
	public Role getUserRole(final Authentication authentication) {
		String authority = getAuthority(authentication);
		
		switch (authority) {
			case "ROLE_ADMIN": return Role.ADMIN;
			case "ROLE_OPERATOR": return Role.OPERATOR;
			default: return null;
		}
	}
	
	/**
	 * Returns user's authority depends on {@code Authentication}.<br />
	 * @param authentication
	 * @return User's {@code Role}.
	 */
	public String getAuthority(final Authentication authentication) {
		String authority = "ROLE_GUEST";
		
		for (GrantedAuthority ga : authentication.getAuthorities()) {
			authority = ga.getAuthority();
			break;
		}
		
		return authority;
	}

	/**
	 * Returns user's role in string format, based on {@code Authentication}.
	 * @param authentication
	 * @return user's role in string format
	 */
	public String getUserRoleStr(final Authentication authentication) {
		return getUserRoleStr(getUserRole(authentication));
	}
	
	/**
	 * Returns user in {@code Operator} type, based on {@code Authentication}.
	 * @param authentication
	 * @return user in {@code Operator} type.
	 * @throws ApplicationException 
	 */
	public Pegawai getPegawai() throws ApplicationException {
		return getUserDetails().getPegawai();
	}
}
