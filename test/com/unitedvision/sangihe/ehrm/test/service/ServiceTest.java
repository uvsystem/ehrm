package com.unitedvision.sangihe.ehrm.test.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AbsenServiceTest.class, AplikasiServiceTest.class,
	JabatanServiceTest.class, KalendarServiceTest.class,
	PegawaiServiceTest.class, SppdServiceTest.class,
	SuratTugasServiceTest.class, TokenServiceTest.class,
	UnitKerjaServiceTest.class
})
public class ServiceTest { }
