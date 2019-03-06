/*
 * 2016年2月27日 
 */
package kevsn.libdemo.cli;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.*;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * @author Kevin
 *
 */
public class KApplication {

	public static void main(String[] args) throws IOException {
		OptionParser parser = new OptionParser() {
			{
				accepts("c").withRequiredArg().ofType(Integer.class)
						.describedAs("count").defaultsTo(1);
				accepts("q").withOptionalArg().ofType(Double.class)
						.describedAs("quantity");
				acceptsAll(asList("v", "talkative", "chatty"),
						"be more verbose");
				accepts("output-file").withOptionalArg().ofType(File.class)
						.describedAs("file");
				acceptsAll(asList("help", "h", "?"), "show help").forHelp();
				acceptsAll(asList("cp", "classpath")).withRequiredArg()
						.describedAs(
								"path1" + File.pathSeparatorChar + "path2:...")
						.ofType(File.class)
						.withValuesSeparatedBy(File.pathSeparatorChar);
			}
		};
		OptionSet parsed = parser.parse(args);
		if (parsed.has("h") || parsed.has("?") || parsed.has("help")) {
			parser.printHelpOn(System.out);
			return;
		}
		Thread main = Thread.currentThread();
		main.setName("KApplicationMainThread");
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("stopping KApplication");
			main.interrupt();
		}));
		while (true) {
			System.out.println("Running KApplication");
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				System.out.println("Application Stopped");
				break;
			}
		}

	}
}
