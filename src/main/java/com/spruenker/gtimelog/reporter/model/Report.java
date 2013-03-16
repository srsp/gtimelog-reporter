/**
 *
 */
package com.spruenker.gtimelog.reporter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author Simon Sprünker
 *
 */
public class Report {

	private List<Day> days = new ArrayList<Day>();

	/**
	 * Adds a log entry to the cumulator.
	 * @param entry log entry
	 */
	public void addLogEntry(String entry) {

		if (entry == null || entry.trim().isEmpty()) {
			return;
		}

		LogEntry logEntry = new LogEntry(entry);
		// Add LogEntry to correct day
		findDay(logEntry.getDate()).addLogEntry(logEntry);
	}

	/**
	 * Gets a list of days, that the cumulator has data for.
	 * @return
	 */
	public List<Day> getDays() {
		return days;
	}

	/**
	 * Finds the correct Day for the given date. This method always returns a day. It never returns null or throws an Exception.
	 * @param date
	 * @return
	 */
	private Day findDay(Date date) {
		for (Day day : days) {
			if (day.isOn(date)) {
				return day;
			}
		}
		Day day = new Day();
		days.add(day);
		return day;
	}

	/**
	 * Gets the slacking time in seconds.
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


}
