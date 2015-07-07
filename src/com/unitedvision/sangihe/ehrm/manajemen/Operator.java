package com.unitedvision.sangihe.ehrm.manajemen;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public class Operator {

	public enum Role {
		ADMIN,
		PEGAWAI
	}
	
	private long id;
	private Aplikasi aplikasi;
	private Pegawai pegawai;
	private Role role;

	public Operator() {
		super();
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "aplikasi")
	public Aplikasi getAplikasi() {
		return aplikasi;
	}

	public void setAplikasi(Aplikasi aplikasi) {
		this.aplikasi = aplikasi;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pegawai")
	public Pegawai getPegawai() {
		return pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	@Column(name = "role")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Transient
	public String getUsername() {
		return pegawai.getNip();
	}
	
	@JsonIgnore
	@Transient
	public String getPassword() {
		return pegawai.getPassword();
	}
}
