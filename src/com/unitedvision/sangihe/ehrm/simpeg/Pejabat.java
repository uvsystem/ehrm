package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;

import com.unitedvision.sangihe.ehrm.NullCollectionException;

public interface Pejabat {

	Jabatan getJabatan() throws NullCollectionException, NoJabatanException;
	Eselon getEselon() throws NullCollectionException, NoJabatanException;
	String getNamaJabatan() throws NullCollectionException, NoJabatanException;
	Date getTanggalMenjabat() throws NullCollectionException, NoJabatanException;

}
