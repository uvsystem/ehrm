package com.unitedvision.sangihe.ehrm.manajemen;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

import java.sql.Date;
import java.util.List;

/**
 * The persistent class for the token database table.
 * 
 * Deddy Christoper Kakunsi
 * 
 * deddy.kakunsi@gmail.com
 */
@Entity
@Table(name = "token")
public class Token implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String token;
	private StatusToken status;
	private Date tanggalBuat;
	private Date tanggalExpire;
	
	private Pegawai pegawai;

	public enum StatusToken {
		AKTIF,
		LOCKED
	}
	
	public Token() {
		super();
		setTanggalBuat(DateUtil.getDate());
		generateExpireDate();
		setStatus(StatusToken.AKTIF);
	}
	
	@Id
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "status")
	public StatusToken getStatus() {
		return this.status;
	}

	public void setStatus(StatusToken status) {
		this.status = status;
	}

	@JsonIgnore
	@Column(name = "tanggal_buat")
	public Date getTanggalBuat() {
		return this.tanggalBuat;
	}

	public void setTanggalBuat(Date tanggalBuat) {
		this.tanggalBuat = tanggalBuat;
	}
	
	@Transient
	public String getTanggalStr() {
		return DateUtil.toFormattedStringDate(tanggalBuat, "/");
	}
	
	public void setTanggalStr(String tanggalStr) { }


	@JsonIgnore
	@Column(name = "tanggal_expire")
	public Date getTanggalExpire() {
		return this.tanggalExpire;
	}

	public void setTanggalExpire(Date tanggalExpire) {
		this.tanggalExpire = tanggalExpire;
	}
	
	@Transient
	public String getExpireStr() {
		return DateUtil.toFormattedStringDate(tanggalExpire, "/");
	}

	public void setExpireDate(Date expire) { }


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pegawai", nullable = false)
	public Pegawai getpegawai() {
		return this.pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	public Date generateExpireDate() {
		return generateExpireDate(tanggalBuat);
	}

	public Date generateExpireDate(Date date) {
		setTanggalExpire(DateUtil.add(date, 2));
		
		return tanggalExpire;
	}
	
	public String generateToken() {
		token = String.format("%d%d", pegawai.hashCode(), tanggalBuat.hashCode());
		
		return token;
	}
	
	public Token extend() {
		if (isRenewable())
			generateExpireDate(DateUtil.getDate());
		
		return this;
	}

	@Transient
	public boolean isRenewable() {
		Date hariIni = DateUtil.getDate();
		Date besok = DateUtil.add(hariIni, 1);

		return DateUtil.equals(tanggalExpire, besok);
	}

	@JsonIgnore
	@Transient
	public List<Operator> getDaftarOperator() {
		return pegawai.getDaftarOperator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pegawai == null) ? 0 : pegawai.hashCode());
		result = prime * result
				+ ((tanggalBuat == null) ? 0 : tanggalBuat.hashCode());
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
		Token other = (Token) obj;
		if (pegawai == null) {
			if (other.pegawai != null)
				return false;
		} else if (!pegawai.equals(other.pegawai))
			return false;
		if (status != other.status)
			return false;
		if (tanggalBuat == null) {
			if (other.tanggalBuat != null)
				return false;
		} else if (!tanggalBuat.equals(other.tanggalBuat))
			return false;
		if (tanggalExpire == null) {
			if (other.tanggalExpire != null)
				return false;
		} else if (!tanggalExpire.equals(other.tanggalExpire))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
	
}