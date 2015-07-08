package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "surat_tugas")
public class SuratTugas {

	private long id;
	private Date tanggal;
	private String nomor;
	private int jumlahHari;
	
	/**
	 * Daerah Tujuan Tugas
	 */
	private String tujuan;
	private String maksud;
	
	private List<PemegangTugas> daftarPemegangTugas;
	
	public SuratTugas() {
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

	@Column(name = "nomor", nullable = false)
	public String getNomor() {
		return nomor;
	}

	public void setNomor(String nomor) {
		this.nomor = nomor;
	}

	@Column(name = "tanggal")
	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	@Column(name = "jumlah_hari", nullable = false)
	public int getJumlahHari() {
		return jumlahHari;
	}

	public void setJumlahHari(int jumlahHari) {
		this.jumlahHari = jumlahHari;
	}

	@Column(name = "tujuan", nullable = false)
	public String getTujuan() {
		return tujuan;
	}

	public void setTujuan(String tujuan) {
		this.tujuan = tujuan;
	}

	@Column(name = "maksud", nullable = false)
	public String getMaksud() {
		return maksud;
	}

	public void setMaksud(String maksud) {
		this.maksud = maksud;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "suratTugas", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<PemegangTugas> getDaftarPemegangTugas() {
		return daftarPemegangTugas;
	}

	public void setDaftarPemegangTugas(List<PemegangTugas> daftarPemegangTugas) {
		this.daftarPemegangTugas = daftarPemegangTugas;
	}

}
