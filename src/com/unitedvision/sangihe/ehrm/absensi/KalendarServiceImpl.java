package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.absensi.repository.KalendarRepository;

@Service
@Transactional(readOnly = true)
public class KalendarServiceImpl implements KalendarService {
	
	@Autowired
	private KalendarRepository kalendarRepository;

	@Override
	@Transactional(readOnly = false)
	public Kalendar tambah(Kalendar kalendar) {
		return kalendarRepository.save(kalendar);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Kalendar tambah(Date tanggal) {
		Kalendar kalendar = new Kalendar(tanggal);
		
		return tambah(kalendar);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Kalendar kalendar) {
		kalendarRepository.delete(kalendar);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Date tanggal) {
		kalendarRepository.delete(tanggal);
	}
	
	@Override
	public Kalendar get(Date tanggal) {
		return kalendarRepository.findOne(tanggal);
	}

	@Override
	public List<Kalendar> get(Month bulan, int tahun) {
		Date awal = DateUtil.getFirstDate(bulan, tahun);
		Date akhir = DateUtil.getLastDate(bulan, tahun);
		
		return get(awal, akhir);
	}

	@Override
	public List<Kalendar> get(Date awal, Date akhir) {
		return kalendarRepository.findByTanggalBetween(awal, akhir);
	}

}
