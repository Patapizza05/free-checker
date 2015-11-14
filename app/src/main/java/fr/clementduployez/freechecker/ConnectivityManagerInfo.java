package fr.clementduployez.freechecker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by cdupl on 11/14/2015.
 */
public class ConnectivityManagerInfo {
    private final ConnectivityManager mConnectivityManager;

    public ConnectivityManagerInfo(Context context, MobileInfo mobileInfo) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public NetworkInfo getActiveNetworkInfo() {
        return mConnectivityManager.getActiveNetworkInfo();
    }

    public boolean isMobileNetwork() {
        try {
            return getActiveNetworkInfo().getTypeName().equals("MOBILE");
        }
        catch(Exception e) {

        }
        return false;

    }

    public boolean isWifiNetwork() {
        try {
        return getActiveNetworkInfo().getTypeName().equals("WIFI");
        }
        catch(Exception e) {

        }
        return false;
    }


}
