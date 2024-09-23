package com.example.otpscreendemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkUtil {

    // Method to check for internet availability
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // For API level 23 and above
                Network network = connectivityManager.getActiveNetwork();
                if (network == null) {
                    return false;
                }
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                return networkCapabilities != null
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            } else {
                // For API level below 23
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null) {
                    return networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                }
            }
        }
        return false;
    }
}

