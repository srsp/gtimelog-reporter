/**
 *
 */
package com.spruenker.gtimelog.reporter;

import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Utility for time calculations.
 *
 * @author Simon Sprünker
 */
public class TimeUtil {

    public enum Period {
        ALL,
        WEEK
    }

	// @formatter:off
    /* This looks uglier than it is. We are only calculating durations that occur in one day. So mostly on one day a
    a minute has 60 seconds, and an hour has 60 minutes. Since we are adding durations that occurred on one day, we can assume
    that when someone has worked 48 hours (not in one piece) that in total he has worked 2 days.
      */
	public enum Duration {
		MINUTE   (60L, "m"),
		HOUR     (3600L, "h"),
		DAY      (24 * 3600L, "d"),
		WORK_DAY (8 * 3600L, "d");

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
		// Initialize map
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
		StringBuilder readableTime = new StringBuilder();
		Iterator<Long> iterator = unitMap.descendingKeySet().iterator();
		unitMap.navigableKeySet();
		while (iterator.hasNext()) {
			long unit = iterator.next();
			if (seconds >= unit) {
				// How many times does the unit fit into the duration?
				Long times = seconds / unit;
				String unitDescription = unitMap.get(unit);
				// if (times == 1) {
				// // singular
				// unitDescription = StringUtils.chop(unitDescription);
				// }
				readableTime.append(StringUtils.leftPad(times.toString(), 2)).append(" ").append(unitDescription).append(" ");
				// Rest
				seconds = seconds % unit;
			} else {
                readableTime.append("     ");
            }
		}
		return readableTime.toString();
	}

}
