package com.unitedvision.sangihe.ehrm.test.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	JabatanControllerTest.class, UnitKerjaControllerTest.class,
	PegawaiControllerTest.class, KalendarControllerTest.class,
	AbsenControllerTest.class, SuratTugasControllerTest.class,
	SppdControllerTest.class, AplikasiControllerTest.class
})
public class ControllerTest { }
