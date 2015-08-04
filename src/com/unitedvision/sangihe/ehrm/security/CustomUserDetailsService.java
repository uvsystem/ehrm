package com.unitedvision.sangihe.ehrm.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unitedvision.sangihe.ehrm.ApplicationConfig;
import com.unitedvision.sangihe.ehrm.OutOfDateEntityException;
import com.unitedvision.sangihe.ehrm.UnauthenticatedAccessException;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;
import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.manajemen.Token;
import com.unitedvision.sangihe.ehrm.manajemen.TokenService;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

/**
 * Custom Authentication Provider.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Service("authService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private TokenService tokenService;
	
	public CustomUser loadAdmin(String username, String password) throws UsernameNotFoundException {
		if (!password.equals("********"))
			throw new UsernameNotFoundException("Username atau Password salah");
		
		return new CustomUser(username, password, null, getAuthorities(Role.ADMIN));
	}

	@Override
	public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Pegawai pegawai = tokenService.login(username);
			Operator operator = getOperator(pegawai);
			
			return new CustomUser(operator.getUsername(), operator.getPassword(), pegawai, getAuthorities(operator.getRole()));
		} catch (PersistenceException | UnauthenticatedAccessException e) {
			throw new UsernameNotFoundException("Username atau password salah");
		}
	}
	
	public CustomUser loadUserByToken(String tokenString) throws UnauthenticatedAccessException, OutOfDateEntityException {
		
		if (tokenString.equals("********"))
			return new CustomUser("superuser", "********", null, getAuthorities(Role.ADMIN));
		
		Token token = tokenService.get(tokenString);
		Operator operator = getOperator(token.getpegawai());
		
		return new CustomUser(operator.getUsername(), token.getToken(), operator.getPegawai(), getAuthorities(operator.getRole()));
	}

	public static List<GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authList = new ArrayList<>();

		if (role.equals(Role.ADMIN)) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authList.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
		} else if (role.equals(Role.OPERATOR)) {
			authList.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
		}

		return authList;
	}
	
	private Operator getOperator(Pegawai pegawai) throws UnauthenticatedAccessException {
		List<Operator> daftarOperator = pegawai.getDaftarOperator();

		for (Operator operator : daftarOperator) {
			if (operator.getAplikasi().getKode().equals(ApplicationConfig.KODE_APLIKASI))
				return operator;
		}
		
		throw new UnauthenticatedAccessException("Aplikasi Kepegawaian tidak bisa anda akses");
	}
}
