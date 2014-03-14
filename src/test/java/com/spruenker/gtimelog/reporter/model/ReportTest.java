package com.spruenker.gtimelog.reporter.model;

import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Simon Sprünker
 */
public class ReportTest {

    @Test
    public void findEmptyDayWhenAskedForDayWithoutLogEntry() {
        // Given
        Report report = new Report();
        // When
        Day day = report.findDay(new LocalDateTime());
        // Then
        Assert.assertNotNull("Day is not null", day);
        Assert.assertTrue("There are no Category Times", day.getCategoryTimes().isEmpty());
        Assert.assertTrue("There are no Task Times", day.getTaskTimes().isEmpty());
        Assert.assertEquals("No Slacking time", 0, day.getSlackingTime());
        Assert.assertEquals("No Working time", 0, day.getWorkingTime());
    }

    @Test
    public void findEmptyDayWhenAskedForDayWithOneLogEntry() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        // When
        Day day = report.findDay(new LocalDateTime(2014, 03, 12, 9, 0));
        // Then
        Assert.assertTrue("There are no Category Times", day.getCategoryTimes().isEmpty());
        Assert.assertTrue("There are no Task Times", day.getTaskTimes().isEmpty());
        Assert.assertEquals("No Slacking time", 0, day.getSlackingTime());
        Assert.assertEquals("No Working time", 0, day.getWorkingTime());
    }

    @Test
    public void categoryTimeIs8hWhenGivenOne8hTask() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        report.addLogEntry("2014-03-12 16:00: someTask");
        // When
        Day day = report.findDay(new LocalDateTime(2014, 03, 12, 9, 0));
        // Then
        Assert.assertEquals("Category time is 8h", 8 * 60 * 60, day.getCategoryTimes().get("someTask").longValue());
    }

    @Test
    public void thereIsOneCategoryWhenGivenOneTask() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        report.addLogEntry("2014-03-12 16:00: someTask");
        // When
        Day day = report.findDay(new LocalDateTime(2014, 03, 12, 9, 0));
        // Then
        Assert.assertEquals("There is one Category Time", 1, day.getCategoryTimes().size());
    }

    @Test
    public void slackingTimeIsZeroWhenGivenOneTask() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        report.addLogEntry("2014-03-12 16:00: someTask");
        // When
        Day day = report.findDay(new LocalDateTime(2014, 03, 12, 9, 0));
        // Then
        Assert.assertEquals("No Slacking time", 0, day.getSlackingTime());
    }

    @Test
    public void workingTimeIs8hWhenGivenOne8hTask() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        report.addLogEntry("2014-03-12 16:00: someTask");
        // When
        Day day = report.findDay(new LocalDateTime(2014, 03, 12, 9, 0));
        // Then
        Assert.assertEquals("Working time is 8h", 8 * 60 * 60, day.getWorkingTime());
    }

    @Test
    public void thereIsOneTaskWhenGivenOneTask() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        report.addLogEntry("2014-03-12 16:00: someTask");
        // When
        Day day = report.findDay(new LocalDateTime(2014, 03, 12, 9, 0));
        // Then
        Assert.assertEquals("There is one Task Time", 1, day.getTaskTimes().size());
    }

    @Test
    public void workingTimeIs8hWhenGivenSameTaskTwiceWith4hEach() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        report.addLogEntry("2014-03-12 12:00: someTask");
        report.addLogEntry("2014-03-12 16:00: someTask");
        // When
        Day day = report.findDay(new LocalDateTime(2014, 03, 12, 9, 0));
        // Then
        Assert.assertEquals("Working time is 8h", 8 * 60 * 60, day.getWorkingTime());
    }


    @Test
    public void daysHasOneWhenOneEntryAdded() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        // When
        List<Day> days = report.getDays();
        // Then
        Assert.assertEquals(1, days.size());
    }

    @Test
    public void daysHasTwoWhenTwoEntriesOnDifferentDaysAreAdded() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        report.addLogEntry("2014-03-13 08:00: arrived");
        // When
        List<Day> days = report.getDays();
        // Then
        Assert.assertEquals(2, days.size());
    }

    @Test
    public void daysHasOneWhenTwoEntriesOnSameDayAreAdded() {
        // Given
        Report report = new Report();
        report.addLogEntry("2014-03-12 08:00: arrived");
        report.addLogEntry("2014-03-12 16:00: someTask");
        // When
        List<Day> days = report.getDays();
        // Then
        Assert.assertEquals(1, days.size());
    }

}
