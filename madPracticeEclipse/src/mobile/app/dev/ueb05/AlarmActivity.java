package mobile.app.dev.ueb05;

import java.util.Calendar;
import java.util.Locale;

import mobile.app.dev.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmActivity extends Activity {

	private TimePicker timePicker;
	private Button activateButton;
	private Button cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		timePicker = (TimePicker) findViewById(R.id.timePickerAlarm);
		timePicker.setIs24HourView(true);
		activateButton = (Button) findViewById(R.id.buttonActivateAlarm);
		cancelButton = (Button) findViewById(R.id.buttonCancelAlarm);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alarm, menu);
		return false;
	}

	public void setAlarm(View button) {
		if (!alarmDoesNotExist()) {
			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(this, Alarm.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			alarmManager.set(AlarmManager.RTC_WAKEUP, getAlarmTimeInMillis(), pendingIntent); 
			Toast.makeText(this, "Wecker klingelt in " + (getAlarmTimeInMillis()-Calendar.getInstance(Locale.GERMANY).getTimeInMillis())/1000 + " Sekunden", Toast.LENGTH_LONG).show();
			Log.d("alarm gesetzt", "yo");
			cancelButton.setEnabled(true);
			activateButton.setEnabled(false);
		}
	}

	public void cancelAlarm(View button) {
		Intent intent = new Intent(this, Alarm.class);
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
		cancelButton.setEnabled(false);
		activateButton.setEnabled(true);
	}

	private long getAlarmTimeInMillis() {
		int hour = timePicker.getCurrentHour();
		Log.d("hour", hour + "");
		int minute = timePicker.getCurrentMinute();
		Log.d("minute", minute + "");

		Calendar calendar = Calendar.getInstance(Locale.GERMANY);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0); //der jung will ja pünktlich geweckt werden, nicht um 19:00:59 ! 
		long alarmTimeInMillis = calendar.getTimeInMillis();
		
		Log.d("Alarmtime in millis", calendar.getTimeInMillis() + "ms");
		Log.d("Alarm Date", calendar.getTime().toString());
		Log.d("Der Wecker klingelt in:", ((alarmTimeInMillis-Calendar.getInstance(Locale.GERMANY).getTimeInMillis())/1000)+" sek");
		return alarmTimeInMillis;
	}

	private boolean alarmDoesNotExist() {
		// TODO Auto-generated method stub
		return false;
	}
}
