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

	public Kalendar(Date tanggal) {
		super();
		this.tanggal = tanggal;
	}

	@Id
	@Column(name = "tanggal")
	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tanggal == null) ? 0 : tanggal.hashCode());
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
		Kalendar other = (Kalendar) obj;
		if (tanggal == null) {
			if (other.tanggal != null)
				return false;
		} else if (!tanggal.equals(other.tanggal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Kalendar [tanggal=" + tanggal + "]";
	}

}
