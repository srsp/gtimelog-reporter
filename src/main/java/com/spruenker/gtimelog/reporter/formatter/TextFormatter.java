package com.spruenker.gtimelog.reporter.formatter;

import com.spruenker.gtimelog.reporter.TimeUtil;
import com.spruenker.gtimelog.reporter.model.Day;
import com.spruenker.gtimelog.reporter.model.Report;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

import static org.apache.commons.lang.StringUtils.rightPad;

/**
 * Formats a Report in plain text using tabs.
 *
 * @author Simon Sprünker
 */
public class TextFormatter implements Formatter {

    private static final TimeUtil timeUtilHour = new TimeUtil(TimeUtil.Duration.MINUTE, TimeUtil.Duration.HOUR);

    private static final int TAB_WIDTH = 2;

    private static final int COLUMN_WIDTH = 23;

    private StringBuffer reportBuffer = new StringBuffer();


    @Override
    public String format(Report report) {

        // Daily Report
        for (Day day : report.getDays()) {
            out("\n");
            out(day.getPrettyDay());
            // For every project
            out(1, "Slacked: " + timeUtilHour.getDuration(day.getSlackingTime()));
            out(1, "Worked:  " + timeUtilHour.getDuration(day.getWorkingTime()));
            out("");
            out(1, "TASKS");
            for (Map.Entry<String, Long> task : day.getTaskTimes().entrySet()) {
                out(2, rightPad(task.getKey() + ": ", COLUMN_WIDTH) + timeUtilHour.getDuration(task.getValue()));
            }
            out("");
            out(1, "CATEGORIES");
            for (Map.Entry<String, Long> category : day.getCategoryTimes().entrySet()) {
                out(2, rightPad(category.getKey() + ": ", COLUMN_WIDTH) + timeUtilHour.getDuration(category.getValue()));
            }
        }
        out("");

        // Monthly report
        out("Slacked: " + timeUtilHour.getDuration(report.getSlackingTime()));
        out("Worked:  " + timeUtilHour.getDuration(report.getWorkingTime()));
        out("");
        out("TASKS");
        for (Map.Entry<String, Long> task : report.getTaskTimes().entrySet()) {
            out(1, rightPad(task.getKey() + ": ", COLUMN_WIDTH) + timeUtilHour.getDuration(task.getValue()));
        }
        out("");
        out("CATEGORIES");
        for (Map.Entry<String, Long> category : report.getCategoryTimes().entrySet()) {
            out(1, rightPad(category.getKey() + ": ", COLUMN_WIDTH) + timeUtilHour.getDuration(category.getValue())); // + "  //  " +
            // timeUtilDay.getDuration(category.getValue()));
        }

        return reportBuffer.toString();

    }

    private void out(String string) {
        reportBuffer.append(string).append("\n");
    }

    private void out(int depth, String string) {
        String left = StringUtils.repeat(" ", TAB_WIDTH * depth);
        reportBuffer.append(left).append(string).append("\n");
    }


}
