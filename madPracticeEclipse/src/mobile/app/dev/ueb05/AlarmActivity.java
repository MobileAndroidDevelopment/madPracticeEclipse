package mobile.app.dev.ueb05;

import java.util.Calendar;
import java.util.Locale;

import mobile.app.dev.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TimePicker;

public class AlarmActivity extends Activity {

	private TimePicker timePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		timePicker = (TimePicker) findViewById(R.id.timePickerAlarm);
		timePicker.setIs24HourView(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

	public void setAlarm(View button) {
		Alarm alarm = new Alarm();
		if (alarmDoesNotExist()) {
			alarm.setAlarm(this, getAlarmTimeInMillis());
		}
	}

	//den shit mach ich noch 
	private long getAlarmTimeInMillis() {
		int hour = timePicker.getCurrentHour();
		Log.d("hour", hour+"");
		int minute = timePicker.getCurrentMinute();
		Log.d("minute", minute+"");
		long now = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		DateUtils utils = new DateUtils();
//		calendar.set(Calendar.getInstance(Locale.GERMANY).getTimeInMillis(), 0, 0, 0, 0, 0);

		return 0;
	}

	private boolean alarmDoesNotExist() {
		// TODO Auto-generated method stub
		return false;
	}
}
