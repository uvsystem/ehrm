package com.unitedvision.sangihe.ehrm;

import com.unitedvision.sangihe.ehrm.absensi.Kalendar;
import com.unitedvision.sangihe.ehrm.manajemen.Aplikasi;
import com.unitedvision.sangihe.ehrm.manajemen.Token;
import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public class EntityRestMessage<T> extends RestMessage {
	private T model;
	
	protected EntityRestMessage(Exception ex) {
		super(ex);
	}
	
	protected EntityRestMessage(T model) {
		super("Berhasil", Type.ENTITY, model);
		this.model = model;
	}
	
	public T getModel() {
		return model;
	}
	
	public static <T> EntityRestMessage<T> entityError(Exception cause) {
		return new EntityRestMessage<T>(cause);
	}
	
	public static EntityRestMessage<UnitKerja> create(UnitKerja unitKerja) {
		return new EntityRestMessage<UnitKerja>(unitKerja);
	}
	
	public static EntityRestMessage<Jabatan> create(Jabatan jabatan) {
		return new EntityRestMessage<Jabatan>(jabatan);
	}
	
	public static EntityRestMessage<Pegawai> create(Pegawai pegawai) {
		return new EntityRestMessage<Pegawai>(pegawai);
	}

	public static EntityRestMessage<Kalendar> create(Kalendar kalendar) {
		return new EntityRestMessage<Kalendar>(kalendar);
	}
	
	public static EntityRestMessage<Aplikasi> create(Aplikasi aplikasi) {
		return new EntityRestMessage<Aplikasi>(aplikasi);
	}
	
	public static EntityRestMessage<Token> create(Token token) {
		return new EntityRestMessage<Token>(token);
	}
}
