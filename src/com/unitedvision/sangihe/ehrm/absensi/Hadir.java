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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((pagi == null) ? 0 : pagi.hashCode());
		result = prime * result
				+ ((pengecekanKedua == null) ? 0 : pengecekanKedua.hashCode());
		result = prime
				* result
				+ ((pengecekanPertama == null) ? 0 : pengecekanPertama
						.hashCode());
		result = prime * result + ((sore == null) ? 0 : sore.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hadir other = (Hadir) obj;
		if (pagi == null) {
			if (other.pagi != null)
				return false;
		} else if (!pagi.equals(other.pagi))
			return false;
		if (pengecekanKedua == null) {
			if (other.pengecekanKedua != null)
				return false;
		} else if (!pengecekanKedua.equals(other.pengecekanKedua))
			return false;
		if (pengecekanPertama == null) {
			if (other.pengecekanPertama != null)
				return false;
		} else if (!pengecekanPertama.equals(other.pengecekanPertama))
			return false;
		if (sore == null) {
			if (other.sore != null)
				return false;
		} else if (!sore.equals(other.sore))
			return false;
		return true;
	}

}
