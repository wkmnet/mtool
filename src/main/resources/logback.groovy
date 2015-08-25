import ch.qos.logback.core.ConsoleAppender
import static ch.qos.logback.classic.Level.DEBUG
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

StringBuilder p = new StringBuilder();
p.append("<message time=\"%d{yyyy-MM-dd/HH:mm:ss}\" ");
p.append("level=\"%-5level\" thread=\"%thread\" ");
p.append("class=\"%class\" line=\"%line\">");
p.append("%n");
p.append("%msg");
p.append("%n");
p.append("</message>");
p.append("%n");

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = p.toString();
    }
}

root(DEBUG, ["CONSOLE"])