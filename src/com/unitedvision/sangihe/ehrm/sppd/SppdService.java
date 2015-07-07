package com.unitedvision.sangihe.ehrm.sppd;

import java.util.List;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public interface SppdService {

	Sppd simpan(Sppd sppd);
	
	Sppd tambahPengikut(Sppd sppd, Pengikut pengikut);
	Sppd tambahPengikut(long idSppd, Pengikut pengikut);

	void hapus(Sppd sppd);
	void hapus(long idSppd);

	Sppd get(long idSppd);
	Sppd get(String nomorSppd);

	List<Sppd> getByPegawai(Pegawai pegawai);
	List<Sppd> getByPegawai(String nip);

}
