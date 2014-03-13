package com.spruenker.gtimelog.reporter.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum of all available Printers.
 *
 * @author Simon Sprünker
 */
public enum PrinterTypes {
    TTY(new SystemOutPrinter(), "tty");

    private final Printer printer;
    private final String shortName;

    private PrinterTypes(Printer printer, String shortName) {
        this.printer = printer;
        this.shortName = shortName;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PrinterTypes.class);

    public Printer getInstance() {
        return printer;
    }

    public String getShortName() {
        return shortName;
    }

    public static Printer getInstance(String shortName) {
        for (PrinterTypes option : PrinterTypes.values()) {
            if (option.getShortName().equals(shortName)) {
                return option.getInstance();
            }
        }
        LOGGER.warn("Unknown Printer '{}'", shortName);
        return PrinterTypes.TTY.getInstance();
    }


}
