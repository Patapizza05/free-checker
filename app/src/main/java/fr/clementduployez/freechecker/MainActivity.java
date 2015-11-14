package fr.clementduployez.freechecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;
    private MobileInfo mMobileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.test);
        mMobileInfo = new MobileInfo(getApplicationContext());
    }

    private void startAntennaCheckService() {
        Intent startIntent = new Intent(MainActivity.this, AntennaCheckService.class);
        startService(startIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAntennaInformation();
        startAntennaCheckService();
    }

    public void updateAntennaInformation() {
        String antenna = mMobileInfo.getTelephonyManagerInfo().getOperatorAntennaName();
        mTextView.setText(antenna);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


































    public void test() {
        Context context = getApplicationContext();
        /**
         * <uses-permission android:name="android.permission.READ_PHONE_STATE"
         * /> <uses-permission
         * android:name="android.permission.ACCESS_NETWORK_STATE"/>
         */

        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = telephonyManager.getNetworkOperator();
        int mcc = 0, mnc = 0;
        if (networkOperator != null) {
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
            mnc = Integer.parseInt(networkOperator.substring(3));
        }

        String SimNumber = telephonyManager.getLine1Number();

        String SimSerialNumber = telephonyManager.getSimSerialNumber();
        String countryISO = telephonyManager.getSimCountryIso();
        String operatorName = telephonyManager.getSimOperatorName();
        String operator = telephonyManager.getSimOperator();
        int simState = telephonyManager.getSimState();

        String voicemailNumer = telephonyManager.getVoiceMailNumber();
        String voicemailAlphaTag = telephonyManager.getVoiceMailAlphaTag();

        // Getting connected network iso country code
        String networkCountry = telephonyManager.getNetworkCountryIso();
        // Getting the connected network operator ID
        String networkOperatorId = telephonyManager.getNetworkOperator();
        // Getting the connected network operator name
        String networkName = telephonyManager.getNetworkOperatorName();

        int networkType = telephonyManager.getNetworkType();

        String network = "";
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (cm.getActiveNetworkInfo().getTypeName().equals("MOBILE"))
                network = "Cell Network/3G";
            else if (cm.getActiveNetworkInfo().getTypeName().equals("WIFI"))
                network = "WiFi";
            else
                network = "N/A";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mTextView.setText("network :" + network +

                "\n" + "countryISO : " + countryISO + "\n" + "operatorName : "
                + operatorName + "\n" + "operator :      " + operator + "\n"
                + "simState :" + simState + "\n" + "Sim Serial Number : "
                + SimSerialNumber + "\n" + "Sim Number : " + SimNumber + "\n"
                + "Voice Mail Numer" + voicemailNumer + "\n"
                + "Voice Mail Alpha Tag" + voicemailAlphaTag + "\n"
                + "Sim State" + simState + "\n" + "Mobile Country Code MCC : "
                + mcc + "\n" + "Mobile Network Code MNC : " + mnc + "\n"
                + "Network Country : " + networkCountry + "\n"
                + "Network OperatorId : " + networkOperatorId + "\n"
                + "Network Name : " + networkName + "\n" + "Network Type : "
                + networkType);
    }
}
