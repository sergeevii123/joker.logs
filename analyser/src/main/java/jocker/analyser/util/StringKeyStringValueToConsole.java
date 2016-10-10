package jocker.analyser.util;

import org.apache.kafka.streams.processor.AbstractProcessor;

import static jocker.analyser.util.Colors.BLACK;
import static jocker.analyser.util.Colors.RED;

/**
 * Created by ilyasergeev on 22/09/16.
 */
public class StringKeyStringValueToConsole extends AbstractProcessor<String, String> {
    @Override
    public void process(String key, String value) {
        System.out.println(RED + key + BLACK + " " + value);
    }
}
