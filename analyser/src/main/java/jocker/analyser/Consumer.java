package jocker.analyser;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.connect.storage.StringConverter;

import java.net.InetAddress;
import java.util.Properties;
import java.util.stream.Stream;


/**
 * Created by ilyasergeev on 20/08/16.
 */
public class Consumer {
    public static void main(String[] args) {
//        Properties config = new Properties();
//        config.put("client.id", "jocker_consumer");
//        config.put("group.id", "foo");
//        config.put("bootstrap.servers", "192.168.99.100:9092");
//        KafkaConsumer consumer = new KafkaConsumer<String, Long>(config);
//
//        while (true) {
//            ConsumerRecords<String, Long> records = consumer.poll(Long.MAX_VALUE);
//            for (ConsumerRecord<String, Long> record : records.records("rsyslog_apps")) {
//                System.out.println(record);
//            }
//            consumer.commitSync();
//        }


    }
}
