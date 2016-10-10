package jocker.analyser.util;

import org.apache.kafka.streams.processor.AbstractProcessor;

import static jocker.analyser.util.Colors.BLACK;
import static jocker.analyser.util.Colors.RED;

/**
 * Created by ilyasergeev on 22/09/16.
 */
public class StringKeyLongValueToConsole extends AbstractProcessor<String, Long> {
    @Override
    public void process(String key, Long value) {
        System.out.println("exception " + RED + key + BLACK + " " + value);
    }
}