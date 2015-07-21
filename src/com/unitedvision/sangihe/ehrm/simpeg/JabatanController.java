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
@RequestMapping("/jabatan")
public class JabatanController {

	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/{idUnitKerja}")
	@ResponseBody
	public RestMessage save(@RequestBody Jabatan jabatan, @PathVariable Long idUnitKerja) throws ApplicationException, PersistenceException {
		jabatanService.simpan(idUnitKerja, jabatan);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public EntityRestMessage<Jabatan> findOne(@PathVariable Long id) throws ApplicationException, PersistenceException {
		Jabatan jabatan = jabatanService.get(id);
		
		return EntityRestMessage.create(jabatan);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage delete(@PathVariable Long id) throws ApplicationException, PersistenceException {
		jabatanService.hapus(id);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/satker/{idUnitKerja}")
	@ResponseBody
	public ListEntityRestMessage<Jabatan> findByUnitKerja(@PathVariable Long idUnitKerja) throws ApplicationException, PersistenceException {
		List<Jabatan> daftarJabatan = jabatanService.getByUnitKerja(idUnitKerja);
		
		return ListEntityRestMessage.createListJabatan(daftarJabatan);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/search/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<Jabatan> search(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Jabatan> daftarJabatan = jabatanService.cari(keyword);
		
		return ListEntityRestMessage.createListJabatan(daftarJabatan);
	}

}
