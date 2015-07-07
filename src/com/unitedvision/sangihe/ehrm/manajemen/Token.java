package com.unitedvision.sangihe.ehrm.manajemen;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

import java.sql.Date;

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
	
	private Pegawai pegawai;;

	public enum StatusToken {
		AKTIF,
		LOCKED
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

	public void setOperator(Pegawai pegawai) {
		this.pegawai = pegawai;
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

	public Date generateExpireDate() {
		return generateExpireDate(tanggalBuat);
	}

	public Date generateExpireDate(Date date) {
		int day = DateUtil.getDay(date);
		int month = DateUtil.getMonthInt(date);
		int year = DateUtil.getYear(date);
		
		tanggalExpire = DateUtil.getDate(year, month, (day + 2));
		return tanggalExpire;
	}
	
	public String generateToken() {
		String username = pegawai.getNip();
		String code = DateUtil.codedString(tanggalBuat);
		
		token = String.format("%s-%s", username, code);
		
		return token;
	}

	@Transient
	public boolean isRenewable() {
		Date today = DateUtil.getNow();
		int day = DateUtil.getDay(today);
		int month = DateUtil.getMonthInt(today);
		int year = DateUtil.getYear(today);

		Date tomorrow = DateUtil.getDate(year, month, (day + 1));

		return DateUtil.equals(tanggalExpire, tomorrow);
	}
}