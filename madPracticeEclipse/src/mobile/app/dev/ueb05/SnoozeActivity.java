package mobile.app.dev.ueb05;

import java.util.Date;

import mobile.app.dev.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

public class SnoozeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snooze);
		//		MediaPlayer player = MediaPlayer.create(context, R.raw.);
		//		player.setLooping(true);
		//		player.start();
		//		SystemClock.sleep(20000);
		//		player.stop();
	}

	public void snooze(View view) {
		Log.d("Test", "Es wurde auf Snooze gedrückt");
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int snoozeMinutes = Integer.parseInt(prefs.getString(AlarmSettingsActivity.SNOOZE_KEY, AlarmSettingsActivity.DEFAULT_SNOOZE_TIME));
		long newAlarmTime = System.currentTimeMillis() + (snoozeMinutes * 60 * 1000);
		newAlarmTime -= newAlarmTime % (60 * 1000);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		AlarmActivity.initAlarmAt(newAlarmTime, this, alarmManager);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Snooze")
				.setContentText(AlarmSettingsActivity.DATE_FORMATTER.format(new Date(newAlarmTime)))
				.setAutoCancel(true);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, AlarmActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(AlarmActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(12, mBuilder.build());

		finish();
	}

}
