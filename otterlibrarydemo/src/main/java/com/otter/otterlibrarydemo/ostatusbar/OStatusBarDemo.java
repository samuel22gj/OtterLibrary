package com.otter.otterlibrarydemo.ostatusbar;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.OStatusBar;
import com.otter.otterlibrarydemo.DemoActivity;

public class OStatusBarDemo extends DemoActivity {
    private static final String BILLBOARD =
            "Needed permission: android.permission.EXPAND_STATUS_BAR";
    private static final String[] OPERATION_ITEM = {
            "expandNotificationsPanel() && collapsePanels()",
            "expandSettingsPanel() && collapsePanels()"
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
            case 0: // expandNotificationsPanel() && collapsePanels()
                appendLog("expandNotificationsPanel()");
                OStatusBar.expandNotificationsPanel(getApplicationContext());

                appendLog("Close notification after 2 seconds...");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        appendLog("collapsePanels()");
                        OStatusBar.collapsePanels(getApplicationContext());
                    }
                }, 2000);
                break;
            case 1: // expandSettingsPanel() && collapsePanels()
                appendLog("expandSettingsPanel()");
                OStatusBar.expandSettingsPanel(getApplicationContext());

                appendLog("Close quick setting after 2 seconds...");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        appendLog("collapsePanels()");
                        OStatusBar.collapsePanels(getApplicationContext());
                    }
                }, 2000);
                break;
            default:
                break;
        }
    }
}
