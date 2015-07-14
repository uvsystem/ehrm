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
import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.OutOfDateEntityException;
import com.unitedvision.sangihe.ehrm.UnauthenticatedAccessException;
import com.unitedvision.sangihe.ehrm.duk.Penduduk.Kontak;
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
	public void setup() throws EntityNotExistException {
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
		
		Kontak kontak = new Kontak();
		kontak.setEmail("deddy.kakunsi@gmail.com");
		kontak.setTelepon("083247643198");
		pegawai.setKontak(kontak);
		
		pegawaiService.simpan(pegawai);

		token = tokenService.create("090213016");

		String username = pegawai.getNip();
		String code = DateUtil.codedString(DateUtil.getNow());
		
		assertEquals(String.format("%s-%s", username, code), token.getToken());
		assertNotEquals("", token.getToken());
		assertNotEquals(0, tokenRepository.count());
	}
	
	@Test
	public void test_simpan() throws EntityNotExistException {
		Token token = tokenService.create("090213016");
		
		assertNotEquals("", token.getToken());
		assertEquals(StatusToken.AKTIF, token.getStatus());
		assertEquals(DateUtil.getNow(), token.getTanggalBuat());
		assertEquals(DateUtil.add(DateUtil.getNow(), 2), token.getTanggalExpire());
	}
	
	@Test
	public void test_lock() throws EntityNotExistException {
		tokenService.lock(token.getToken());

		assertEquals(StatusToken.LOCKED, token.getStatus());
	}
	
	@Test
	public void test_get() throws EntityNotExistException, OutOfDateEntityException, UnauthenticatedAccessException {
		Token token2 = tokenService.get(token.getToken());
		
		assertNotEquals(0, token2.getToken());
		assertEquals(StatusToken.AKTIF, token2.getStatus());
	}
	
	@Test(expected = UnauthenticatedAccessException.class)
	public void test_get_locked() throws EntityNotExistException, OutOfDateEntityException, UnauthenticatedAccessException {
		tokenService.lock(token.getToken());
		tokenService.get(token.getToken());
	}

	@Ignore
	@Test(expected = OutOfDateEntityException.class)
	public void test_get_expire() throws EntityNotExistException, OutOfDateEntityException {
		// take expire token
	}
}
