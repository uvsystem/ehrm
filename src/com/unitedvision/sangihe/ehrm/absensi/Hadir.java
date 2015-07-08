package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "absen")
public class Hadir extends Absen {

	private Time pagi;
	private Time pengecekanPertama;
	private Time pengecekanKedua;
	private Time sore;
	
	public Hadir() {
		super();
	}

	@Column(name = "pagi")
	public Time getPagi() {
		return pagi;
	}

	public void setPagi(Time pagi) {
		this.pagi = pagi;
	}

	@Column(name = "pengecekan_satu")
	public Time getPengecekanPertama() {
		return pengecekanPertama;
	}

	public void setPengecekanPertama(Time pengecekanPertama) {
		this.pengecekanPertama = pengecekanPertama;
	}

	@Column(name = "pengecekan_dua")
	public Time getPengecekanKedua() {
		return pengecekanKedua;
	}

	public void setPengecekanKedua(Time pengecekanKedua) {
		this.pengecekanKedua = pengecekanKedua;
	}

	@Column(name = "sore")
	public Time getSore() {
		return sore;
	}

	public void setSore(Time sore) {
		this.sore = sore;
	}

}
