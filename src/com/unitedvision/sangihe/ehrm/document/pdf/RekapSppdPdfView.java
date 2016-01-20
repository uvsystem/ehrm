package com.unitedvision.sangihe.ehrm.document.pdf;

import java.awt.Color;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.sppd.RekapSppd;

public class RekapSppdPdfView extends CustomAbstractPdfView {
	private String judul;
	
	@Override
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		@SuppressWarnings("unchecked")
		List<RekapSppd> list = (List<RekapSppd>) model.get("rekap");
		Date awal = (Date) model.get("awal");
		Date akhir = (Date) model.get("akhir");
		judul = (String) model.get("judul");
		
		doc.newPage();
		
		createTitle(doc);
		addEmptyLine(doc, 1);
		
		createSubTitle(doc, awal, akhir);
		addEmptyLine(doc, 1);
		
		createContent(doc, list);
		addEmptyLine(doc, 2);
		
		return doc;
	}

	@Override
	protected void createTitle(Document doc) throws DocumentException {
		Paragraph title = new Paragraph();
		title.add(new Paragraph("Pemerintah Kabupaten Kepulauan Sangihe", fontTitle));
		title.add(new Paragraph(String.format("Rekap %s", judul), fontSubTitle));
		title.setAlignment(Element.ALIGN_CENTER);
		
		doc.add(title);
	}

	protected void createSubTitle(Document doc, Date awal, Date akhir) throws DocumentException {
		Paragraph subTitle = new Paragraph();
		subTitle.setAlignment(Element.ALIGN_CENTER);
		
		float[] columnWidth = {2, 4, 2, 4};
		
		PdfPTable table = new PdfPTable(columnWidth);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Periode", align, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
		insertCell(table, String.format(": %s s/d %s", DateUtil.toFormattedStringDate(awal, "-"), DateUtil.toFormattedStringDate(akhir, "-")), align, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
		Date tanggalCetak = DateUtil.getDate();
		insertCell(table, String.format("Tanggal Cetak: %s", DateUtil.toFormattedStringDate(tanggalCetak, "-")), Element.ALIGN_RIGHT, 2, fontContent, Rectangle.BOX, Color.WHITE, null);
		
		subTitle.add(table);
		
		doc.add(subTitle);
	}

	@Override
	protected void createContent(Document doc, List<?> list) throws DocumentException {
		
		Paragraph content = new Paragraph();
		content.setAlignment(Element.ALIGN_CENTER);
		
		// 8 Kolom
		float[] columnWidth = {4, 8, 8, 2, 2};

		PdfPTable table = new PdfPTable(columnWidth);
		table.setWidthPercentage(tablePercentage);

		insertCell(table, "Nip", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Nama", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Satuan Kerja", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Jumlah Surat", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Jumlah Hari", align, 1, fontHeader, Rectangle.BOX, null, null);
		table.setHeaderRows(1);
		
		for (Object object : list) {

			RekapSppd rekap = (RekapSppd)object;

			insertCell(table, rekap.getNip(), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, rekap.getNama(), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, rekap.getNamaUnitKerja(), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Integer.toString(rekap.getJumlahSppd()), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Integer.toString(rekap.getJumlahTugasLuar()), align, 1, fontContent, Rectangle.BOX, null, null);
		}

		content.add(table);
		
		doc.add(content);
	}
	
	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4, 0.1f, 0.1f, 0.1f, 0.1f);
	}
}
