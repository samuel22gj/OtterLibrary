package com.otter.otterlibrarydemo.omessagedigest;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.OFile;
import com.otter.otterlibrary.OMessageDigest;
import com.otter.otterlibrarydemo.DemoActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class OMessageDigestDemo extends DemoActivity {
    private static final String BILLBOARD =
            "Calculate a fixed-length byte sequence,\n" +
            "according different cryptographic hash function.";
    private static final String[] OPERATION_ITEM = {
            "calculate(String algorithm, byte[] data)",
            "calculate(String algorithm, File file)",
            "calculate(String algorithm, InputStream inputStream)"
    };

    private File mParentDir;
    private File mSampleFile;

    private String mAssetsFileName = "TPLocation.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParentDir = getFilesDir();

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
            case 0: // calculate(String algorithm, byte[] data)
                String str = "Hello world!";
                setLog("calculate(String algorithm, byte[] data)");
                appendLog("MD5: " + OMessageDigest.calculate("MD5", str.getBytes()));
                appendLog("SHA-1: " + OMessageDigest.calculate("SHA-1", str.getBytes()));
                appendLog("SHA-256: " + OMessageDigest.calculate("SHA-256", str.getBytes()));
                appendLog("o123: " + OMessageDigest.calculate("o123", str.getBytes()));
                break;
            case 1: // calculate(String algorithm, File file)
                setLog("calculate(String algorithm, File file)");
                appendLog("MD5: " + OMessageDigest.calculate("MD5", mSampleFile));
                appendLog("SHA-1: " + OMessageDigest.calculate("SHA-1", mSampleFile));
                appendLog("SHA-256: " + OMessageDigest.calculate("SHA-256", mSampleFile));
                appendLog("o123: " + OMessageDigest.calculate("o123", mSampleFile));
                break;
            case 2: // calculate(String algorithm, InputStream inputStream)
                try {
                    InputStream input = getAssets().open(mAssetsFileName);
                    setLog("calculate(String algorithm, InputStream inputStream)");
                    appendLog("MD5: " + OMessageDigest.calculate("MD5", input));
                    appendLog("SHA-1: " + OMessageDigest.calculate("SHA-1", input));
                    appendLog("SHA-256: " + OMessageDigest.calculate("SHA-256", input));
                    appendLog("o123: " + OMessageDigest.calculate("o123", input));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
