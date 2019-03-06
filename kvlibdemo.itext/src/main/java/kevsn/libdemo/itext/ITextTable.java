/*
 * 2016年3月14日 
 */
package kevsn.libdemo.itext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Kevin
 *
 */
public class ITextTable {

	private static Font smallFont;

	private static Font largeFont;

	static {
		smallFont = getFont(10, Font.NORMAL);
		largeFont = getFont(15, Font.BOLD);
	}

	public static void main(String[] arg)
			throws DocumentException, FileNotFoundException, IOException {
		Rectangle pageSize = PageSize.A4.rotate();
		Document document = new Document(pageSize, 60, 60, 40, 40);
		document.addCreationDate();
		HeaderFooter footer = new HeaderFooter(new Phrase("", smallFont), true);
		footer.setBorder(Rectangle.NO_BORDER);
		footer.setAlignment(Element.ALIGN_CENTER);
		Table table = null;
		try (FileOutputStream out = new FileOutputStream("outtable.pdf")) {
			PdfWriter.getInstance(document, out);
			try {
				document.open();
				Paragraph para = new Paragraph("绍兴市区渣土准运登记表", largeFont);
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(1);
				document.add(para);
				table = createTable();
				document.add(table);
				Paragraph end = new Paragraph("本表一式两份", smallFont);
				document.add(end);
			} finally {
				document.close();
			}
			System.out.println(document.left());
			System.out.println(document.right());
			System.out.println(document.bottom());
		}
	}

	// private static void addTable(Document document)
	// throws BadElementException, DocumentException {
	// Table tablePDF = new Table(2);
	// tablePDF.setCellsFitPage(true);
	// tablePDF.setWidth(100);
	// tablePDF.setAlignment(Element.ALIGN_CENTER);
	// tablePDF.setPadding(2);
	// tablePDF.setSpacing(0);
	// document.add(createTable());
	// document.add(tablePDF);
	// }

	private static Table createTable() throws BadElementException {
		Table tablePDF = new Table(4);
		tablePDF.setCellsFitPage(true);
		tablePDF.setWidth(100);
		tablePDF.setAlignment(Element.ALIGN_RIGHT);
		tablePDF.setPadding(2);
		tablePDF.setSpacing(0);
		Cell cell11 = new Cell(new Chunk("工程名称", smallFont));
		tablePDF.addCell(cell11);
		Cell cell12 = new Cell(new Chunk("凤起路2号街新建", smallFont));

		cell12.setColspan(3);
		tablePDF.addCell(cell12);
		Cell cell21 = new Cell(new Chunk("工程地址", smallFont));
		tablePDF.addCell(cell21);
		Cell cell22 = new Cell(new Chunk("凤起路", smallFont));
		tablePDF.addCell(cell22);
		Cell cell23 = new Cell(new Chunk("渣土总量", smallFont));
		tablePDF.addCell(cell23);
		Cell cell24 = new Cell(new Chunk("1300"));
		cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablePDF.addCell(cell24);
		Cell cell31 = new Cell(new Chunk("运输路线", smallFont));
		tablePDF.addCell(cell31);
		Cell cell32 = new Cell(new Chunk(getLongText(200), smallFont));
		cell32.setColspan(3);
		tablePDF.addCell(cell32);
		return tablePDF;
	}

	private static String getLongText(int size) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; ++i) {
			builder.append("字");
		}
		return builder.toString();
	}

	private static Font getFont(int size, int fontStyle) {
		BaseFont bfChinese;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			Font smallFont = new Font(bfChinese, size, fontStyle);
			return smallFont;
		} catch (DocumentException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
