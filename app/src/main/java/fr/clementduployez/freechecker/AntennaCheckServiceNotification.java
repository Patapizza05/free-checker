package fr.clementduployez.freechecker;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    public static void sendAntennaCheckNotification(MncInfo mncInfo) {
        Context context = FreeCheckerApplication.getContext();
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        final String ticker = "Free Checker";

        int drawable;
        if (mncInfo != null && mncInfo.getBrand().equals(MncConstants.FREE)) {
            drawable = R.drawable.circle;
        }
        else {
            drawable = R.drawable.cross;
        }

        String operator;
        if (mncInfo != null) {
            operator = mncInfo.getOperator();
        }
        else {
            operator = "Unknown";
        }

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(operator)
                .setTicker(ticker)
                .setSmallIcon(drawable)
                .setOngoing(true)
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
