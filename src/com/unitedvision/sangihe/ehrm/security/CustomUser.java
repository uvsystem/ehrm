package com.unitedvision.sangihe.ehrm.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public class CustomUser extends User {
	private static final long serialVersionUID = 1L;
	private Pegawai pegawai;

	public CustomUser(String username, String password, Pegawai pegawai, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.pegawai = pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}
	
	public Pegawai getPegawai() {
		return pegawai;
	}
}
