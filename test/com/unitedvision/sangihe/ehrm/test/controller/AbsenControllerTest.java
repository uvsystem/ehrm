package com.unitedvision.sangihe.ehrm.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.Month;

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
import com.unitedvision.sangihe.ehrm.absensi.KalendarService;
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
public class AbsenControllerTest {

	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private KalendarService kalendarService;

	private MockMvc mockMvc;
	private UnitKerja bkd;
	private Pegawai pegawai;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		bkd = new UnitKerja();
		bkd.setTipe(TipeUnitKerja.BADAN);
		bkd.setNama("Badan Kepegawaian Daerah");
		bkd.setSingkatan("BKD");
		unitKerjaService.simpan(bkd);
		
		pegawai = new Pegawai(bkd);
		pegawai.setNik("7171070512910002");
		pegawai.setNip("090213016");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		pegawai.setEmail("deddy.kakunsi@gmail.com");
		pegawai.setTelepon("082347643198");
		pegawaiService.simpan(pegawai);
		
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 1));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 2));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 3));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 4));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 5));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JULY, 17));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JULY, 18));
		kalendarService.tambah(DateUtil.getDate());
	}

	@Test
	public void test_absen_hadir() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/hadir/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"pagiStr\": \"07:00:00\", \"cek1Str\": \"11:00:00\", \"cek2Str\": \"13:00:00\", \"soreStr\": \"16:00:00\"}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_hadir_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/hadir/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"pagiStr\": \"07:00:00\", \"cek1Str\": \"11:00:00\", \"cek2Str\": \"13:00:00\", \"soreStr\": \"16:00:00\"}")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				post(String.format("/absen/%s/hadir/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"pagiStr\": \"07:00:00\", \"cek1Str\": \"11:00:00\", \"cek2Str\": \"13:00:00\", \"soreStr\": \"16:00:00\"}")
			)
			.andExpect(jsonPath("$.message").value("Absen sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}
	
	@Test
	public void test_absen_pagi_simple() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/pagi", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_pagi_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/pagi/01-01-2015/07:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				post(String.format("/absen/%s/pagi/01-01-2015/07:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Sudah absen pagi"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

	@Test
	public void test_absen_pagi() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/pagi/01-01-2015/07:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_pagi_list() throws Exception {
		this.mockMvc.perform(
				post("/absen/pagi")
				.contentType(MediaType.APPLICATION_JSON)
				.content("["
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-01-2015\", \"jamStr\": \"07:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-02-2015\", \"jamStr\": \"07:00:00\"} "
						+ "]")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_cek1_simple() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/cek1", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_cek1() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/cek1/01-01-2015/11:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_cek1_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/cek1/01-01-2015/11:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				post(String.format("/absen/%s/cek1/01-01-2015/11:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Sudah absen pengecekan pertama"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

	@Test
	public void test_absen_cek1_list() throws Exception {
		this.mockMvc.perform(
				post("/absen/cek1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("["
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-01-2015\", \"jamStr\": \"11:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-02-2015\", \"jamStr\": \"11:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-03-2015\", \"jamStr\": \"11:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-04-2015\", \"jamStr\": \"11:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-05-2015\", \"jamStr\": \"11:00:00\"} "
						+ "]")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_cek2_simple() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/cek2", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_cek2() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/cek2/01-01-2015/13:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_cek2_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/cek2/01-01-2015/13:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				post(String.format("/absen/%s/cek2/01-01-2015/13:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Sudah absen pengecekan kedua"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

	@Test
	public void test_absen_cek2_list() throws Exception {
		this.mockMvc.perform(
				post("/absen/cek2")
				.contentType(MediaType.APPLICATION_JSON)
				.content("["
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-01-2015\", \"jamStr\": \"13:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-02-2015\", \"jamStr\": \"13:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-03-2015\", \"jamStr\": \"13:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-04-2015\", \"jamStr\": \"13:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-05-2015\", \"jamStr\": \"13:00:00\"} "
						+ "]")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_sore_simple() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/sore", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_sore() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/sore/01-01-2015/16:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_sore_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/sore/01-01-2015/16:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				post(String.format("/absen/%s/sore/01-01-2015/16:00:00", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Sudah absen sore"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

	@Test
	public void test_absen_sore_list() throws Exception {
		this.mockMvc.perform(
				post("/absen/sore")
				.contentType(MediaType.APPLICATION_JSON)
				.content("["
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-01-2015\", \"jamStr\": \"16:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-02-2015\", \"jamStr\": \"16:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-03-2015\", \"jamStr\": \"16:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-04-2015\", \"jamStr\": \"16:00:00\"}, "
						+ "{\"nip\": \"090213016\", \"tanggalStr\": \"01-05-2015\", \"jamStr\": \"16:00:00\"} "
						+ "]")
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_sakit() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/sakit/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_sakit_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/sakit/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				post(String.format("/absen/%s/sakit/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Absen sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

	@Test
	public void test_absen_izin() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/izin/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_izin_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/izin/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));

		this.mockMvc.perform(
				post(String.format("/absen/%s/izin/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Absen sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

	@Test
	public void test_absen_cuti() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/cuti/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
	}

	@Test
	public void test_absen_cuti_duplicate() throws Exception {
		this.mockMvc.perform(
				post(String.format("/absen/%s/cuti/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Berhasil"))
			.andExpect(jsonPath("$.tipe").value("SUCCESS"));
		
		this.mockMvc.perform(
				post(String.format("/absen/%s/cuti/01-01-2015", pegawai.getNip()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.message").value("Absen sudah terdaftar"))
			.andExpect(jsonPath("$.tipe").value("ERROR"));
	}

}
