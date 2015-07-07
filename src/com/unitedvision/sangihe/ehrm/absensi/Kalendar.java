package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kalendar")
public class Kalendar {

	private Date tanggal;

	public Kalendar() {
		super();
	}

	@Id
	@Column(name = "tanggal")
	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

}
