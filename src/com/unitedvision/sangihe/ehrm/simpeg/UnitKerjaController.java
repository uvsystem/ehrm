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
@RequestMapping("/satker")
public class UnitKerjaController {

	@Autowired
	private UnitKerjaService unitKerjaService;
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	@ResponseBody
	public RestMessage save(@RequestBody UnitKerja unitKerja) throws ApplicationException, PersistenceException {
		if (unitKerja.getParent() != null && unitKerja.getParent().getTipe() == null)
			unitKerja.setParent(null);
		unitKerjaService.simpan(unitKerja);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage delete(@PathVariable Long id) throws ApplicationException, PersistenceException {
		unitKerjaService.hapus(id);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public EntityRestMessage<UnitKerja> findOne(@PathVariable Long id) throws ApplicationException, PersistenceException {
		UnitKerja unitKerja = unitKerjaService.get(id);
		
		return EntityRestMessage.create(unitKerja);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{kode}")
	@ResponseBody
	public EntityRestMessage<UnitKerja> findByKode(@PathVariable String kode) throws ApplicationException, PersistenceException {
		UnitKerja unitKerja = unitKerjaService.get(kode);
		
		return EntityRestMessage.create(unitKerja);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/kode/{kode}")
	@ResponseBody
	public EntityRestMessage<UnitKerja> find(@PathVariable String kode) throws ApplicationException, PersistenceException {
		UnitKerja unitKerja = unitKerjaService.get(kode);
		
		return EntityRestMessage.create(unitKerja);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/search/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<UnitKerja> cari(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<UnitKerja> daftarUnitKerja = unitKerjaService.cari(keyword);
		
		return ListEntityRestMessage.createListUnitKerja(daftarUnitKerja);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{kode}/sub")
	@ResponseBody
	public ListEntityRestMessage<UnitKerja> findSubUnit(@PathVariable String kode) throws ApplicationException, PersistenceException {
		List<UnitKerja> daftarSubUnitKerja = unitKerjaService.getSubUnitKerja(kode);
		
		return ListEntityRestMessage.createListUnitKerja(daftarSubUnitKerja);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{kode}/sub")
	@ResponseBody
	public RestMessage tambahSubUnit(@PathVariable String kode, @RequestBody UnitKerja subUnitKerja) throws ApplicationException, PersistenceException {
		unitKerjaService.tambahSubUnit(kode, subUnitKerja);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<UnitKerja> get() throws ApplicationException, PersistenceException {
		List<UnitKerja> daftarUnitKerja = unitKerjaService.get();
		
		return ListEntityRestMessage.createListUnitKerja(daftarUnitKerja);
	}

}
