package devatech.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


public class RebootReceiver extends BroadcastReceiver {



	@Override
	public void onReceive(Context context, Intent intent)
	{

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
		Intent intent1 = new Intent(context, RestoreAlarmService.class);
		PendingIntent pendingItent1 = PendingIntent.getBroadcast(context, 0,
				intent1, PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar calendar = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		//check whether the time is earlier than current time. If so, set it to tomorrow. Otherwise, all alarms for earlier time will fire

		if(calendar.before(now))
		{
			calendar.add(Calendar.DATE, 1);
		}
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingItent1);

	}

}
