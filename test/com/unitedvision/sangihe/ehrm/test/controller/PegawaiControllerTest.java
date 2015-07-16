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
public class PegawaiControllerTest {

	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;
	
	private MockMvc mockMvc;
	private UnitKerja unitKerja;
	private Pegawai pegawai;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		unitKerja = new UnitKerja();
		unitKerja.setTipe(TipeUnitKerja.BADAN);
		unitKerja.setNama("Badan Kepegawaian Daerah");
		unitKerja.setSingkatan("BKD");
		unitKerjaService.simpan(unitKerja);
		
		pegawai = new Pegawai(unitKerja);
		pegawai.setNik("7171070512910002");
		pegawai.setNip("090213016");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		pegawai.setUnitKerja(unitKerja);
		pegawai.setEmail("deddy.kakunsi@gmail.com");
		pegawai.setTelepon("082347643198");
		pegawaiService.simpan(pegawai);
	}

	@Test
	public void tambah_pegawai() throws Exception {
		this.mockMvc.perform(
				post(String.format("/pegawai/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": \"0\", \"nik\": \"7171070512910001\", \"nip\": \"090213015\", "
						+ "\"nama\": \"Deddy Kakunsi\", \"passwordStr\": \"dkakunsi\", "
						+ "\"tanggalLahirStr\": \"12-05-1991\", \"email\": \"dkakunsi@gmail.com\", \"telepon\": \"082347643198\"}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void tambah_pegawai_duplicate_nik() throws Exception {
		this.mockMvc.perform(
				post(String.format("/pegawai/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"0\", \"nik\":\"7171070512910002\", \"nip\":\"090213015\", "
						+ "\"nama\": \"Deddy Kakunsi\", \"passwordStr\":\"dkakunsi\" ,"
						+ "\"tanggalLahirStr\": \"12-05-1991\", \"email\": \"dkakunsi@gmail.com\", \"telepon\": \"082347643198\"}")
			)
			.andExpect(jsonPath("$.message").value("NIK yang anda masukan sudah digunakan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void tambah_pegawai_duplicate_nip() throws Exception {
		this.mockMvc.perform(
				post(String.format("/pegawai/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"0\", \"nik\":\"7171070512910001\", \"nip\":\"090213016\", "
						+ "\"nama\": \"Deddy Kakunsi\", \"passwordStr\":\"dkakunsi\", "
						+ "\"tanggalLahirStr\": \"12-05-1991\", \"email\": \"dkakunsi@gmail.com\", \"telepon\": \"082347643198\"}")
			)
			.andExpect(jsonPath("$.message").value("NIP yang anda masukan sudah digunakan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void edit_pegawai() throws Exception {
		this.mockMvc.perform(
				put(String.format("/pegawai/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.format("{\"id\":\"%d\", \"nik\":\"7171070512910002\", \"nip\":\"090213015\", "
						+ "\"nama\": \"Deddy Kakunsi\", \"passwordStr\":\"dkakunsi\", "
						+ "\"tanggalLahirStr\": \"12-05-1991\", \"email\": \"deddy.kakunsi@gmail.com\", "
						+ "\"telepon\": \"082347643198\", \"idPenduduk\": \"%d\"}", pegawai.getId(), pegawai.getIdPenduduk()))
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_get_by_nip() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pegawai/%s", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.unitKerja.nama").value("Badan Kepegawaian Daerah"))
			.andExpect(jsonPath("$.model.nik").value("7171070512910002"))
			.andExpect(jsonPath("$.model.nama").value("Deddy Christoper Kakunsi"))
			.andExpect(jsonPath("$.model.passwordStr").value("****"))
			.andExpect(jsonPath("$.model.tanggalLahirStr").value("5-12-1991"))
			.andExpect(jsonPath("$.model.email").value("deddy.kakunsi@gmail.com"))
			.andExpect(jsonPath("$.model.telepon").value("082347643198"));
	}

	@Test
	public void test_get_by_id_not_found() throws Exception {
		this.mockMvc.perform(
				get("/pegawai/9000121")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Data tidak ditemukan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

	@Test
	public void test_get_by_unit_kerja() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pegawai/satker/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
	@Test
	public void test_search_by_nama() throws Exception {
		this.mockMvc.perform(
				get("/pegawai/search/Deddy")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
	@Test
	public void test_search_by_nip() throws Exception {
		this.mockMvc.perform(
				get("/pegawai/search/090213")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
}
