package com.unitedvision.sangihe.ehrm.interceptor;

import java.util.List;

import javax.persistence.PersistenceException;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryInterceptor {

	@AfterReturning(
		pointcut = "execution(public * com.unitedvision.sangihe.ehrm.*.repository.*.find*(..))",
		returning = "object"
	)
	public void nullEntityReturned(Object object) throws PersistenceException {
		
		if (object == null)
			throw new PersistenceException("Data tidak ditemukan");
		
		if ((object instanceof List) && ((List<?>)object).size() <= 0)
			throw new PersistenceException("Tidak ada data yang ditemukan");
	}

	@AfterThrowing(
		pointcut = "execution(public * com.unitedvision.sangihe.ehrm.*.repository.*.save(..))",
		throwing = "ex")
	public void errorThrown(PersistenceException ex) throws PersistenceException {
		throw new PersistenceException(createMessage(ex));
	}
	
	private String createMessage(Exception ex) {
		String message = ex.getMessage();
		System.out.println(String.format("Exception: %s", message));
		
		if (message.contains("username")) {
			return "Username yang anda masukan sudah digunakan";
		} else if (message.contains("nomor_induk_kependudukan")) {
			return "NIK yang anda masukan sudah digunakan";
		} else if (message.contains("penduduk_pekerja")) {
			return "Penduduk yang anda masukan sudah memiliki data pegawai";
		} else if (message.contains("nomor_induk_pegawai")) {
			return "NIP yang anda masukan sudah digunakan";
		} else if (message.contains("nama_pegawai")) {
			return "Nama yang anda masukan sudah digunakan";
		} else if (message.contains("akronim")) {
			return "Singkatan yang anda masukan sudah digunakan";
		} else if(message.contains("jabatan_unit")) {
			return "Jabatan yang anda masukan sudah digunakan";
		} else if(message.contains("absen_harian")) {
			return "Absen sudah terdaftar";
		} else if(message.contains("kode_aplikasi")) {
			return "Kode aplikasi sudah terdaftar";
		} else if(message.contains("nama_aplikasi")) {
			return "Nama aplikasi sudah terdaftar";
		} else if(message.contains("url_aplikasi")) {
			return "URL aplikasi sudah terdaftar";
		} else if(message.contains("kalendar")) {
			return "Tanggal belum terdaftar sebagai hari kerja";
		} else if(message.contains("nomor_surat_tugas")) {
			return "Nomor sudah digunakan";
		} else if(message.contains("operator_aplikasi")) {
			return "Operator sudah terdaftar";
		} else {
			return "Terdapat kesalahan";
		}
	}

}
