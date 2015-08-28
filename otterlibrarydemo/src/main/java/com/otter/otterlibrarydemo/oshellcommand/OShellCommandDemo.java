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
            "ps", "ls .; ls /mnt", "cat /proc/cmdline",
            "su; cat /proc/cmdline", "su; cd /system/app; ls"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayoutWeight(2, 3);
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
            case 0: // ps
                setLog(OShellCommand.exec("ps"));
                break;
            case 1: // ls .; ls /mnt
                setLog(OShellCommand.exec("ls .", "ls /mnt"));
                break;
            case 2: // cat /proc/cmdline
                setLog(OShellCommand.exec("cat /proc/cmdline"));
                break;
            case 3: // su; cat /proc/cmdline
                setLog(OShellCommand.su("cat /proc/cmdline"));
                break;
            case 4: // su; cd /system/app; ls
                setLog(OShellCommand.su("cd /system/app", "ls"));
                break;
            default:
                break;
        }
    }
}
