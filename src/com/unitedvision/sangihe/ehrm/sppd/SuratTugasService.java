package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;
import java.util.List;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas.Status;

public interface SuratTugasService {
	
	SuratTugas tambah(SuratTugas suratTugas);
	SuratTugas tambah(SuratTugas suratTugas, List<Pegawai> daftarPegawai);
	SuratTugas tambahWithNip(SuratTugas suratTugas, List<String> daftarPegawai);

	SuratTugas ajukan(SuratTugas suratTugas);
	SuratTugas ajukan(SuratTugas suratTugas, List<Pegawai> daftarPegawai);
	SuratTugas ajukanWithNip(SuratTugas suratTugas, List<String> daftarPegawai);
	
	SuratTugas izinkanPengajuan(SuratTugas suratTugas);
	SuratTugas izinkanPengajuan(String nomor);

	SuratTugas tolakPengajuan(SuratTugas suratTugas);
	SuratTugas tolakPengajuan(String nomor);
	
	SuratTugas tambahPegawai(long idSuratTugas, String nip);
	SuratTugas tambahPegawai(SuratTugas suratTugas, Pegawai pegawai);
	
	void hapus(SuratTugas suratTugas);
	void hapus(long idSuratTugas);
	
	SuratTugas get(long idSuratTugas);
	SuratTugas get(String nomorSuratTugas);

	List<SuratTugas> get(Date tanggalAwal, Date tanggalAkhir);
	
	List<SuratTugas> getByPegawai(Pegawai pegawai);
	List<SuratTugas> getByPegawai(String nip);
	List<SuratTugas> getByStatus(Status status);
	List<SuratTugas> getBySatker(String kode);

	List<SuratTugas> cari(String keyword);

	void hapus(Long id);
	
	@Deprecated
	List<RekapSppd> rekap(Integer tahun);
	List<RekapSppd> rekap(Date awal, Date akhir);
}
