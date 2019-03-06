/*
 * 2016年8月29日 
 */
package kevsn.libdemo.spark;

import java.util.Arrays;
import java.util.List;
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
		List<Character> list = Arrays.asList('A', 'B', 'C', 'D', 'E');
		Stream<Character> stream = list.stream();
		stream.filter(c -> c % 2 == 0).forEach(System.out::println);
		stream.filter(c -> c % 2 != 0).forEach(System.out::println);
	}

}
