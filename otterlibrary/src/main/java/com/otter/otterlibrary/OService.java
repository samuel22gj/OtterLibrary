package com.otter.otterlibrary;

import android.app.ActivityManager;
import android.content.Context;

/**
 * OService is a toolkit of service operation.
 */
public class OService {

    /** Return whether the service is running or not. */
    public static boolean isRunning(Context ctx, Class<?> cls) {
        return isRunning(ctx, cls.getName());
    }

    /** Return whether the service is running or not. */
    public static boolean isRunning(Context ctx, String cls) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (cls.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
