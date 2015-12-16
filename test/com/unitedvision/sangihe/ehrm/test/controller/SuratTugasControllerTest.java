package com.unitedvision.sangihe.ehrm.test.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

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
import com.unitedvision.sangihe.ehrm.simpeg.Eselon;
import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.JabatanService;
import com.unitedvision.sangihe.ehrm.simpeg.Pangkat;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugasService;
import com.unitedvision.sangihe.ehrm.sppd.repository.SuratTugasRepository;
import com.unitedvision.sangihe.ehrm.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class SuratTugasControllerTest {

	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private SuratTugasService suratTugasService;
	@Autowired
	private SuratTugasRepository suratTugasRepository;
	@Autowired
	private JabatanService jabatanService;

	private MockMvc mockMvc;
	private UnitKerja unitKerja;
	private Pegawai pegawai;
	private SuratTugas suratTugas;
	
	@Before
	public void setup() throws IdenticRelationshipException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		unitKerja = new UnitKerja();
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
		pegawaiService.promosi(pegawai, Pangkat.IIIB, DateUtil.getDate(), null, "001-SK-2015");

		Jabatan jabatan = new Jabatan(unitKerja);
		jabatan.setNama("Kepala Badan");
		jabatan.setEselon(Eselon.Va);
		jabatan.setPangkat(Pangkat.IIIB);
		jabatanService.simpan(jabatan);

		pegawaiService.promosi(pegawai, jabatan, DateUtil.getDate(), null, "001-SK-2015");
		
		suratTugas = new SuratTugas();
		suratTugas.setJumlahHari(3);
		suratTugas.setMaksud("Konsultasi Teknis Pengembangan Rencana Induk TIK");
		suratTugas.setTujuan("Manado");
		suratTugas.setNomor("001-STG-2015");
		
		List<Pegawai> daftarPegawai = new ArrayList<>();
		daftarPegawai.add(pegawai);
		
		suratTugas= suratTugasService.ajukan(suratTugas, daftarPegawai);
		
		assertNotEquals(0, suratTugas.getId());
	}
	
	@Test
	public void test_request_surat_tugas() throws Exception {
		this.mockMvc.perform(
				post(String.format("/suratTugas/%s", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"jumlahHari\": \"2\", \"maksud\": \"Renegosiasi Kontrak\", \"tujuan\": \"Manado\"}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_accept_surat_tugas() throws Exception {
		this.mockMvc.perform(
				put(String.format("/suratTugas/%s/terima", suratTugas.getNomor()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_decline_surat_tugas() throws Exception {
		this.mockMvc.perform(
				put(String.format("/suratTugas/%s/tolak", suratTugas.getNomor()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_get_request_surat_tugas() throws Exception {
		this.mockMvc.perform(
				get(String.format("/suratTugas/pending", suratTugas.getNomor()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}

	@Test
	public void test_get_surat_tugas_pegawai() throws Exception {
		this.mockMvc.perform(
				get(String.format("/suratTugas/%s", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
}
