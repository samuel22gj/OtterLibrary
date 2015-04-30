package com.otter.otterlibrary;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * OLog is a wrapper of {@link android.util.Log}.
 */
public class OLog {
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$");
    private static final int MAX_LOG_LENGTH = 4000;

    private static int sPriority = Log.VERBOSE;

    /** No instance. */
    public OLog() {
        throw new AssertionError("No instances.");
    }

    /** Set the priority of log. */
    public static void setPriority(int priority) {
        sPriority = priority;
    }

    /** Send a verbose message. */
    public static void v(String msg) {
        prepareLog(Log.VERBOSE, null, msg, null);
    }

    /** Send a verbose message with explicitly tag. */
    public static void v(String tag, String msg) {
        prepareLog(Log.VERBOSE, tag, msg, null);
    }

    /** Send a verbose message and exception. */
    public static void v(String msg, Throwable tr) {
        prepareLog(Log.VERBOSE, null, msg, tr);
    }

    /** Send a verbose message and exception with explicitly tag. */
    public static void v(String tag, String msg, Throwable tr) {
        prepareLog(Log.VERBOSE, tag, msg, tr);
    }

    /** Send a debug message. */
    public static void d(String msg) {
        prepareLog(Log.DEBUG, null, msg, null);
    }

    /** Send a debug message with explicitly tag. */
    public static void d(String tag, String msg) {
        prepareLog(Log.DEBUG, tag, msg, null);
    }

    /** Send a debug message and exception. */
    public static void d(String msg, Throwable tr) {
        prepareLog(Log.DEBUG, null, msg, tr);
    }

    /** Send a debug message and exception with explicitly tag. */
    public static void d(String tag, String msg, Throwable tr) {
        prepareLog(Log.DEBUG, tag, msg, tr);
    }

    /** Send a info message. */
    public static void i(String msg) {
        prepareLog(Log.INFO, null, msg, null);
    }

    /** Send a info message with explicitly tag. */
    public static void i(String tag, String msg) {
        prepareLog(Log.INFO, tag, msg, null);
    }

    /** Send a info message and exception. */
    public static void i(String msg, Throwable tr) {
        prepareLog(Log.INFO, null, msg, tr);
    }

    /** Send a info message and exception with explicitly tag. */
    public static void i(String tag, String msg, Throwable tr) {
        prepareLog(Log.INFO, tag, msg, tr);
    }

    /** Send a warning message. */
    public static void w(String msg) {
        prepareLog(Log.WARN, null, msg, null);
    }

    /** Send a warning message with explicitly tag. */
    public static void w(String tag, String msg) {
        prepareLog(Log.WARN, tag, msg, null);
    }

    /** Send a warning message and exception. */
    public static void w(String msg, Throwable tr) {
        prepareLog(Log.WARN, null, msg, tr);
    }

    /** Send a warning message and exception with explicitly tag. */
    public static void w(String tag, String msg, Throwable tr) {
        prepareLog(Log.WARN, tag, msg, tr);
    }

    /** Send an error message. */
    public static void e(String msg) {
        prepareLog(Log.ERROR, null, msg, null);
    }

    /** Send an error message with explicitly tag. */
    public static void e(String tag, String msg) {
        prepareLog(Log.ERROR, tag, msg, null);
    }

    /** Send an error message and exception. */
    public static void e(String msg, Throwable tr) {
        prepareLog(Log.ERROR, null, msg, tr);
    }

    /** Send an error message and exception with explicitly tag. */
    public static void e(String tag, String msg, Throwable tr) {
        prepareLog(Log.ERROR, tag, msg, tr);
    }

    /** Send an assert message. */
    public static void wtf(String msg) {
        prepareLog(Log.ASSERT, null, msg, null);
    }

    /** Send an assert message with explicitly tag. */
    public static void wtf(String tag, String msg) {
        prepareLog(Log.ASSERT, tag, msg, null);
    }

    /** Send an assert message and exception. */
    public static void wtf(String msg, Throwable tr) {
        prepareLog(Log.ASSERT, null, msg, tr);
    }

    /** Send an assert message and exception with explicitly tag. */
    public static void wtf(String tag, String msg, Throwable tr) {
        prepareLog(Log.ASSERT, tag, msg, tr);
    }

    private static void prepareLog(int priority, String tag, String msg, Throwable tr) {
        // Abort this log if priority less than we want.
        if (priority < sPriority) return;

        // If tag is null or empty, set it to class name.
        if (tag != null && tag.length() == 0) {
            tag = null;
        }
        if (tag == null) {
            tag = getClassName(3);
        }

        if (msg != null && msg.length() == 0) {
            msg = null;
        }
        if (msg == null) {
            // Abort this log if message and throwable are null.
            if (tr == null) {
                return;
            }

            msg = Log.getStackTraceString(tr);
        } else {
            if (tr != null) {
                msg += "\n" + Log.getStackTraceString(tr);
            }
        }

        printLog(priority, tag, msg);
    }

    private static void printLog(int priority, String tag, String msg) {
        if (msg.length() < MAX_LOG_LENGTH) {
            Log.println(priority, tag, msg);
            return;
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        for (int i = 0, length = msg.length(); i < length; i++) {
            int newline = msg.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                String part = msg.substring(i, end);
                Log.println(priority, tag, part);
                i = end;
            } while (i < newline);
        }
    }

    private static String getClassName(int stackIndex) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= stackIndex) {
            throw new IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements. Are you using ProGuard?");
        }

        StackTraceElement element = stackTrace[stackIndex];
        String tag = element.getClassName();

        // Remain class name and remove extension.
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) tag = m.replaceAll("");

        return tag.substring(tag.lastIndexOf('.') + 1);
    }
}
