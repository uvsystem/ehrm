package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.ehrm.ApplicationException;
import com.unitedvision.sangihe.ehrm.EntityRestMessage;
import com.unitedvision.sangihe.ehrm.ListEntityRestMessage;
import com.unitedvision.sangihe.ehrm.RestMessage;

@Controller
@RequestMapping("/pegawai")
public class PegawaiController {

	@Autowired
	private PegawaiService pegawaiService;

	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/{idUnitKerja}")
	@ResponseBody
	public RestMessage save(@RequestBody Pegawai pegawai, @PathVariable Long idUnitKerja) throws ApplicationException, PersistenceException {
		System.out.println("DEBUG:");
		System.out.println(pegawai.toString());
		
		pegawaiService.simpan(idUnitKerja, pegawai);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{nip}")
	@ResponseBody
	public EntityRestMessage<Pegawai> findOne(@PathVariable String nip) throws ApplicationException, PersistenceException {
		Pegawai pegawai = pegawaiService.getByNip(nip);
		
		return EntityRestMessage.create(pegawai);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/satker/{idUnitKerja}")
	@ResponseBody
	public ListEntityRestMessage<Pegawai> findByUnitKerja(@PathVariable Long idUnitKerja) throws ApplicationException, PersistenceException {
		List<Pegawai> daftarPegawai = pegawaiService.getByUnitKerja(idUnitKerja);
		
		return ListEntityRestMessage.createListPegawai(daftarPegawai);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<Pegawai> search(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Pegawai> daftarPegawai = pegawaiService.cari(keyword);
		
		return ListEntityRestMessage.createListPegawai(daftarPegawai);
	}
	
}
