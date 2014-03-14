package com.spruenker.gtimelog.reporter.formatter;

import com.spruenker.gtimelog.reporter.TimeUtil;
import com.spruenker.gtimelog.reporter.model.Day;
import com.spruenker.gtimelog.reporter.model.Report;

import java.util.Map;

/**
 * Formats a Report in plain text using tabs.
 *
 * @author Simon Sprünker
 */
public class TextFormatter implements Formatter {

    private static final TimeUtil timeUtilHour = new TimeUtil(TimeUtil.Duration.MINUTE, TimeUtil.Duration.HOUR);

    private StringBuffer reportBuffer = new StringBuffer();

    @Override
    public String format(Report report) {

        // Daily Report
        for (Day day : report.getDays()) {
            out("\n");
            out(day.getPrettyDay());
            // For every project
            out("\tSlacked: " + timeUtilHour.getDuration(day.getSlackingTime()));
            out("\tWorked:  " + timeUtilHour.getDuration(day.getWorkingTime()));
            out("");
            out("\tTASKS");
            for (Map.Entry<String, Long> task : day.getTaskTimes().entrySet()) {
                out("\t\t" + task.getKey() + ": " + timeUtilHour.getDuration(task.getValue()));
            }
            out("");
            out("\tCATEGORIES");
            for (Map.Entry<String, Long> category : day.getCategoryTimes().entrySet()) {
                out("\t\t" + category.getKey() + ": " + timeUtilHour.getDuration(category.getValue()));
            }
        }
        out("");

        // Monthly report
        out("Slacked: " + timeUtilHour.getDuration(report.getSlackingTime()));
        out("Worked:  " + timeUtilHour.getDuration(report.getWorkingTime()));
        out("");
        out("TASKS");
        for (Map.Entry<String, Long> task : report.getTaskTimes().entrySet()) {
            out("\t" + task.getKey() + ": " + timeUtilHour.getDuration(task.getValue()));
        }
        out("");
        out("CATEGORIES");
        for (Map.Entry<String, Long> category : report.getCategoryTimes().entrySet()) {
            out("\t" + category.getKey() + ": " + timeUtilHour.getDuration(category.getValue())); // + "  //  " +
            // timeUtilDay.getDuration(category.getValue()));
        }

        return reportBuffer.toString();

    }

    private void out(String string) {
        reportBuffer.append(string).append("\n");
    }


}
