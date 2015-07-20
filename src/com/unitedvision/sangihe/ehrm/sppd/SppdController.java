package com.unitedvision.sangihe.ehrm.sppd;

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
import com.unitedvision.sangihe.ehrm.ListEntityRestMessage;
import com.unitedvision.sangihe.ehrm.RestMessage;

@Controller
@RequestMapping("/sppd")
public class SppdController {

	@Autowired
	private SppdService sppdService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/suratTugas/{nomor}")
	@ResponseBody
	public RestMessage simpan(@PathVariable String nip, @PathVariable String nomor, @RequestBody Sppd.Message message) throws ApplicationException, PersistenceException {
		sppdService.simpan(nip, nomor, message);

		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{nomor}/pengikut")
	@ResponseBody
	public RestMessage tambahPengikut(@PathVariable String nomor, @RequestBody Pengikut.Message message) throws ApplicationException, PersistenceException {
		sppdService.tambahPengikut(nomor, new Pengikut(message));

		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{nip}")
	@ResponseBody
	public ListEntityRestMessage<Sppd> get(@PathVariable String nip) throws ApplicationException, PersistenceException {
		List<Sppd> daftarSppd = sppdService.getByPegawai(nip);
		
		return ListEntityRestMessage.createListSppd(daftarSppd);
	}
}
