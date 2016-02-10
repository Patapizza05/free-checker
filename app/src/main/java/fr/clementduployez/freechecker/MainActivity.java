package fr.clementduployez.freechecker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private MobileInfo mMobileInfo;
    private MenuItem mToggleServiceItem;

    private boolean wasChecked = false;
    private TextView mTitle;
    private ImageView mLogo;
    private TextView mMnc;
    private TextView mMcc;
    private TextView mOperator;
    private TextView mRoaming;
    private TextView mCountry;

    SharedPreferences sharedPref;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("Update")){
                updateAntennaInformation();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = (TextView)findViewById(R.id.title);
        mLogo = (ImageView)findViewById(R.id.logo);
        mMnc = (TextView)findViewById(R.id.mnc);
        mMcc = (TextView)findViewById(R.id.mcc);
        mOperator = (TextView)findViewById(R.id.operator);
        mRoaming = (TextView)findViewById(R.id.roaming);
        mCountry = (TextView)findViewById(R.id.country);
        mMobileInfo = new MobileInfo(getApplicationContext());

        sharedPref = getPreferences(Context.MODE_PRIVATE);
    }

    private void saveData(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    private boolean loadData(String key, boolean defaultValue) {
        return sharedPref.getBoolean(key, defaultValue);
    }

    private boolean loadData(String key) {
        return loadData(key, false);
    }

    private void startAntennaCheckService() {
        Intent startIntent = new Intent(MainActivity.this, AntennaCheckService.class);
        startService(startIntent);
        saveData("runService",true);
    }

    private void stopAntennaCheckService() {
        Intent stopIntent = new Intent(MainActivity.this, AntennaCheckService.class);
        stopIntent.setAction("Close");
        Intent stopServiceIntent = new Intent(MainActivity.this, AntennaCheckService.class);
        stopService(stopServiceIntent);
        saveData("runService", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mToggleServiceItem = menu.findItem(R.id.action_toggleService);
        mToggleServiceItem.setChecked(loadData("runService"));
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAntennaInformation();
        if (loadData("runService")) {
            startAntennaCheckService();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("Update");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }


    public void updateAntennaInformation() {
        String title = null;
        Integer mnc = null;
        Integer mcc = null;
        String operatorName = null;
        String operatorId = null;
        String country = null;
        String brand;
        Boolean roaming = false;
        int imageResource = R.drawable.cross;

        if (mMobileInfo != null) {
            TelephonyManagerInfo telephonyManager = mMobileInfo.getTelephonyManagerInfo();

            if (telephonyManager != null) {
                title = telephonyManager.getOperatorAntennaName();

                MncInfo mncInfo = telephonyManager.getMncCode();
                if (mncInfo != null) {
                    brand = mncInfo.getBrand();
                    if (brand == null) brand = "";
                    if (brand.equals(MncConstants.FREE)) {
                        imageResource = R.drawable.freemobile;
                    }
                    else if (brand.equals(MncConstants.ORANGE)) {
                        imageResource = R.drawable.orangelogo;
                    }

                    mnc = telephonyManager.getMNC();
                    mcc = telephonyManager.getMCC();
                    operatorName = telephonyManager.getOperatorName();
                    operatorId = telephonyManager.getOperatorId();
                    country = telephonyManager.getCountryIso();
                }
            }

            ConnectivityManagerInfo conn = mMobileInfo.getConnectivityManagerInfo();
            if (conn != null) {
                NetworkInfo networkInfo = conn.getActiveNetworkInfo();
                if (networkInfo != null) {
                    roaming = networkInfo.isRoaming();
                    roaming = roaming != null ? roaming : false;
                }
            }
        }

        mLogo.setImageResource(imageResource);
        mTitle.setText(title);
        if (mnc != null) {
            mMnc.setText(mnc.toString());
        }
        else {
            mMnc.setText("");
        }

        if (mcc != null){
            mMcc.setText(mcc.toString());
        }
        else {
            mMcc.setText("");
        }

        if (operatorName != null && operatorId != null) {
            mOperator.setText(operatorName+" ("+operatorId+")");
        }
        else {
            mOperator.setText("");
        }

        if (country != null) {
            mCountry.setText(country.toUpperCase());
        }
        else {
            mCountry.setText("");
        }

        if (roaming) {
            mRoaming.setText("Oui");
        }
        else {
            mRoaming.setText("Non");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toggleService) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                startAntennaCheckService();
            }
            else {
                stopAntennaCheckService();
            }

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

        mTitle.setText("network :" + network +

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
