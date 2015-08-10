package com.unitedvision.sangihe.ehrm.manajemen;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.ehrm.ApplicationConfig;
import com.unitedvision.sangihe.ehrm.ApplicationException;
import com.unitedvision.sangihe.ehrm.CodeUtil;
import com.unitedvision.sangihe.ehrm.EntityRestMessage;
import com.unitedvision.sangihe.ehrm.ListEntityRestMessage;
import com.unitedvision.sangihe.ehrm.RestMessage;

@Controller
@RequestMapping("/aplikasi")
public class AplikasiController {

	@Autowired
	private AplikasiService aplikasiService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public RestMessage tambahAplikasi(@RequestBody Aplikasi aplikasi) throws ApplicationException, PersistenceException {
		aplikasiService.simpan(aplikasi);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage hapusAplikasi(@PathVariable Long id) throws ApplicationException, PersistenceException {
		aplikasiService.hapus(id);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{kode}")
	@ResponseBody
	public EntityRestMessage<Aplikasi> getAplikasi(@PathVariable String kode) throws ApplicationException, PersistenceException {
		Aplikasi aplikasi = aplikasiService.getByKode(kode);
		
		return EntityRestMessage.create(aplikasi);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<Aplikasi> getAplikasi() throws ApplicationException, PersistenceException {
		List<Aplikasi> daftarAplikasi = aplikasiService.get();
		
		return ListEntityRestMessage.createListAplikasi(daftarAplikasi);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/kode")
	@ResponseBody
	public RestMessage getKodeAplikasi() throws ApplicationException, PersistenceException {
		return RestMessage.create(ApplicationConfig.KODE_APLIKASI);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/drive/token/{token}")
	@ResponseBody
	public RestMessage saveDriveToken(@PathVariable String token) throws ApplicationException, PersistenceException {
		CodeUtil.driveToken = token;
		
		return RestMessage.create(token);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/drive/token/")
	@ResponseBody
	public RestMessage saveDriveToken() throws ApplicationException, PersistenceException {
		String token = CodeUtil.driveToken;
		if ( token == null || token.length() <= 0 )
			token = "null";
		
		return RestMessage.create(token);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{kode}/operator/{nip}")
	@ResponseBody
	public RestMessage tambahOperator(@PathVariable String kode, @PathVariable String nip) throws ApplicationException, PersistenceException {
		aplikasiService.tambahOperator(nip, kode);

		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{kode}/operator")
	@ResponseBody
	public ListEntityRestMessage<Operator> getOperator(@PathVariable String kode) throws ApplicationException, PersistenceException {
		List<Operator> daftarOperator = aplikasiService.getOperator(kode);
		
		return ListEntityRestMessage.createListOperator(daftarOperator);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{kode}/admin/{nip}")
	@ResponseBody
	public RestMessage tambahAdmin(@PathVariable String kode, @PathVariable String nip) throws ApplicationException, PersistenceException {
		aplikasiService.tambahAdmin(nip, kode);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{kode}/admin")
	@ResponseBody
	public ListEntityRestMessage<Operator> getAdmin(@PathVariable String kode) throws ApplicationException, PersistenceException {
		List<Operator> daftarOperator = aplikasiService.getAdmin(kode);
		
		return ListEntityRestMessage.createListOperator(daftarOperator);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/operator/{id}")
	@ResponseBody
	public RestMessage hapusAdmin(@PathVariable Long id) throws ApplicationException, PersistenceException {
		aplikasiService.hapusOperator(id);
		
		return RestMessage.success();
	}
}
