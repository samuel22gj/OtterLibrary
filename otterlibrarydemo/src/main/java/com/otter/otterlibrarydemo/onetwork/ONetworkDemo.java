package com.otter.otterlibrarydemo.onetwork;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;

import com.otter.otterlibrary.ONetwork;
import com.otter.otterlibrarydemo.DemoActivity;

public class ONetworkDemo extends DemoActivity {
    private static final String BILLBOARD =
            "Get network status.";
    private static final String[] OPERATION_ITEM = {
            "Open Settings", "isConnected", "isConnectedWifi", "isConnectedMobile",
            "getIpAddress", "getMacAddress"
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
            case 0: // Open Settings
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case 1: // isConnected
                appendLog("isConnected: " + ONetwork.isConnected(getApplicationContext()));
                break;
            case 2: // isConnectedWifi
                appendLog("isConnectedWifi: " + ONetwork.isConnectedWifi(getApplicationContext()));
                break;
            case 3: // isConnectedMobile
                appendLog("isConnectedMobile: " + ONetwork.isConnectedMobile(getApplicationContext()));
                break;
            case 4: // getIpAddress
                setLog("getIpAddress(eth0, true): " + ONetwork.getIpAddress("eth0", true));
                appendLog("getIpAddress(eth0, false): " + ONetwork.getIpAddress("eth0", false));
                appendLog("getIpAddress(eth1, true): " + ONetwork.getIpAddress("eth1", true));
                appendLog("getIpAddress(eth1, false): " + ONetwork.getIpAddress("eth1", false));
                appendLog("getIpAddress(wlan0, true): " + ONetwork.getIpAddress("wlan0", true));
                appendLog("getIpAddress(wlan0, false): " + ONetwork.getIpAddress("wlan0", false));
                appendLog("getIpAddress(null, true): " + ONetwork.getIpAddress(null, true));
                appendLog("getIpAddress(null, false): " + ONetwork.getIpAddress(null, false));
                appendLog("getIpAddress(123, true): " + ONetwork.getIpAddress("123", true));
                appendLog("getIpAddress(123, false): " + ONetwork.getIpAddress("123", false));
                break;
            case 5: // getMacAddress
                setLog("getMacAddress(eth0): " + ONetwork.getMacAddress("eth0"));
                appendLog("getMacAddress(eth1): " + ONetwork.getMacAddress("eth1"));
                appendLog("getMacAddress(wlan0): " + ONetwork.getMacAddress("wlan0"));
                appendLog("getMacAddress(null): " + ONetwork.getMacAddress(null));
                appendLog("getMacAddress(123): " + ONetwork.getMacAddress("123"));
                break;
            default:
                break;
        }
    }
}
