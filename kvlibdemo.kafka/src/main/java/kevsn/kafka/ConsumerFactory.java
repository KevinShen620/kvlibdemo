/*
 * 2016年8月6日 
 */
package kevsn.kafka;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author Kevin
 *
 */
public class ConsumerFactory {

	public static KafkaConsumer<String, String> getConsumer(String groupName) {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(
				getProperties(groupName));
		return consumer;
	}

	private static Properties getProperties(String groupId) {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		// props.put("enable.auto.commit", "false");
		// props.put("auto.commit.interval.ms", "1000");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10 * 1000);
		props.put("key.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		return props;
	}
}