/*
 * 2016年3月15日 
 */
package kevsn.libdemo.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfContentParser;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

/**
 * @author Kevin
 *
 */
public class ItextTableReader {

	/**
	 * @param args
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void main(String[] args)
			throws IOException, DocumentException {
		PdfReader reader = null;
		try {
			read("outtable.pdf");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	private static void read(String toRead)
			throws IOException, DocumentException {
		Document rd = new Document();
		try (FileOutputStream out = new FileOutputStream("read.pdf")) {
			PdfWriter writer = PdfWriter.getInstance(rd, out);
			rd.open();
			PdfReader reader = new PdfReader(toRead);
			int num = reader.getNumberOfPages();
			for (int i = 1; i <= num; i++) {
				PdfImportedPage page = writer.getImportedPage(reader, i);
				PdfDocument doc = page.getPdfDocument();
				
				System.out.println(page.getHeight());
			}
			rd.close();
		}
	}
}
