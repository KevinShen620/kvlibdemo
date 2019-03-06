/*
 * 2016年7月11日 
 */
package kevsn.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes.StringSerde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;

/**
 * @author Kevin
 *
 */
public class StreamTest {

	public static void main(String[] args) {
		Map<String, Object> props = new HashMap<>();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "C2");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
				"192.168.1.25:9092,192.168.1.26:9002");
		// props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG,
		// "192.168.1.25:2181,192.168.1.26:2181/kafka");
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, StringSerde.class);
		props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, StringSerde.class);
		StreamsConfig config = new StreamsConfig(props);
		KStreamBuilder builder = new KStreamBuilder();
		builder.stream("ktest").foreach((k, v) -> {
			System.out.println("K=" + k + ",V=" + v);
		});
		KafkaStreams streams = new KafkaStreams(builder, config);
		streams.start();
	}
}
