package com.otter.otterlibrary;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * OShellCommand is a wrapper of shell command execution.
 */
public class OShellCommand {

    // TODO: Support multiple commands.
    /** Run command with application PID & UID and return output string. */
    public static String exec(String command) {
        String output = null;

        try {
            Process app = Runtime.getRuntime().exec(command);
            output = getOutput(app);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    /** Run command with root permission and return output string. */
    public static String su(String... commands) {
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
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}
