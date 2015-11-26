package fr.clementduployez.freechecker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.util.Log;

/**
 * Created by cdupl on 11/14/2015.
 */
public class AntennaCheckService extends Service {

    private MobileInfo mMobileInfo = new MobileInfo(FreeCheckerApplication.getContext());

    private AntennaListener mAntennaListener = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals("Close")) {
            closeService(intent);
        }
        else {
            if (mAntennaListener == null) {
                initAntennaListener();
            }
            return Service.START_STICKY;
        }

        return Service.START_NOT_STICKY;

    }

    private void closeService(Intent intent) {
        removeAntennaListener();
        AntennaCheckServiceNotification.removeNotification();
        stopSelf();
        stopService(intent);
    }

    private void removeAntennaListener() {
        mMobileInfo.getTelephonyManagerInfo().getTelephonyManager().listen(mAntennaListener,PhoneStateListener.LISTEN_NONE);
        mAntennaListener = null;
    }

    private void initAntennaListener() {
        if (this.mAntennaListener == null) {
            this.mAntennaListener = new AntennaListener(mMobileInfo);
        }
        mMobileInfo.getTelephonyManagerInfo().getTelephonyManager().listen(mAntennaListener,PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
    }
}
