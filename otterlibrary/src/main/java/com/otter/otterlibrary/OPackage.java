package com.otter.otterlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.RemoteException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * OPackage is a toolkit of application packages operation.
 */
public class OPackage {

    static class PackageInstallObserver extends IPackageInstallObserver.Stub {
        @Override
        public void packageInstalled(String packageName, int returnCode) throws RemoteException {
            // TODO: Send message to those who are interested.
        }
    }

    static class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
        @Override
        public void packageDeleted(String packageName, int returnCode) throws RemoteException {
            // TODO: Send message to those who are interested.
        }
    }

    /**
     * Install package from Apk file.
     */
    public static void install(Context ctx, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);// or ACTION_INSTALL_PACKAGE?
        intent.setDataAndType(Uri.parse("file://" + filePath),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    /**
     * Install package from Google Play Market.
     */
    public static void installFromMarket(Context ctx, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    /**
     * Uninstall package.
     */
    public static void uninstall(Context ctx, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    /**
     * Install package from Apk file and don't need user accept.
     */
    public static void installWithoutConfirm(Context ctx, String filePath) {
        PackageInstallObserver observer = new PackageInstallObserver();
        PackageManager pm = ctx.getPackageManager();

        try {
            Method method = pm.getClass().getDeclaredMethod("installPackage",
                    new Class[]{Uri.class, IPackageInstallObserver.class, int.class, String.class});
            method.invoke(pm,
                    new Object[]{Uri.parse("file://" + filePath), observer,
                            2 /* PackageManager.INSTALL_REPLACE_EXISTING */, null});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uninstall package and don't need user accept.
     */
    public static void uninstallWithoutConfirm(Context ctx, String packageName) {
        PackageDeleteObserver observer = new PackageDeleteObserver();
        PackageManager pm = ctx.getPackageManager();

        try {
            Method method = pm.getClass().getDeclaredMethod("deletePackage",
                    new Class[]{String.class, IPackageDeleteObserver.class, int.class});
            method.invoke(pm,
                    new Object[]{packageName, observer,
                            2 /* PackageManager.DELETE_ALL_USERS */});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return whether the package is installed or not.
     */
    public static boolean isInstalled(Context ctx, String packageName) {
        List<PackageInfo> installedList = ctx.getPackageManager().getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES);

        for (PackageInfo packageInfo : installedList) {
            if (packageName.equalsIgnoreCase(packageInfo.packageName)) {
                return true;
            }
        }

        return false;
    }
}
