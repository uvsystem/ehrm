package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("CUTI")
public class Cuti extends Absen {
	
	private String jenisCuti;

	@Column(name = "keterangan")
	public String getJenisCuti() {
		return jenisCuti;
	}

	public void setJenisCuti(String jenisCuti) {
		this.jenisCuti = jenisCuti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((jenisCuti == null) ? 0 : jenisCuti.hashCode());
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
		Cuti other = (Cuti) obj;
		if (jenisCuti == null) {
			if (other.jenisCuti != null)
				return false;
		} else if (!jenisCuti.equals(other.jenisCuti))
			return false;
		return true;
	}

	@Override
	@Transient
	public String getTipe() {
		return "CUTI";
	}

}
