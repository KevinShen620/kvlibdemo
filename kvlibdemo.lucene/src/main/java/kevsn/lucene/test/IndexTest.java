package kevsn.lucene.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.FilterDirectory;

public class IndexTest {

	public static void main(String[] args) throws IOException {
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		Path path = Paths.get("/Users/kevin/lucenetest");
		Path indexPath = path.resolve("index");

		try (FSDirectory dir = FSDirectory.open(indexPath);
				IndexWriter writer = new IndexWriter(dir, iwc)) {
			
			Path filePath = path.resolve("files");
			buildIndex(filePath, writer);
		}
	}

	private static void buildIndex(Path dir, IndexWriter iwc) throws IOException {
		Files.walk(dir).filter(IndexTest::isTextFile).forEach(f -> {
			try {
				System.out.println("build index for file " + f);
				// buildFileIndex(f, iwc);
				buildLineIndex(f, iwc);
				System.out.println("finish build index for file " + f);
			} catch (Exception e) {
				System.err.println(e);
			}
		});
	}

	private static boolean isTextFile(Path file) {
		if (Files.isDirectory(file)) {
			return false;
		}
		String fileName = file.getFileName().toString().toLowerCase();
		return !fileName.endsWith(".gif");
	}

	private static void buildFileIndex(Path file, IndexWriter iwc) throws IOException {
		Document doc = new Document();
		doc.add(new StringField("path", file.toString(), Field.Store.YES));
		FileTime lastModified = Files.getLastModifiedTime(file);
		doc.add(new LongField("lastmodify", lastModified.toMillis(), Field.Store.YES));
		// int lineNum = 0;
		try (BufferedReader reader = Files.newBufferedReader(file)) {
			doc.add(new TextField("content", reader));
			/*
			 * String line; while ((line = reader.readLine()) != null) {
			 * doc.add(new TextField(String.valueOf(lineNum++), line,
			 * Field.Store.NO)); }
			 */
			iwc.addDocument(doc);
		}
	}

	private static void buildLineIndex(Path file, IndexWriter iwc) throws IOException {
		try (BufferedReader reader = Files.newBufferedReader(file)) {
			String line;
			int lineNum = 0;
			while ((line = reader.readLine()) != null) {
				Document doc = new Document();
				doc.add(new IntField("lineNum", lineNum++, Field.Store.YES));
				doc.add(new StringField("path", file.toString(), Field.Store.YES));
				doc.add(new TextField("content", line, Field.Store.NO));
				iwc.addDocument(doc);
			}
		}
	}
}
