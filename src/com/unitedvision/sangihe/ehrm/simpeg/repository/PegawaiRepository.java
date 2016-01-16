package com.unitedvision.sangihe.ehrm.simpeg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.sangihe.ehrm.simpeg.Eselon;
import com.unitedvision.sangihe.ehrm.simpeg.Pangkat;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface PegawaiRepository extends JpaRepository<Pegawai, Long> {

	Pegawai findByNip(String nip);

	@Query("FROM Pegawai p WHERE p.unitKerja = ?1 OR p.unitKerja.parent = ?1")
	List<Pegawai> findByUnitKerja(UnitKerja unitKerja);
	List<Pegawai> findByUnitKerja_Id(Long idUnitKerja);
	List<Pegawai> findByUnitKerjaIn(List<UnitKerja> daftarSubUnitKerja);

	@Query("FROM RiwayatPangkat rp JOIN rp.pegawai pg WHERE rp.pangkat = ?1 AND rp.tanggalSelesai IS NULL")
	List<Pegawai> findByPangkat(Pangkat pangkat);

	@Query("FROM RiwayatJabatan rj JOIN rj.pegawai pg WHERE rj.jabatan.eselon = ?1 AND rj.tanggalSelesai IS NULL")
	List<Pegawai> findByJabatan_Eselon(Eselon eselon);

	@Query("FROM Pegawai pg WHERE pg.nip LIKE CONCAT('%', :keyword, '%') OR pg.penduduk.nama LIKE CONCAT('%', :keyword, '%')")
	List<Pegawai> findByNipContainingOrPenduduk_NamaContaining(@Param("keyword") String keyword);

	List<Pegawai> findByNipIn(List<String> daftarPegawai);

	@Query("FROM Pegawai pg WHERE pg.nip = ?1 OR pg.penduduk.nama = ?1")
	Pegawai findByNipOrPenduduk_Nama(String keyword);

	@Modifying
	@Query("Update Pegawai p Set p.password = ?2 Where p.id = ?1")
	void updatePassword(Long id, String password);

}
