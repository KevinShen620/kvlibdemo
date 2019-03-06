/*
 * 2016年2月26日 
 */
package kevsn.libdemo.cli;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.util.Arrays.*;
import java.util.Date;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.ValueConverter;

/**
 * @author Kevin
 *
 */
public class JoptionDemo {

	public static void demo2() {
		OptionParser parser = new OptionParser();
		parser.accepts("start");
		OptionSet parsed = parser.parse("start");
		List<?> args = parsed.nonOptionArguments();
		for (Object object : args) {
			System.out.println(object);
		}
	}

	public static void demo1() throws IOException {
		OptionParser parser = new OptionParser() {
			{
				accepts("c").withRequiredArg().ofType(Integer.class)
						.describedAs("count").defaultsTo(1);
				accepts("q").withOptionalArg().ofType(Double.class)
						.describedAs("quantity");
				accepts("d", "some date").withRequiredArg().required()
						.withValuesConvertedBy(datePattern("MM/dd/yy"));
				acceptsAll(asList("v", "talkative", "chatty"),
						"be more verbose");
				accepts("output-file").withOptionalArg().ofType(File.class)
						.describedAs("file");
				acceptsAll(asList("h", "?"), "show help").forHelp();
				acceptsAll(asList("cp", "classpath")).withRequiredArg()
						.describedAs(
								"path1" + File.pathSeparatorChar + "path2:...")
						.ofType(File.class)
						.withValuesSeparatedBy(File.pathSeparatorChar);
			}
		};
		OptionSet parsed = parser.parse("-h");
		if (parsed.has("h") || parsed.has("?")) {
			parser.printHelpOn(System.out);
		}
	}

	private static ValueConverter<Date> datePattern(String pattern) {
		return new ValueConverter<Date>() {

			@Override
			public Date convert(String value) {
				SimpleDateFormat f = new SimpleDateFormat(pattern);
				try {
					return f.parse(value);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public Class<Date> valueType() {
				return Date.class;
			}

			@Override
			public String valuePattern() {
				return null;
			}
		};

	}

	public static void parseDemo(String[] args) {
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
		
		
	}
}
