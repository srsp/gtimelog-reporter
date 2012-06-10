/**
 *
 */
package com.spruenker.gtimelog.reporter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model for one line of the gtimelog-report.
 *
 * @author Simon Sprünker
 *
 */
public class LogEntry {

	/** Where does the Task start (and the Date end)? */
	private static final int START_TASK = 18;

	/** Activities with indicator are non-working times. Meaning you slacked! */
	private static final String SLACKING_INDICATOR = "**";

	/** Additional semantics for the activity. */
	private static final String CATEGORY_DIVIDER = ":";

	/** Date format. */
	private static final String dateFormat = "yyyy-MM-dd HH:mm";

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(dateFormat);

	private String logEntry;


	@SuppressWarnings("unused")
	private LogEntry() {
	}


	public LogEntry(String logEntry) {
		this.logEntry = logEntry;
	}


	public String getTask() {
		return logEntry.substring(START_TASK);
	}


	public Date getDate() {
		try {
			String dateString = logEntry.substring(0, START_TASK - 2);
			return SIMPLE_DATE_FORMAT.parse(dateString);
		} catch (ParseException e) {
			// TODO Log Exception
		}
		return null;
	}


	public boolean isSlacking() {
		return getTask().endsWith(SLACKING_INDICATOR);
	}


	/**
	 * Returns a list of categories. Examples:
	 *
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
