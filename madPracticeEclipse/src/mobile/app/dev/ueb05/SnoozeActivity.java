package mobile.app.dev.ueb05;

import mobile.app.dev.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
	}

}
