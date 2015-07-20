package com.unitedvision.sangihe.ehrm.document.pdf;

import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;

public class ExceptionPdfView extends CustomAbstractPdfView {
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	protected void createTitle(Document doc) throws DocumentException {
		Paragraph title = new Paragraph();
		title.add(new Paragraph("Tidak Bisa Menampilkan File", fontTitle));
		title.add(new Paragraph(message, fontContent));
		title.setAlignment(Element.ALIGN_CENTER);
		
		doc.add(title);
	}

	@Override
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		decorateDocument(doc, "Laporan Kesalahan");
		setMessage((String)model.get("message"));
		
		createTitle(doc);
		
		return doc;
	}

	@Override
	protected void createContent(Document doc, List<? extends Object> list) {
		// TODO Auto-generated method stub
		
	}
}
