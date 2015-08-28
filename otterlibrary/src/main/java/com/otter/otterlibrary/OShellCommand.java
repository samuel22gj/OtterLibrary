package com.otter.otterlibrary;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * OShellCommand is a wrapper of shell command execution.
 */
public class OShellCommand {

    /** Run command with application PID & UID and return output string. */
    public static String exec(String... commands) {
        if (commands == null) return null;

        String output = null;

        // Use normal command delimeter ";" to combine commands.
        StringBuilder mixedCommand = new StringBuilder();
        for (String command : commands) {
            mixedCommand.append(command);
            // Must add space around semicolon! Due to it will be broke down
            // into array by StringTokenizer in java.lang.Runtime(Line:238).
            mixedCommand.append(" ; ");
        }
        // Remove last semicolon.
        mixedCommand.delete(mixedCommand.length() - 3, mixedCommand.length());

        try {
            Process app = Runtime.getRuntime().exec(mixedCommand.toString());
            output = getOutput(app);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    /** Run command with root permission and return output string. */
    public static String su(String... commands) {
        if (commands == null) return null;

        String output = null;

        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (String command : commands) {
                outputStream.writeBytes(command + "\n");
                outputStream.flush();
            }
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();

            output = getOutput(su);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output;
    }

    /** Get the stdout. */
    private static String getOutput(Process process) {
        StringBuilder output = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}
