package com.unitedvision.sangihe.ehrm.sppd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RekapSppd {

	private String nip;
	private String nama;
	private String namaUnitKerja;
	
	private Integer jumlahSppd;
	private Integer jumlahTugasLuar;
	
	public RekapSppd() {
		super();
	}

	@Id
	@Column(name = "nip")
	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "unit_kerja")
	public String getNamaUnitKerja() {
		return namaUnitKerja;
	}

	public void setNamaUnitKerja(String namaUnitKerja) {
		this.namaUnitKerja = namaUnitKerja;
	}

	@Column(name = "jumlah")
	public Integer getJumlahSppd() {
		return jumlahSppd;
	}

	public void setJumlahSppd(Integer jumlahSppd) {
		this.jumlahSppd = jumlahSppd;
	}

	@Column(name = "jumlah_hari")
	public Integer getJumlahTugasLuar() {
		return jumlahTugasLuar;
	}

	public void setJumlahTugasLuar(Integer jumlahTugasLuar) {
		this.jumlahTugasLuar = jumlahTugasLuar;
	}

	@Override
	public String toString() {
		return "RekapSppd [nip=" + nip + ", nama=" + nama + ", namaUnitKerja="
				+ namaUnitKerja + ", jumlahSppd=" + jumlahSppd
				+ ", jumlahTugasLuar=" + jumlahTugasLuar + "]";
	}
}
