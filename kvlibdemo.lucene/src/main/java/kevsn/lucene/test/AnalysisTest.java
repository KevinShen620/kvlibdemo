package kevsn.lucene.test;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

public class AnalysisTest {

	public static void main(String[] args) throws IOException {
		tokenTest();
	}

	public static void tokenTest() throws IOException {
		String string = "I'm a programmer,I'm Kevin";
		try (Analyzer analyzer = new StandardAnalyzer();
				TokenStream ts = analyzer.tokenStream("myfield",
						new StringReader(string))) {
			OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
			ts.reset(); // Resets this stream to the beginning. (Required)
			while (ts.incrementToken()) {
				// Use AttributeSource.reflectAsString(boolean)
				// for token stream debugging.
				// System.out.println("token: " + ts.reflectAsString(true));

				// System.out.println(ts.reflectAsString(true));
				// System.out.println("token start offset: " +
				// offsetAtt.startOffset());
				// System.out.println("token end offset: " +
				// offsetAtt.endOffset());

				int start = offsetAtt.startOffset();
				int end = offsetAtt.endOffset();

				System.out.print("{" + ts.reflectAsString(true) + "["
						+ string.substring(start, end) + "]}");
			}
			ts.end();
		}
	}

}
