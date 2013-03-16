/**
 *
 */
package com.spruenker.gtimelog.reporter;

import com.spruenker.gtimelog.reporter.formatter.Formatter;
import com.spruenker.gtimelog.reporter.formatter.FormatterTypes;
import com.spruenker.gtimelog.reporter.model.Report;
import com.spruenker.gtimelog.reporter.printer.Printer;
import com.spruenker.gtimelog.reporter.printer.PrinterTypes;
import com.spruenker.gtimelog.reporter.printer.SystemOutPrinter;
import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Main class.
 *
 * @author Simon Sprünker
 */
public class Reporter {

    private static final String IN_FILE_DEFAULT = "/Users/simon/.gtimelog/timelog.txt";
    private static String file = IN_FILE_DEFAULT;
    private static Formatter formatter = FormatterTypes.TEXT.getInstance();
    private static Printer printer = new SystemOutPrinter();

    /**
     * Run with "java com.spruenker.gtimelog.reporter.Reporter "~/.gtimelog/timelog.txt"
     *
     * @param args First (and only) argument is the location of you timelog.txt file.
     */
    public static void main(String[] args) {

        Report report = importFromFile();

        String reportString = formatter.format(report);

        printer.print(reportString);
    }

    /**
     * Reads the timelog.txt and returns its content as a Report-Object.
     *
     * @return Report
     */
    private static Report importFromFile() {
        Report report = new Report();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            line = br.readLine();
            while (line != null) {
                report.addLogEntry(line);
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find " + file);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not open " + file);
            e.printStackTrace();
        }
        return report;
    }

    private static void parseArgs(String[] args) {

        // create Options object
        Options options = new Options();
        options.addOption("f", "formatter", true, "The Formatter to use. Available formatters are: text");
        options.addOption("p", "printer", true, "The Printer to use. Available printers are: tty");
        //options.addOption("o", "output", true, "The name of the file the report is written into");


        CommandLineParser parser = new PosixParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            String formatterArg = cmd.getOptionValue("f", "text");
            String printerArg = cmd.getOptionValue("p", "tty");

            formatter = FormatterTypes.getInstance(formatterArg);
            printer = PrinterTypes.getInstance(printerArg);

        } catch (ParseException e) {
            // TODO Make nice
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

}
