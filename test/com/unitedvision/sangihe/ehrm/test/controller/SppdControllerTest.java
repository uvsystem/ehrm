package com.unitedvision.sangihe.ehrm.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Month;
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
import com.unitedvision.sangihe.ehrm.absensi.KalendarService;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugasService;
import com.unitedvision.sangihe.ehrm.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class SppdControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private SuratTugasService suratTugasService;
	@Autowired
	private KalendarService kalendarService;

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
		
		suratTugas = new SuratTugas();
		suratTugas.setJumlahHari(3);
		suratTugas.setMaksud("Konsultasi Teknis Pengembangan Rencana Induk TIK");
		suratTugas.setTujuan("Manado");
		suratTugas.setNomor("001-STG-2015");
		
		List<Pegawai> daftarPegawai = new ArrayList<>();
		daftarPegawai.add(pegawai);
		
		suratTugas = suratTugasService.ajukan(suratTugas, daftarPegawai);
		
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 1));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 2));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 3));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 4));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 5));
	}
	
	@Test
	public void test_buat_sppd() throws Exception {
		this.mockMvc.perform(
				post(String.format("/sppd/%s/suratTugas/%s", pegawai.getNip(), suratTugas.getNomor()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"nomor\": \"001-SPPD-2015\", "
						+ "\"tanggalBerangkatStr\": \"01-01-2015\", "
						+ "\"modaTransportasi\": \"Kapal Laut\", "
						+ "\"kodeRekening\": \"01.01.00001\", "
						+ "\"nomorDpa\": \"001-DPA\", "
						+ "\"tingkat\": \"Prioritas\", "
						+ "\"daftarPengikut\": [{"
						+ "\"nama\": \"Nora Eligia\", "
						+ "\"tanggalLahirStr\": \"01-21-2015\", "
						+ "\"keterangan\": \"Teman\""
						+ "}]"
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_tambah_pengikut() throws Exception {
		this.mockMvc.perform(
				post(String.format("/sppd/%s/suratTugas/%s", pegawai.getNip(), suratTugas.getNomor()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"nomor\": \"001-SPPD-2015\", "
						+ "\"tanggalBerangkatStr\": \"01-01-2015\", "
						+ "\"modaTransportasi\": \"Kapal Laut\", "
						+ "\"kodeRekening\": \"01.01.00001\", "
						+ "\"nomorDpa\": \"001-DPA\", "
						+ "\"tingkat\": \"Prioritas\", "
						+ "\"daftarPengikut\": [{"
						+ "\"nama\": \"Nora Eligia\", "
						+ "\"tanggalLahirStr\": \"01-21-2015\", "
						+ "\"keterangan\": \"Teman\""
						+ "}]"
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				post("/sppd/001-SPPD-2015/pengikut")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"nama\": \"Debra Tiwow\", "
						+ "\"tanggalLahirStr\": \"04-10-2015\", "
						+ "\"keterangan\": \"Teman\""
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}
	
	@Test
	public void test_get_sppd_pegawai() throws Exception {
		this.mockMvc.perform(
				post(String.format("/sppd/%s/suratTugas/%s", pegawai.getNip(), suratTugas.getNomor()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"nomor\": \"001-SPPD-2015\", "
						+ "\"tanggalBerangkatStr\": \"01-01-2015\", "
						+ "\"modaTransportasi\": \"Kapal Laut\", "
						+ "\"kodeRekening\": \"01.01.00001\", "
						+ "\"nomorDpa\": \"001-DPA\", "
						+ "\"tingkat\": \"Prioritas\", "
						+ "\"daftarPengikut\": [{"
						+ "\"nama\": \"Nora Eligia\", "
						+ "\"tanggalLahirStr\": \"01-21-2015\", "
						+ "\"keterangan\": \"Teman\""
						+ "}]"
						+ "}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				get(String.format("/sppd/%s", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("LIST"));
	}
}
