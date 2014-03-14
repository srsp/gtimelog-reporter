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
import java.util.Map;
import java.util.TreeMap;

/**
 * Model for one day. Holds all the activities and times for one day.
 *
 * @author Simon Sprünker
 *
 */
public class Day {

    private final static Logger LOGGER = LoggerFactory.getLogger(Day.class);

	private static final int MILLIS_TO_SECONDS = 1000;

    private static final DateTimeFormatter PRETTY_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    /** Holds the day this instance is for. */
    private LocalDateTime dayDate = null;

	/** Holds the Tasks and times for these on the given day. */
	private Map<String, Long> taskTimes = new TreeMap<String, Long>();

	/** Holds the Categories and times for these on the given day. */
	private Map<String, Long> categoryTimes = new TreeMap<String, Long>();

	/** Holds the logged entries for the day. */
	private List<LogEntry> logEntries = new ArrayList<LogEntry>();

	/** Time spent slacking in seconds. */
	private long slackingTime = 0;

	/** Time spent working in seconds. */
	private long workingTime = 0;


	public void addLogEntry(LogEntry entry) {
        if (dayDate == null) {
            dayDate = entry.getDateTime();
        }
		logEntries.add(entry);
	}


	/**
	 * Task times in seconds.
	 *
	 * @return
	 */
	public Map<String, Long> getTaskTimes() {
        calculate();
		return taskTimes;
	}


	/**
	 * Category times in seconds.
	 *
	 * @return
	 */
	public Map<String, Long> getCategoryTimes() {
        calculate();
		return categoryTimes;
	}


	/**
	 * Slacking time in seconds.
	 *
	 * @return
	 */
	public long getSlackingTime() {
        calculate();
		return slackingTime;
	}


	/**
	 * Working time in seconds.
	 *
	 * @return
	 */
	public long getWorkingTime() {
        calculate();
		return workingTime;
	}


	/**
	 * Times are calculated in seconds.
	 */
	private void calculate() {
		// Initialize values
		taskTimes = new TreeMap<String, Long>();
		categoryTimes = new TreeMap<String, Long>();
		workingTime = 0;
		slackingTime = 0;

		// If we don't have entries or only one entries we cannot calculate a duration.
        if (logEntries == null) {
            LOGGER.debug("Cannot calculate duration.");
            return;
        } else if (logEntries.size() < 2) {
            if (LOGGER.isDebugEnabled() && dayDate != null) {
			    LOGGER.debug("Cannot calculate duration for {}", PRETTY_DATE_FORMATTER.print(dayDate));
            }
			return;
		}

		// Start the calculation
		LocalDateTime start = dayDate;

		for (LogEntry logEntry : logEntries) {
			// First logEntry is not interesting.
			if (start.equals(logEntry.getDateTime())) {
				continue;
			}

			String task = logEntry.getTask();

			// Add time in Task map
			long time = (logEntry.getDateTime().toDate().getTime() - start.toDate().getTime()) / MILLIS_TO_SECONDS;
			if (taskTimes.containsKey(task)) {
				long totalTime = time + taskTimes.get(task);
				taskTimes.put(task, totalTime);
			} else {
				taskTimes.put(task, time);
			}

			// Sort in Working / Slacking Category
			if (logEntry.isSlacking()) {
				slackingTime += time;
			} else {
				workingTime += time;
			}

			// Calculate times for categories
			for (String category : logEntry.getCategories()) {
				if (categoryTimes.containsKey(category)) {
					long totalTime = time + categoryTimes.get(category);
					categoryTimes.put(category, totalTime);
				} else {
					categoryTimes.put(category, time);
				}
			}

			// Set new start date
			start = logEntry.getDateTime();

		}
	}


    public boolean isSameDay(final LocalDateTime otherDay) {
        // The day is unused, therefore it can be used for the given date.
        if (logEntries.isEmpty()) {
            return true;
        }

        LocalDateTime thisDay = dayDate;

        return (thisDay.getYear() == otherDay.getYear() && thisDay.getDayOfYear() == otherDay.getDayOfYear());
    }


	public String getPrettyDay() {
		if (logEntries.isEmpty()) {
			return "";
		}

		LocalDateTime dayDate = logEntries.get(0).getDateTime();
		return PRETTY_DATE_FORMATTER.print(dayDate);
	}

}
