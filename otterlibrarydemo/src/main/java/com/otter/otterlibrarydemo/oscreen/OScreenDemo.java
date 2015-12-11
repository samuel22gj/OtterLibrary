package com.otter.otterlibrarydemo.oscreen;

import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.OScreen;
import com.otter.otterlibrarydemo.DemoActivity;

public class OScreenDemo extends DemoActivity {
    private static final String BILLBOARD =
            "Press below buttons to get screen width, height and density.";
    private static final String[] OPERATION_ITEM = {
            "getWidth", "getHeight",
            "getRealWidth", "getRealHeight",
            "getDensity", "getDensityDpi",
            "getRotation", "getOrientation"
    };

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
            case 0: // getWidth
                appendLog("getWidth: " + OScreen.getWidth(getApplicationContext()));
                break;
            case 1: // getHeight
                appendLog("getHeight: " + OScreen.getHeight(getApplicationContext()));
                break;
            case 2: // getRealWidth
                appendLog("getRealWidth: " + OScreen.getRealWidth(getApplicationContext()));
                break;
            case 3: // getRealHeight
                appendLog("getRealHeight: " + OScreen.getRealHeight(getApplicationContext()));
                break;
            case 4: // getDensity
                appendLog("getDensity: " + OScreen.getDensity(getApplicationContext()));
                break;
            case 5: // getDensityDpi
                appendLog("getDensityDpi: " + OScreen.getDensityDpi(getApplicationContext()));
                break;
            case 6: // getRotation
                appendLog("getRotation: " + OScreen.getRotation(getApplicationContext()));
                break;
            case 7: // getOrientation
                appendLog("getOrientation: " + OScreen.getOrientation(getApplicationContext()));
                break;
            default:
                break;
        }
    }
}
