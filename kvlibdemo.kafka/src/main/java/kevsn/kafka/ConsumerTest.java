/*
 * 2016年7月11日 
 */
package kevsn.kafka;

import java.util.Arrays;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kevin
 *
 */
public class ConsumerTest {

	private static final Logger logger = LoggerFactory
			.getLogger(ConsumerTest.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable r = () -> {
			String group = Thread.currentThread().getName();
			KafkaConsumer<String, String> consumer = ConsumerFactory
					.getConsumer(group);
			consumer.subscribe(Arrays.asList("recent"));
			// assignpart(consumer);
			while (true) {
				consume(consumer);
			}
		};
		Thread t1 = new Thread(r, "T1");
		// Thread t2 = new Thread(r, "T2");
		t1.start();
		// t2.start();
	}

	private static void assignpart(KafkaConsumer<String, String> consumer) {
		List<PartitionInfo> partInfos = consumer.partitionsFor("recent");
		int part = partInfos.get(0).partition();
		consumer.assign(Arrays.asList(new TopicPartition("recent", part)));
	}

	private static void consume(KafkaConsumer<String, String> consumer) {
		ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
		int i = 0;
		for (ConsumerRecord<String, String> record : records) {
			System.out.printf(
					Thread.currentThread().getName()
							+ ": offset = %d, key = %s, value = %s,part = %d",
					record.offset(), record.key(), record.value(),
					record.partition());
			System.out.println();

			// if (i++ == 99) {
			consumer.commitSync();
			// }
		}
	}
}
