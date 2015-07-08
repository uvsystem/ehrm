package com.unitedvision.sangihe.ehrm.simpeg;

public enum Pangkat {

	IA, IB, IC, ID,
	IIA, IIB, IIC, IID,
	IIIA, IIIB, IIIC, IIID,
	IVA, IVB, IVC, IVD, IVE;

	public String getNama() {
		
		switch(this) {
			case IIA:
				return "Pengatur Muda";
			case IIB:
				return "Pengatur Muda Tingkat 1";
			case IIC:
				return "Pengatur";
			case IID:
				return "Pengatur Tingkat I";
			case IIIA:
				return "Penata Muda";
			case IIIB:
				return "Penata Muda Tingkat 1";
			case IIIC:
				return "Penata";
			case IIID:
				return "Penata Tingkat 1";
			case IVA:
				return "Pembina";
			case IVB:
				return "Pembina Tingkat 1";
			case IVC:
				return "Pembina Utama Muda";
			case IVD:
				return "Pembina Utama Madya";
			case IVE:
				return "Pembina Utama";
			default:
				return "";
		}

	}
}
