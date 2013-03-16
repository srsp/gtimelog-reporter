/**
 *
 */
package com.spruenker.gtimelog.reporter;


import com.spruenker.gtimelog.reporter.model.Day;
import com.spruenker.gtimelog.reporter.model.LogEntry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		Assert.assertEquals(107 * 60L, day.getSlackingTime());
	}

	@Test
	public void testWorkingTime() {
		Assert.assertEquals(396 * 60L, day.getWorkingTime());
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
}
