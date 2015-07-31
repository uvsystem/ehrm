package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "jabatan")
public class Jabatan {

	private long id;
	private UnitKerja unitKerja;
	private Eselon eselon;
	private Pangkat pangkat;
	private String nama;
	
	private List<RiwayatJabatan> daftarRiwayat;
	
	public Jabatan() {
		super();
	}

	public Jabatan(UnitKerja unitKerja) {
		super();
		setUnitKerja(unitKerja);
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
	@JoinColumn(name = "unit_kerja", nullable = false)
	public UnitKerja getUnitKerja() {
		return unitKerja;
	}

	public void setUnitKerja(UnitKerja unitKerja) {
		this.unitKerja = unitKerja;
	}

	@Column(name = "eselon")
	public Eselon getEselon() {
		return eselon;
	}

	public void setEselon(Eselon eselon) {
		this.eselon = eselon;
	}

	@Column(name = "pangkat")
	public Pangkat getPangkat() {
		return pangkat;
	}

	public void setPangkat(Pangkat pangkat) {
		this.pangkat = pangkat;
	}

	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "jabatan", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<RiwayatJabatan> getDaftarRiwayat() {
		return daftarRiwayat;
	}

	public void setDaftarRiwayat(List<RiwayatJabatan> daftarRiwayat) {
		this.daftarRiwayat = daftarRiwayat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eselon == null) ? 0 : eselon.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result + ((pangkat == null) ? 0 : pangkat.hashCode());
		result = prime * result
				+ ((unitKerja == null) ? 0 : unitKerja.hashCode());
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
		Jabatan other = (Jabatan) obj;
		if (daftarRiwayat == null) {
			if (other.daftarRiwayat != null)
				return false;
		} else if (!daftarRiwayat.equals(other.daftarRiwayat))
			return false;
		if (eselon != other.eselon)
			return false;
		if (id != other.id)
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (pangkat != other.pangkat)
			return false;
		if (unitKerja == null) {
			if (other.unitKerja != null)
				return false;
		} else if (!unitKerja.equals(other.unitKerja))
			return false;
		return true;
	}

}
