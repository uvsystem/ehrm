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
@RequestMapping("/suratTugas")
public class SuratTugasController {

	@Autowired
	private SuratTugasService suratTugasService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}")
	@ResponseBody
	public RestMessage ajukanSuratTugas(@PathVariable String nip, @RequestBody SuratTugas.Message message) throws ApplicationException, PersistenceException {
		List<String> daftarPegawai = message.getDaftarPegawai();
		daftarPegawai.add(nip);
		
		SuratTugas suratTugas = new SuratTugas(message);
		
		suratTugasService.simpanWithNip(suratTugas, daftarPegawai);

		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{nomor}/terima")
	@ResponseBody
	public RestMessage terima(@PathVariable String nomor) throws ApplicationException, PersistenceException {
		suratTugasService.izinkanPengajuan(nomor);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{nomor}/tolak")
	@ResponseBody
	public RestMessage tolak(@PathVariable String nomor) throws ApplicationException, PersistenceException {
		suratTugasService.tolakPengajuan(nomor);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pending")
	@ResponseBody
	public ListEntityRestMessage<SuratTugas> getPending() throws ApplicationException, PersistenceException {
		List<SuratTugas> daftarSuratTugas = suratTugasService.getByStatus(SuratTugas.Status.PENDING);
		
		return ListEntityRestMessage.createListSuratTugas(daftarSuratTugas);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/terima")
	@ResponseBody
	public ListEntityRestMessage<SuratTugas> getTerima() throws ApplicationException, PersistenceException {
		List<SuratTugas> daftarSuratTugas = suratTugasService.getByStatus(SuratTugas.Status.DITERIMA);
		
		return ListEntityRestMessage.createListSuratTugas(daftarSuratTugas);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/tolak")
	@ResponseBody
	public ListEntityRestMessage<SuratTugas> getTolak() throws ApplicationException, PersistenceException {
		List<SuratTugas> daftarSuratTugas = suratTugasService.getByStatus(SuratTugas.Status.DITOLAK);
		
		return ListEntityRestMessage.createListSuratTugas(daftarSuratTugas);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{nip}")
	@ResponseBody
	public ListEntityRestMessage<SuratTugas> get(@PathVariable String nip) throws ApplicationException, PersistenceException {
		List<SuratTugas> daftarSuratTugas = suratTugasService.getByPegawai(nip);;
		
		return ListEntityRestMessage.createListSuratTugas(daftarSuratTugas);
	}

}
