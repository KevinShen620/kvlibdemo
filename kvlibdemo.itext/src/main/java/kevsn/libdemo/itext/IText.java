/*
 * 2016年3月14日 
 */
package kevsn.libdemo.itext;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Kevin
 *
 */
public class IText {

	public static void main(String[] args)
			throws DocumentException, IOException {
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
