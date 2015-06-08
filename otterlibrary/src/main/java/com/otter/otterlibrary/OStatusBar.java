package com.otter.otterlibrary;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * OStatusBar is a toolkit of status bar operation.
 */
public class OStatusBar {

    /** Expand the notifications panel. */
    public static void expandNotificationsPanel(Context ctx) {
        try {
            @SuppressWarnings("ResourceType")
            Object sbservice = ctx.getSystemService("statusbar");
            Class<?> statusbarManager;
            statusbarManager = Class.forName("android.app.StatusBarManager");
            Method method;
            if (Build.VERSION.SDK_INT >= 17) {
                method = statusbarManager.getMethod("expandNotificationsPanel");
            } else {
                method = statusbarManager.getMethod("expand");
            }
            method.invoke(sbservice);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /** Expand the settings panel. */
    public static void expandSettingsPanel(Context ctx) {
        try {
            @SuppressWarnings("ResourceType")
            Object sbservice = ctx.getSystemService("statusbar");
            Class<?> statusbarManager;
            statusbarManager = Class.forName("android.app.StatusBarManager");
            Method method;
            if (Build.VERSION.SDK_INT >= 17) {
                method = statusbarManager.getMethod("expandSettingsPanel");
            } else {
                method = statusbarManager.getMethod("expand");
            }
            method.invoke(sbservice);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /** Collapse the notifications and settings panels. */
    public static void collapsePanels(Context ctx) {
        try {
            @SuppressWarnings("ResourceType")
            Object sbservice = ctx.getSystemService("statusbar");
            Class<?> statusbarManager;
            statusbarManager = Class.forName("android.app.StatusBarManager");
            Method method;
            method = statusbarManager.getMethod("collapsePanels");
            method.invoke(sbservice);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
