package com.unitedvision.sangihe.ehrm.simpeg;

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
import com.unitedvision.sangihe.ehrm.absensi.Absen;
import com.unitedvision.sangihe.ehrm.duk.Penduduk;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;
import com.unitedvision.sangihe.ehrm.manajemen.Token;
import com.unitedvision.sangihe.ehrm.sppd.PemegangTugas;

@Entity
@Table(name = "pegawai")
public class Pegawai {

	private long id;
	private String nip;
	private Penduduk penduduk;
	private UnitKerja unitKerja;
	private String password;
	
	// Property sementara
	// private Pangkat pangkat;
	// private Eselon eselon;
	private String pangkat;
	private String eselon;
	private String namaJabatan;
	
	private List<RiwayatPangkat> daftarPangkat = new ArrayList<>();
	private List<RiwayatJabatan> daftarJabatan = new ArrayList<>();
	private List<Operator> daftarOperator = new ArrayList<>();
	private List<Token> daftarToken = new ArrayList<>();
	private List<PemegangTugas> daftarTugas = new ArrayList<>();
	private List<Absen> daftarAbsen = new ArrayList<>();
	
	private List<RiwayatPangkat> listPangkat = new ArrayList<>();
	private List<RiwayatJabatan> listJabatan = new ArrayList<>();
	private List<Operator> listOperator = new ArrayList<>();
	private List<Absen> listAbsen = new ArrayList<>();

	public Pegawai() {
		super();
		penduduk = new Penduduk();
	}

	public Pegawai(UnitKerja unitKerja) {
		this();
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

	@Column(name = "nip", unique = true, nullable = false)
	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "penduduk")
	public Penduduk getPenduduk() {
		return penduduk;
	}

	public void setPenduduk(Penduduk penduduk) {
		this.penduduk = penduduk;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_kerja", nullable = false)
	public UnitKerja getUnitKerja() {
		return unitKerja;
	}

	public void setUnitKerja(UnitKerja unitKerja) {
		this.unitKerja = unitKerja;
	}

	@JsonIgnore
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getPasswordStr() {
		return getPassword();
	}
	
	public void setPasswordStr(String password) {
		setPassword(password);
	}
	
//	@Column(name = "pangkat")
//	public Pangkat getPangkat() {
//		return pangkat;
//	}
//
//	public void setPangkat(Pangkat pangkat) {
//		this.pangkat = pangkat;
//	}
//	
//	@Column(name = "eselon")
//	public Eselon getEselon() {
//		return eselon;
//	}
//
//	public void setEselon(Eselon eselon) {
//		this.eselon = eselon;
//	}

	@Column(name = "pangkat")
	public String getPangkat() {
		return pangkat;
	}

	public void setPangkat(String pangkat) {
		this.pangkat = pangkat;
	}
	
	@Column(name = "eselon")
	public String getEselon() {
		return eselon;
	}

	public void setEselon(String eselon) {
		this.eselon = eselon;
	}

	@Column(name = "jabatan")
	public String getNamaJabatan() {
		return namaJabatan;
	}
	
	public void setNamaJabatan(String namaJabatan) {
		this.namaJabatan = namaJabatan;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RiwayatPangkat> getDaftarPangkat() {
		return daftarPangkat;
	}

	public void setDaftarPangkat(List<RiwayatPangkat> daftarPangkat) {
		this.daftarPangkat = daftarPangkat;
	}

	@Transient
	public List<RiwayatPangkat> getListPangkat() {
		return listPangkat;
	}

	public void setListPangkat(List<RiwayatPangkat> daftarPangkat) {
		this.listPangkat = daftarPangkat;
		this.daftarPangkat = daftarPangkat;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<RiwayatJabatan> getDaftarJabatan() {
		return daftarJabatan;
	}

	public void setDaftarJabatan(List<RiwayatJabatan> daftarJabatan) {
		this.daftarJabatan = daftarJabatan;
	}

	@Transient
	public List<RiwayatJabatan> getListJabatan() {
		return listJabatan;
	}

	public void setListJabatan(List<RiwayatJabatan> daftarJabatan) {
		this.listJabatan = daftarJabatan;
		this.daftarJabatan = daftarJabatan;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Operator> getDaftarOperator() {
		return daftarOperator;
	}

	public void setDaftarOperator(List<Operator> daftarOperator) {
		this.daftarOperator = daftarOperator;
	}

	@Transient
	public List<Operator> getListOperator() {
		return listOperator;
	}

	public void setListOperator(List<Operator> daftarOperator) {
		this.listOperator = daftarOperator;
		this.daftarOperator = daftarOperator;
	}

	public void addOperator(Operator operator) {
		operator.setPegawai(this);
		listOperator.add(operator);
		daftarOperator.add(operator);
	}

	public void removeOperator(Operator operator) {
		operator.setPegawai(null);
		listOperator.remove(operator);
		daftarOperator.remove(operator);
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Absen> getDaftarAbsen() {
		return daftarAbsen;
	}

	public void setDaftarAbsen(List<Absen> daftarAbsen) {
		this.daftarAbsen = daftarAbsen;
	}

	@Transient
	public List<Absen> getListAbsen() {
		return listAbsen;
	}

	public void setListAbsen(List<Absen> daftarAbsen) {
		this.listAbsen = daftarAbsen;
		this.daftarAbsen = daftarAbsen;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public List<PemegangTugas> getDaftarTugas() {
		return daftarTugas;
	}

	public void setDaftarTugas(List<PemegangTugas> daftarTugas) {
		this.daftarTugas = daftarTugas;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Token> getDaftarToken() {
		return daftarToken;
	}

	public void setDaftarToken(List<Token> daftarToken) {
		this.daftarToken = daftarToken;
	}

	@JsonIgnore
	@Transient
	public RiwayatPangkat getPangkatTerakhir() throws NullCollectionException, NoPangkatException {
		if (listPangkat == null)
			throw new NullCollectionException();

		for (RiwayatPangkat rp : listPangkat) {
			if (rp.getTanggalSelesai() == null)
				return rp;
		}
		
		throw new NoPangkatException("Tidak ada pangkat yang aktif");
	}

	@JsonIgnore
	@Transient
	public RiwayatJabatan getJabatanTerakhir() throws NullCollectionException, NoJabatanException {
		if (listJabatan == null)
			throw new NullCollectionException();

		for (RiwayatJabatan rj : listJabatan) {
			if (rj.getTanggalSelesai() == null)
				return rj;
		}
		
		throw new NoJabatanException("Tidak ada jabatan yang aktif");
	}

	@JsonIgnore
	@Transient
	public Jabatan getJabatan() throws NullCollectionException, NoJabatanException {
		return getJabatanTerakhir().getJabatan();
	}
//
//	@JsonIgnore
//	@Override
//	@Transient
//	public Date getTanggalMenjabat() throws NoJabatanException {
//		try {
//			return getJabatanTerakhir().getTanggalMulai();
//		} catch (NullCollectionException e) {
//			return null;
//		}
//	}
//
//	@Transient
//	public String getTanggalMenjabatStr() {
//		try {
//			return DateUtil.toStringDate(getTanggalMenjabat(), "-");
//		} catch (NoJabatanException e) {
//			return null;
//		}
//	}

	@Transient
	public String getNik() {
		return penduduk.getNik();
	}

	public void setNik(String nik) {
		penduduk.setNik(nik);
	}

	@Transient
	public String getNama() {
		return penduduk.getNama();
	}

	public void setNama(String nama) {
		penduduk.setNama(nama);
	}

	@JsonIgnore
	@Transient
	public Date getTanggalLahir() {
		return penduduk.getTanggalLahir();
	}

	public void setTanggalLahir(Date tanggalLahir) {
		penduduk.setTanggalLahir(tanggalLahir);
	}

	@Transient
	public String getTanggalLahirStr() {
		return DateUtil.toStringDate(getTanggalLahir(), "-");
	}

	public void setTanggalLahirStr(String tanggalLahir) {
		Date date = DateUtil.getDate(tanggalLahir, "-");
		
		setTanggalLahir(date);
	}
	
	@Transient
	public String getEmail() {
		return penduduk.getEmail();
	}

	public void setEmail(String email) {
		penduduk.setEmail(email);
	}

	@Transient
	public String getTelepon() {
		return penduduk.getTelepon();
	}

	public void setTelepon(String telepon) {
		penduduk.setTelepon(telepon);
	}
	
	@Transient
	public Long getIdPenduduk() {
		return penduduk.getId();
	}
	
	public void setIdPenduduk(Long idPenduduk) {
		penduduk.setId(idPenduduk);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nip == null) ? 0 : nip.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((penduduk == null) ? 0 : penduduk.hashCode());
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
		Pegawai other = (Pegawai) obj;
		if (daftarJabatan == null) {
			if (other.daftarJabatan != null)
				return false;
		} else if (!daftarJabatan.equals(other.daftarJabatan))
			return false;
		if (daftarOperator == null) {
			if (other.daftarOperator != null)
				return false;
		} else if (!daftarOperator.equals(other.daftarOperator))
			return false;
		if (daftarPangkat == null) {
			if (other.daftarPangkat != null)
				return false;
		} else if (!daftarPangkat.equals(other.daftarPangkat))
			return false;
		if (daftarToken == null) {
			if (other.daftarToken != null)
				return false;
		} else if (!daftarToken.equals(other.daftarToken))
			return false;
		if (daftarTugas == null) {
			if (other.daftarTugas != null)
				return false;
		} else if (!daftarTugas.equals(other.daftarTugas))
			return false;
		if (id != other.id)
			return false;
		if (nip == null) {
			if (other.nip != null)
				return false;
		} else if (!nip.equals(other.nip))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (penduduk == null) {
			if (other.penduduk != null)
				return false;
		} else if (!penduduk.equals(other.penduduk))
			return false;
		if (unitKerja == null) {
			if (other.unitKerja != null)
				return false;
		} else if (!unitKerja.equals(other.unitKerja))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Pegawai [id=" + id + ", nip=" + nip + ", penduduk=" + penduduk
				+ ", unitKerja=" + unitKerja + ", password=" + password + "]";
	}
	
	
}
