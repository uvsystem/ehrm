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

import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class UnitKerjaControllerTest {

	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private UnitKerjaService unitKerjaService;
	
	private MockMvc mockMvc;
	private UnitKerja unitKerja;
	private UnitKerja subUnitKerja;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		unitKerja = new UnitKerja();
		unitKerja.setTipe(TipeUnitKerja.BADAN);
		unitKerja.setNama("Badan Kepegawaian Daerah");
		unitKerja.setSingkatan("BKD");
		unitKerjaService.simpan(unitKerja);
		
		subUnitKerja = new UnitKerja(unitKerja);
		subUnitKerja.setTipe(TipeUnitKerja.BIDANG);
		subUnitKerja.setNama("Data dan Informasi");
		subUnitKerja.setSingkatan("DI-BKD");
		unitKerjaService.simpan(subUnitKerja);
	}
	
	@Test
	public void test_tambah() throws Exception {
		this.mockMvc.perform(
				post("/satker")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"0\", \"tipe\":\"DINAS\", \"nama\":\"Dinas Perikanan dan Kelautan\", \"singkatan\":\"DPK\"}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_tambah_sub_unit() throws Exception {
		this.mockMvc.perform(
				post("/satker/" + unitKerja.getId() + "/sub")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"0\", \"tipe\":\"DINAS\", \"nama\":\"Dinas Perikanan dan Kelautan\", \"singkatan\":\"DPK\"}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_tambah_duplicate_akronim() throws Exception {
		this.mockMvc.perform(
				post("/satker")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"0\", \"tipe\":\"BADAN\", \"nama\":\"Badan Kepegawaian Daerah\", \"singkatan\":\"BKD\"}")
			)
			.andExpect(jsonPath("$.message").value("Singkatan yang anda masukan sudah digunakan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_edit() throws Exception {
		this.mockMvc.perform(
				put("/satker")
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.format("{\"id\":\"%d\", \"tipe\": \"BADAN\", \"singkatan\":\"SKPD2\", \"nama\":\"Nama SKPD 2\"}", unitKerja.getId()))
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_get_by_id() throws Exception {
		this.mockMvc.perform(
				get(String.format("/satker/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.nama").value("Badan Kepegawaian Daerah"))
			.andExpect(jsonPath("$.model.tipe").value("BADAN"))
			.andExpect(jsonPath("$.model.singkatan").value("BKD"));
	}

	@Test
	public void test_get_sub_unit_by_id() throws Exception {
		this.mockMvc.perform(
				get(String.format("/satker/%d", subUnitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.nama").value("Data dan Informasi"))
			.andExpect(jsonPath("$.model.tipe").value("BIDANG"))
			.andExpect(jsonPath("$.model.singkatan").value("DI-BKD"));
	}
	
	@Test
	public void test_get_by_id_not_found() throws Exception {
		this.mockMvc.perform(
				get("/satker/9000121")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Data tidak ditemukan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

	@Test
	public void test_get_all_sub_unit_kerja() throws Exception {
		this.mockMvc.perform(
				get(String.format("/satker/%d/sub", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
	@Test
	public void test_search() throws Exception {
		this.mockMvc.perform(
				get("/satker/search/pegawai")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}

}
