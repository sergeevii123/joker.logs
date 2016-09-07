import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.*

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
    }
}


logger('org.springframework', OFF)
logger('org.springframework.boot', OFF)
logger('org.springframework.web.servlet', OFF)
logger('org.springframework.security.web', OFF)
logger('org.springframework.context.support', OFF)
logger('org.apache.kafka', OFF)

root(INFO, ["STDOUT"])