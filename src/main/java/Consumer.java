import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer {
	public static void main(String[] args) {
		var consumer = new KafkaConsumer<String, String>(properties());
		consumer.subscribe(Collections.singletonList("ecommerce.compras"));

		while (true) {
			var records = consumer.poll(Duration.ofMillis(100));

			for (var record : records) {
				System.out.println("------------------------------------------------");
				System.out.println("Nova compra");
				System.out.println("Mensagem recebida: " + record.value());
				System.out.println("Partição: " + record.partition());
				System.out.println("Offset: " + record.offset());
				System.out.println("Timestamp: " + record.timestamp());
			}
		}
	}

	private static Properties properties() {
		var properties = new Properties();

		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "consumo-cliente");

		return properties;
	}
}
