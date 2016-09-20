package jocker.analyser.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;

import static jocker.analyser.util.Colors.BLACK;
import static jocker.analyser.util.Colors.RED;

/**
 * Created by ilyasergeev on 17/09/16.
 */
public class MessageConsumer {

    public static void main(String[] args) {

        String topic = args[0];
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.99.100:9092");
        props.put("group.id", String.valueOf(Math.random()));
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props,
                new StringDeserializer(), new StringDeserializer());

        consumer.subscribe(Collections.singletonList(topic));
        System.out.println("Subscribed to topic " + topic);
        int i = 0;

        try(PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));) {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    out.println(RED + record.key() + BLACK + " " + record.value());
                    out.flush();
                }
            }
        }
    }

}
