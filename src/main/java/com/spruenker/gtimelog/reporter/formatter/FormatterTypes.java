package com.spruenker.gtimelog.reporter.formatter;

/**
 * Enum of all available Formatters.
 * @author Simon Sprünker
 */
public enum FormatterTypes {
    TEXT    (new TextFormatter(), "text");

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
        return FormatterTypes.TEXT.getInstance();
    }


}
