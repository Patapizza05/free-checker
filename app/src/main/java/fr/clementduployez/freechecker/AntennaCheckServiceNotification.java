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

/**
 * Created by cdupl on 11/14/2015.
 */
public class AntennaCheckServiceNotification {

    /**
     * The unique identifier for this type of notification.
     */
    private static final String NOTIFICATION_TAG = "MainNotification";
    private static final int NOTIFICATION_ID = 1;
    private static final String TITLE = "Free Checker";
    private static final String TICKER = TITLE + " Service";

    public static void sendAntennaCheckNotification(MncInfo mncInfo) {

        if (mncInfo != null) {
            if (mncInfo.getBrand().equals(MncConstants.FREE)) {
                sendFreeNotification(mncInfo.getOperator());
            }
            else if (mncInfo.getBrand().equals(MncConstants.ORANGE)) {
                sendOrangeNotification(mncInfo.getOperator());
            }
            else
            {
                sendUnknownNotification();
            }
        }
        else {
            sendUnknownNotification();
        }
    }

    public static void sendFreeNotification(String text) {
        sendNotification(text,R.drawable.circle, FreeCheckerApplication.getContext().getResources().getColor(R.color.green));
    }

    public static void sendOrangeNotification(String text) {
        sendNotification(text,R.drawable.cross, FreeCheckerApplication.getContext().getResources().getColor(R.color.red));
    }

    public static void sendUnknownNotification() {
        sendNotification("Unknown",R.drawable.cross, FreeCheckerApplication.getContext().getResources().getColor(R.color.red));
    }

    private static void sendNotification(String text, int drawable, int color) {
        Context context = FreeCheckerApplication.getContext();

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);


        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(text)
                .setContentText(TITLE)
                .setTicker(TICKER)
                .setContentIntent(pendingIntent)
                .setSmallIcon(drawable)
                .setOngoing(true)
                .setColor(color)
                .build();

        notify(context, notification);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }
}
