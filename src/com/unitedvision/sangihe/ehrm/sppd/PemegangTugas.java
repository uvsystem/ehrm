package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unitedvision.sangihe.ehrm.NullCollectionException;
import com.unitedvision.sangihe.ehrm.simpeg.NoJabatanException;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

@Entity
@Table(name = "pemegang_tugas")
public class PemegangTugas {

	private long id;
	private SuratTugas suratTugas;
	private Pegawai pegawai;
	
	private Sppd sppd;
	
	public PemegangTugas() {
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

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "surat_tugas", nullable = false)
	public SuratTugas getSuratTugas() {
		return suratTugas;
	}
	
	public void setSuratTugas(SuratTugas suratTugas) {
		this.suratTugas = suratTugas;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pegawai")
	public Pegawai getPegawai() {
		return pegawai;
	}
	
	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	@OneToOne(mappedBy = "pemegangTugas")
	public Sppd getSppd() {
		return sppd;
	}

	public void setSppd(Sppd sppd) {
		this.sppd = sppd;
	}

	@Transient
	public String getNip() {
		return pegawai.getNip();
	}

	@Transient
	public UnitKerja getUnitKerja() {
		return pegawai.getUnitKerja();
	}
//
//	@Transient
//	public Pangkat getPangkat() {
//		return pegawai.getPangkat();
//	}

	@Transient
	public String getPangkat() {
		return pegawai.getPangkat();
	}

	@Transient
	public String getJabatan() {
		try {
			return pegawai.getJabatan().getNama();
		} catch (NoJabatanException | NullCollectionException e) {
			return "tidak ada jabatan";
		}
	}
//
//	@Transient
//	public Eselon getEselon() {
//		return pegawai.getEselon();
//	}

	@Transient
	public String getEselon() {
		return pegawai.getEselon();
	}

	@Transient
	public String getNama() {
		return pegawai.getNama();
	}

	@Transient
	public String getNomor() {
		return suratTugas.getNomor();
	}

	@Transient
	public Date getTanggal() {
		return suratTugas.getTanggal();
	}

	@Transient
	public int getJumlahHari() {
		return suratTugas.getJumlahHari();
	}

	@Transient
	public String getTujuan() {
		return suratTugas.getTujuan();
	}

	@Transient
	public String getMaksud() {
		return suratTugas.getMaksud();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((pegawai == null) ? 0 : pegawai.hashCode());
		result = prime * result + ((sppd == null) ? 0 : sppd.hashCode());
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
		PemegangTugas other = (PemegangTugas) obj;
		if (id != other.id)
			return false;
		if (pegawai == null) {
			if (other.pegawai != null)
				return false;
		} else if (!pegawai.equals(other.pegawai))
			return false;
		if (sppd == null) {
			if (other.sppd != null)
				return false;
		} else if (!sppd.equals(other.sppd))
			return false;
		if (suratTugas == null) {
			if (other.suratTugas != null)
				return false;
		} else if (!suratTugas.equals(other.suratTugas))
			return false;
		return true;
	}

}
