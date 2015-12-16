package com.unitedvision.sangihe.ehrm.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.IdenticRelationshipException;
import com.unitedvision.sangihe.ehrm.manajemen.Token;
import com.unitedvision.sangihe.ehrm.manajemen.TokenService;
import com.unitedvision.sangihe.ehrm.simpeg.Eselon;
import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.JabatanService;
import com.unitedvision.sangihe.ehrm.simpeg.Pangkat;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class TokenControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private JabatanService jabatanService;

	private UnitKerja unitKerja;
	private Pegawai pegawai;
	private Token token;
	private Jabatan jabatan;

	@Before
	public void setup() throws IdenticRelationshipException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		unitKerja = new UnitKerja();
		unitKerja.setNama("Pengelolaan Data Elektronik");
		unitKerja.setSingkatan("BPDE");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		unitKerjaService.simpan(unitKerja);

		jabatan = new Jabatan(unitKerja);
		jabatan.setNama("Kepala Badan");
		jabatan.setEselon(Eselon.IIIa);
		jabatan.setPangkat(Pangkat.IVB);
		jabatanService.simpan(jabatan);
		
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
		pegawaiService.promosi(pegawai, Pangkat.IIIB, DateUtil.getDate(), null, "001-SK-2015");
		pegawaiService.promosi(pegawai, jabatan, DateUtil.getDate(), null, "001-SK-2015");
		
		token = new Token();
		token.setPegawai(pegawai);
		token.setTanggalBuat(DateUtil.getDate());
	}
	
	@Test
	public void test_create() throws Exception {
		this.mockMvc.perform(
				post("/token/090213016")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_get() throws Exception {
		this.mockMvc.perform(
				post("/token/090213016")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				get(String.format("/token/%s", token.generateToken()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"));
	}
	
	@Test
	public void test_lock() throws Exception {
		this.mockMvc.perform(
				post("/token/090213016")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				put(String.format("/token/%s", token.generateToken()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

}
