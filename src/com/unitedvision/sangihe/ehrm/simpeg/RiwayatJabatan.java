package com.unitedvision.sangihe.ehrm.simpeg;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "riwayat_jabatan")
public class RiwayatJabatan extends Riwayat {

	private Jabatan jabatan;
	
	public RiwayatJabatan() {
		super();
	}
	
	public RiwayatJabatan(Detail detail) {
		super(detail);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "jabatan", nullable = false)
	public Jabatan getJabatan() {
		return jabatan;
	}

	public void setJabatan(Jabatan jabatan) {
		this.jabatan = jabatan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((jabatan == null) ? 0 : jabatan.hashCode());
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
		RiwayatJabatan other = (RiwayatJabatan) obj;
		if (jabatan == null) {
			if (other.jabatan != null)
				return false;
		} else if (!jabatan.equals(other.jabatan))
			return false;
		return true;
	}
}
