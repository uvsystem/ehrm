package com.unitedvision.sangihe.ehrm.absensi;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

@MappedSuperclass
public abstract class Absen {

	private long id;
	private Kalendar kalendar;
	private Pegawai pegawai;
	
	public Absen() {
		super();
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "kalendar", nullable = false)
	public Kalendar getKalendar() {
		return kalendar;
	}

	public void setKalendar(Kalendar kalendar) {
		this.kalendar = kalendar;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pegawai", nullable = false)
	public Pegawai getPegawai() {
		return pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((kalendar == null) ? 0 : kalendar.hashCode());
		result = prime * result + ((pegawai == null) ? 0 : pegawai.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Absen other = (Absen) obj;
		if (id != other.id)
			return false;
		if (kalendar == null) {
			if (other.kalendar != null)
				return false;
		} else if (!kalendar.equals(other.kalendar))
			return false;
		if (pegawai == null) {
			if (other.pegawai != null)
				return false;
		} else if (!pegawai.equals(other.pegawai))
			return false;
		return true;
	}
}
