/**
 *
 */
package com.spruenker.gtimelog.reporter;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Utility for time calculations.
 *
 * @author Simon Sprünker
 */
public class TimeUtil {

	// @formatter:off
	public enum Duration {
		MINUTE   (60L, "m"),
		HOUR     (3600L, "h"),
		DAY      (24 * 3600L, "d"),
		WORK_DAY (8 * 3600L, "d"),
		WEEK     (7 * 8 * 3600L, "w"),
		YEAR     (365 * 24 * 3600L, "y");

		private final long time;
		private final String description;


		Duration(long time, String description) {
			this.time = time;
			this.description = description;
		}


		private long time() {
			return time;
		}


		private String description() {
			return description;
		}
	}
	// @formatter:on

	/**
	 * Ordered map with time units. We have to take the TreeMap and not the interface, because we need the sorted KeySet.
	 */
	private TreeMap<Long, String> unitMap;


	/**
	 * Initialize the TimeUtil with the durations.
	 *
	 * @param durations Durations
	 */
	public TimeUtil(Duration... durations) {
		// Map initialisieren
		unitMap = new TreeMap<Long, String>();
		for (Duration duration : durations) {
			unitMap.put(duration.time(), duration.description());
		}
	}


	/**
	 * Returns a human readable time frame. Example (not necessarily correct):
	 *
	 * getIntervalDescription(9384729382938) = 5 years 4 months 3 weeks 6 days 14 hours 2 minutes 38 seconds
	 *
	 * @param seconds
	 *            time range in seconds
	 * @return human readable time frame
	 */
	public String getDuration(long seconds) {
		StringBuffer readableTime = new StringBuffer();
		Iterator<Long> iterator = unitMap.descendingKeySet().iterator();
		unitMap.navigableKeySet();
		while (iterator.hasNext()) {
			long unit = iterator.next();
			if (seconds >= unit) {
				// How many times does the unit fit into the duration?
				long times = seconds / unit;
				String unitDescription = unitMap.get(unit);
				// if (times == 1) {
				// // singular
				// unitDescription = StringUtils.chop(unitDescription);
				// }
				readableTime.append(times).append(" ").append(unitDescription).append(" ");
				// Rest
				seconds = seconds % unit;
			}
		}
		return readableTime.toString();
	}

}
