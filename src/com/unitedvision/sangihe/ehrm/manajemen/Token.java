package com.unitedvision.sangihe.ehrm.manajemen;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.UnauthenticatedAccessException;
import com.unitedvision.sangihe.ehrm.duk.Penduduk;
import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;

import java.sql.Date;
import java.sql.Time;
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
	private Time time;
	
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
		time = DateUtil.getTime();
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
		return DateUtil.toStringDate(tanggalBuat, "-");
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
		return DateUtil.toStringDate(tanggalExpire, "-");
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
	
	public Token extend() throws UnauthenticatedAccessException {
		if (isRenewable()) {
			generateExpireDate(DateUtil.getDate());
			return this;
		}
		
		throw new UnauthenticatedAccessException();
	}

	@Transient
	public boolean isRenewable() {
		return tanggalExpire.after(DateUtil.getDate());
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
		result = prime * result
				+ ((time == null) ? 0 : time.hashCode());
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

	public static Token createAdmin() {
		Penduduk penduduk = new Penduduk();
		penduduk.setNama("ADMIN");
		penduduk.setTanggalLahir(DateUtil.getDate());
		penduduk.setId(0);
		penduduk.setNik("ADMIN");
		penduduk.setTelepon("082347643198");
		penduduk.setEmail("admin@sangihe.go.id");
		
		UnitKerja unitKerja = new UnitKerja();
		unitKerja.setId(0);
		unitKerja.setNama("ADMIN");
		unitKerja.setTipe(TipeUnitKerja.SEKRETARIAT);
		unitKerja.setSingkatan("ADMIN");
		
		Pegawai pegawai = new Pegawai();
		pegawai.setNip("superuser");
		pegawai.setUnitKerja(unitKerja);
		pegawai.setPenduduk(penduduk);

		Aplikasi aplikasi = new Aplikasi();
		aplikasi.setKode("ALL");
		
		Operator operator = new Operator();
		operator.setRole(Role.ADMIN);
		operator.setAplikasi(aplikasi);
		operator.setPegawai(pegawai);

		pegawai.addOperator(operator);

		Token token = new Token();
		token.setPegawai(null);
		token.setToken("********");
		token.setPegawai(pegawai);
		
		return token;
	}
	
}