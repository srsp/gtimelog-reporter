package com.spruenker.gtimelog.reporter.formatter;

import com.spruenker.gtimelog.reporter.model.Report;

/**
 * Interface for all Formatters.
 *
 * @author Simon Sprünker
 */
public interface Formatter {

    String format(Report report);

}
