package kevsn.lucene.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearchTest {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException, ParseException {
		String index = "/Users/kevin/lucenetest/index";
		Map<String, Queue<Integer>> matches = new HashMap<>();
		try (IndexReader reader = DirectoryReader
				.open(FSDirectory.open(Paths.get(index)))) {
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			QueryParser parser = new QueryParser("content", analyzer);
			while (true) {
				String line = getSearchValue();
				if (StringUtils.isEmpty(line)) {
					break;
				}
				Query query = parser.parse(line);
				System.out.println("Searching for: " + query.toString("contents"));
				TopDocs hits = searcher.search(query, 10);
				ScoreDoc[] docs = hits.scoreDocs;
				for (ScoreDoc scoreDoc : docs) {
					Document document = searcher.doc(scoreDoc.doc);
					Field pathField = (Field) document.getField("path");
					String pathValue = pathField.stringValue();
					IndexableField lineNumField = document.getField("lineNum");
					Integer lineNum = (Integer) lineNumField.numericValue();
					addLineNum(matches, pathValue, lineNum);
				}
				matches.forEach((pathValue, lineNums) -> {
					Path path = Paths.get(pathValue);
					printLineContent(lineNums, path);
				});
				matches.clear();
			}
			System.out.println("end search");
		}
	}

	private static void addLineNum(Map<String, Queue<Integer>> map, String path,
			Integer lineNum) {
		Queue<Integer> queue = map.get(path);
		if (queue == null) {
			queue = new LinkedList<>();
			map.put(path, queue);
		}
		queue.add(lineNum);
	}

	private static String getSearchValue() {
		System.out.print("input search value:");
		return scanner.next();
	}

	private static void printLineContent(Queue<Integer> lineNumbs, Path file) {
		try (BufferedReader reader = Files.newBufferedReader(file)) {
			int num = 0;
			String line;
			Integer n = lineNumbs.peek();
			while (((line = reader.readLine()) != null) && !lineNumbs.isEmpty()) {
				if (n == num++) {
					lineNumbs.remove();
					System.out.println(file.getFileName().toString() + " line " + (num) + ":" + line);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
