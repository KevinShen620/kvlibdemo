/*
 * 2016年3月15日 
 */
package kevsn.itext2;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Kevin
 *
 */
public class Main {

	public static void main(String[] args)
			throws Exception {
		Document document = new Document();
		try (FileOutputStream out = new FileOutputStream("out.pdf")) {
			PdfWriter.getInstance(document, out);
			try {
				document.open();
				document.add(new Paragraph("浙 D30707", getFont()));
			} finally {
				document.close();
			}
		}
	}

	private static Font getFont() throws DocumentException, IOException {
		BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
				BaseFont.NOT_EMBEDDED);
		Font font = new Font(bf, 12, Font.NORMAL);
		return font;
	}

}
