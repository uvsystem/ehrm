package com.unitedvision.sangihe.ehrm.test.document;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.sangihe.ehrm.ApplicationConfig;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.absensi.AbsenService;
import com.unitedvision.sangihe.ehrm.absensi.RekapAbsen;
import com.unitedvision.sangihe.ehrm.document.pdf.RekapAbsenPdfView;

public class RekapAbsenPdfViewTest extends RekapAbsenPdfView {

	private static RekapAbsenPdfViewTest pdf = new RekapAbsenPdfViewTest();
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
	private static AbsenService absenService = appContext.getBean(AbsenService.class);
	
	public static void main(String[] args) {
        Document document = pdf.newDocument();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("E:\\rekap-absen.pdf"));

            document.open();

            Date awal = DateUtil.getDate(2015, Month.JANUARY, 1);
            Date akhir = DateUtil.getDate(2015, Month.DECEMBER, 31);
            List<RekapAbsen> list = absenService.rekap(awal, akhir);

            Map<String, Object> model = new HashMap<>();
            model.put("rekap", list);
            model.put("tanggalAwal", DateUtil.toFormattedStringDate(awal, "-"));
            model.put("tanggalAkhir", DateUtil.toFormattedStringDate(akhir, "-"));
            
            pdf.create(model, document);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
        
        System.out.println("DONE...");
	}
	
	@Override
	protected Document newDocument() {
		return super.newDocument();
	}
	
}
