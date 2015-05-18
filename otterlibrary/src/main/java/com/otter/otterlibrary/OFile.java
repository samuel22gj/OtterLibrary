package com.otter.otterlibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * OFile is a toolkit of file operation.
 */
public class OFile {

    /** Copy the file. It will override current file. */
    public static boolean copyFile(File from, File to) {
        if (from == null || to == null) {
            return false;
        }
        if (!from.exists() || !from.isFile() || !from.canRead()) {
            return false;
        }

        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(from);
            out = new FileOutputStream(to);
            byte[] buffer = new byte[1024];
            int byteCount;

            while ((byteCount = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteCount);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        long fromLength = from.length();
        long toLength = to.length();
        return fromLength == toLength;
    }

    /**
     * Copy the directory recursively.
     * If the destination directory did exist, it will be deleted first.
     */
    public static boolean copyDir(File from, File to) {
        if (from == null || to == null) {
            return false;
        }
        if (!from.exists() || !from.canRead()) {
            return false;
        }

        if (from.isDirectory()) {
            if (to.exists()) {
                // Destination directory did exist, delete it.
                if (!deleteDir(to)) {
                    return false;
                }
            }
            if (!to.mkdirs()) {
                return false;
            }

            String[] children = from.list();
            for (String child : children) {
                if (!copyDir(new File(from, child), new File(to, child))) {
                    return false;
                }
            }
        } else {
            return copyFile(from, to);
        }

        // The directory copy is completely.
        return true;
    }

    /** Delete the folder recursively. */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }

        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File child : children) {
                if (!deleteDir(child)) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
}
