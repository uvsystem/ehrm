package com.unitedvision.sangihe.ehrm.sppd;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Digunakan hanya untuk mapping, bukan untuk pengolahan data kegiatan.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */

@Entity
@Table(name = "kegiatan")
public class Kegiatan {

	private long id;
	private String nama;
	
	private List<Sppd> daftarSppd;

	public Kegiatan() {
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

	@Column(name = "nama")
	public String getNama() {
		return nama;
	}
	
	public void setNama(String nama) {
		this.nama = nama;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "kegiatan", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<Sppd> getDaftarSppd() {
		return daftarSppd;
	}

	public void setDaftarSppd(List<Sppd> daftarSppd) {
		this.daftarSppd = daftarSppd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
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
		Kegiatan other = (Kegiatan) obj;
		if (daftarSppd == null) {
			if (other.daftarSppd != null)
				return false;
		} else if (!daftarSppd.equals(other.daftarSppd))
			return false;
		if (id != other.id)
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		return true;
	}

}
