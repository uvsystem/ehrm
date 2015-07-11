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

	public enum Status {
		PENDING,
		DITERIMA,
		DITOLAK
	}
	
	private long id;
	private Date tanggal;
	private String nomor;
	private int jumlahHari;
	private Status status;
	
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

	@Column(name = "status", nullable = false)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "suratTugas", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<PemegangTugas> getDaftarPemegangTugas() {
		return daftarPemegangTugas;
	}

	public void setDaftarPemegangTugas(List<PemegangTugas> daftarPemegangTugas) {
		this.daftarPemegangTugas = daftarPemegangTugas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((daftarPemegangTugas == null) ? 0 : daftarPemegangTugas
						.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + jumlahHari;
		result = prime * result + ((maksud == null) ? 0 : maksud.hashCode());
		result = prime * result + ((nomor == null) ? 0 : nomor.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tanggal == null) ? 0 : tanggal.hashCode());
		result = prime * result + ((tujuan == null) ? 0 : tujuan.hashCode());
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
		SuratTugas other = (SuratTugas) obj;
		if (daftarPemegangTugas == null) {
			if (other.daftarPemegangTugas != null)
				return false;
		} else if (!daftarPemegangTugas.equals(other.daftarPemegangTugas))
			return false;
		if (id != other.id)
			return false;
		if (jumlahHari != other.jumlahHari)
			return false;
		if (maksud == null) {
			if (other.maksud != null)
				return false;
		} else if (!maksud.equals(other.maksud))
			return false;
		if (nomor == null) {
			if (other.nomor != null)
				return false;
		} else if (!nomor.equals(other.nomor))
			return false;
		if (status != other.status)
			return false;
		if (tanggal == null) {
			if (other.tanggal != null)
				return false;
		} else if (!tanggal.equals(other.tanggal))
			return false;
		if (tujuan == null) {
			if (other.tujuan != null)
				return false;
		} else if (!tujuan.equals(other.tujuan))
			return false;
		return true;
	}

}
