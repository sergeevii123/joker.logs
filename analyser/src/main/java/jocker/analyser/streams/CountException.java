package jocker.analyser.streams;

import jocker.analyser.util.MyEventTimeExtractor;
import jocker.analyser.util.StringKeyLongValueToConsole;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.Properties;

import static jocker.analyser.util.Colors.BLACK;
import static jocker.analyser.util.Colors.RED;

/**
 * Created by ilyasergeev on 07/09/16.
 */
public class CountException {
    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "count-exception");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "192.168.99.100:2182");
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, MyEventTimeExtractor.class.getName());

        StreamsConfig config = new StreamsConfig(settings);
        KStreamBuilder kStreamBuilder = new KStreamBuilder();

        KStream<String, String> kStream =  kStreamBuilder.stream(
                new Serdes.StringSerde(), new Serdes.StringSerde(), "info");

        kStream.filter((k, v) -> v.contains("exception"))
                .countByKey(Serdes.String(), "tableForCountExceptions")
                .toStream()
                .process(StringKeyLongValueToConsole::new);

        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder, config);
        kafkaStreams.start();
    }
}
