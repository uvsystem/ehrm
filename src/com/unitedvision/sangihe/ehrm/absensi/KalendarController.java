package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.ehrm.ApplicationException;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.EntityRestMessage;
import com.unitedvision.sangihe.ehrm.ListEntityRestMessage;
import com.unitedvision.sangihe.ehrm.RestMessage;

@Controller
@RequestMapping("/kalendar")
public class KalendarController {

	@Autowired
	private KalendarService kalendarService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{tanggal}")
	@ResponseBody
	public RestMessage tambah(@PathVariable String tanggal) throws ApplicationException, PersistenceException {
		Date date = DateUtil.getDate(tanggal, "-");
		kalendarService.tambah(date);
		
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{tanggal}")
	@ResponseBody
	public EntityRestMessage<Kalendar> get(@PathVariable String tanggal) throws ApplicationException, PersistenceException {
		Date date = DateUtil.getDate(tanggal, "-");
		Kalendar kalendar = kalendarService.get(date);
		
		return EntityRestMessage.create(kalendar);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{tanggal}")
	@ResponseBody
	public RestMessage hapus(@PathVariable String tanggal) throws ApplicationException, PersistenceException {
		Date date = DateUtil.getDate(tanggal, "-");
		kalendarService.hapus(date);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{tanggal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<Kalendar> get(@PathVariable String tanggal, @PathVariable String akhir) throws ApplicationException, PersistenceException {
		Date date = DateUtil.getDate(tanggal, "-");
		Date date2 = DateUtil.getDate(akhir, "-");
		
		List<Kalendar> daftarKalendar = kalendarService.get(date, date2);
		
		return ListEntityRestMessage.createListKalendar(daftarKalendar);
	}
}
