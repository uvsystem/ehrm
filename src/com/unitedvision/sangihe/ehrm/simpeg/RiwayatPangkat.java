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

	@Column(name = "pangkat", nullable = false)
	public Pangkat getPangkat() {
		return pangkat;
	}

	public void setPangkat(Pangkat pangkat) {
		this.pangkat = pangkat;
	}
}
