package com.otter.otterlibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * OScreen is a toolkit of getting screen information.
 */
public class OScreen {

    private static Display getDisplay(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    private static DisplayMetrics getDisplayMetrics(Context ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        getDisplay(ctx).getMetrics(dm);
        return dm;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static DisplayMetrics getRealDisplayMetrics(Context ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        getDisplay(ctx).getRealMetrics(dm);
        return dm;
    }

    /** The absolute width of the display in pixels. */
    public static int getWidth(Context ctx) {
        return getDisplayMetrics(ctx).widthPixels;
    }

    /** The absolute height of the display in pixels. */
    public static int getHeight(Context ctx) {
        return getDisplayMetrics(ctx).heightPixels;
    }

    /** The absolute width of the display in pixels based on the real size */
    public static int getRealWidth(Context ctx) {
        if (Build.VERSION.SDK_INT >= 17) {
            return getRealDisplayMetrics(ctx).widthPixels;
        } else if (Build.VERSION.SDK_INT >= 13){
            int width = -1;

            try {
                Display display = getDisplay(ctx);
                Method method = Display.class.getMethod("getRawWidth");
                width = (Integer) method.invoke(display);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return width;
        } else {
            return getWidth(ctx);
        }
    }

    /** The absolute height of the display in pixels based on the real size */
    public static int getRealHeight(Context ctx) {
        if (Build.VERSION.SDK_INT >= 17) {
            return getRealDisplayMetrics(ctx).heightPixels;
        } else if (Build.VERSION.SDK_INT >= 13){
            int height = -1;

            try {
                Display display = getDisplay(ctx);
                Method method = Display.class.getMethod("getRawHeight");
                height = (Integer) method.invoke(display);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return height;
        } else {
            return getHeight(ctx);
        }
    }

    /** The logical density of the display. */
    public static float getDensity(Context ctx) {
        return getDisplayMetrics(ctx).density;
    }

    /** The screen density expressed as dots-per-inch. */
    public static float getDensityDpi(Context ctx) {
        return getRealDisplayMetrics(ctx).densityDpi;
    }

    /**
     * Returns the rotation of the screen from its "natural" orientation.
     *
     * @see android.view.Surface#ROTATION_0
     * @see android.view.Surface#ROTATION_90
     * @see android.view.Surface#ROTATION_180
     * @see android.view.Surface#ROTATION_270
     */
    public static int getRotation (Context ctx) {
        return getDisplay(ctx).getRotation();
    }
}
