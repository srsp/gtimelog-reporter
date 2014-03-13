gtimelog-reporter
=================

gtimelog-reporter is a small Java command line tool that generates text reports from gtimelogs timelog.txt file. It shows you
how long you worked on each task a day and in total.

[gtimelog](https://mg.pov.lt/gtimelog/)

Example
-------

Your ~/.gtimelog/timelog.txt looks like this:

    2013-03-30 09:49: arrived
    2013-03-30 11:02: ACME Inc: Refactoring
    2013-03-30 12:05: Stuff
    2013-03-30 13:05: break **
    2013-03-30 15:27: ACME Inc: Workshop
    2013-03-30 18:43: ACME Inc: Refactoring

Running gtimelog-reporter yields:

    $ java com.spruenker.gtimelog.reporter.Reporter -i "~/.gtimelog/timelog.txt"
    2013-03-30
        Slacked: 1 h
        Worked:  7 h 54 m

        TASKS
            ACME Inc: Refactoring: 4 h 29 m
            ACME Inc: Workshop: 2 h 22 m
            Stuff: 1 h 3 m
            break **: 1 h

        CATEGORIES
            ACME Inc: 6 h 51 m
            ACME Inc: Refactoring: 4 h 29 m
            ACME Inc: Workshop: 2 h 22 m
            Stuff: 1 h 3 m
            break **: 1 h

    Slacked: 1 h
    Worked:  7 h 54 m

    TASKS
        ACME Inc: Refactoring: 4 h 29 m
        ACME Inc: Workshop: 2 h 22 m
        Stuff: 1 h 3 m
        break **: 1 h

    CATEGORIES
        ACME Inc: 6 h 51 m
        ACME Inc: Refactoring: 4 h 29 m
        ACME Inc: Workshop: 2 h 22 m
        Stuff: 1 h 3 m
        break **: 1 h

What does this show you?

* First you get a report for every day:

        2013-03-30

* On each day you get a small summary:

        Slacked: 1 h
        Worked:  7 h 54 m

* You get a summary of the time you spent on each Task:

        TASKS
            ACME Inc: Refactoring: 4 h 29 m
            ACME Inc: Workshop: 2 h 22 m
            Stuff: 1 h 3 m
            break **: 1 h

* You get a summary of all Categories you worked on. Categories are grouped hierarchically:

        CATEGORIES
            ACME Inc: 6 h 51 m
            ACME Inc: Refactoring: 4 h 29 m
            ACME Inc: Workshop: 2 h 22 m
            Stuff: 1 h 3 m
            break **: 1 h

* Next up is the same information for the whole timeframe in your timelog.txt. In this case it is the same as the
above because we only have one day.