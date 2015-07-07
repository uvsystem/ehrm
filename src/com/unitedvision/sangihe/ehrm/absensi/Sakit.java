package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "absen")
public class Sakit extends Absen {
	
	private String penyakit;

	@Column(name = "keterangan")
	public String getPenyakit() {
		return penyakit;
	}

	public void setPenyakit(String penyakit) {
		this.penyakit = penyakit;
	}

}
