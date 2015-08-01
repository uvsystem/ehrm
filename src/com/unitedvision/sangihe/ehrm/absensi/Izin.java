package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("IZIN")
public class Izin extends Absen {
	
	private String alasan;

	@Column(name = "keterangan")
	public String getAlasan() {
		return alasan;
	}

	public void setAlasan(String alasan) {
		this.alasan = alasan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((alasan == null) ? 0 : alasan.hashCode());
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
		Izin other = (Izin) obj;
		if (alasan == null) {
			if (other.alasan != null)
				return false;
		} else if (!alasan.equals(other.alasan))
			return false;
		return true;
	}

	@Override
	@Transient
	public String getTipe() {
		return "IZIN";
	}

}
