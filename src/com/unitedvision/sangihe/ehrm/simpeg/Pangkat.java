package com.unitedvision.sangihe.ehrm.simpeg;

public enum Pangkat {
	
	IA("-"),
	IB("-"),
	IC("-"),
	ID("-"),
	IIA("Pengatur Muda"),
	IIB("Pengatur Muda Tingkat I"),
	IIC("Pengatur"),
	IID("Pengatur Tingkat I"),
	IIIA("Penata Muda"),
	IIIB("Penata Muda Tingkat I"),
	IIIC("Penata"),
	IIID("Penata Tingkat I"),
	IVA("Pembina"),
	IVB("Pembina Tingkat I"),
	IVC("Pembina Utama Muda"),
	IVD("Pembina Utama Madya"),
	IVE("Pembina Utama");

	private String nama;
	
	Pangkat(String nama) {
		this.nama = nama;
	}

	public String getNama() {
		return nama;
	}
}
