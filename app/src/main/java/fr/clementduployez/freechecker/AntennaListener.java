package fr.clementduployez.freechecker;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by cdupl on 11/14/2015.
 */
public class AntennaListener extends PhoneStateListener {

    private final MobileInfo mMobileInfo;
    private TelephonyManager mTelephonyManager;


    public AntennaListener(MobileInfo mobileInfo) {
        mMobileInfo = mobileInfo;
        mTelephonyManager = mMobileInfo.getTelephonyManagerInfo().getTelephonyManager();

        /*callStateListener = new PhoneStateListener(){

        };
        myTelephonyManager.listen(callStateListener,
                PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);*/
    }

    public void onDataConnectionStateChanged(int state){
        Log.i("onDataConnecStateCha",""+state);
        AntennaCheckServiceNotification.sendAntennaCheckNotification(mMobileInfo.getTelephonyManagerInfo().getMncCode());
    }


}
