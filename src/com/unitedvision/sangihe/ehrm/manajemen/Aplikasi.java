package com.unitedvision.sangihe.ehrm.manajemen;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

@Entity
@Table(name = "aplikasi")
public class Aplikasi {

	private long id;
	private String kode;
	private String nama;
	private String url;
	
	private List<Operator> daftarOperator;
	
	public Aplikasi() {
		super();
		daftarOperator = new ArrayList<>();
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "kode")
	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@OneToMany(mappedBy = "aplikasi", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<Operator> getDaftarOperator() {
		return daftarOperator;
	}

	public void setDaftarOperator(List<Operator> daftarOperator) {
		this.daftarOperator = daftarOperator;
	}
	
	public void addOperator(Operator operator) {
		operator.setAplikasi(this);
		daftarOperator.add(operator);
	}
	
	public void addOperator(Pegawai pegawai, Role role) {
		Operator operator = new Operator();
		operator.setPegawai(pegawai);
		operator.setRole(role);
		
		addOperator(operator);
	}
	
	public void removeOperator(Operator operator) {
		operator.setAplikasi(null);
		daftarOperator.remove(operator);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((kode == null) ? 0 : kode.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Aplikasi other = (Aplikasi) obj;
		if (daftarOperator == null) {
			if (other.daftarOperator != null)
				return false;
		} else if (!daftarOperator.equals(other.daftarOperator))
			return false;
		if (id != other.id)
			return false;
		if (kode == null) {
			if (other.kode != null)
				return false;
		} else if (!kode.equals(other.kode))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
