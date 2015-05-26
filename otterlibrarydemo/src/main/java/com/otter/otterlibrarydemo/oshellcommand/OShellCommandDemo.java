package com.otter.otterlibrarydemo.oshellcommand;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.OShellCommand;
import com.otter.otterlibrarydemo.DemoActivity;

public class OShellCommandDemo extends DemoActivity {private static final String BILLBOARD =
        "Select below command and the stdout will print.\n" +
        "Need to root device and install superuser manager app for \"su\".";
    private static final String[] OPERATION_ITEM = {
            "ls", "ps", "cat /proc/cmdline", "su && cat /proc/cmdline"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayoutWeight(1, 2);
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
            case 0: // ls
                setLog(OShellCommand.exec("ls"));
                break;
            case 1: // ps
                setLog(OShellCommand.exec("ps"));
                break;
            case 2: // cat /proc/cmdline
                setLog(OShellCommand.exec("cat /proc/cmdline"));
                break;
            case 3: // su && cat /proc/cmdline
                setLog(OShellCommand.su("cat /proc/cmdline"));
                break;
            default:
                break;
        }
    }
}
