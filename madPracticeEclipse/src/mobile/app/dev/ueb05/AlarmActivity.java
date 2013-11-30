package mobile.app.dev.ueb05;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mobile.app.dev.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
		//Bug in der API setzt Wert des TimePickers nicht im 24Stunden Modus
		timePicker.setCurrentHour(new Date().getHours());
		activateButton = (Button) findViewById(R.id.buttonActivateAlarm);
		cancelButton = (Button) findViewById(R.id.buttonCancelAlarm);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		long time = Long.parseLong(prefs.getString(AlarmSettingsActivity.NEXT_ALARM_KEY, AlarmSettingsActivity.DEFAULT_ALARM_SET));
		if (time != 0) {
			cancelButton.setEnabled(true);
			activateButton.setEnabled(false);
		} else {
			cancelButton.setEnabled(false);
			activateButton.setEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.alarm_settings:
				Intent intent = new Intent(this, AlarmSettingsActivity.class);
				startActivity(intent);
				return super.onOptionsItemSelected(item);
			default:
				return super.onOptionsItemSelected(item);
		}
	}


	public void setAlarm(View button) {
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		long newTime = getAlarmTimeInMillis();
		initAlarmAt(newTime, this, alarmManager);
		cancelButton.setEnabled(true);
		activateButton.setEnabled(false);
	}
	
	private static String getAlarmString(long millisToAlarm)
	{
		String alarmString;
		int hours = (int) (millisToAlarm/1000/3600);
		millisToAlarm = millisToAlarm - (hours*1000*3600);
		int minutes = (int) (millisToAlarm/1000/60);
		millisToAlarm = millisToAlarm - (minutes*1000*60);
		int seconds = (int) (millisToAlarm/1000);
		
		if(hours != 0)
		{
			alarmString = "Wecker klingelt in " + hours + " Stunden und " + minutes + " Minuten";
		}
		else
		{
			if(minutes != 0)
			{
				alarmString = "Wecker klingelt in " + minutes + " Minuten und " + seconds + " Sekunden";
			}
			else
			{
				alarmString = "Wecker klingelt in " + seconds + " Sekunden";
			}
		}				
		
		return alarmString;		
	}
	
	public static void initAlarmAt(long millis, Context context, AlarmManager manager) {
		Intent intent = new Intent(context, Alarm.class);
		Long actuellTimeInMillis =  Calendar.getInstance(Locale.GERMANY).getTimeInMillis();
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				
		//wenn Alarm erst am nächsten Tag erfolgt		
		if((millis - actuellTimeInMillis) < 0)		
		{	//dann rechne einen Tag drauf
			millis += 86400*1000;
		}
		
		manager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
		Toast.makeText(context, getAlarmString(millis - actuellTimeInMillis), Toast.LENGTH_LONG).show();
		Log.d("alarm gesetzt", "yo");
		saveAlarmPreferenceSet(millis, context);
	}

	private static void saveAlarmPreferenceSet(long millis, Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(AlarmSettingsActivity.NEXT_ALARM_KEY, millis + "");
		boolean preferencesSet = editor.commit();
		if (!preferencesSet)
			throw new RuntimeException("Da ging was schief");
	}

	public void cancelAlarm(View button) {
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		cancelAlarm(this, alarmManager);
		cancelButton.setEnabled(false);
		activateButton.setEnabled(true);
	}
	
	public static void cancelAlarm(Context context, AlarmManager manager){
		Intent intent = new Intent(context, Alarm.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		manager.cancel(sender);

		saveAlarmPreferenceSet(0, context);
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
		Log.d("Der Wecker klingelt in:", ((alarmTimeInMillis - Calendar.getInstance(Locale.GERMANY).getTimeInMillis()) / 1000) + " sek");
		return alarmTimeInMillis;
	}
}
