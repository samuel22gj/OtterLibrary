package com.otter.otterlibrarydemo.olog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.OLog;
import com.otter.otterlibrarydemo.DemoActivity;

public class OLogDemo extends DemoActivity {
    private static final String BILLBOARD =
            "Open logcat, and click items to see what happened.";
    private static final String[] OPERATION_ITEM = {
            "Verbose", "Debug", "Info", "Warning", "Error", "Assert",
            "Priority", "Null Test", "Empty Test", "Other Class"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayoutWeight(5, 1);
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
            case 0: // Verbose
                OLog.v("Verbose");
                OLog.v("V_TAG", "Verbose");
                OLog.v("Verbose", new Throwable());
                OLog.v("V_TAG", "Verbose", new Throwable());
                break;
            case 1: // Debug
                OLog.d("Debug");
                OLog.d("D_TAG", "Debug");
                OLog.d("Debug", new Throwable());
                OLog.d("D_TAG", "Debug", new Throwable());
                break;
            case 2: // Info
                OLog.i("Info");
                OLog.i("I_TAG", "Info");
                OLog.i("Info", new Throwable());
                OLog.i("I_TAG", "Info", new Throwable());
                break;
            case 3: // Warning
                OLog.w("Warning");
                OLog.w("W_TAG", "Warning");
                OLog.w("Warning", new Throwable());
                OLog.w("W_TAG", "Warning", new Throwable());
                break;
            case 4: // Error
                OLog.e("Error");
                OLog.e("E_TAG", "Error");
                OLog.e("Error", new Throwable());
                OLog.e("E_TAG", "Error", new Throwable());
                break;
            case 5: // Assert
                OLog.wtf("Assert");
                OLog.wtf("A_TAG", "Assert");
                OLog.wtf("Assert", new Throwable());
                OLog.wtf("A_TAG", "Assert", new Throwable());
                break;
            case 6: // Priority
                OLog.wtf("Set priority to Log.INFO");
                OLog.setPriority(Log.INFO);
                OLog.d("Debug");
                OLog.w("Warning");
                OLog.wtf("Set priority to Log.VERBOSE");
                OLog.setPriority(Log.VERBOSE);
                OLog.d("Debug");
                OLog.w("Warning");
                break;
            case 7: // Null Test
                OLog.d("Null Test");
                OLog.v(null, null, null);
                break;
            case 8: // Empty Test
                OLog.d("Empty Test");
                OLog.v(null, null, null);
                break;
            case 9: // Other Class
                OLog.v("In OLogDemo");
                InnerClass c2 = new InnerClass();
                c2.logv();
                OuterClass c3 = new OuterClass();
                c3.logv();
            default:
                break;
        }
    }

    private class InnerClass {
        public void logv() {
            OLog.v("In InnerClass");
        }
    }
}
