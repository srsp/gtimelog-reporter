package com.spruenker.gtimelog.reporter.formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum of all available Formatters.
 *
 * @author Simon Sprünker
 */
public enum FormatterTypes {
    TEXT    (new TextFormatter(), "text");

    private static final Logger LOGGER = LoggerFactory.getLogger(FormatterTypes.class);

    private final Formatter formatter;
    private final String shortName;

    private FormatterTypes(Formatter formatter, String shortName) {
        this.formatter = formatter;
        this.shortName = shortName;
    }

    public Formatter getInstance() {
        return formatter;
    }

    public String getShortName() {
        return shortName;
    }

    public static Formatter getInstance(String shortName) {
        for (FormatterTypes option : FormatterTypes.values()) {
            if (option.getShortName().equals(shortName)) {
                return option.getInstance();
            }
        }
        LOGGER.warn("Unknown Formatter '{}'", shortName);
        return FormatterTypes.TEXT.getInstance();
    }


}
