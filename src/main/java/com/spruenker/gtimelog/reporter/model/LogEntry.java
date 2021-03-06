/**
 *
 */
package com.spruenker.gtimelog.reporter.model;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for one line of the gtimelog-report.
 *
 * @author Simon Sprünker
 */
public class LogEntry {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogEntry.class);

    /**
     * On what index of the gtimelog string does the Task start (and the Date end)?
     */
    private static final int INDEX_OF_TASK = 18;

    /**
     * Activities with indicator are non-working times. Meaning you slacked!
     */
    private static final String SLACKING_INDICATOR = "**";

    /**
     * Additional semantics for the activity.
     */
    private static final String CATEGORY_DIVIDER = ":";

    /**
     * The date format that is used by gtimelog.
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

    private String logEntry;


    @SuppressWarnings("unused")
    private LogEntry() {
    }


    /**
     * Creates a new instance of the LogEntry with the given String as task.
     *
     * @param logEntry String that describes the task.
     */
    public LogEntry(String logEntry) {
        this.logEntry = logEntry;
    }


    /**
     * Returns the Task of this LogEntry.
     *
     * @return Task
     */
    public String getTask() {
        return logEntry.substring(INDEX_OF_TASK);
    }


    /**
     * Returns the DateTime of this LogEntry.
     * @return Date and Time
     */
    public LocalDateTime getDateTime() {
        String dateString = "";
        dateString = logEntry.substring(0, INDEX_OF_TASK - 2);
        return DATE_FORMATTER.parseLocalDateTime(dateString);
    }


    /**
     * Checks whether this LogEntry is for Work or for Slacking.
     *
     * @return True, if the given LogEntry is work. False otherwise.
     */
    public boolean isSlacking() {
        return getTask().endsWith(SLACKING_INDICATOR);
    }


    /**
     * Returns a list of categories. Examples:
     * <p/>
     * "Bugfixing" -> { "Bugfixing" } "Acme: support" -> { "Acme", "Acme: support" } "Foo Inc: Migration: project setup" -> {
     * "Foo Inc", "Foo Inc: Migration", "Foo Inc: Migration: project setup" }
     *
     * @return List of categories
     */
    public List<String> getCategories() {

        List<String> categories = new ArrayList<String>();

        String[] items = getTask().split(CATEGORY_DIVIDER);

        StringBuilder category = new StringBuilder();

        for (String item : items) {
            item = item.trim();
            if (category.toString().isEmpty()) {
                category.append(item);
            } else {
                category.append(CATEGORY_DIVIDER).append(" ").append(item);
            }
            categories.add(category.toString());

        }

        return categories;

    }

}
