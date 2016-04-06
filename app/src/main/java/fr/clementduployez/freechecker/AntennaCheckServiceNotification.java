package fr.clementduployez.freechecker;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.Console;

/**
 * Created by cdupl on 11/14/2015.
 */
public class AntennaCheckServiceNotification {

    /**
     * The unique identifier for this type of notification.
     */
    public static final int NOTIFICATION_ID = 1;
    private static final String TITLE = "Free Checker";
    private static final String TICKER = TITLE + " Service";

    private static Notification currentNotification = null;
    private static Notification freeNotification = null;
    private static Notification orangeNotification = null;
    private static Notification unknownNotification = null;

    public static void sendAntennaCheckNotification(MncInfo mncInfo, AntennaCheckService service) {

        if (mncInfo != null) {
            if (mncInfo.getBrand().equals(MncConstants.FREE)) {
                sendFreeNotification(mncInfo.getOperator(), service);
            }
            else if (mncInfo.getBrand().equals(MncConstants.ORANGE)) {
                sendOrangeNotification(mncInfo.getOperator(), service);
            }
            else
            {
                sendUnknownNotification(service);
            }
        }
        else {
            sendUnknownNotification(service);
        }
    }

    public static void sendFreeNotification(String text, AntennaCheckService service) {
        if (freeNotification == null) {
            freeNotification = makeNotification(text, R.drawable.circle, FreeCheckerApplication.getContext().getResources().getColor(R.color.green));
        }
        sendNotification(freeNotification, service);
    }

    public static void sendOrangeNotification(String text, AntennaCheckService service) {
        if (orangeNotification == null) {
            orangeNotification = makeNotification(text, R.drawable.cross, FreeCheckerApplication.getContext().getResources().getColor(R.color.red));
        }
        sendNotification(orangeNotification, service);
    }

    public static void sendUnknownNotification(AntennaCheckService service) {
        if (unknownNotification == null) {
            unknownNotification = makeNotification("Unknown", R.drawable.ic_help_outline_white_18dp, FreeCheckerApplication.getContext().getResources().getColor(R.color.red));
        }
        sendNotification(unknownNotification, service);
    }

    private static Notification makeNotification(String text, int drawable, int color) {
        Context context = FreeCheckerApplication.getContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);


        /**
         * Expanded Notification Actions
         */
        NotificationCompat.Action quitAction = makeAction("Close","Close",R.drawable.ic_power_settings_new_black_24dp);
        NotificationCompat.Action settingsAction = makeAction("Settings","Settings",R.drawable.ic_settings_black_24dp);
        //NotificationCompat.Action refreshAction = makeAction("","Refresh",R.drawable.ic_autorenew_black_24dp);
        /**
         *
         */

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(text)
                .setContentText(TITLE)
                .setTicker(TICKER)
                .setContentIntent(pendingIntent)
                .setSmallIcon(drawable)
                .setOngoing(true)
                .setColor(color)
                .addAction(settingsAction)
                //.addAction(refreshAction)
                .addAction(quitAction)
                .setAutoCancel(true)
                .build();

        return notification;
    }

    private static NotificationCompat.Action makeAction(String title, String actionTag, int drawable)
    {
        Context context = FreeCheckerApplication.getContext();
        Intent intent = new Intent(context, AntennaCheckService.class);
        intent.setAction(actionTag);
        PendingIntent pIntent = PendingIntent.getService(context, 0, intent, 0);
        return new NotificationCompat.Action.Builder(drawable, title, pIntent).build();
    }

    private static void sendNotification(Notification notification, AntennaCheckService service) {
        Context context = FreeCheckerApplication.getContext();
        notify(context, notification);
        service.startForeground(NOTIFICATION_ID, notification);
        Intent updateIntent = new Intent("Update");
        service.sendBroadcast(updateIntent);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        currentNotification = notification;
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(NOTIFICATION_ID, notification);
    }

    public static void removeNotification() {
        Context context = FreeCheckerApplication.getContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationManager.cancel(NOTIFICATION_ID);
        notificationManager.cancelAll(); //Just in case
    }

    public static Notification getCurrentNotification() {
        return currentNotification;
    }
}
