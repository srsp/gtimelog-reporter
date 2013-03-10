/**
 *
 */
package com.spruenker.gtimelog.reporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;

import com.spruenker.gtimelog.reporter.TimeUtil.Duration;

/**
 * Main class.
 * 
 * @author Simon Sprünker
 */
public class Reporter {

	private static final String IN_FILE_DEFAULT = "/Users/simon/.gtimelog/timelog.txt";

	private static final TimeUtil timeUtilHour = new TimeUtil(Duration.MINUTE, Duration.HOUR);
	private static final TimeUtil timeUtilDay = new TimeUtil(Duration.MINUTE, Duration.HOUR, Duration.WORK_DAY);


	/**
	 * Run with "java com.spruenker.gtimelog.reporter.Reporter "~/.gtimelog/timelog.txt"
	 * 
	 * @param args
	 *            First (and only) argument is the location of you timelog.txt file.
	 */
	public static void main(String[] args) {

		String file = IN_FILE_DEFAULT;

		if (args != null && args.length > 0) {
			file = args[0];
		}

		try {
			Cumulator cumulator = new Cumulator();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			line = br.readLine();
			while (line != null) {
				cumulator.addLogEntry(line);
				line = br.readLine();
			}
			br.close();
			printResult(cumulator);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static void printResult(Cumulator cumulator) {
		// Daily Report
		for (Day day : cumulator.getDays()) {
			out("\n");
			out(day.getPrettyDay());
			// For every project
			out("\tSlacked: " + timeUtilHour.getDuration(day.getSlackingTime()));
			out("\tWorked:  " + timeUtilHour.getDuration(day.getWorkingTime()));
			out("");
			out("\tTASKS");
			for (Entry<String, Long> task : day.getTaskTimes().entrySet()) {
				out("\t\t" + task.getKey() + ": " + timeUtilHour.getDuration(task.getValue()));
			}
			out("");
			out("\tCATEGORIES");
			for (Entry<String, Long> category : day.getCategoryTimes().entrySet()) {
				out("\t\t" + category.getKey() + ": " + timeUtilHour.getDuration(category.getValue()));
			}
		}
		out("");

		// Monthly report
		out("Slacked: " + timeUtilHour.getDuration(cumulator.getSlackingTime()));
		out("Worked:  " + timeUtilHour.getDuration(cumulator.getWorkingTime()));
		out("");
		out("TASKS");
		for (Entry<String, Long> task : cumulator.getTaskTimes().entrySet()) {
			out("\t" + task.getKey() + ": " + timeUtilHour.getDuration(task.getValue()));
		}
		out("");
		out("CATEGORIES");
		for (Entry<String, Long> category : cumulator.getCategoryTimes().entrySet()) {
			out("\t" + category.getKey() + ": " + timeUtilHour.getDuration(category.getValue())); // + "  //  " +
																									// timeUtilDay.getDuration(category.getValue()));
		}

	}


	private static void out(String string) {
		System.out.println(string);
	}

}
