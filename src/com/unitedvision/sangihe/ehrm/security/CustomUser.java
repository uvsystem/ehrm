package com.unitedvision.sangihe.ehrm.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.unitedvision.sangihe.ehrm.manajemen.Operator;

public class CustomUser extends User {
	private static final long serialVersionUID = 1L;
	private Operator operator;

	public CustomUser(String username, String password, Operator operator, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.operator = operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public Operator getOperator() {
		return operator;
	}
}
