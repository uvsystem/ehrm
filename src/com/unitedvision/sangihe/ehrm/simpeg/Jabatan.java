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

	@OneToMany(mappedBy = "jabatan", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<RiwayatJabatan> getDaftarRiwayat() {
		return daftarRiwayat;
	}

	public void setDaftarRiwayat(List<RiwayatJabatan> daftarRiwayat) {
		this.daftarRiwayat = daftarRiwayat;
	}

}
