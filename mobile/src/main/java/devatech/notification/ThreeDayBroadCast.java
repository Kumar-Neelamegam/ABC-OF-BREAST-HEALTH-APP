package devatech.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import devatech.dashboard.Task_Navigation;
import devatech.kims.R;

public class ThreeDayBroadCast extends BroadcastReceiver {
    public ThreeDayBroadCast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");

        showThreeDaysNotification(context);
    }

    public void showThreeDaysNotification(Context context)
    {

        {

            PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, Task_Navigation.class), 0);
            Uri notificationS = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            Notification notification = new NotificationCompat.Builder(context)
                    .setTicker("Breast Health -3Day not Play Math&Facts")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Breast Health - 3Day Not Play Math & Facts")
                    .setContentText("Play Myths & Facts")
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setSound(notificationS)
                    .build();
            notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);

        }


    }
}
