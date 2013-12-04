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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class SnoozeActivity extends Activity {

	private static final int NOTIFICATION_ID = 12;
	MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snooze);
		
		cancelNotification();
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
	            + WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
	            + WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
	            + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		player = MediaPlayer.create(this, R.raw.pippilangstrompessang);
		player.setLooping(true);
		player.start();
		
	}

	
	private void cancelNotification(){
		NotificationManager mgn = (NotificationManager)
				getSystemService(Context.NOTIFICATION_SERVICE);
				mgn.cancel(NOTIFICATION_ID);
	}
	public void snooze(View view) {
		Log.d("Test", "Es wurde auf Snooze gedrückt");
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int snoozeMinutes = Integer.parseInt(prefs.getString(AlarmSettingsActivity.SNOOZE_KEY, AlarmSettingsActivity.DEFAULT_SNOOZE_TIME));
		long newAlarmTime = System.currentTimeMillis() + (snoozeMinutes * 60 * 1000);
		newAlarmTime -= newAlarmTime % (60 * 1000);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		AlarmActivity.initAlarmAt(newAlarmTime, this, alarmManager);
		createNotification(newAlarmTime);

		finish();
	}

	private void createNotification(long newAlarmTime) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(getString(R.string.SNOOZE_NOTIFICATION_TITLE))
				.setContentText(AlarmSettingsActivity.DATE_FORMATTER.format(new Date(newAlarmTime)))
				.setAutoCancel(true);
		Intent resultIntent = new Intent(this, AlarmActivity.class);

		// The stack builder object will contain an artificial back stack for the started Activity.
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
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

	@Override
	public void onStop() {
		super.onStop();
		player.stop();
	}

	public void stop(View view) {
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		AlarmActivity.cancelAlarm(this, alarmManager);
		finish();
	}

}
