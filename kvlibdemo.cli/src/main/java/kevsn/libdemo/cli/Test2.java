/*
 * 2016年8月12日 
 */
package kevsn.libdemo.cli;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * @author Kevin
 *
 */
public class Test2 {

	public static void main(String[] args) {
		OptionParser parser = new OptionParser() {
			{
				accepts("p").withRequiredArg().ofType(Integer.class)
						.describedAs("count").defaultsTo(1);
			}
		};
		OptionSet parsed = parser.parse(args);
		System.out.println(parsed.valueOf("p"));
	}
}
