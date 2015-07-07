package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "absen")
public class Izin extends Absen {
	
	private String alasan;

	@Column(name = "keterangan")
	public String getAlasan() {
		return alasan;
	}

	public void setAlasan(String alasan) {
		this.alasan = alasan;
	}


}
