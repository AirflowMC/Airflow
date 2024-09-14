package me.glicz.airflow.console;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;

import java.util.List;
import java.util.regex.Pattern;

@Plugin(name = "stripAnsi", category = PatternConverter.CATEGORY)
@ConverterKeys({"stripAnsi"})
public class StripANSIConverter extends LogEventPatternConverter {
    private static final Pattern ANSI_PATTERN = Pattern.compile("\\e\\[[\\d;]*[^\\d;]");
    private final List<PatternFormatter> formatters;

    protected StripANSIConverter(List<PatternFormatter> formatters) {
        super("stripAnsi", null);
        this.formatters = formatters;
    }

    public static StripANSIConverter newInstance(Configuration config, String[] options) {
        if (options.length != 1) {
            return null;
        }

        return new StripANSIConverter(PatternLayout.createPatternParser(config).parse(options[0]));
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        StringBuilder builder = new StringBuilder();
        for (PatternFormatter converter : this.formatters) {
            converter.format(event, builder);
        }

        String logOutput = builder.toString();
        toAppendTo.append(ANSI_PATTERN.matcher(logOutput).replaceAll(""));
    }
}
