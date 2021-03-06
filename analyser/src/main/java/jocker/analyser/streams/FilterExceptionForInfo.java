package jocker.analyser.streams;

import jocker.analyser.util.MyEventTimeExtractor;
import jocker.analyser.util.StringKeyStringValueToConsole;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.util.Properties;

/**
 * Created by ilyasergeev on 23/08/16.
 */
public class FilterExceptionForInfo {

    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "filter-exceptions-for-info");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "192.168.99.100:2182");
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG,  MyEventTimeExtractor.class.getName());

        StreamsConfig config = new StreamsConfig(settings);
        KStreamBuilder kStreamBuilder = new KStreamBuilder();

        KStream<String, String> kStream =  kStreamBuilder.stream(
                Serdes.String(),
                Serdes.String(),
                "info");

        kStream.filter((k, v) -> v.contains("exception"))
                .process(StringKeyStringValueToConsole::new);

        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder, config);
        kafkaStreams.start();

    }
}
