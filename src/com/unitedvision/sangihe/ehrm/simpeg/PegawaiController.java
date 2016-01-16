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
import com.unitedvision.sangihe.ehrm.manajemen.PasswordWrapper;

@Controller
@RequestMapping("/pegawai")
public class PegawaiController {

	@Autowired
	private PegawaiService pegawaiService;

	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/{idUnitKerja}")
	@ResponseBody
	public RestMessage save(@RequestBody Pegawai pegawai, @PathVariable Long idUnitKerja) throws ApplicationException, PersistenceException {
		pegawaiService.simpan(idUnitKerja, pegawai);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage delete(@PathVariable Long id) throws ApplicationException, PersistenceException {
		pegawaiService.hapus(id);
		
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

	@RequestMapping(method = RequestMethod.GET, value = "/satker/{idUnitKerja}/minim")
	@ResponseBody
	public ListEntityRestMessage<Pegawai> findByUnitKerjaMinim(@PathVariable Long idUnitKerja) throws ApplicationException, PersistenceException {
		List<Pegawai> daftarPegawai = pegawaiService.getByUnitKerja(idUnitKerja);
		for (Pegawai pegawai : daftarPegawai)
			pegawai.setPassword("");
		
		return ListEntityRestMessage.createListPegawai(daftarPegawai);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ListEntityRestMessage<Pegawai> findAll() throws ApplicationException, PersistenceException {
		List<Pegawai> daftarPegawai = pegawaiService.get();
		
		return ListEntityRestMessage.createListPegawai(daftarPegawai);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<Pegawai> search(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Pegawai> daftarPegawai = pegawaiService.cari(keyword);
		
		return ListEntityRestMessage.createListPegawai(daftarPegawai);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/mutasi/{kode}")
	@ResponseBody
	public EntityRestMessage<Pegawai> mutasi(@PathVariable String nip, @PathVariable String kode) throws ApplicationException, PersistenceException {
		Pegawai pegawai = pegawaiService.mutasi(nip, kode);
		
		return EntityRestMessage.create(pegawai);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/pangkat/{pangkat}")
	@ResponseBody
	public EntityRestMessage<Pegawai> promosi(@PathVariable String nip, @PathVariable Pangkat pangkat, @RequestBody Riwayat.Detail detail) throws ApplicationException, PersistenceException {
		Pegawai pegawai = pegawaiService.promosi(nip, pangkat, detail);
		
		return EntityRestMessage.create(pegawai);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{nip}/pangkat/{pangkat}")
	@ResponseBody
	public RestMessage deleteRiwayatPangkat(@PathVariable String nip, @PathVariable Pangkat pangkat) throws ApplicationException, PersistenceException {
		pegawaiService.hapusRiwayatPangkat(nip, pangkat);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/jabatan/{idJabatan}")
	@ResponseBody
	public EntityRestMessage<Pegawai> promosiJabatan(@PathVariable String nip, @PathVariable Long idJabatan, @RequestBody Riwayat.Detail detail) throws ApplicationException, PersistenceException {
		Pegawai pegawai = pegawaiService.promosi(nip, idJabatan, detail);
		
		return EntityRestMessage.create(pegawai);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{nip}/jabatan/{idJabatan}")
	@ResponseBody
	public RestMessage hapusRiwayatJabatan(@PathVariable String nip, @PathVariable Long idJabatan) throws ApplicationException, PersistenceException {
		pegawaiService.hapusRiwayatJabatan(nip, idJabatan);

		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pangkat/{pangkat}")
	@ResponseBody
	public ListEntityRestMessage<Pegawai> findByPangkat(@PathVariable Pangkat pangkat) throws ApplicationException, PersistenceException {
		List<Pegawai> daftarPegawai = pegawaiService.get(pangkat);
		
		return ListEntityRestMessage.createListPegawai(daftarPegawai);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/eselon/{eselon}")
	@ResponseBody
	public ListEntityRestMessage<Pegawai> findByEselon(@PathVariable Eselon eselon) throws ApplicationException, PersistenceException {
		List<Pegawai> daftarPegawai = pegawaiService.get(eselon);
		
		return ListEntityRestMessage.createListPegawai(daftarPegawai);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/password")
	@ResponseBody
	public RestMessage updatePassword(@PathVariable Long id, @RequestBody PasswordWrapper passwordWrapper) throws ApplicationException, PersistenceException {
		pegawaiService.updatePassword(id, passwordWrapper.getPassword());
		
		return RestMessage.success();
	}
	
}
