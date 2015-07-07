package com.unitedvision.sangihe.ehrm.sppd;

import java.util.List;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public interface SuratTugasService {

	SuratTugas simpan(SuratTugas suratTugas);
	SuratTugas simpan(SuratTugas suratTugas, List<Pegawai> daftarPegawai);
	
	SuratTugas tambahPegawai(SuratTugas suratTugas, Pegawai pegawai);
	SuratTugas tambahPegawai(long idSuratTugas, Pegawai pegawai);
	
	void hapus(SuratTugas suratTugas);
	void hapus(long idSuratTugas);
	
	SuratTugas get(long idSuratTugas);
	SuratTugas get(String nomorSuratTugas);
	
	List<SuratTugas> getByPegawai(Pegawai pegawai);
	List<SuratTugas> getByPegawai(String nip);

}
