package com.unitedvision.sangihe.ehrm.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unitedvision.sangihe.ehrm.sppd.Sppd;

public class SppdTest {

	@Test
	public void testCreateFromMessage() {

		Sppd.Message message = new Sppd.Message();
		message.setId(0L);
		message.setKodeRekening("01.00.25612.01");
		message.setModaTransportasi("Kapal Laut");
		message.setNomor("001/SPT/BPDE/I/2015");
		message.setNomorDpa("001/DPA/BPDE");
		message.setTanggalBerangkatStr("01-05-2015");
		message.setTingkat("Penting");
		
		Sppd sppd = new Sppd(message);
		
		assertNotNull(sppd);
	}
}
