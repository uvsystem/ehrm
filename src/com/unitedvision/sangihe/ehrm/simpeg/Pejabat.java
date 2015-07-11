package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;

import com.unitedvision.sangihe.ehrm.NullCollectionException;

public interface Pejabat {

	Jabatan getJabatan() throws NullCollectionException;
	Eselon getEselon() throws NullCollectionException ;
	Date tanggalMulai() throws NullCollectionException ;

}
