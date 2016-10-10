package jocker.analyser.streams;

import jocker.analyser.util.MyEventTimeExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.processor.AbstractProcessor;

import java.util.Properties;

import static jocker.analyser.util.Colors.*;

/**
 * Created by ilyasergeev on 07/09/16.
 */
public class MergeInfoWithAdministrationAndMapColor {
    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "merge-info-with-administration-and-map-color");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "192.168.99.100:2182");
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, MyEventTimeExtractor.class.getName());

        StreamsConfig config = new StreamsConfig(settings);
        KStreamBuilder kStreamBuilder = new KStreamBuilder();

        KStream<String, String> kStream =  kStreamBuilder.stream(
                new Serdes.StringSerde(), new Serdes.StringSerde(), "info");

        KStream<String, String> kStreamAdm =  kStreamBuilder.stream(
                new Serdes.StringSerde(), new Serdes.StringSerde(), "administration");

        kStream.outerJoin(kStreamAdm,
                (value1, value2) -> value1 != null ? value1 : value2,
                JoinWindows.of("window").with(1000L),
                Serdes.String(),
                Serdes.String(),
                Serdes.String())
                .map((key, value) -> {
                    switch (key){
                        case "baseinfo" : return new KeyValue<>(key, YELLOW + value + BLACK);
                        case "additionalinfo" : return new KeyValue<>(key, BLUE + value + BLACK);
                        case "customerinfo" : return new KeyValue<>(key, PURPLE + value + BLACK);
                        default: return new KeyValue<>(key, "strange");
                    }
                }).process(() -> new AbstractProcessor<String, String>() {

            @Override
            public void process(String key, String value) {
                System.out.println(RED + key + BLACK + " " + value);
            }

        });


        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder, config);
        kafkaStreams.start();
    }
}
