package com.unitedvision.sangihe.ehrm.simpeg;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "unit_kerja")
@DiscriminatorValue("SUB_SKPD")
public class SubUnitKerja extends UnitKerja {
	
	private UnitKerja unitKerja;

	public SubUnitKerja() {
		super();
	}
	
	public SubUnitKerja(UnitKerja unitKerja) {
		super();
		this.setUnitKerja(unitKerja);
		this.setNama(unitKerja.getNama());
		this.setSingkatan(unitKerja.getSingkatan());
		this.setTipe(unitKerja.getTipe());
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent", nullable = false)
	public UnitKerja getUnitKerja() {
		return unitKerja;
	}

	public void setUnitKerja(UnitKerja unitKerja) {
		this.unitKerja = unitKerja;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((unitKerja == null) ? 0 : unitKerja.hashCode());
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
		SubUnitKerja other = (SubUnitKerja) obj;
		if (unitKerja == null) {
			if (other.unitKerja != null)
				return false;
		} else if (!unitKerja.equals(other.unitKerja))
			return false;
		return true;
	}
}
