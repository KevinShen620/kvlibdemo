/*
 * 2016年3月15日 
 */
package kevsn.itext2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @author Kevin
 *
 */
public class Html2Pdf {

	public static void main(String[] args) throws Exception {
		String pdfFile = "demo.pdf";
		String htmlFile = "demo.html";
		Document document = new Document();
		PdfWriter pdfwriter = PdfWriter.getInstance(document,
				new FileOutputStream(pdfFile));
		pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
		document.open();
		BufferedReader reader = Files.newBufferedReader(Paths.get(htmlFile));
		XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, reader);
		document.close();
	}
}
