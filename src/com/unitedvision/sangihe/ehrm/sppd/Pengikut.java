package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;

@Entity
@Table(name = "pengikut")
public class Pengikut {

	private long id;
	private Sppd sppd;
	private String nama;
	private Date tanggalLahir;
	private String keterangan;
	
	public Pengikut() {
		super();
	}
	
	public Pengikut(Message message) {
		super();
		setNama(message.getNama());
		setTanggalLahir(message.getTanggalLahir());
		setKeterangan(message.getKeterangan());
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
	@JoinColumn(name = "sppd")
	public Sppd getSppd() {
		return sppd;
	}

	public void setSppd(Sppd sppd) {
		this.sppd = sppd;
	}

	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "tanggal_lahir")
	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	@Column(name = "keterangan")
	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public static class Message {
		
		private String nama;
		private String tanggalLahirStr;
		private String keterangan;

		@JsonIgnore
		public Date getTanggalLahir() {
			return DateUtil.getDate(tanggalLahirStr, "-");
		}
		
		public String getNama() {
			return nama;
		}
		
		public void setNama(String nama) {
			this.nama = nama;
		}
		
		public String getTanggalLahirStr() {
			return tanggalLahirStr;
		}
		
		public void setTanggalLahirStr(String tanggalLahirStr) {
			this.tanggalLahirStr = tanggalLahirStr;
		}
		
		public String getKeterangan() {
			return keterangan;
		}
		
		public void setKeterangan(String keterangan) {
			this.keterangan = keterangan;
		}

	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((keterangan == null) ? 0 : keterangan.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result + ((sppd == null) ? 0 : sppd.hashCode());
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
		Pengikut other = (Pengikut) obj;
		if (id != other.id)
			return false;
		if (keterangan == null) {
			if (other.keterangan != null)
				return false;
		} else if (!keterangan.equals(other.keterangan))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (sppd == null) {
			if (other.sppd != null)
				return false;
		} else if (!sppd.equals(other.sppd))
			return false;
		if (tanggalLahir == null) {
			if (other.tanggalLahir != null)
				return false;
		} else if (!tanggalLahir.equals(other.tanggalLahir))
			return false;
		return true;
	}
}
