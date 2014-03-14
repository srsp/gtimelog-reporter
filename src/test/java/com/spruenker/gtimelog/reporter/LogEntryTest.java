/**
 *
 */
package com.spruenker.gtimelog.reporter;


import static org.junit.Assert.assertEquals;

import java.util.Calendar;


import com.spruenker.gtimelog.reporter.model.LogEntry;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;

/**
 * @author Simon Sprünker
 *
 */
public class LogEntryTest {

	@Test
	public void testGetTask() {
        // Given
		LogEntry logEntry = new LogEntry("2011-11-21 19:14: Acme Inc: Implementation");
        // When
        String task = logEntry.getTask();
        // Then
		assertEquals("Acme Inc: Implementation", task);
	}

	@Test
	public void getDateAndTimeWhenLogEntryAdded() {
        // Given
		LogEntry logEntry = new LogEntry("2011-11-21 19:14: Acme Inc: Implementation");
		Calendar expected = Calendar.getInstance();
		expected.clear();
		expected.set(2011, 10, 21, 19, 14, 0); // Month is zero-based
        // When
        LocalDateTime date = logEntry.getDateTime();
        // Then
		assertEquals(expected.getTimeInMillis(), date.toDate().getTime());
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
