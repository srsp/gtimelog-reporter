/**
 *
 */
package com.spruenker.gtimelog.reporter.model;

import com.spruenker.gtimelog.reporter.TimeUtil;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Holds a report of activities for several days.
 *
 * @author Simon Sprünker
 */
public class Report {

    private List<Day> days = new ArrayList<Day>();

    /**
     * Adds a log entry to the cumulator.
     *
     * @param entry log entry
     */
    public void addLogEntry(String entry) {

        if (entry == null || entry.trim().isEmpty()) {
            return;
        }

        LogEntry logEntry = new LogEntry(entry);
        // Add LogEntry to correct day
        Day day = findDay(logEntry.getDateTime());
        day.addLogEntry(logEntry);
    }

    /**
     * Gets a list of days, that the cumulator has data for.
     *
     * @return
     */
    public List<Day> getDays() {
        return days;
    }

    /**
     * Finds the correct Day for the given date. This method always returns a day. It never returns null or throws an Exception.
     *
     * @param date
     * @return
     */
    protected Day findDay(LocalDateTime date) {
        for (Day day : days) {
            if (day.isSameDay(date)) {
                return day;
            }
        }
        Day day = new Day();
        days.add(day);
        return day;
    }

    /**
     * Gets the slacking time in seconds.
     *
     * @return
     */
    public Long getSlackingTime() {
        Long slackingTime = 0L;
        for (Day day : days) {
            slackingTime += day.getSlackingTime();
        }
        return slackingTime;
    }

    /**
     * Gets the working time in seconds.
     *
     * @return
     */
    public Long getWorkingTime() {
        Long workingTime = 0L;
        for (Day day : days) {
            workingTime += day.getWorkingTime();
        }
        return workingTime;
    }

    public Map<String, Long> getTaskTimes() {
        Map<String, Long> totalTasks = new TreeMap<String, Long>();
        for (Day day : days) {
            for (Entry<String, Long> taskEntry : day.getTaskTimes().entrySet()) {
                String task = taskEntry.getKey();
                Long duration = taskEntry.getValue();
                if (!totalTasks.containsKey(task)) {
                    totalTasks.put(task, 0L);
                }
                // Add given tasks duration to the already summed up duration
                totalTasks.put(task, duration + totalTasks.get(task));
            }
        }
        return totalTasks;
    }

    public Map<String, Long> getCategoryTimes() {
        Map<String, Long> totalCategories = new TreeMap<String, Long>();
        for (Day day : days) {
            for (Entry<String, Long> categoryEntry : day.getCategoryTimes().entrySet()) {
                String category = categoryEntry.getKey();
                Long duration = categoryEntry.getValue();
                if (!totalCategories.containsKey(category)) {
                    totalCategories.put(category, 0L);
                }
                // Add given tasks duration to the already summed up duration
                totalCategories.put(category, duration + totalCategories.get(category));
            }
        }
        return totalCategories;
    }

    public Report getPeriod(TimeUtil.Period period) {
        if (TimeUtil.Period.WEEK.equals(period)) {
            Report weeklyReport = new Report();
            LocalDateTime now = new LocalDateTime();
            LocalDateTime monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
            // This week starting Monday
            for (int i = 0; i < 7; i++) {
                LocalDateTime currentDay = monday.plusDays(i);
                for (Day day : days) {
                    if (day.isSameDay(currentDay)) {
                        weeklyReport.days.add(day);
                    }
                }
            }
            return weeklyReport;
        }
        return this;
    }

}
