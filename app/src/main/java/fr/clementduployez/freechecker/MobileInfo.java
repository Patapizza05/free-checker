package fr.clementduployez.freechecker;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by cdupl on 11/14/2015.
 */
public class MobileInfo {

    private final Context mContext;
    private final TelephonyManagerInfo mTelephonyManagerInfo;
    private final ConnectivityManagerInfo mConnectivityManagerInfo;

    public MobileInfo(Context context) {
        this.mContext = context;
        mTelephonyManagerInfo = new TelephonyManagerInfo(context,this);
        mConnectivityManagerInfo = new ConnectivityManagerInfo(context, this);
    }


}
