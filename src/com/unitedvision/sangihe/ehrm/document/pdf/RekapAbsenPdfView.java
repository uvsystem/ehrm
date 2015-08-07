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
import com.unitedvision.sangihe.ehrm.absensi.RekapAbsen;

public class RekapAbsenPdfView extends CustomAbstractPdfView {

	@Override
	public Document create(Map<String, Object> model, Document doc) throws DocumentException {
		@SuppressWarnings("unchecked")
		List<RekapAbsen> list = (List<RekapAbsen>) model.get("rekap");
		String tanggalAwal = (String) model.get("tanggalAwal");
		String tanggalAkhir = (String) model.get("tanggalAkhir");
		
		doc.newPage();
		
		createTitle(doc);
		addEmptyLine(doc, 1);
		
		createSubTitle(doc, tanggalAwal, tanggalAkhir);
		addEmptyLine(doc, 1);
		
		createContent(doc, list);
		addEmptyLine(doc, 2);
		
		return doc;
	}

	@Override
	protected void createTitle(Document doc) throws DocumentException {
		Paragraph title = new Paragraph();
		title.add(new Paragraph("Pemerintah Kabupaten Kepulauan Sangihe", fontTitle));
		title.add(new Paragraph("Rekap Kehadiran Pegawai", fontSubTitle));
		title.setAlignment(Element.ALIGN_CENTER);
		
		doc.add(title);
	}

	protected void createSubTitle(Document doc, String tanggalAwal, String tanggalAkhir) throws DocumentException {
		Paragraph subTitle = new Paragraph();
		subTitle.setAlignment(Element.ALIGN_CENTER);
		
		float[] columnWidth = {12};
		
		PdfPTable table = new PdfPTable(columnWidth);
		table.setWidthPercentage(tablePercentage);
		
		insertCell(table, String.format("Rentang Waktu: %s s/d %s", tanggalAwal, tanggalAkhir), align, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
		Date tanggalCetak = DateUtil.getDate();
		insertCell(table, String.format("Tanggal Cetak: %s", DateUtil.toFormattedStringDate(tanggalCetak, "-")), align, 1, fontHeader, Rectangle.BOX, Color.WHITE, null);
		
		subTitle.add(table);
		
		doc.add(subTitle);
	}

	@Override
	protected void createContent(Document doc, List<?> list) throws DocumentException {
		
		Paragraph content = new Paragraph();
		content.setAlignment(Element.ALIGN_CENTER);
		
		// 8 Kolom
		float[] columnWidth = {4, 8, 2, 2, 2, 2, 2, 2};

		PdfPTable table = new PdfPTable(columnWidth);
		table.setWidthPercentage(tablePercentage);

		insertCell(table, "Nip", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Nama", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Hadir", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Terlambat", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Tugas Luar", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Sakit", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Izin", align, 1, fontHeader, Rectangle.BOX, null, null);
		insertCell(table, "Cuti", align, 1, fontHeader, Rectangle.BOX, null, null);
		table.setHeaderRows(1);
		
		String deter = null;
		for (Object object : list) {

			RekapAbsen rekap = (RekapAbsen)object;
			String namaUnitKerja = rekap.getNamaUnitKerja();
			
			if ( !namaUnitKerja.equals(deter) ) {

				deter = namaUnitKerja;
				
				insertCell(table, deter, align, 8, fontHeader, Rectangle.BOX, null, Color.GRAY);
				
			}

			insertCell(table, rekap.getNip(), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, rekap.getNama(), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Long.toString(rekap.getHadir()), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Long.toString(rekap.getTerlambat()), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Long.toString(rekap.getTugasLuar()), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Long.toString(rekap.getSakit()), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Long.toString(rekap.getIzin()), align, 1, fontContent, Rectangle.BOX, null, null);
			insertCell(table, Long.toString(rekap.getCuti()), align, 1, fontContent, Rectangle.BOX, null, null);
		}

		content.add(table);
		
		doc.add(content);
	}
	
	@Override
	protected Document newDocument() {
		return new Document(PageSize.A4.rotate(), 0.1f, 0.1f, 0.1f, 0.1f);
	}
}
