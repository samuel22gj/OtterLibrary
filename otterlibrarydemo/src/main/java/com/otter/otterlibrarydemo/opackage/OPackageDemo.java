package com.otter.otterlibrarydemo.opackage;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.OFile;
import com.otter.otterlibrary.OPackage;
import com.otter.otterlibrarydemo.DemoActivity;

import java.io.File;

public class OPackageDemo extends DemoActivity {
    private static final String BILLBOARD =
            "If you want to install/uninstall package without confirm dialog, " +
            "you need to make app be system app and add uses-permission as below:\n" +
            "android.permission.INSTALL_PACKAGES\n" +
            "android.permission.DELETE_PACKAGES";
    private static final String[] OPERATION_ITEM = {
            "installFromMarket (Pocket)", "uninstall (Pocket)",
            "install (TPLocation)", "uninstall (TPLocation)",
            "installWithoutConfirm (TPLocation)", "uninstallWithoutConfirm (TPLocation)",
            "isInstalled (TPLocation)"
    };

    private String mTPLocationFileName = "TPLocation.apk";
    private String mTPLocationFilePath = Environment.getExternalStorageDirectory().getPath()
            + File.separator + mTPLocationFileName;
    private String mTPLocationPackageName = "com.android.TPLocation";
    private String mPocketPackageName = "com.ideashower.readitlater.pro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Copy TPLocation.apk file from assets to external storage directory.
        OFile.copyFileFromAssets(getApplicationContext(),
                mTPLocationFileName, new File(mTPLocationFilePath));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Delete TPLocation.apk file.
        File apkFile = new File(mTPLocationFilePath);
        if (apkFile.exists()) {
            apkFile.delete();
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
            case 0: // installFromMarket (Pocket)
                appendLog("installFromMarket(" + mPocketPackageName + ")");
                OPackage.installFromMarket(getApplicationContext(), mPocketPackageName);
                break;
            case 1: // uninstall (Pocket)
                appendLog("uninstall(" + mPocketPackageName + ")");
                OPackage.uninstall(getApplicationContext(), mPocketPackageName);
                break;
            case 2: // install (TPLocation)
                appendLog("install(" + mTPLocationFilePath + ")");
                OPackage.install(getApplicationContext(), mTPLocationFilePath);
                break;
            case 3: // uninstall (TPLocation)
                appendLog("uninstall(" + mTPLocationPackageName + ")");
                OPackage.uninstall(getApplicationContext(), mTPLocationPackageName);
                break;
            case 4: // installWithoutConfirm (TPLocation)
                appendLog("installWithoutConfirm(" + mTPLocationFilePath + ")");
                OPackage.installWithoutConfirm(getApplicationContext(), mTPLocationFilePath);
                break;
            case 5: // uninstallWithoutConfirm (TPLocation)
                appendLog("uninstallWithoutConfirm(" + mTPLocationPackageName + ")");
                OPackage.uninstallWithoutConfirm(getApplicationContext(), mTPLocationPackageName);
                break;
            case 6: // isInstalled (TPLocation)
                appendLog("isInstalled(" + mTPLocationPackageName + "): "
                        + OPackage.isInstalled(getApplicationContext(), mTPLocationPackageName));
                break;
            default:
                break;
        }
    }
}
