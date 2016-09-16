package jocker.analyser;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.AbstractProcessor;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by ilyasergeev on 07/09/16.
 */
public class CountExceptionPerSecondBot {
    public static void main(String[] args) throws IOException {
        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "count-exception-per-second-bot");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "192.168.99.100:2182");
        settings.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG,  MyEventTimeExtractor.class.getName());

        SlackSession session = SlackSessionFactory.createWebSocketSlackSession("");
        session.connect();
        SlackChannel channel = session.findChannelByName("general");

        StreamsConfig config = new StreamsConfig(settings);
        KStreamBuilder kStreamBuilder = new KStreamBuilder();

        KStream<String, String> kStream = kStreamBuilder.stream(
                new Serdes.StringSerde(), new Serdes.StringSerde(), "info");

        kStream.filter((k, v) -> v.contains("exception")).
                countByKey(
                        TimeWindows.of("window", 1000L)
                                .advanceBy(500L),
                        Serdes.String())
                .toStream((k, v) -> k.key())
                .process(() -> new AbstractProcessor<String, Long>() {
                    @Override
                    public void process(String key, Long value) {
                        String info = "exception " + (key) + " " + (value);
                        System.out.println(info);
                        if (value > 3) session.sendMessage(channel, info);
                    }
                });

        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder, config);
        kafkaStreams.start();

    }
}
