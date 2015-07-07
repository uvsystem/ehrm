package com.unitedvision.sangihe.ehrm.duk;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "penduduk")
public class Penduduk {

	private long id;
	private String nik;
	private String nama;
	private Date tanggalLahir;
	private Kontak kontak;
	
	public Penduduk() {
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

	@Temporal(TemporalType.DATE)
	@Column(name = "tanggal_lahir", nullable = false)
	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	@Embedded
	public Kontak getKontak() {
		return kontak;
	}

	public void setKontak(Kontak kontak) {
		this.kontak = kontak;
	}
	
	@Embeddable
	public class Kontak {
		
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
	}
}
