/*
 * 2016年8月29日 
 */
package kevsn.lucene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Kevin
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Character> list = new ArrayList<>();
		for (char c = 'a'; c <= 'z'; c++) {
			list.add(c);
		}
		Stream<Character> stream = list.stream();
		stream.filter(c -> c % 2 == 0).forEach(System.out::println);
		System.out.println("---------------");
		stream.filter(c -> c % 2 == 1).forEach(System.out::println);
	}
}
