/*
 * 2016年7月11日 
 */
package kevsn.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author Kevin
 *
 */
public class ProducerTest {

	public static void main(String[] args) throws InterruptedException {
		Map<String, Object> confmap = new HashMap<>();
		confmap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		confmap.put(ProducerConfig.RETRIES_CONFIG, 3);
		confmap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class);
		confmap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				StringSerializer.class);
		try (KafkaProducer<String, String> producer = new KafkaProducer<>(
				confmap)) {
			for (int i = 0; i < 100000; i++) {
				// producer.send(new ProducerRecord<String, String>("t2",
				// "T2:" + Integer.toString(i)));
				// producer.send(new ProducerRecord<String, String>("t3",
				// "T3:" + Integer.toString(i)));
				producer.send(new ProducerRecord<String, String>("recent",
						"recent" + i));
				System.out.println("put to recent");
				Thread.sleep(1 * 100);
			}
			System.out.println("finish");
		}

	}
}
