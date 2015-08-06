package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RekapAbsen {

	private String nip;
	private String nama;
	private String namaUnitKerja;

	private Long hadir;
	private Long tugasLuar;
	private Long sakit;
	private Long izin;
	private Long cuti;

	private Long terlambat;
	private Long pulang;
	
	private Long jumlahHari;
	
	@Column(name = "nip")
	@Id
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
	
	@Column(name = "nama_unit_kerja")
	public String getNamaUnitKerja() {
		return namaUnitKerja;
	}

	public void setNamaUnitKerja(String namaUnitKerja) {
		this.namaUnitKerja = namaUnitKerja;
	}

	@Column(name = "hadir")
	public Long getHadir() {
		return hadir;
	}

	public void setHadir(Long hadir) {
		this.hadir = hadir;
	}
	
	@Column(name = "tugas_luar")
	public Long getTugasLuar() {
		return tugasLuar;
	}

	public void setTugasLuar(Long tugasLuar) {
		this.tugasLuar = tugasLuar;
	}

	@Column(name = "sakit")
	public Long getSakit() {
		return sakit;
	}
	
	public void setSakit(Long sakit) {
		this.sakit = sakit;
	}
	
	@Column(name = "izin")
	public Long getIzin() {
		return izin;
	}
	
	public void setIzin(Long izin) {
		this.izin = izin;
	}
	
	@Column(name = "cuti")
	public Long getCuti() {
		return cuti;
	}
	
	public void setCuti(Long cuti) {
		this.cuti = cuti;
	}

	@Column(name = "terlambat")
	public Long getTerlambat() {
		return terlambat;
	}
	
	public void setTerlambat(Long terlambat) {
		this.terlambat = terlambat;
	}
	
	@Column(name = "pulang")
	public Long getPulang() {
		return pulang;
	}

	public void setPulang(Long pulang) {
		this.pulang = pulang;
	}
	
	@Column(name = "jumlah_hari")
	public Long getJumlahHari() {
		return jumlahHari;
	}

	public void setJumlahHari(Long jumlahHari) {
		this.jumlahHari = jumlahHari;
	}
	
	@Transient
	public Float getPresentase() {
		if (hadir == 0)
			return 0F;
		return ((float)hadir / (float)jumlahHari) * 100;
	}

	@Override
	public String toString() {
		return "RekapPegawai [" 
				+ "nip=" + nip + ", nama=" + nama + ", jumlah hari=" + jumlahHari
				+ ", unit kerja=" + namaUnitKerja
				+ ", hadir=" + hadir + ", terlambat=" + terlambat 
				+ ", pulang=" + pulang + ", sakit=" + sakit + ", izin=" 
				+ izin + ", cuti=" + cuti + ", presentase=" + getPresentase()
				+ "]";
	}
}
