package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("SAKIT")
public class Sakit extends Absen {
	
	private String penyakit;

	@Column(name = "keterangan")
	public String getPenyakit() {
		return penyakit;
	}

	public void setPenyakit(String penyakit) {
		this.penyakit = penyakit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((penyakit == null) ? 0 : penyakit.hashCode());
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
		Sakit other = (Sakit) obj;
		if (penyakit == null) {
			if (other.penyakit != null)
				return false;
		} else if (!penyakit.equals(other.penyakit))
			return false;
		return true;
	}

	@Override
	@Transient
	public String getTipe() {
		return "SAKIT";
	}

}
