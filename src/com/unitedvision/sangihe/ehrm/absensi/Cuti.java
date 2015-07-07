package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "absen")
public class Cuti extends Absen {
	
	private String jenisCuti;

	@Column(name = "keterangan")
	public String getJenisCuti() {
		return jenisCuti;
	}

	public void setJenisCuti(String jenisCuti) {
		this.jenisCuti = jenisCuti;
	}

}
