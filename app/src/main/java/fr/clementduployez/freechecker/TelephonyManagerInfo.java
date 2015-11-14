package fr.clementduployez.freechecker;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by cdupl on 11/14/2015.
 */
public class TelephonyManagerInfo {

    private final MobileInfo mMobileInfo;
    private final Context mContext;
    private final TelephonyManager mTelephonyManager;

    public TelephonyManagerInfo(Context context, MobileInfo mobileInfo) {
        mMobileInfo = mobileInfo;
        mContext = context;
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

    }



    public String getSimNumber() {
        return mTelephonyManager.getLine1Number();
    }

    public String getSimSerialNumber() {
        return mTelephonyManager.getSimSerialNumber();
    }

    public String getCountryIso() {
        return mTelephonyManager.getSimCountryIso();
    }

    public String getOperatorName() {
        return mTelephonyManager.getSimOperatorName();
    }

    public String getOperatorId() {
        return mTelephonyManager.getSimOperator();
    }

    public int getSimState() {
        return mTelephonyManager.getSimState();
    }

    public String getVoiceMailNumber() {
        return mTelephonyManager.getVoiceMailNumber();
    }

    public String getVoiceMailAlphaTag() {
        return mTelephonyManager.getVoiceMailAlphaTag();
    }

    public String getNetworkCountryIsoCode() {
        return mTelephonyManager.getNetworkCountryIso();
    }

    public String getNetworkOperatorId() {
        return mTelephonyManager.getNetworkOperator();
    }

    public String getNetworkOperatorName() {
        return mTelephonyManager.getNetworkOperatorName();
    }

    public int getNetworkType() {
        return mTelephonyManager.getNetworkType();
    }
}
