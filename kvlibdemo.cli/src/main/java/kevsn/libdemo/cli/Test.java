/*
 * 2016年3月26日 
 */
package kevsn.libdemo.cli;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 * @author Kevin
 *
 */
public class Test {

	public static void main(String[] args) throws IOException {
		OptionParser parser = new OptionParser();
		parser.acceptsAll(Arrays.asList("h", "help", "?"), "print help")
				.forHelp();
		parser.acceptsAll(Arrays.asList("c", "conf")).withRequiredArg();
		OptionSet op = parser.parse("-h", "-c", "app.properties");
		op = parser.parse("-a", "b");
		System.out.println(op.has("help"));
		List<OptionSpec<?>> sps = op.specs();
		for (OptionSpec<?> optionSpec : sps) {
			if (optionSpec.isForHelp()) {
				parser.printHelpOn(System.out);
			}
			System.out.println(op.valueOf(optionSpec));
		}
	}
}
