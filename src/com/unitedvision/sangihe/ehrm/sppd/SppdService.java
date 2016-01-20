package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;
import java.util.List;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.sppd.Sppd.Message;

public interface SppdService {

	Sppd simpan(Sppd sppd);
	Sppd simpan(String nip, String nomor, Message message);
	
	Sppd tambahPengikut(Sppd sppd, Pengikut pengikut);
	Sppd tambahPengikut(long idSppd, Pengikut pengikut);
	Sppd tambahPengikut(String nomor, Pengikut pengikut);
	Sppd tambahPengikut(Sppd sppd, List<Pengikut> daftarPengikut);

	void hapus(Sppd sppd);
	void hapus(Long id);

	Sppd get(long idSppd);
	Sppd get(String nomorSppd);

	List<Sppd> getByPegawai(Pegawai pegawai);
	List<Sppd> getByPegawai(String nip);
	List<Sppd> getByTanggal(Date awal, Date akhir);
	List<Sppd> getUnitKerja(String kode);

	List<Sppd> cari(String kode);

	@Deprecated
	List<RekapSppd> rekap(Integer tahun);
	List<RekapSppd> rekap(Date awal, Date akhir);
	
}
