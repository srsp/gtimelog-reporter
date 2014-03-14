/**
 *
 */
package com.spruenker.gtimelog.reporter;


import com.spruenker.gtimelog.reporter.model.Day;
import com.spruenker.gtimelog.reporter.model.LogEntry;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author Simon Sprünker
 *
 */
public class DayTest {

	private Day day;

	private String timelog = "2011-11-25 09:24: arrived\n"
			+ "2011-11-25 09:37: Acme TS Support\n"
			+ "2011-11-25 12:03: Foo: Konfiguration in Datenbank migriert\n"
			+ "2011-11-25 12:23: break **\n"
			+ "2011-11-25 13:15: break **\n"
			+ "2011-11-25 13:59: Acme TS RequestViewer: Implementierung\n"
			+ "2011-11-25 14:34: break **\n"
			+ "2011-11-25 16:00: Acme TS RequestViewer: Implementierung\n"
			+ "2011-11-25 16:53: UnitMeeting\n"
			+ "2011-11-25 17:47: Acme TS RequestViewer: Implementierung\n";

	@Before
	public void setup() {
		day = new Day();
		String[] logs = timelog.split("\n");
		for (String log : logs) {
			day.addLogEntry(new LogEntry(log));
		}
	}

	@Test
	public void testSlackingTime() {
		Assert.assertEquals(107 * 60L, day.getSlackingTime()); // 107 minutes
	}

	@Test
	public void testWorkingTime() {
		Assert.assertEquals(396 * 60L, day.getWorkingTime()); // 396 minutes
	}

	@Test
	public void testCategoryBreak() {
		Long actual = day.getCategoryTimes().get("break **");
		Long expected = 107 * 60L;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCategoryComplicatedMainCategory() {
		Long actual = day.getCategoryTimes().get("Foo");
		Long expected = 146 * 60L;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCategoryComplicatedSubCategory() {
		Long actual = day.getCategoryTimes().get("Foo: Konfiguration in Datenbank migriert");
		Long expected = 146 * 60L;
		Assert.assertEquals(expected, actual);
	}

    @Test
    public void showTaskWhenOnlyTwoEntriesGiven() {
        // Given
        Day day = new Day();
        day.addLogEntry(new LogEntry("2014-03-12 08:00: arrived"));
        day.addLogEntry(new LogEntry("2014-03-12 16:00: Vacation"));
        // When
        Map<String, Long> tasks = day.getTaskTimes();
        // Then
        Assert.assertEquals("There is one task", 1, tasks.size());
        Assert.assertTrue("The task is 'Vacation'", tasks.containsKey("Vacation"));
        Assert.assertTrue("The task duration is 8h", 8 * 60 * 60L == tasks.get("Vacation"));
    }

    @Test
    public void isOnSameDayWhenGivenSameDates() {
        // Given
        Day day = new Day();
        day.addLogEntry(new LogEntry("2014-03-12 08:00: arrived"));
        LocalDateTime date = new LocalDateTime(2014, 3, 12, 8, 0);
        // When
        boolean isOn = day.isSameDay(date);
        // Then
        Assert.assertTrue("Same dates is not on same day", isOn);
    }

    @Test
    public void isOnSameDayWhenGivenSameDayDifferentHour() {
        // Given
        Day day = new Day();
        day.addLogEntry(new LogEntry("2014-03-12 08:00: arrived"));
        LocalDateTime date = new LocalDateTime(2014, 3, 12, 16, 0);
        // When
        boolean isOn = day.isSameDay(date);
        // Then
        Assert.assertTrue("Same dates is not on same day", isOn);
    }

    @Test
    public void isNotOnSameDayWhenGivenFollowingDaySameTime() {
        // Given
        Day day = new Day();
        day.addLogEntry(new LogEntry("2014-03-12 08:00: arrived"));
        LocalDateTime date = new LocalDateTime(2014, 3, 13, 8, 0);
        // When
        boolean isOn = day.isSameDay(date);
        // Then
        Assert.assertFalse("Different dates on same day", isOn);
    }

    @Test
    public void isNotOnSameDayWhenGivenFollowingDayLessThan24h() {
        // Given
        Day day = new Day();
        day.addLogEntry(new LogEntry("2014-03-12 08:00: arrived"));
        LocalDateTime date = new LocalDateTime(2014, 3, 13, 3, 0);
        // When
        boolean isOn = day.isSameDay(date);
        // Then
        Assert.assertFalse("Different dates on same day", isOn);
    }
}
