package com.unitedvision.sangihe.ehrm;

import java.util.List;

import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.SubUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

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

	public static ListEntityRestMessage<SubUnitKerja> createListSubUnitKerja(List<SubUnitKerja> daftarSubUnitKerja) {
		return new ListEntityRestMessage<SubUnitKerja>(daftarSubUnitKerja);
	}

}
