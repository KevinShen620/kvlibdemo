/*
 * 2016年3月15日 
 */
package kevsn.libdemo.itext;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfBorderArray;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * @author Kevin
 *
 */
public class IextSign {

	public static void main(String[] args)
			throws IOException, DocumentException {
		PdfReader reader = null;
		try {
			reader = new PdfReader("outtable.pdf");
			sign(reader);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	private static void sign(PdfReader reader)
			throws DocumentException, IOException {
		try (FileOutputStream out = new FileOutputStream("sign.pdf")) {
			PdfStamper stamper = new PdfStamper(reader, out);
			Image img = Image.getInstance("sign.jpg");
			float x = 10;
			float y = 10;
			float w = img.getScaledWidth();
			float h = img.getScaledHeight();
			img.setAbsolutePosition(x, y);
			stamper.getOverContent(1).addImage(img);
			Rectangle linkLocation = new Rectangle(x, y, x + w, y + h);
			PdfDestination destination = new PdfDestination(PdfDestination.FIT);
			PdfAnnotation link = PdfAnnotation.createLink(stamper.getWriter(),
					linkLocation, PdfAnnotation.HIGHLIGHT_INVERT,
					reader.getNumberOfPages(), destination);
			link.setBorder(new PdfBorderArray(0, 0, 0));
			stamper.addAnnotation(link, 1);
			stamper.close();
		}
	}
}
