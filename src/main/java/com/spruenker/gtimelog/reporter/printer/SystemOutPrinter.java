package com.spruenker.gtimelog.reporter.printer;

/**
 * Prints the report to stdout.
 *
 * @author Simon Sprünker
 */
public class SystemOutPrinter implements Printer {

    @Override
    public void print(String report) {
        System.out.println(report);
    }

}

