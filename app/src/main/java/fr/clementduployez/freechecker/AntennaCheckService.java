package fr.clementduployez.freechecker;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.util.Log;

/**
 * Created by cdupl on 11/14/2015.
 */
public class AntennaCheckService extends Service {

    private MobileInfo mMobileInfo = new MobileInfo(FreeCheckerApplication.getContext());

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("AntennaCheckServcie", "onStartCommand");
        AntennaListener antennaListener = new AntennaListener(mMobileInfo);
        mMobileInfo.getTelephonyManagerInfo().getTelephonyManager().listen(antennaListener,PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
        return Service.START_STICKY;
    }

    private void makeNotification() {
        AntennaCheckServiceNotification.sendAntennaCheckNotification(mMobileInfo.getTelephonyManagerInfo().getMncCode());
    }
}
