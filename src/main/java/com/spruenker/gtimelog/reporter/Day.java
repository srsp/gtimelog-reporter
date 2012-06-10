/**
 *
 */
package com.spruenker.gtimelog.reporter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

	private static final int MILLIS_TO_SECONDS = 1000;

	private static final SimpleDateFormat PRETTY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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

		// Something todo?
		if (logEntries == null || logEntries.size() < 2) {
			// TODO Log
			return;
		}

		// Start the calculation
		Date start = logEntries.get(0).getDate();

		for (LogEntry logEntry : logEntries) {
			// First logEntry is not interesting.
			if (start.equals(logEntry.getDate())) {
				continue;
			}
			;

			String task = logEntry.getTask();

			// Add time in Task map
			long time = (logEntry.getDate().getTime() - start.getTime()) / MILLIS_TO_SECONDS;
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
			start = logEntry.getDate();

		}
	}


	public boolean isOn(Date date) {
		// The day is unused, therefore it can be used for the given date.
		if (logEntries.isEmpty()) {
			return true;
		}

		// Convert dates into Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		Date dayDate = logEntries.get(0).getDate();
		Calendar dayCalendar = Calendar.getInstance();
		dayCalendar.setTimeInMillis(dayDate.getTime());

		// Calculate if we have the correct day
		if (dayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
				&& dayCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
				&& dayCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		return false;
	}


	public String getPrettyDay() {
		// The day is unused, therefore it can be used for the given date.
		if (logEntries.isEmpty()) {
			return "";
		}

		Date dayDate = logEntries.get(0).getDate();
		return PRETTY_DATE_FORMAT.format(dayDate);
	}

}
