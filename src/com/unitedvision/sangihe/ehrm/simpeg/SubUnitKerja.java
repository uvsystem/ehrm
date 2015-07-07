package com.unitedvision.sangihe.ehrm.simpeg;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "unit_kerja")
public class SubUnitKerja extends UnitKerja {
	
	private UnitKerja unitKerja;

	public SubUnitKerja() {
		super();
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent", nullable = false)
	public UnitKerja getUnitKerja() {
		return unitKerja;
	}

	public void setUnitKerja(UnitKerja unitKerja) {
		this.unitKerja = unitKerja;
	}
}
