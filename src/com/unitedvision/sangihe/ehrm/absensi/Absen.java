package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Absen {

	private long id;
	private Kalendar kalendar;
	
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
}
