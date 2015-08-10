package com.unitedvision.sangihe.ehrm;

import java.util.List;

import com.unitedvision.sangihe.ehrm.absensi.Absen;
import com.unitedvision.sangihe.ehrm.absensi.Kalendar;
import com.unitedvision.sangihe.ehrm.absensi.RekapAbsen;
import com.unitedvision.sangihe.ehrm.manajemen.Aplikasi;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;
import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.sppd.RekapSppd;
import com.unitedvision.sangihe.ehrm.sppd.Sppd;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas;

public class ListEntityRestMessage<T> extends RestMessage {
	private List<T> list;
	
	protected ListEntityRestMessage(Exception ex) {
		super(ex);
	}

	protected ListEntityRestMessage(List<T> list) {
		super("Berhasil", Type.LIST);
		this.list = list;
	}
	
	public List<T> getList() {
		return list;
	}
	
	public static <T> ListEntityRestMessage<T> listEntityError(Exception cause) {
		return new ListEntityRestMessage<T>(cause);
	}
	
	public static ListEntityRestMessage<UnitKerja> createListUnitKerja(List<UnitKerja> daftarUnitKerja) {
		return new ListEntityRestMessage<UnitKerja>(daftarUnitKerja);
	}
	
	public static ListEntityRestMessage<Jabatan> createListJabatan(List<Jabatan> daftarJabatan) {
		return new ListEntityRestMessage<Jabatan>(daftarJabatan);
	}
	
	public static ListEntityRestMessage<Pegawai> createListPegawai(List<Pegawai> daftarPegawai) {
		return new ListEntityRestMessage<Pegawai>(daftarPegawai);
	}

	public static ListEntityRestMessage<Kalendar> createListKalendar(List<Kalendar> daftarKalendar) {
		return new ListEntityRestMessage<Kalendar>(daftarKalendar);
	}

	public static ListEntityRestMessage<SuratTugas> createListSuratTugas(List<SuratTugas> daftarSuratTugas) {
		return new ListEntityRestMessage<SuratTugas>(daftarSuratTugas);
	}

	public static ListEntityRestMessage<Sppd> createListSppd(List<Sppd> daftarSppd) {
		return new ListEntityRestMessage<Sppd>(daftarSppd);
	}

	public static ListEntityRestMessage<Aplikasi> createListAplikasi(List<Aplikasi> daftarAplikasi) {
		return new ListEntityRestMessage<Aplikasi>(daftarAplikasi);
	}

	public static ListEntityRestMessage<Operator> createListOperator(List<Operator> daftarOperator) {
		return new ListEntityRestMessage<Operator>(daftarOperator);
	}

	public static ListEntityRestMessage<Absen> createListAbsen(List<Absen> daftarAbsen) {
		return new ListEntityRestMessage<Absen>(daftarAbsen);
	}

	public static ListEntityRestMessage<RekapAbsen> createListRekapAbsen(List<RekapAbsen> daftarRekap) {
		return new ListEntityRestMessage<RekapAbsen>(daftarRekap);
	}

	public static ListEntityRestMessage<RekapSppd> createListRekapSppd(List<RekapSppd> daftarRekap) {
		return new ListEntityRestMessage<RekapSppd>(daftarRekap);
	}

}
