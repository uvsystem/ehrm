package com.unitedvision.sangihe.ehrm.sppd;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

@Entity
@Table(name = "pemegang_tugas")
public class PemegangTugas {

	private long id;
	private SuratTugas suratTugas;
	private Pegawai pegawai;
	
	public PemegangTugas() {
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "surat_tugas", nullable = false)
	public SuratTugas getSuratTugas() {
		return suratTugas;
	}
	
	public void setSuratTugas(SuratTugas suratTugas) {
		this.suratTugas = suratTugas;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pegawai")
	public Pegawai getPegawai() {
		return pegawai;
	}
	
	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

}
