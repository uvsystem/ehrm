package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;

@MappedSuperclass
public abstract class Riwayat {

	private long id;
	private String nomorSk;
	private Pegawai pegawai;
	private Date tanggalMulai;
	private Date tanggalSelesai;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Nomor Surat Pembuktian
	 * @return
	 */
	@Column(name = "nomor_sk")
	public String getNomorSk() {
		return nomorSk;
	}

	public void setNomorSk(String nomorSk) {
		this.nomorSk = nomorSk;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pegawai")
	public Pegawai getPegawai() {
		return pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	@JsonIgnore
	@Column(name = "tanggal_mulai")
	public Date getTanggalMulai() {
		return tanggalMulai;
	}

	public void setTanggalMulai(Date tanggalMulai) {
		this.tanggalMulai = tanggalMulai;
	}

	@Transient
	public String getTanggalMulaiStr() {
		return DateUtil.toFormattedStringDate(tanggalMulai, "-");
	}
	
	@JsonIgnore
	@Column(name = "tanggal_selesai")
	public Date getTanggalSelesai() {
		return tanggalSelesai;
	}
	
	public void setTanggalSelesai(Date tanggalSelesai) {
		this.tanggalSelesai = tanggalSelesai;
	}

	@Transient
	public String getTanggalSelesaiStr() {
		return DateUtil.toFormattedStringDate(tanggalSelesai, "-");
	}

	@Transient
	public boolean isCurrent() {
		if (tanggalSelesai == null)
			return false;
		return false;
	}
	
}
