package com.unitedvision.sangihe.ehrm.simpeg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "riwayat_pangkat")
public class RiwayatPangkat extends Riwayat {

	private Pangkat pangkat;
	
	public RiwayatPangkat() {
		super();
	}
	
	public RiwayatPangkat(Detail detail) {
		super(detail);
	}

	@Column(name = "pangkat", nullable = false)
	public Pangkat getPangkat() {
		return pangkat;
	}

	public void setPangkat(Pangkat pangkat) {
		this.pangkat = pangkat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((pangkat == null) ? 0 : pangkat.hashCode());
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
		RiwayatPangkat other = (RiwayatPangkat) obj;
		if (pangkat != other.pangkat)
			return false;
		return true;
	}
}
