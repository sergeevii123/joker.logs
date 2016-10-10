import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.pattern.SyslogStartConverter
import ch.qos.logback.core.encoder.LayoutWrappingEncoder
import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.core.ConsoleAppender
import net.logstash.logback.appender.LogstashTcpSocketAppender
import net.logstash.logback.encoder.LogstashEncoder
import net.logstash.logback.stacktrace.ShortenedThrowableConverter

import static ch.qos.logback.classic.Level.*

def appInstanceName = "DashboardAPI"
def TAGS = "forwarding,json"
def rHost = "192.168.99.100"
def rPort = 514

def baseConv = ShortenedThrowableConverter.newInstance(
        [maxDepthPerThrowable    : 60,
         maxLength               : 4048,
         shortenedClassNameLength: 40,
         excludes                : [],
         rootCauseFirst          : true]
)

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
    }
}

conversionRule("syslogStart", SyslogStartConverter)

appender("RSYSLOG_APPENDER", LogstashTcpSocketAppender) {
    remoteHost = rHost
    port = rPort
    encoder(LogstashEncoder) {
        throwableConverter = baseConv
        prefix(LayoutWrappingEncoder) {
            layout(PatternLayout) {
                pattern="%syslogStart{USER}" + appInstanceName + "/" + TAGS + " "
            }
        }
    }
}

logger('org.springframework', OFF)
logger('org.springframework.boot', OFF)
logger('org.springframework.web.servlet', OFF)
logger('org.springframework.security.web', OFF)
logger('org.springframework.context.support', OFF)

logger("joker.demodashboardapi", INFO, ["STDOUT", "RSYSLOG_APPENDER"], false)

root(INFO, ["RSYSLOG_APPENDER", "STDOUT"])