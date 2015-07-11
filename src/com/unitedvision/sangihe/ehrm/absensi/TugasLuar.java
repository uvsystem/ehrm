package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.unitedvision.sangihe.ehrm.sppd.Sppd;

@Entity
@Table(name = "absen")
public class TugasLuar extends Absen {

	private Sppd sppd;
	
	public TugasLuar() {
		super();
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sppd", nullable = false)
	public Sppd getSppd() {
		return sppd;
	}

	public void setSppd(Sppd sppd) {
		this.sppd = sppd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sppd == null) ? 0 : sppd.hashCode());
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
		TugasLuar other = (TugasLuar) obj;
		if (sppd == null) {
			if (other.sppd != null)
				return false;
		} else if (!sppd.equals(other.sppd))
			return false;
		return true;
	}
}
