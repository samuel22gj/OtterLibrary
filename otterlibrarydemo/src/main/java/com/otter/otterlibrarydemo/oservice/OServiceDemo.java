package com.otter.otterlibrarydemo.oservice;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.OService;
import com.otter.otterlibrarydemo.DemoActivity;

public class OServiceDemo extends DemoActivity {
    private static final String BILLBOARD =
            "You can start/stop dummy service.\n" +
            "It will show on notification if it start.";
    private static final String[] OPERATION_ITEM = {
            "Start dummy service", "Stop dummy service",
            "isRunning(Context, Class<?>)", "isRunning(Context, String)"
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (OService.isRunning(getApplication(), DummyService.class)) {
            stopService(new Intent(getApplicationContext(), DummyService.class));
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
            case 0: // Start dummy service
                startService(new Intent(getApplicationContext(), DummyService.class));
                appendLog("Start dummy service");
                break;
            case 1: // Stop dummy service
                stopService(new Intent(getApplicationContext(), DummyService.class));
                appendLog("Stop dummy service");
                break;
            case 2: // isRunning(Context, Class<?>)
                appendLog("isRunning(Context, Class<?>): " +
                        OService.isRunning(getApplicationContext(), DummyService.class));
                break;
            case 3: // isRunning(Context, String)
                appendLog("isRunning(Context, String): " +
                        OService.isRunning(getApplicationContext(), "com.otter.otterlibrarydemo.oservice.DummyService"));
                break;
            default:
                break;
        }
    }
}
