/**
 *
 */
package com.spruenker.gtimelog.reporter;

import com.spruenker.gtimelog.reporter.formatter.Formatter;
import com.spruenker.gtimelog.reporter.formatter.FormatterTypes;
import com.spruenker.gtimelog.reporter.model.Report;
import com.spruenker.gtimelog.reporter.printer.Printer;
import com.spruenker.gtimelog.reporter.printer.PrinterTypes;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Main class for the GTimelog Reporter.
 *
 * This class reads a gtimelog-file, puts the data in a computable Model (Report), formats it using a Formatter, and
 * prints it, using a Printer. Formatter and Printer are Interfaces so you can write your own.
 *
 * @author Simon Sprünker
 */
public class Reporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Reporter.class);

    private static String file;
    private static Formatter formatter;
    private static Printer printer;

    /**
     * Run with "java com.spruenker.gtimelog.reporter.Reporter -i "~/.gtimelog/timelog.txt"
     *
     * @param args First (and only) argument is the location of you timelog.txt file.
     */
    public static void main(String[] args) {

        // Parse command line arguments
        parseArgs(args);

        // Read gtimelog-file
        Report report = importFromFile();

        // Generate and print report
        printer.print(formatter.format(report));
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
        options.addOption("i", "infile", true, "The location and file name of your gtimelog-file. Example ~/.gtimelog/timelog.txt");
        options.addOption("f", "formatter", true, "The Formatter to use. Available formatters are: text");
        options.addOption("p", "printer", true, "The Printer to use. Available printers are: tty");
        //options.addOption("o", "output", true, "The name of the file the report is written into");


        CommandLineParser parser = new PosixParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            String infileArg = cmd.getOptionValue("i", System.getProperty("user.home") +"/.gtimelog/timelog.txt");
            String formatterArg = cmd.getOptionValue("f", "text");
            String printerArg = cmd.getOptionValue("p", "tty");

            file = infileArg;
            formatter = FormatterTypes.getInstance(formatterArg);
            printer = PrinterTypes.getInstance(printerArg);

        } catch (ParseException e) {
            LOGGER.warn("Could not parse command line arguments: ", e);
        }


    }

}
