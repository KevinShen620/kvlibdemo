/*
 * 2016年4月18日 
 */
package kevsn.kvlibdemo.jackson;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.vamsc.util.file.ClassPathIOUtil;

import kevsn.kvlibdemo.jackson.User.Gender;
import kevsn.kvlibdemo.jackson.User.Name;

/**
 * @author Kevin
 *
 */
public class Jackson {

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		AnnotationIntrospector a1 = new JaxbAnnotationIntrospector(
				TypeFactory.defaultInstance());
		AnnotationIntrospector a2 = new JacksonAnnotationIntrospector();
		mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(a1, a2));
		// mapper.getDeserializationConfig().with(a1)
		// .withAppendedAnnotationIntrospector(a2);

		// make serializer use JAXB annotations (only)
		// mapper.getSerializationConfig().with(a1)
		// .withAppendedAnnotationIntrospector(a2);
	}

	public static void stream() throws IOException {
		JsonFactory f = new JsonFactory();
		JsonGenerator g = f.createGenerator(new File("tree.json"),
				JsonEncoding.UTF8);
		g.writeStartObject();
		g.writeObjectFieldStart("name");
		g.writeStringField("first", "Joe");
		g.writeStringField("last", "Sixpack");
		g.writeEndObject(); // for field 'name'
		g.writeStringField("gender", Gender.MALE.toString());
		g.writeBooleanField("verified", false);
		byte[] binaryData = new byte[] { 1, 2, 2 };
		g.writeBinary(binaryData);
		g.writeEndObject();
		// important: will force flushing of output, close underlying
		// output stream
		g.close();
	}

	public static void jsonToBean()
			throws JsonParseException, JsonMappingException, IOException {
		Enumeration<URL> urls = ClassPathIOUtil
				.readClassPathResourcesAsURL("user.json");
		URL url = urls.nextElement();
		User user = mapper.readValue(url, User.class);
		System.out.println(user);
	}

	public static void beanToJson()
			throws JsonGenerationException, JsonMappingException, IOException {
		User user = new User();
		user.setGender(Gender.MALE);
		Name name = new User.Name();
		name.setFirst("Kevin");
		name.setLast("Shen");
		user.setName(name);
		user.setUserImage(new byte[] { 1, 2, 3 });
		mapper.writeValue(Paths.get("user-out.json").toFile(), user);
	}

	public static void beanToJson2()
			throws JsonGenerationException, JsonMappingException, IOException {
		CompBean bean = new CompBean();
		bean.setIntValue(999);
		bean.setStringValue("Kevin Shen");
		bean.setStringList(Arrays.asList("A", "B", "C"));
		bean.setStringArray(new String[] { "D", "E", "F" });
		bean.setDateValue(new Date());
		mapper.writeValue(Paths.get("out2.json").toFile(), bean);
	}

	public static void json2Map()
			throws JsonParseException, JsonMappingException, IOException {
		URL url = ClassPathIOUtil.readClassPathResourcesAsURL("bean2.json")
				.nextElement();
		Map<?, ?> map = mapper.readValue(url, Map.class);
		map.forEach((k, v) -> {
			System.out.print(k + ":" + v);
			System.out.println(" valuetype:" + v.getClass().getName());
		});
	}

	public static void bean2JsonWithAnno()
			throws JsonGenerationException, JsonMappingException, IOException {
		XmlBean bean = new XmlBean();
		bean.setBoolValue(true);
		bean.setIntValue(999);
		bean.setStringValue("KevinShen");
		mapper.writeValue(new File("out3.json"), bean);
	}

	public static void json2Array()
			throws JsonParseException, JsonMappingException, IOException {
		String ar = "[1,2,3]";
		String[] array = mapper.readValue(ar, String[].class);
		for (String each : array) {
			System.out.println(each);
		}
	}

	public static void array2Json() throws JsonProcessingException {
		String[] s = { "1", "2", "3", "4" };
		String json = mapper.writeValueAsString(s);
		System.out.println(json);
	}

	public static void main(String[] args)
			throws JsonParseException, JsonMappingException, IOException {
		array2Json();
	}
}
