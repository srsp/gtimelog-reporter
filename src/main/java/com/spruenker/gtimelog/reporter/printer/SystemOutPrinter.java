package com.spruenker.gtimelog.reporter.printer;

import com.spruenker.gtimelog.reporter.model.Day;
import com.spruenker.gtimelog.reporter.model.Report;
import com.spruenker.gtimelog.reporter.TimeUtil;

import java.util.Map;

/**
 * Provides methods of printing the report.
 *
 * @author Simon Sprünker
 */
public class SystemOutPrinter implements Printer {

    private static final TimeUtil timeUtilHour = new TimeUtil(TimeUtil.Duration.MINUTE, TimeUtil.Duration.HOUR);
    private static final TimeUtil timeUtilDay = new TimeUtil(TimeUtil.Duration.MINUTE, TimeUtil.Duration.HOUR, TimeUtil.Duration.WORK_DAY);

    @Override
    public void print(String report) {

        System.out.println(report);

    }

}

