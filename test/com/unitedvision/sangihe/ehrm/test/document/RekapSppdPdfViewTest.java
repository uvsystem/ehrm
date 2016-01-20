package com.unitedvision.sangihe.ehrm.test.document;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.unitedvision.sangihe.ehrm.ApplicationConfig;
import com.unitedvision.sangihe.ehrm.document.pdf.RekapSppdPdfView;
import com.unitedvision.sangihe.ehrm.sppd.RekapSppd;
import com.unitedvision.sangihe.ehrm.sppd.SppdService;

public class RekapSppdPdfViewTest extends RekapSppdPdfView {

	private static RekapSppdPdfViewTest pdf = new RekapSppdPdfViewTest();
	private static ApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
	private static SppdService sppdService = appContext.getBean(SppdService.class);
	
	public static void main(String[] args) {
        Document document = pdf.newDocument();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("E:\\rekap-sppd.pdf"));

            document.open();

            int tahun = 2015;
            @SuppressWarnings("deprecation")
			List<RekapSppd> list = sppdService.rekap(tahun);

            Map<String, Object> model = new HashMap<>();
            model.put("rekap", list);
            model.put("tahun", tahun);
            
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
