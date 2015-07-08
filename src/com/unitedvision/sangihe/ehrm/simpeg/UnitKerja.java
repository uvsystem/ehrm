package com.unitedvision.sangihe.ehrm.simpeg;

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

@Entity
@Table(name = "unit_kerja")
public class UnitKerja {

	public enum TipeUnitKerja {
		SEKRETARIAT,
		DINAS,
		BADAN,
		UPT,
		BIDANG,
		BAGIAN,
		SEKSI
	}
	
	private long id;
	private String nama;
	private TipeUnitKerja tipe;
	private String singkatan;
	
	private List<SubUnitKerja> daftarSubUnit;
	private List<Pegawai> daftarPegawai;
	private List<Jabatan> daftarJabatan;
	
	public UnitKerja() {
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

	@Column(name = "nama", nullable = false)
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "tipe", nullable = false)
	public TipeUnitKerja getTipe() {
		return tipe;
	}

	public void setTipe(TipeUnitKerja tipe) {
		this.tipe = tipe;
	}

	@Column(name = "singkatan")
	public String getSingkatan() {
		return singkatan;
	}

	public void setSingkatan(String singkatan) {
		this.singkatan = singkatan;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "unitKerja", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<SubUnitKerja> getDaftarSubUnit() {
		return daftarSubUnit;
	}

	public void setDaftarSubUnit(List<SubUnitKerja> daftarSubUnit) {
		this.daftarSubUnit = daftarSubUnit;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "unitKerja", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<Pegawai> getDaftarPegawai() {
		return daftarPegawai;
	}

	public void setDaftarPegawai(List<Pegawai> daftarPegawai) {
		this.daftarPegawai = daftarPegawai;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "unitKerja", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<Jabatan> getDaftarJabatan() {
		return daftarJabatan;
	}

	public void setDaftarJabatan(List<Jabatan> daftarJabatan) {
		this.daftarJabatan = daftarJabatan;
	}

}
