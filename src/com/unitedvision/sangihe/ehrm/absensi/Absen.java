package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

@Entity
@Table(name = "absen")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	name = "status",
	discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("ABSEN")
public abstract class Absen {

	private long id;
	private Kalendar kalendar;
	private Pegawai pegawai;
	
	public Absen() {
		super();
		kalendar = new Kalendar();
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
	@JoinColumn(name = "kalendar", nullable = false)
	public Kalendar getKalendar() {
		return kalendar;
	}

	public void setKalendar(Kalendar kalendar) {
		this.kalendar = kalendar;
	}

	@Transient
	public Date getTanggal() {
		return kalendar.getTanggal();
	}
	
	public void setTanggal(Date tanggal) {
		kalendar.setTanggal(tanggal);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pegawai", nullable = false)
	public Pegawai getPegawai() {
		return pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	public static class Detail {
		private String nip;
		private String tanggalStr;
		private String jamStr;

		public Detail() {
			super();
		}
		
		public Detail(String nip, String tanggalStr, String jamStr) {
			super();
			this.nip = nip;
			this.tanggalStr = tanggalStr;
			this.jamStr = jamStr;
		}
		
		public String getNip() {
			return nip;
		}

		void setNip(String nip) {
			this.nip = nip;
		}
		
		public String getTanggalStr() {
			return tanggalStr;
		}
		
		@JsonIgnore
		public Date getTanggal() {
			return DateUtil.getDate(tanggalStr, "-");
		}
		
		public void setTanggalStr(String tanggalStr) {
			this.tanggalStr = tanggalStr;
		}
		
		public String getJamStr() {
			return jamStr;
		}
		
		@JsonIgnore
		public Time getJam() {
			return DateUtil.getTime(jamStr, ":");
		}
		
		public void setJamStr(String jamStr) {
			this.jamStr = jamStr;
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((kalendar == null) ? 0 : kalendar.hashCode());
		result = prime * result + ((pegawai == null) ? 0 : pegawai.hashCode());
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
		Absen other = (Absen) obj;
		if (id != other.id)
			return false;
		if (kalendar == null) {
			if (other.kalendar != null)
				return false;
		} else if (!kalendar.equals(other.kalendar))
			return false;
		if (pegawai == null) {
			if (other.pegawai != null)
				return false;
		} else if (!pegawai.equals(other.pegawai))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Absen [id=" + id + ", kalendar=" + kalendar + ", pegawai="
				+ pegawai + "]";
	}

	@Transient
	public abstract String getTipe();
}
