package com.unitedvision.sangihe.ehrm.manajemen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

@Entity
@Table(name = "operator")
public class Operator {

	public enum Role {
		ADMIN,
		OPERATOR
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

	@JsonBackReference("aplikasiReference")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "aplikasi")
	public Aplikasi getAplikasi() {
		return aplikasi;
	}

	public void setAplikasi(Aplikasi aplikasi) {
		this.aplikasi = aplikasi;
	}

	@JsonBackReference("pegawaiReference")
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
		return getNip();
	}

	@Transient
	public String getNip() {
		return pegawai.getNip();
	}

	@Transient
	public String getNama() {
		return pegawai.getNama();
	}
	
	@JsonIgnore
	@Transient
	public String getPassword() {
		return pegawai.getPassword();
	}
	
	@Transient
	public String getKodeAplikasi() {
		return aplikasi.getKode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((aplikasi == null) ? 0 : aplikasi.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((pegawai == null) ? 0 : pegawai.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operator other = (Operator) obj;
		if (aplikasi == null) {
			if (other.aplikasi != null)
				return false;
		} else if (!aplikasi.equals(other.aplikasi))
			return false;
		if (id != other.id)
			return false;
		if (pegawai == null) {
			if (other.pegawai != null)
				return false;
		} else if (!pegawai.equals(other.pegawai))
			return false;
		if (role != other.role)
			return false;
		return true;
	}
}
