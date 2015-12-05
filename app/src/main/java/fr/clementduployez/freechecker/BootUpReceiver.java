package fr.clementduployez.freechecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by cdupl on 12/5/2015.
 */
public class BootUpReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        /****** For Start Activity *****/
        /*Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*/

        /***** For start Service  ****/
        Intent myIntent = new Intent(context, AntennaCheckService.class);
        context.startService(myIntent);
    }

}
