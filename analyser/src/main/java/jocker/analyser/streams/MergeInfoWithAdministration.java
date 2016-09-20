package jocker.analyser.streams;

import jocker.analyser.util.MyEventTimeExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.kafka.streams.processor.AbstractProcessor;

import java.util.Properties;

import static jocker.analyser.util.Colors.BLACK;
import static jocker.analyser.util.Colors.RED;

/**
 * Created by ilyasergeev on 07/09/16.
 */
public class MergeInfoWithAdministration {
    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "merge-info-with-administration");
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
                .process(() -> new AbstractProcessor<String, String>() {

            @Override
            public void process(String key, String value) {
                System.out.println(RED + key + BLACK + " " + value);
            }

        });

//        kStream.outerJoin(kStreamAdm,
//                (value1, value2) -> value1 != null ? value1 : value2,
//                JoinWindows.of("window").with(1000L),
//                Serdes.String(),
//                Serdes.String(),
//                Serdes.String())
//                .to(Serdes.String(), Serdes.String(), "mergeinfoadministration");

        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder, config);
        kafkaStreams.start();
    }
}
