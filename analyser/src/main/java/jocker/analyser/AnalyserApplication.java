package jocker.analyser;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.Properties;

//import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by ilyasergeev on 17/08/16.
 */
public class AnalyserApplication {

    public static void main(String[] args) {
        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-first-streams-application");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "192.168.99.100:2181");
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG,  MyEventTimeExtractor.class.getName());

        StreamsConfig config = new StreamsConfig(settings);

        KStreamBuilder kStreamBuilder = new KStreamBuilder();

        KStream<String, String> kStream =  kStreamBuilder.stream(Serdes.String(), Serdes.String(), "rsyslog_apps");

        KTable<Windowed<String>, Long> notificationCounts =
                kStream.countByKey(
                        TimeWindows.of("window", 1000L)
                                .advanceBy(100L)
                                .until(1000L),
                        Serdes.String());

        notificationCounts.toStream((k,v) -> k.key()).to(Serdes.String(), Serdes.Long(), "metrics");


//        KTable<String, Long> notificationCounts =
//                kStream.countByKey(Serdes.String(), "window");
//
//        notificationCounts.toStream().process(() -> new AbstractProcessor<String, Long>() {
//            @Override
//            public void process(String key, Long value) {
//                System.out.println(value);
//            }
//        });
        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder, config);
        kafkaStreams.start();

    }

}
