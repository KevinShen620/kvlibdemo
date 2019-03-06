/*
 * 2016年3月15日 
 */
package kevsn.libdemo.itext;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

/**
 * @author Kevin
 *
 */
public class ChinesFont {

	public static final Font smallFont;

	public static final Font largeFont;

	static {
		smallFont = getFont(10, Font.NORMAL);
		largeFont = getFont(15, Font.BOLD);
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
