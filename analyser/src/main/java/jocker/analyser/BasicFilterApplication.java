package jocker.analyser;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.Properties;

/**
 * Created by ilyasergeev on 23/08/16.
 */
public class BasicFilterApplication {

    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-first-streams-application");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "192.168.99.100:2181");
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG,  WallclockTimestampExtractor.class.getName());
        StreamsConfig config = new StreamsConfig(settings);
        KStreamBuilder kStreamBuilder = new KStreamBuilder();

        KStream<String, String> kStream =  kStreamBuilder.stream(new Serdes.StringSerde(), new Serdes.StringSerde(),"rsyslog_apps");

        kStream.filter((k, v) -> v.contains("start")).to(new Serdes.StringSerde(), new Serdes.StringSerde(),"onlystart");

        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder, config);
        kafkaStreams.start();

    }
}
