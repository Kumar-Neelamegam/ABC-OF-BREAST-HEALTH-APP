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
import devatech.kims.Baseconfig;
import devatech.kims.R;


public class RestoreAlarmService extends BroadcastReceiver {



	/*@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{


		showNotification(getApplicationContext());

		return START_NOT_STICKY;
	}*/

	@Override
	public void onReceive(Context context, Intent intent)
	{



		/*******
		 * @Ponnusamy
		 * Date: 15.2.2017
		 * Description: Daily notification to insert date into math fact table
		 * *****/

		//new Baseconfig(context).insertMathFact();

		boolean value=new Baseconfig(context).check3DayNotPlayMathFact();

	/*	if(value){
			//Sent Notification
			showThreeDaysNotification(context);
		}else{
			showNotification(context);
		}
*/
		showNotification(context);

	}


	public void showNotification(Context context)
	{

		{

			//((Activity)context).finishAffinity();

			PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, Task_Navigation.class), 0);
			Uri notificationS = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


			Notification notification = new NotificationCompat.Builder(context)
					.setTicker("Breast Health - Daily Reminder")
					.setSmallIcon(R.mipmap.ic_launcher)
					.setContentTitle("Breast Health - Daily Reminder")
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

	public void showThreeDaysNotification(Context context)
	{

		{

			PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, Task_Navigation.class), 0);
			Uri notificationS = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


			Notification notification = new NotificationCompat.Builder(context)
					.setTicker("Breast Health - 3 Day not Play Math&Facts")
					.setSmallIcon(R.mipmap.ic_launcher)
					.setContentTitle("Breast Health - 3 Day Not Play Math & Facts")
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
