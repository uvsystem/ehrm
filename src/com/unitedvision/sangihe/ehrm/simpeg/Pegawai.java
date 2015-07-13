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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.NullCollectionException;
import com.unitedvision.sangihe.ehrm.absensi.Absen;
import com.unitedvision.sangihe.ehrm.duk.Penduduk;
import com.unitedvision.sangihe.ehrm.duk.Penduduk.Kontak;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;
import com.unitedvision.sangihe.ehrm.manajemen.Token;
import com.unitedvision.sangihe.ehrm.sppd.PemegangTugas;

@Entity
@Table(name = "pegawai")
public class Pegawai implements Pejabat {

	private long id;
	private String nip;
	private Penduduk penduduk;
	private UnitKerja unitKerja;
	private String password;

	private List<RiwayatPangkat> daftarPangkat = new ArrayList<>();
	private List<RiwayatJabatan> daftarJabatan = new ArrayList<>();
	private List<Operator> daftarOperator = new ArrayList<>();
	private List<Token> daftarToken = new ArrayList<>();
	private List<PemegangTugas> daftarTugas = new ArrayList<>();

	private List<Absen> daftarAbsen = new ArrayList<>();
	//private List<Hadir> daftarHadir = new ArrayList<>();
	//private List<TugasLuar> daftarTugasLuar = new ArrayList<>();
	//private List<Sakit> daftarSakit = new ArrayList<>();
	//private List<Izin> daftarIzin = new ArrayList<>();
	//private List<Cuti> daftarCuti = new ArrayList<>();
	
	public Pegawai() {
		super();
		penduduk = new Penduduk();
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

	@OneToMany(mappedBy = "pegawai")
	@Fetch(FetchMode.SUBSELECT)
	public List<RiwayatPangkat> getDaftarPangkat() {
		return daftarPangkat;
	}

	public void setDaftarPangkat(List<RiwayatPangkat> daftarPangkat) {
		this.daftarPangkat = daftarPangkat;
	}

	@OneToMany(mappedBy = "pegawai")
	@Fetch(FetchMode.SUBSELECT)
	public List<RiwayatJabatan> getDaftarJabatan() {
		return daftarJabatan;
	}

	public void setDaftarJabatan(List<RiwayatJabatan> daftarJabatan) {
		this.daftarJabatan = daftarJabatan;
	}

	@OneToMany(mappedBy = "pegawai")
	@Fetch(FetchMode.SUBSELECT)
	public List<Operator> getDaftarOperator() {
		return daftarOperator;
	}

	public void setDaftarOperator(List<Operator> daftarOperator) {
		this.daftarOperator = daftarOperator;
	}

	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<PemegangTugas> getDaftarTugas() {
		return daftarTugas;
	}

	public void setDaftarTugas(List<PemegangTugas> daftarTugas) {
		this.daftarTugas = daftarTugas;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	public List<Token> getDaftarToken() {
		return daftarToken;
	}

	public void setDaftarToken(List<Token> daftarToken) {
		this.daftarToken = daftarToken;
	}

	
	/*
	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, orphanRemoval = true)
	public List<Hadir> getDaftarHadir() {
		return daftarHadir;
	}

	public void setDaftarHadir(List<Hadir> daftarHadir) {
		this.daftarHadir = daftarHadir;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, orphanRemoval = true)
	public List<TugasLuar> getDaftarTugasLuar() {
		return daftarTugasLuar;
	}

	public void setDaftarTugasLuar(List<TugasLuar> daftarTugasLuar) {
		this.daftarTugasLuar = daftarTugasLuar;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, orphanRemoval = true)
	public List<Sakit> getDaftarSakit() {
		return daftarSakit;
	}

	public void setDaftarSakit(List<Sakit> daftarSakit) {
		this.daftarSakit = daftarSakit;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, orphanRemoval = true)
	public List<Izin> getDaftarIzin() {
		return daftarIzin;
	}

	public void setDaftarIzin(List<Izin> daftarIzin) {
		this.daftarIzin = daftarIzin;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, orphanRemoval = true)
	public List<Cuti> getDaftarCuti() {
		return daftarCuti;
	}

	public void setDaftarCuti(List<Cuti> daftarCuti) {
		this.daftarCuti = daftarCuti;
	}
	*/

	@JsonIgnore
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, orphanRemoval = true)
	public List<Absen> getDaftarAbsen() {
		return daftarAbsen;
	}

	public void setDaftarAbsen(List<Absen> daftarAbsen) {
		this.daftarAbsen = daftarAbsen;
	}

	@JsonIgnore
	@Transient
	public RiwayatPangkat getPangkatTerakhir() throws NullCollectionException {
		if (getDaftarPangkat() == null)
			throw new NullCollectionException();

		for (RiwayatPangkat rp : daftarPangkat) {
			if (rp.getTanggalSelesai() == null)
				return rp;
		}
		
		throw new NullCollectionException();
	}

	@Transient
	public Pangkat getPangkat() throws NullCollectionException {
		return getPangkatTerakhir().getPangkat();
	}

	@JsonIgnore
	@Transient
	public RiwayatJabatan getJabatanTerakhir() throws NullCollectionException {
		if (getDaftarJabatan() == null)
			throw new NullCollectionException();

		for (RiwayatJabatan rj : daftarJabatan) {
			if (rj.getTanggalSelesai() == null)
				return rj;
		}
		
		throw new NullCollectionException();
	}
	
	@Override
	@Transient
	public Jabatan getJabatan() throws NullCollectionException {
		return getJabatanTerakhir().getJabatan();
	}

	@Override
	@Transient
	public Eselon getEselon() throws NullCollectionException {
		return getJabatan().getEselon();
	}

	@Override
	@Transient
	public Date tanggalMulai() throws NullCollectionException {
		return getJabatanTerakhir().getTanggalMulai();
	}

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

	@Transient
	public Date getTanggalLahir() {
		return penduduk.getTanggalLahir();
	}

	public void setTanggalLahir(Date tanggalLahir) {
		penduduk.setTanggalLahir(tanggalLahir);
	}

	@Transient
	public Kontak getKontak() {
		return penduduk.getKontak();
	}

	public void setKontak(Kontak kontak) {
		penduduk.setKontak(kontak);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((daftarJabatan == null) ? 0 : daftarJabatan.hashCode());
		result = prime * result
				+ ((daftarOperator == null) ? 0 : daftarOperator.hashCode());
		result = prime * result
				+ ((daftarPangkat == null) ? 0 : daftarPangkat.hashCode());
		result = prime * result
				+ ((daftarToken == null) ? 0 : daftarToken.hashCode());
		result = prime * result
				+ ((daftarTugas == null) ? 0 : daftarTugas.hashCode());
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
	
}
