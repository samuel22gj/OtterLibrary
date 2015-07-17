package com.otter.otterlibrary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * ONetwork is a wrapper of {@link android.net.ConnectivityManager}.
 */
public class ONetwork {

    private static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /** Check if there is any connectivity */
    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /** Check if there is any connectivity to a Wifi network */
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected()
                && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /** Check if there is any connectivity to a mobile network */
    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected()
                && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Get IPv4 or IPv6 address from the given interface name.
     *
     * @param interfaceName eth0, wlan0, NULL(use first non-localhost interface), etc.
     * @param useIpv4 Return IPv4 if it is true, IPv6 otherwise.
     *
     * @return Target IP address or null.
     */
    public static String getIpAddress(String interfaceName, boolean useIpv4) {
        // Using a AsyncTask to avoid android.os.NetworkOnMainThreadException Error
        GetIpAddressTask task = new GetIpAddressTask();
        try {
            return task.execute(interfaceName, String.valueOf(useIpv4)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static class GetIpAddressTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String interfaceName = params[0];
            boolean useIpv4 = Boolean.valueOf(params[1]);

            try {
                List<NetworkInterface> intfs = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface intf : intfs) {
                    if (interfaceName != null) {
                        if (!intf.getName().equalsIgnoreCase(interfaceName)) {
                            continue;
                        }
                    } else {
                        if (intf.isLoopback() || !intf.isUp()) {
                            // Ignore loopback or downing network interface.
                            continue;
                        }
                    }

                    List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                    for (InetAddress addr : addrs) {
                        if (useIpv4) {
                            if (addr instanceof Inet4Address) {
                                return addr.getHostAddress().toUpperCase();
                            }
                        } else {
                            if (addr instanceof Inet6Address) {
                                String ipv6 = addr.getHostAddress().toUpperCase();
                                int delim = ipv6.indexOf('%'); // Drop ip6 port suffix.
                                return delim < 0 ? ipv6 : ipv6.substring(0, delim);
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * Get MAC address from the given interface name.
     *
     * @param interfaceName eth0, wlan0, NULL(use first interface), etc.
     *
     * @return MAC address or null.
     */
    public static String getMacAddress(String interfaceName) {
        // Using a AsyncTask to avoid android.os.NetworkOnMainThreadException Error
        GetMacAddress task = new GetMacAddress();
        try {
            return task.execute(interfaceName).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static class GetMacAddress extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String interfaceName = params[0];

            try {
                List<NetworkInterface> intfs = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface intf : intfs) {
                    if (interfaceName != null) {
                        if (!intf.getName().equalsIgnoreCase(interfaceName)) {
                            continue;
                        }
                    }

                    byte[] macBytes = intf.getHardwareAddress();
                    if (macBytes == null) return null;

                    // Convert MAC address to human-readable string.
                    StringBuilder macStr = new StringBuilder();
                    for (byte macByte : macBytes) {
                        macStr.append(String.format("%02X:", macByte));
                    }
                    if (macStr.length() > 0) macStr.deleteCharAt(macStr.length()-1);

                    return macStr.toString();
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
