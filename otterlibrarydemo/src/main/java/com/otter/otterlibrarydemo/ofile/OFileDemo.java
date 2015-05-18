package com.otter.otterlibrarydemo.ofile;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.OFile;
import com.otter.otterlibrarydemo.DemoActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OFileDemo extends DemoActivity {
    private static final String BILLBOARD =
            "The operations is under application internal directory.\n" +
            "Those files will be deleted when application destroy.";
    private static final String[] OPERATION_ITEM = {
            "Print directory structure", "Create sample directory",
            "copyFile", "copyDir", "deleteDir"
    };

    private File mParentDir;
    private File mSampleFile;
    private File mSampleDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayoutWeight(1, 2, 4);

        mParentDir =  getFilesDir();

        // Prepare sample file has 1 KB.
        mSampleFile = new File(mParentDir, "sample-file");
        try {
            RandomAccessFile raf = new RandomAccessFile(mSampleFile, "rw");
            raf.setLength(1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Clean internal directory.
        File[] children = mParentDir.listFiles();
        for (File child : children) {
            OFile.deleteDir(child);
        }
    }

    @Override
    public String setBillboard() {
        return BILLBOARD;
    }

    @Override
    public String[] setOperationItem() {
        return OPERATION_ITEM;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: // Print directory structure
                printDirOnLog(mParentDir);
                break;
            case 1: // Create sample directory
                createSampleDir();
                break;
            case 2: // copyFile
                appendLog("copyFile: " + OFile.copyFile(
                        mSampleFile, new File(mParentDir, "copyFile")));
                break;
            case 3: // copyDir
                appendLog("copyDir: " + OFile.copyDir(
                        mSampleDir, new File(mParentDir, "copyDir")));
                break;
            case 4: // deleteDir
                appendLog("deleteDir: " + OFile.deleteDir(new File(mParentDir, "copyDir")));
                break;
            default:
                break;
        }
    }

    private void createSampleDir() {
        OFile.deleteDir(mSampleDir);

        mSampleDir = new File(mParentDir, "sample-dir");
        mSampleDir.mkdirs();

        try {
            // A file with timestamp.
            SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss", Locale.US);
            String time = format.format(new Date());
            File timestamp = new File(mSampleDir, "file_" + time);
            timestamp.createNewFile();

            // Level 2 dir/file
            File dir  = new File(mSampleDir, "dir");
            dir.mkdirs();
            new File(dir, "file2").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
