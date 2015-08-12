package com.unitedvision.sangihe.ehrm.duk;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

@Entity
@Table(name = "penduduk")
public class Penduduk {

	private long id;
	private String nik;
	private String nama;
	private Date tanggalLahir;
	private Kontak kontak;
	private Pegawai pegawai;
	
	public Penduduk() {
		super();
		kontak = new Kontak();
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "nik", unique = true, nullable = false)
	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	@Column(name = "nama", nullable = false)
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@JsonIgnore
	@Column(name = "tanggal_lahir", nullable = false)
	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}
	
	@Transient
	public String getTanggalLahirStr() {
		return DateUtil.toStringDate(tanggalLahir, "-");
	}
	
	public void setTanggalLahirStr(String tanggalLahirStr) {
		setTanggalLahir(DateUtil.getDate(tanggalLahirStr, "-"));
	}

	@Embedded
	@JsonIgnore
	public Kontak getKontak() {
		return kontak;
	}

	public void setKontak(Kontak kontak) {
		this.kontak = kontak;
	}

	@JsonBackReference
	@OneToOne(mappedBy = "penduduk", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Pegawai getPegawai() {
		return pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	@Transient
	public String getEmail() {
		if (kontak == null)
			return "";
		return kontak.getEmail();
	}

	@Transient
	public void setEmail(String email) {
		kontak.setEmail(email);
	}

	@Transient
	public String getTelepon() {
		if (kontak == null)
			return "";
		return kontak.getTelepon();
	}

	@Transient
	public void setTelepon(String telepon) {
		kontak.setTelepon(telepon);
	}

	@Embeddable
	public static class Kontak {
		
		private String email;
		private String telepon;
		
		public Kontak() {
			super();
		}

		@Column(name = "email", unique = true)
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Column(name = "telepon", unique = true)
		public String getTelepon() {
			return telepon;
		}

		public void setTelepon(String telepon) {
			this.telepon = telepon;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result
					+ ((telepon == null) ? 0 : telepon.hashCode());
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
			Kontak other = (Kontak) obj;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (telepon == null) {
				if (other.telepon != null)
					return false;
			} else if (!telepon.equals(other.telepon))
				return false;
			return true;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((kontak == null) ? 0 : kontak.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result + ((nik == null) ? 0 : nik.hashCode());
		result = prime * result
				+ ((tanggalLahir == null) ? 0 : tanggalLahir.hashCode());
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
		Penduduk other = (Penduduk) obj;
		if (id != other.id)
			return false;
		if (kontak == null) {
			if (other.kontak != null)
				return false;
		} else if (!kontak.equals(other.kontak))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (nik == null) {
			if (other.nik != null)
				return false;
		} else if (!nik.equals(other.nik))
			return false;
		if (tanggalLahir == null) {
			if (other.tanggalLahir != null)
				return false;
		} else if (!tanggalLahir.equals(other.tanggalLahir))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Penduduk [id=" + id + ", nik=" + nik + ", nama=" + nama
				+ ", tanggalLahir=" + tanggalLahir + ", kontak=" + kontak + "]";
	}
	
}
