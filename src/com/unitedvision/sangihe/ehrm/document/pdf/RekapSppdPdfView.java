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

	@Override
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		@SuppressWarnings("unchecked")
		List<RekapSppd> list = (List<RekapSppd>) model.get("rekap");
		Integer tahun = (Integer) model.get("tahun");
		
		doc.newPage();
		
		createTitle(doc);
		addEmptyLine(doc, 1);
		
		createSubTitle(doc, tahun);
		addEmptyLine(doc, 1);
		
		createContent(doc, list);
		addEmptyLine(doc, 2);
		
		return doc;
	}

	@Override
	protected void createTitle(Document doc) throws DocumentException {
		Paragraph title = new Paragraph();
		title.add(new Paragraph("Pemerintah Kabupaten Kepulauan Sangihe", fontTitle));
		title.add(new Paragraph("Rekap SPPD & Tugas Luar", fontSubTitle));
		title.setAlignment(Element.ALIGN_CENTER);
		
		doc.add(title);
	}

	protected void createSubTitle(Document doc, Integer tahun) throws DocumentException {
		Paragraph subTitle = new Paragraph();
		subTitle.setAlignment(Element.ALIGN_CENTER);
		
		float[] columnWidth = {2, 4, 2, 4};
		
		PdfPTable table = new PdfPTable(columnWidth);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, "Tahun", align, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
		insertCell(table, String.format(": %d", tahun), align, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
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
		insertCell(table, "SPPD", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "TL", align, 1, fontHeader, Rectangle.BOX, null, null);
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
