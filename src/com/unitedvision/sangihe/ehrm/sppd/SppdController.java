package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;
import java.time.Month;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.unitedvision.sangihe.ehrm.ApplicationException;
import com.unitedvision.sangihe.ehrm.DateUtil;
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
		nomor = nomor.replaceAll("-", "/");
		sppdService.simpan(nip, nomor, message);

		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{nomor}/pengikut")
	@ResponseBody
	public RestMessage tambahPengikut(@PathVariable String nomor, @RequestBody Pengikut.Message message) throws ApplicationException, PersistenceException {
		nomor = nomor.replaceAll("-", "/");
		sppdService.tambahPengikut(nomor, new Pengikut(message));

		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{nip}")
	@ResponseBody
	public ListEntityRestMessage<Sppd> get(@PathVariable String nip) throws ApplicationException, PersistenceException {
		List<Sppd> daftarSppd = sppdService.getByPegawai(nip);
		
		return ListEntityRestMessage.createListSppd(daftarSppd);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/awal/{awal}/akhir/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<Sppd> get(@PathVariable String awal, @PathVariable String akhir) throws ApplicationException, PersistenceException {
		Date tanggalAwal = DateUtil.getDate(awal, "-");
		Date tanggalAkhir = DateUtil.getDate(akhir, "-");
		
		List<Sppd> daftarSppd = sppdService.getByTanggal(tanggalAwal, tanggalAkhir);
		
		return ListEntityRestMessage.createListSppd(daftarSppd);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/satker/{kode}")
	@ResponseBody
	public ListEntityRestMessage<Sppd> getByUnitKerja(@PathVariable String kode) throws ApplicationException, PersistenceException {
		List<Sppd> daftarSppd = sppdService.getUnitKerja(kode);
		
		return ListEntityRestMessage.createListSppd(daftarSppd);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/{keyword}")
	@ResponseBody
	public ListEntityRestMessage<Sppd> search(@PathVariable String keyword) throws ApplicationException, PersistenceException {
		List<Sppd> daftarSppd = sppdService.cari(keyword);
		
		return ListEntityRestMessage.createListSppd(daftarSppd);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage delete(@PathVariable Long id) throws ApplicationException, PersistenceException {
		sppdService.hapus(id);
		
		return RestMessage.success();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}")
	@ResponseBody
	public ListEntityRestMessage<RekapSppd> rekapView(@PathVariable Integer tahun) throws ApplicationException, PersistenceException {
		Date awal = DateUtil.getDate(tahun, Month.JANUARY, 1);
		Date akhir = DateUtil.getDate(tahun, Month.DECEMBER, 31);

		List<RekapSppd> rekap = sppdService.rekap(awal, akhir);
		
		return ListEntityRestMessage.createListRekapSppd(rekap);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rekap/tahun/{tahun}/cetak")
	public ModelAndView rekap(@PathVariable Integer tahun, Map<String, Object> model) throws ApplicationException, PersistenceException {
		Date awal = DateUtil.getDate(tahun, Month.JANUARY, 1);
		Date akhir = DateUtil.getDate(tahun, Month.DECEMBER, 31);

		try {
			List<RekapSppd> rekap = sppdService.rekap(awal, akhir);
			
			model.put("rekap", rekap);
			model.put("tahun", tahun);
			model.put("judul", "SPPD & Tugas Luar");

			return new ModelAndView("rekapSppd", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rekap/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<RekapSppd> rekapView(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<RekapSppd> rekap = sppdService.rekap(awal, akhir);
		
		return ListEntityRestMessage.createListRekapSppd(rekap);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rekap/{awal}/to/{akhir}/cetak")
	public ModelAndView rekap(@PathVariable Date awal, @PathVariable Date akhir, Map<String, Object> model) throws ApplicationException, PersistenceException {
		try {
			List<RekapSppd> rekap = sppdService.rekap(awal, akhir);
			
			model.put("rekap", rekap);
			model.put("awal", awal);
			model.put("akhir", akhir);
			model.put("judul", "SPPD & Tugas Luar");

			return new ModelAndView("rekapSppd", model);
		} catch (PersistenceException e) {
			return new ModelAndView("pdfException", model);
		}
	}
}
