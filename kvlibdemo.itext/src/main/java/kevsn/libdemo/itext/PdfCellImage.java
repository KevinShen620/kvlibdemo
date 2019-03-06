/*
 * 2016年3月15日 
 */
package kevsn.libdemo.itext;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Kevin
 *
 */
public class PdfCellImage {

	public static void main(String[] args) throws Exception {

		float[] widths = { 1f, 4f };

		Document document = new Document(PageSize.A4.rotate());
		try (OutputStream out = Files
				.newOutputStream(Paths.get("cellimage.pdf"))) {
			PdfWriter.getInstance(document, out);
			document.open();
			PdfPTable table = new PdfPTable(widths);

			// 插入图片
			table.addCell(
					new PdfPCell(new Paragraph("图片测试", ChinesFont.smallFont)));
			Image image = Image.getInstance("sign.jpg");
			table.addCell(image);

			// 调整图片大小
			table.addCell("This two");
			table.addCell(new PdfPCell(image, true));
			// 不调整
			table.addCell("This three");
			table.addCell(new PdfPCell(image, false));
			document.add(table);
			document.close();
		}
	}
}
