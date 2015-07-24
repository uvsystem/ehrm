package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;

@MappedSuperclass
public abstract class Riwayat {

	private long id;
	private String nomorSk;
	private Pegawai pegawai;
	private Date tanggalMulai;
	private Date tanggalSelesai;
	
	public Riwayat() {
		super();
	}
	
	public Riwayat(Detail detail) {
		super();
		setNomorSk(detail.getNomorSk());
		setTanggalMulai(detail.getTanggalMulai());
		setTanggalSelesai(detail.getTanggalSelesai());
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Nomor Surat Pembuktian
	 * @return
	 */
	@Column(name = "nomor_sk")
	public String getNomorSk() {
		return nomorSk;
	}

	public void setNomorSk(String nomorSk) {
		this.nomorSk = nomorSk;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pegawai")
	@JsonBackReference
	public Pegawai getPegawai() {
		return pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	@JsonIgnore
	@Column(name = "tanggal_mulai")
	public Date getTanggalMulai() {
		return tanggalMulai;
	}

	public void setTanggalMulai(Date tanggalMulai) {
		this.tanggalMulai = tanggalMulai;
	}

	@Transient
	public String getTanggalMulaiStr() {
		return DateUtil.toStringDate(tanggalMulai, "-");
	}
	
	@JsonIgnore
	@Column(name = "tanggal_selesai")
	public Date getTanggalSelesai() {
		return tanggalSelesai;
	}
	
	public void setTanggalSelesai(Date tanggalSelesai) {
		this.tanggalSelesai = tanggalSelesai;
	}

	@Transient
	public String getTanggalSelesaiStr() {
		if (!isCurrent())
			return DateUtil.toStringDate(tanggalSelesai, "-");
		return "belum selesai";
	}

	@Transient
	public boolean isCurrent() {
		if (tanggalSelesai == null)
			return true;
		return false;
	}
	
	public static class Detail {
		private String nomorSk;
		private String tanggalMulaiStr;
		private String tanggalSelesaiStr;

		public String getNomorSk() {
			return nomorSk;
		}
		
		public void setNomorSk(String nomorSk) {
			this.nomorSk = nomorSk;
		}

		public String getTanggalMulaiStr() {
			return tanggalMulaiStr;
		}
		
		@JsonIgnore
		public Date getTanggalMulai() {
			return DateUtil.getDate(tanggalMulaiStr, "-");
		}

		public void setTanggalMulaiStr(String tanggalMulaiStr) {
			this.tanggalMulaiStr = tanggalMulaiStr;
		}

		public String getTanggalSelesaiStr() {
			return tanggalSelesaiStr;
		}
		
		@JsonIgnore
		public Date getTanggalSelesai() {
			return DateUtil.getDate(tanggalSelesaiStr, "-");
		}

		public void setTanggalSelesaiStr(String tanggalSelesaiStr) {
			this.tanggalSelesaiStr = tanggalSelesaiStr;
		}

		@Override
		public String toString() {
			return "Detail [nomorSk=" + nomorSk + ", tanggalMulaiStr="
					+ tanggalMulaiStr + ", tanggalSelesaiStr="
					+ tanggalSelesaiStr + "]";
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nomorSk == null) ? 0 : nomorSk.hashCode());
		result = prime * result + ((pegawai == null) ? 0 : pegawai.hashCode());
		result = prime * result
				+ ((tanggalMulai == null) ? 0 : tanggalMulai.hashCode());
		result = prime * result
				+ ((tanggalSelesai == null) ? 0 : tanggalSelesai.hashCode());
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
		Riwayat other = (Riwayat) obj;
		if (id != other.id)
			return false;
		if (nomorSk == null) {
			if (other.nomorSk != null)
				return false;
		} else if (!nomorSk.equals(other.nomorSk))
			return false;
		if (pegawai == null) {
			if (other.pegawai != null)
				return false;
		} else if (!pegawai.equals(other.pegawai))
			return false;
		if (tanggalMulai == null) {
			if (other.tanggalMulai != null)
				return false;
		} else if (!tanggalMulai.equals(other.tanggalMulai))
			return false;
		if (tanggalSelesai == null) {
			if (other.tanggalSelesai != null)
				return false;
		} else if (!tanggalSelesai.equals(other.tanggalSelesai))
			return false;
		return true;
	}
	
}
