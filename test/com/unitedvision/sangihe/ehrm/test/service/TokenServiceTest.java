package com.unitedvision.sangihe.ehrm.test.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.ApplicationConfig;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.OutOfDateEntityException;
import com.unitedvision.sangihe.ehrm.UnauthenticatedAccessException;
import com.unitedvision.sangihe.ehrm.manajemen.Aplikasi;
import com.unitedvision.sangihe.ehrm.manajemen.AplikasiService;
import com.unitedvision.sangihe.ehrm.manajemen.Token;
import com.unitedvision.sangihe.ehrm.manajemen.TokenService;
import com.unitedvision.sangihe.ehrm.manajemen.Token.StatusToken;
import com.unitedvision.sangihe.ehrm.manajemen.repository.TokenRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class TokenServiceTest {

	@Autowired
	private AplikasiService aplikasiService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private TokenRepository tokenRepository;

	private Token token;
	private Pegawai pegawai;
	
	@Before
	public void setup() throws UnauthenticatedAccessException {
		Aplikasi aplikasi = new Aplikasi();
		aplikasi.setKode("SIMPEG");
		aplikasi.setNama("Sistem Informasi Manajemen Pegawai");
		aplikasi.setUrl("https://sistem.sangihekab.go.id/simpeg");
		aplikasiService.simpan(aplikasi);
		
		UnitKerja unitKerja = new UnitKerja();
		unitKerja.setNama("Pengelolaan Data Elektronik");
		unitKerja.setSingkatan("BPDE");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		
		unitKerjaService.simpan(unitKerja);

		pegawai = new Pegawai();
		pegawai.setNik("7171070512910002");
		pegawai.setNip("090213016");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		pegawai.setUnitKerja(unitKerja);
		pegawai.setEmail("deddy.kakunsi@gmail.com");
		pegawai.setTelepon("083247643198");
		pegawaiService.simpan(pegawai);
		
		aplikasiService.tambahOperator("090213016", "SIMPEG");

		token = tokenService.create("090213016", "password");
		
		assertEquals(String.format("%d%d", pegawai.hashCode(), DateUtil.getDate().hashCode()), token.getToken());
		assertNotEquals("", token.getToken());
		assertNotEquals(0, tokenRepository.count());
	}
	
	@Test
	public void test_create() throws UnauthenticatedAccessException {
		Token token = tokenService.create("090213016", "password");
		
		assertNotEquals("", token.getToken());
		assertEquals(StatusToken.AKTIF, token.getStatus());
		assertEquals(DateUtil.getDate(), token.getTanggalBuat());
		assertEquals(DateUtil.add(DateUtil.getDate(), 2), token.getTanggalExpire());
		assertNotEquals(0, token.getDaftarOperator());
	}
	
	@Test
	public void test_lock() {
		token = tokenService.lock(token.getToken());

		assertEquals(StatusToken.LOCKED, token.getStatus());
	}
	
	@Test
	public void test_get() throws OutOfDateEntityException, UnauthenticatedAccessException {
		Token token2 = tokenService.get(token.getToken());
		
		assertNotEquals(0, token2.getToken());
		assertEquals(StatusToken.AKTIF, token2.getStatus());
		assertNotEquals(0, token2.getDaftarOperator());
	}
	
	@Test(expected = UnauthenticatedAccessException.class)
	public void test_get_locked() throws OutOfDateEntityException, UnauthenticatedAccessException {
		tokenService.lock(token.getToken());
		tokenService.get(token.getToken());
	}

	@Ignore
	@Test(expected = OutOfDateEntityException.class)
	public void test_get_expire() throws OutOfDateEntityException {
		// take expire token
	}
}
