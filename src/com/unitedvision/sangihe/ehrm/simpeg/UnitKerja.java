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
import javax.persistence.Transient;

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
		SEKSI,
		KEASISTENAN
	}
	
	private long id;
	private String nama;
	private TipeUnitKerja tipe;
	private String singkatan;
	private UnitKerja parent;
	
	private List<UnitKerja> daftarSubUnit;
	private List<Pegawai> daftarPegawai;
	private List<Jabatan> daftarJabatan;

	public UnitKerja() {
		super();
	}
	
	public UnitKerja(UnitKerja unitKerja) {
		this();
		setParent(unitKerja);
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
	@JoinColumn(name = "parent")
	public UnitKerja getParent() {
		return parent;
	}

	public void setParent(UnitKerja parent) {
		this.parent = parent;
	}

	@Transient
	public Long getIdParent() {
		if (parent == null)
			return 0L;
		return parent.getId();
	}
	
	public void setIdParent(Long idParent) {
		if (parent == null)
			parent = new UnitKerja();
		parent.setId(idParent);
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
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<UnitKerja> getDaftarSubUnit() {
		return daftarSubUnit;
	}

	public void setDaftarSubUnit(List<UnitKerja> daftarSubUnit) {
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
	@OneToMany(mappedBy = "unitKerja", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<Jabatan> getDaftarJabatan() {
		return daftarJabatan;
	}

	public void setDaftarJabatan(List<Jabatan> daftarJabatan) {
		this.daftarJabatan = daftarJabatan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result
				+ ((singkatan == null) ? 0 : singkatan.hashCode());
		result = prime * result + ((tipe == null) ? 0 : tipe.hashCode());
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
		UnitKerja other = (UnitKerja) obj;
		if (daftarJabatan == null) {
			if (other.daftarJabatan != null)
				return false;
		} else if (!daftarJabatan.equals(other.daftarJabatan))
			return false;
		if (daftarPegawai == null) {
			if (other.daftarPegawai != null)
				return false;
		} else if (!daftarPegawai.equals(other.daftarPegawai))
			return false;
		if (daftarSubUnit == null) {
			if (other.daftarSubUnit != null)
				return false;
		} else if (!daftarSubUnit.equals(other.daftarSubUnit))
			return false;
		if (id != other.id)
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (singkatan == null) {
			if (other.singkatan != null)
				return false;
		} else if (!singkatan.equals(other.singkatan))
			return false;
		if (tipe != other.tipe)
			return false;
		return true;
	}

}
