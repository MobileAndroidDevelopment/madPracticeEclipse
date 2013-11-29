package mobile.app.dev.ueb05;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mobile.app.dev.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TimePicker;

public class AlarmActivity extends Activity {

	private TimePicker timePicker;
	private Alarm alarm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		timePicker = (TimePicker) findViewById(R.id.timePickerAlarm);
		timePicker.setIs24HourView(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alarm, menu);
		return false;
	}

	public void setAlarm(View button) {
		alarm = new Alarm();
		if (!alarmDoesNotExist()) {
			alarm.setAlarm(this, getAlarmTimeInMillis());
		}
	}

	public void cancelAlarm(View button){
		alarm.cancelAlarm(this);
	}
	
	private long getAlarmTimeInMillis() {
		int hour = timePicker.getCurrentHour();
		Log.d("hour", hour+"");
		int minute = timePicker.getCurrentMinute();
		Log.d("minute", minute+"");
		
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0); //der jung will ja pünktlich geweckt werden, nicht um 19:00:59 ! 
		
		Log.d("Alarmtime in millis", calendar.getTimeInMillis()+"ms");
		Log.d("Alarm Date", calendar.getTime().toString());
		return calendar.getTimeInMillis();
	}

	private boolean alarmDoesNotExist() {
		// TODO Auto-generated method stub
		return false;
	}
}
