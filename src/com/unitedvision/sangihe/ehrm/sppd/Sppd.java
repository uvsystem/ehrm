package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.NullCollectionException;
import com.unitedvision.sangihe.ehrm.absensi.TugasLuar;
import com.unitedvision.sangihe.ehrm.simpeg.NoJabatanException;
import com.unitedvision.sangihe.ehrm.simpeg.NoPangkatException;
import com.unitedvision.sangihe.ehrm.simpeg.Pangkat;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.SubUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

@Entity
@Table(name = "sppd")
public class Sppd {

	private long id;
	private String nomor;
	private PemegangTugas pemegangTugas;
	private String tingkat;

	private Kegiatan kegiatan;

	private String nomorDpa;
	private String kodeRekening;
	private String modaTransportasi;
	private String asal = "Tahuna";
	private Date tanggalBerangkat;
	
	private List<TugasLuar> daftarAbsen;
	private List<Pengikut> daftarPengikut;

	public Sppd() {
		super();
		daftarPengikut = new ArrayList<>();
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "nomor", unique = true, nullable = false)
	public String getNomor() {
		return nomor;
	}

	public void setNomor(String nomor) {
		this.nomor = nomor;
	}

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "pemegang_tugas", referencedColumnName = "id")
	public PemegangTugas getPemegangTugas() {
		return pemegangTugas;
	}

	public void setPemegangTugas(PemegangTugas pemegangTugas) {
		this.pemegangTugas = pemegangTugas;
	}

	@Column(name = "tingkat")
	public String getTingkat() {
		return tingkat;
	}

	public void setTingkat(String tingkat) {
		this.tingkat = tingkat;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "kegiatan")
	public Kegiatan getKegiatan() {
		return kegiatan;
	}

	public void setKegiatan(Kegiatan kegiatan) {
		this.kegiatan = kegiatan;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "sppd", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<TugasLuar> getDaftarAbsen() {
		return daftarAbsen;
	}

	public void setDaftarAbsen(List<TugasLuar> daftarAbsen) {
		this.daftarAbsen = daftarAbsen;
	}
	
	public void addAbsen(TugasLuar tugasLuar) {
		tugasLuar.setSppd(this);
		daftarAbsen.add(tugasLuar);
	}
	
	public void removeAbsen(TugasLuar tugasLuar) {
		tugasLuar.setSppd(null);
		daftarAbsen.remove(tugasLuar);
	}

	@OneToMany(mappedBy = "sppd", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Pengikut> getDaftarPengikut() {
		return daftarPengikut;
	}

	public void setDaftarPengikut(List<Pengikut> daftarPengikut) {
		this.daftarPengikut = daftarPengikut;
	}
	
	public void addPengikut(Pengikut pengikut) {
		pengikut.setSppd(this);
		daftarPengikut.add(pengikut);
	}
	
	public void removePengikut(Pengikut pengikut) {
		pengikut.setSppd(null);
		daftarPengikut.remove(pengikut);
	}

	@Column(name = "nomor_dpa", nullable = false)
	public String getNomorDpa() {
		return nomorDpa;
	}

	public void setNomorDpa(String nomorDpa) {
		this.nomorDpa = nomorDpa;
	}
	
	@Transient
	public String getNamaKegiatan() {
		return getKegiatan().getNama();
	}

	@Column(name = "rekening", nullable = false)
	public String getKodeRekening() {
		return kodeRekening;
	}

	public void setKodeRekening(String kodeRekening) {
		this.kodeRekening = kodeRekening;
	}

	@Column(name = "moda_transportasi", nullable = false)
	public String getModaTransportasi() {
		return modaTransportasi;
	}

	public void setModaTransportasi(String modaTransportasi) {
		this.modaTransportasi = modaTransportasi;
	}

	@Column(name = "asal", nullable = false)
	public String getAsal() {
		return asal;
	}

	public void setAsal(String asal) {
		this.asal = asal;
	}

	@JsonIgnore
	@Column(name = "tanggal_berangkat", nullable = false)
	public Date getTanggalBerangkat() {
		return tanggalBerangkat;
	}

	public void setTanggalBerangkat(Date tanggalBerangkat) {
		this.tanggalBerangkat = tanggalBerangkat;
	}

	@Transient
	public String getBerangkat() {
		return DateUtil.toFormattedStringDate(tanggalBerangkat, "-");
	}

	public void setBerangkat(String berangkat) {
		tanggalBerangkat = DateUtil.getDate(berangkat);
	}

	@JsonIgnore
	@Transient
	public Date getTanggalKembali() {
		return DateUtil.add(tanggalBerangkat, getJumlahHari() - 1);
	}

	@Transient
	public String getKembali() {
		return DateUtil.toFormattedStringDate(getTanggalKembali(), "-");
	}

	public void setKembali(String kembali) {
		// do nothing
	}
	
	@Transient
	public int getJumlahHari() {
		SuratTugas suratTugas = getPemegangTugas().getSuratTugas();
		
		return suratTugas.getJumlahHari();
	}
	
	public class Detail {

		private Pegawai getPegawai() {
			return pemegangTugas.getPegawai();
		}
		
		private SuratTugas getSuratTugas() {
			return pemegangTugas.getSuratTugas();
		}
		
		public String getNama() {
			return getPegawai().getNama();
		}
		
		public String getNip() {
			return getPegawai().getNip();
		}
		
		public String getPangkat() throws NullCollectionException, NoPangkatException {
			Pangkat pangkat = getPegawai().getPangkat();
			String nama = pangkat.getNama();
			String namaPangkat = pangkat.name();
			
			return String.format("%s, %s", nama, namaPangkat);
		}
		
		public String getJabatan() throws NullCollectionException, NoJabatanException {
			return getPegawai().getJabatan().getNama();
		}
		
		public String getSatuanKerja() {
			UnitKerja unitKerja = getPegawai().getUnitKerja();
			
			if (unitKerja instanceof SubUnitKerja)
				return ((SubUnitKerja) unitKerja).getUnitKerja().getNama();
			
			return unitKerja.getNama();
		}
		
		public String getMaksud() {
			return getSuratTugas().getMaksud();
		}
		
		public String getNomorSuratTugas() {
			return getSuratTugas().getNomor();
		}
		
		public String getTanggalSuratTugas() {
			return DateUtil.toFormattedStringDate(getSuratTugas().getTanggal(), "-");
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((daftarAbsen == null) ? 0 : daftarAbsen.hashCode());
		result = prime * result
				+ ((daftarPengikut == null) ? 0 : daftarPengikut.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((kegiatan == null) ? 0 : kegiatan.hashCode());
		result = prime * result + ((nomor == null) ? 0 : nomor.hashCode());
		result = prime * result
				+ ((pemegangTugas == null) ? 0 : pemegangTugas.hashCode());
		result = prime * result + ((tingkat == null) ? 0 : tingkat.hashCode());
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
		Sppd other = (Sppd) obj;
		if (daftarAbsen == null) {
			if (other.daftarAbsen != null)
				return false;
		} else if (!daftarAbsen.equals(other.daftarAbsen))
			return false;
		if (daftarPengikut == null) {
			if (other.daftarPengikut != null)
				return false;
		} else if (!daftarPengikut.equals(other.daftarPengikut))
			return false;
		if (id != other.id)
			return false;
		if (kegiatan == null) {
			if (other.kegiatan != null)
				return false;
		} else if (!kegiatan.equals(other.kegiatan))
			return false;
		if (nomor == null) {
			if (other.nomor != null)
				return false;
		} else if (!nomor.equals(other.nomor))
			return false;
		if (pemegangTugas == null) {
			if (other.pemegangTugas != null)
				return false;
		} else if (!pemegangTugas.equals(other.pemegangTugas))
			return false;
		if (tingkat == null) {
			if (other.tingkat != null)
				return false;
		} else if (!tingkat.equals(other.tingkat))
			return false;
		return true;
	}

}
