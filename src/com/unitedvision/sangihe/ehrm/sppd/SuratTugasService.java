package com.unitedvision.sangihe.ehrm.sppd;

import java.util.List;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas.Status;

public interface SuratTugasService {

	SuratTugas simpan(SuratTugas suratTugas);
	SuratTugas simpan(SuratTugas suratTugas, List<Pegawai> daftarPegawai);
	SuratTugas simpanWithNip(SuratTugas suratTugas, List<String> daftarPegawai);
	
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
	
	List<SuratTugas> getByPegawai(Pegawai pegawai);
	List<SuratTugas> getByPegawai(String nip);

	List<SuratTugas> getByStatus(Status pending);

}
