package com.unitedvision.sangihe.ehrm.absensi;

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
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.ListEntityRestMessage;
import com.unitedvision.sangihe.ehrm.RestMessage;
import com.unitedvision.sangihe.ehrm.absensi.Absen.Detail;

@Controller
@RequestMapping("/absen")
public class AbsenController {

	@Autowired
	private AbsenService absenService;

	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/pagi")
	@ResponseBody
	public RestMessage pagi(@PathVariable String nip) throws ApplicationException, PersistenceException {
		absenService.apelPagi(nip, DateUtil.getDate(), DateUtil.getTime());
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/pagi/{tanggal}/{jam}")
	@ResponseBody
	public RestMessage pagi(@PathVariable String nip, @PathVariable String tanggal, @PathVariable String jam) throws ApplicationException, PersistenceException {
		absenService.apelPagi(nip, DateUtil.getDate(tanggal, "-"), DateUtil.getTime(jam, ":"));
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/pagi")
	@ResponseBody
	public RestMessage pagi(@RequestBody List<Detail> daftarAbsen) throws ApplicationException, PersistenceException {
		absenService.apelPagi(daftarAbsen);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/cek1")
	@ResponseBody
	public RestMessage pengecekanPertama(@PathVariable String nip) throws ApplicationException, PersistenceException {
		absenService.pengecekanSatu(nip, DateUtil.getDate(), DateUtil.getTime());
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/cek1/{tanggal}/{jam}")
	@ResponseBody
	public RestMessage pengecekanPertama(@PathVariable String nip, @PathVariable String tanggal, @PathVariable String jam) throws ApplicationException, PersistenceException {
		absenService.pengecekanSatu(nip, DateUtil.getDate(tanggal, "-"), DateUtil.getTime(jam, ":"));
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/cek1")
	@ResponseBody
	public RestMessage cek1(@RequestBody List<Detail> daftarAbsen) throws ApplicationException, PersistenceException {
		absenService.pengecekanSatu(daftarAbsen);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/cek2")
	@ResponseBody
	public RestMessage pengecekanKedua(@PathVariable String nip) throws ApplicationException, PersistenceException {
		absenService.pengecekanDua(nip, DateUtil.getDate(), DateUtil.getTime());
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/cek2/{tanggal}/{jam}")
	@ResponseBody
	public RestMessage pengecekanDua(@PathVariable String nip, @PathVariable String tanggal, @PathVariable String jam) throws ApplicationException, PersistenceException {
		absenService.pengecekanDua(nip, DateUtil.getDate(tanggal, "-"), DateUtil.getTime(jam, ":"));
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/cek2")
	@ResponseBody
	public RestMessage cek2(@RequestBody List<Detail> daftarAbsen) throws ApplicationException, PersistenceException {
		absenService.pengecekanDua(daftarAbsen);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/sore")
	@ResponseBody
	public RestMessage sore(@PathVariable String nip) throws ApplicationException, PersistenceException {
		absenService.apelSore(nip, DateUtil.getDate(), DateUtil.getTime());
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/sore/{tanggal}/{jam}")
	@ResponseBody
	public RestMessage sore(@PathVariable String nip, @PathVariable String tanggal, @PathVariable String jam) throws ApplicationException, PersistenceException {
		absenService.apelSore(nip, DateUtil.getDate(tanggal, "-"), DateUtil.getTime(jam, ":"));
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/sore")
	@ResponseBody
	public RestMessage sore(@RequestBody List<Detail> daftarAbsen) throws ApplicationException, PersistenceException {
		absenService.apelSore(daftarAbsen);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/hadir/{tanggal}")
	@ResponseBody
	public RestMessage hadir(@PathVariable String nip, @PathVariable String tanggal, @RequestBody Hadir.Detail detail) throws ApplicationException, PersistenceException {
		absenService.hadir(nip, DateUtil.getDate(tanggal, "-"), detail);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/sakit/{tanggal}")
	@ResponseBody
	public RestMessage sakit(@PathVariable String nip, @PathVariable String tanggal) throws ApplicationException, PersistenceException {
		absenService.tambahSakit(nip, DateUtil.getDate(tanggal, "-"), "");
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/izin/{tanggal}")
	@ResponseBody
	public RestMessage izin(@PathVariable String nip, @PathVariable String tanggal) throws ApplicationException, PersistenceException {
		absenService.tambahIzin(nip, DateUtil.getDate(tanggal, "-"), "");
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{nip}/cuti/{tanggal}")
	@ResponseBody
	public RestMessage cuti(@PathVariable String nip, @PathVariable String tanggal) throws ApplicationException, PersistenceException {
		absenService.tambahCuti(nip, DateUtil.getDate(tanggal, "-"), "");
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/satker/{kode}/tanggal/{tanggal}")
	@ResponseBody
	public ListEntityRestMessage<Absen> get(@PathVariable String kode, @PathVariable String tanggal) throws ApplicationException, PersistenceException {
		List<Absen> listAbsen = absenService.find(kode, DateUtil.getDate(tanggal, "-"));
		
		return ListEntityRestMessage.createListAbsen(listAbsen);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/search/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<Absen> cari(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Absen> listAbsen = absenService.cari(keyword);
		
		return ListEntityRestMessage.createListAbsen(listAbsen);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}/status/{status}")
	@ResponseBody
	public RestMessage delete(@PathVariable Long id, @PathVariable String status) throws ApplicationException, PersistenceException {
		absenService.hapus(id, status);
		
		return RestMessage.success();
	}
	
}
