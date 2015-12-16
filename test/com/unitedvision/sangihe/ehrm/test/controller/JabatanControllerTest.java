package com.unitedvision.sangihe.ehrm.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

import com.unitedvision.sangihe.ehrm.simpeg.Eselon;
import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.JabatanService;
import com.unitedvision.sangihe.ehrm.simpeg.Pangkat;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class JabatanControllerTest {

	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private JabatanService jabatanService;
	
	private MockMvc mockMvc;
	private UnitKerja unitKerja;
	private Jabatan jabatan;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		unitKerja = new UnitKerja();
		unitKerja.setTipe(TipeUnitKerja.BADAN);
		unitKerja.setNama("Badan Kepegawaian Daerah");
		unitKerja.setSingkatan("BKD");
		unitKerjaService.simpan(unitKerja);
		
		jabatan = new Jabatan(unitKerja);
		jabatan.setNama("Kepala Badan");
		jabatan.setEselon(Eselon.IIIa);
		jabatan.setPangkat(Pangkat.IVB);
		jabatanService.simpan(jabatan);
	}

	@Test
	public void test_tambah() throws Exception {
		this.mockMvc.perform(
				post(String.format("/jabatan/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"0\", \"eselon\":\"IV\", \"nama\":\"Kepala Bidang Data dan Informasi\", \"pangkat\":\"IVA\"}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_tambah_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/jabatan/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":\"0\", \"eselon\":\"III\", \"nama\":\"Kepala Badan\", \"pangkat\":\"IVB\"}")
			)
			.andExpect(jsonPath("$.message").value("Jabatan yang anda masukan sudah digunakan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_edit() throws Exception {
		this.mockMvc.perform(
				put(String.format("/jabatan/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.format("{\"id\":\"%d\", \"eselon\": \"II\", \"nama\":\"Kepala Badan\", \"pangkat\":\"IVA\"}", jabatan.getId()))
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_get_by_id() throws Exception {
		this.mockMvc.perform(
				get(String.format("/jabatan/%d", jabatan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.nama").value("Kepala Badan"))
			.andExpect(jsonPath("$.model.eselon").value("III"))
			.andExpect(jsonPath("$.model.pangkat").value("IVB"));
	}
	
	@Test
	public void test_get_by_id_not_found() throws Exception {
		this.mockMvc.perform(
				get("/jabatan/9000121")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Data tidak ditemukan"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

	@Test
	public void test_get_by_unit_kerja() throws Exception {
		this.mockMvc.perform(
				get(String.format("/jabatan/satker/%d", unitKerja.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
	@Test
	public void test_search() throws Exception {
		this.mockMvc.perform(
				get("/jabatan/search/Kepala")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}

}
