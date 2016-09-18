package jocker.analyser.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by ilyasergeev on 26/08/16.
 */
public class MyEventTimeExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record) {
        DateTimeFormatter dateFormatter = ISODateTimeFormat.dateTimeParser();
        String stringValue = record.value().toString();
        DateTime dateTime = dateFormatter.parseDateTime(stringValue.substring(0, stringValue.indexOf(' ')));
        return dateTime.toDate().getTime();
    }

}