package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
	
	@Temporal(TemporalType.DATE)
	@Column(name = "tanggal_mulai")
	public Date getTanggalMulai() {
		return tanggalMulai;
	}

	public void setTanggalMulai(Date tanggalMulai) {
		this.tanggalMulai = tanggalMulai;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "tanggal_selesai")
	public Date getTanggalSelesai() {
		return tanggalSelesai;
	}
	
	public void setTanggalSelesai(Date tanggalSelesai) {
		this.tanggalSelesai = tanggalSelesai;
	}

	@Transient
	public boolean isCurrent() {
		if (tanggalSelesai == null)
			return false;
		return false;
	}
	
}
