package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.absensi.TugasLuar;
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
	
	private Anggaran anggaran;
	private Perjalanan perjalanan;
	
	private List<TugasLuar> daftarAbsen;
	private List<Pengikut> daftarPengikut;

	public Sppd() {
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

	@Column(name = "nomor", unique = true, nullable = false)
	public String getNomor() {
		return nomor;
	}

	public void setNomor(String nomor) {
		this.nomor = nomor;
	}

	@JsonIgnore
	@OneToOne(targetEntity = PemegangTugas.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
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

	@Embedded
	public Anggaran getAnggaran() {
		return anggaran;
	}

	public void setAnggaran(Anggaran anggaran) {
		this.anggaran = anggaran;
	}

	@Embedded
	public Perjalanan getPerjalanan() {
		return perjalanan;
	}

	public void setPerjalanan(Perjalanan perjalanan) {
		this.perjalanan = perjalanan;
	}

	@Embeddable
	public class Anggaran {
		
		private String nomorDpa;
		private String kodeRekening;

		public Anggaran() {
			super();
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
			return kegiatan.getNama();
		}

		@Column(name = "rekening", nullable = false)
		public String getKodeRekening() {
			return kodeRekening;
		}

		public void setKodeRekening(String kodeRekening) {
			this.kodeRekening = kodeRekening;
		}

	}
	
	@Embeddable
	public class Perjalanan {
		
		private String modaTransportasi;
		private String asal = "Tahuna";
		private Date tanggalBerangkat;

		public Perjalanan() {
			super();
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
		@Temporal(TemporalType.DATE)
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
			return DateUtil.add(tanggalBerangkat, getJumlahHari());
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
			SuratTugas suratTugas = pemegangTugas.getSuratTugas();
			
			return suratTugas.getJumlahHari();
		}
		
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
		
		public String getPangkat() {
			Pangkat pangkat = getPegawai().getPangkat();
			String nama = pangkat.getNama();
			String namaPangkat = pangkat.name();
			
			return String.format("%s, %s", nama, namaPangkat);
		}
		
		public String getJabatan() {
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

}
