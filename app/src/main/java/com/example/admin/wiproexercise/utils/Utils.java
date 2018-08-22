package com.example.admin.wiproexercise.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {

    /**
     * Checking for internet connection available or not.
     *
     * @param context
     * @return
     */
    public static boolean getDataConnection(Context context) {
        try {
            if (context != null) {
                ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (conn != null) {
                    boolean wifiConnected = (conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
                    boolean mobileConnected = (conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected());
                    boolean wiMaxConnected = (conn.getNetworkInfo(ConnectivityManager.TYPE_WIMAX) != null && conn.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isConnected());
                    if (wifiConnected || mobileConnected || wiMaxConnected) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
