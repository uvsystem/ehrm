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
}
