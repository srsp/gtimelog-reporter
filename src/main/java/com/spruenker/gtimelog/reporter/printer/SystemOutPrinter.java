package com.spruenker.gtimelog.reporter.printer;

import com.spruenker.gtimelog.reporter.model.Day;
import com.spruenker.gtimelog.reporter.model.Report;
import com.spruenker.gtimelog.reporter.TimeUtil;

import java.util.Map;

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

