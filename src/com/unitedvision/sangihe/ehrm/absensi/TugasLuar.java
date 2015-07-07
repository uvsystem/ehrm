package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.unitedvision.sangihe.ehrm.sppd.Sppd;

@Entity
@Table(name = "absen")
public class TugasLuar extends Absen {

	private Sppd sppd;
	
	public TugasLuar() {
		super();
	}

	@Column(name = "sppd", nullable = false)
	public Sppd getSppd() {
		return sppd;
	}

	public void setSppd(Sppd sppd) {
		this.sppd = sppd;
	}
}
