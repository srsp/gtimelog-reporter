/**
 *
 */
package com.spruenker.gtimelog.reporter;


import static org.junit.Assert.assertEquals;

import java.util.Calendar;


import org.junit.Test;

/**
 * @author Simon Sprünker
 *
 */
public class LogEntryTest {

	@Test
	public void testGetTask() {
		LogEntry logEntry = new LogEntry("2011-11-21 19:14: Acme Inc: Implementation");
		assertEquals("Acme Inc: Implementation", logEntry.getTask());
	}

	@Test
	public void testGetDate() {
		LogEntry logEntry = new LogEntry("2011-11-21 19:14: Acme Inc: Implementation");
		Calendar expected = Calendar.getInstance();
		expected.clear();
		expected.set(2011, 10, 21, 19, 14, 0); // Month is zero-based
		assertEquals(expected.getTimeInMillis(), logEntry.getDate().getTime());
	}

	@Test
	public void testGetCategoryOne() {
		LogEntry logEntry = new LogEntry("2011-11-21 19:14: Bugfixing");
		assertEquals(1, logEntry.getCategories().size());
		assertEquals("Bugfixing", logEntry.getCategories().get(0));
	}

	@Test
	public void testGetCategoryTwo() {
		LogEntry logEntry = new LogEntry("2011-11-21 19:14: Foo: Bugfixing");
		assertEquals(2, logEntry.getCategories().size());
		assertEquals("Foo", logEntry.getCategories().get(0));
		assertEquals("Foo: Bugfixing", logEntry.getCategories().get(1));
	}

	@Test
	public void testGetCategoryThree() {
		LogEntry logEntry = new LogEntry("2011-11-21 19:14: Foo: Bar: Bugfixing");
		assertEquals(3, logEntry.getCategories().size());
		assertEquals("Foo", logEntry.getCategories().get(0));
		assertEquals("Foo: Bar", logEntry.getCategories().get(1));
		assertEquals("Foo: Bar: Bugfixing", logEntry.getCategories().get(2));
	}

}
