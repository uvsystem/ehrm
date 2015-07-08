package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

@MappedSuperclass
public abstract class Absen {

	private long id;
	private Kalendar kalendar;
	private Pegawai pegawai;
	
	public Absen() {
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
	@JoinColumn(name = "kalendar", nullable = false)
	public Kalendar getKalendar() {
		return kalendar;
	}

	public void setKalendar(Kalendar kalendar) {
		this.kalendar = kalendar;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pegawai", nullable = false)
	public Pegawai getPegawai() {
		return pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}
}
