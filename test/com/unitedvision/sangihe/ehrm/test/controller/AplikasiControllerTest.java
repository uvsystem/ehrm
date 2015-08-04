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
import com.unitedvision.sangihe.ehrm.manajemen.Aplikasi;
import com.unitedvision.sangihe.ehrm.manajemen.AplikasiService;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class AplikasiControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	private AplikasiService aplikasiService;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;

	private Aplikasi aplikasi;
	private UnitKerja unitKerja;
	private Pegawai pegawai;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		aplikasi = new Aplikasi();
		aplikasi.setKode("SIMPEG");
		aplikasi.setNama("Sistem Informasi Manajemen Kepegawaian");
		aplikasi.setUrl("sistem.sangihekab.go.id/simpeg");
		aplikasiService.simpan(aplikasi);
		
		unitKerja = new UnitKerja();
		unitKerja.setNama("Unit Kerja");
		unitKerja.setSingkatan("uk");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		
		unitKerjaService.simpan(unitKerja);

		pegawai = new Pegawai();
		pegawai.setNik("7171070512910000");
		pegawai.setNip("090213010");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		pegawai.setUnitKerja(unitKerja);
		pegawai.setEmail("deddy.kakunsi@gmail.com");
		pegawai.setTelepon("083247643198");
		
		pegawaiService.simpan(pegawai);
	}
	
	@Test
	public void test_tambah() throws Exception {
		this.mockMvc.perform(
				post("/aplikasi")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"password\": \"MONEV\", "
						+ "\"nama\": \"Sistem Informasi Monitoring dan Evaluasi\", "
						+ "\"url\": \"sistem.sangihekab.go.id/monev\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_tambah_duplicate_kode() throws Exception {
		this.mockMvc.perform(
				post("/aplikasi")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"password\": \"SIMPEG\", "
						+ "\"nama\": \"Sistem Informasi Manajemen Kepegawaian\", "
						+ "\"url\": \"sistem.sangihekab.go.id/simpeg\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Kode aplikasi sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_tambah_duplicate_nama() throws Exception {
		this.mockMvc.perform(
				post("/aplikasi")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"password\": \"SIMPEG2\", "
						+ "\"nama\": \"Sistem Informasi Manajemen Kepegawaian\", "
						+ "\"url\": \"sistem.sangihekab.go.id/simpeg\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Nama aplikasi sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_tambah_duplicate_url() throws Exception {
		this.mockMvc.perform(
				post("/aplikasi")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"password\": \"SIMPEG2\", "
						+ "\"nama\": \"Sistem Informasi Manajemen Kepegawaian2\", "
						+ "\"url\": \"sistem.sangihekab.go.id/simpeg\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("URL aplikasi sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_get_by_kode() throws Exception {
		this.mockMvc.perform(
				get("/aplikasi/SIMPEG")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("ENTITY"));
	}
	
	@Test
	public void test_tambah_operator() throws Exception {
		this.mockMvc.perform(
				post("/aplikasi/SIMPEG/operator/090213010")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_tambah_admin() throws Exception {
		this.mockMvc.perform(
				post("/aplikasi/SIMPEG/admin/090213010")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_get_operator() throws Exception {
		this.mockMvc.perform(
				post("/aplikasi/SIMPEG/operator/090213010")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				get("/aplikasi/SIMPEG/operator")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
	
	@Test
	public void test_get_admin() throws Exception {
		this.mockMvc.perform(
				post("/aplikasi/SIMPEG/admin/090213010")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				get("/aplikasi/SIMPEG/admin")
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
}
