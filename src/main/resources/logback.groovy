import ch.qos.logback.core.ConsoleAppender
import static ch.qos.logback.classic.Level.DEBUG
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

StringBuilder p = new StringBuilder();
p.append("mtool %d{yyyy-MM-dd/HH:mm:ss} ");
p.append("%-5level %thread ");
p.append("%class %line 【");
p.append("%msg");
p.append("】");
p.append("%n");

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = p.toString();
    }
}

root(DEBUG, ["CONSOLE"])