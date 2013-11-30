package mobile.app.dev.ueb05;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mobile.app.dev.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;

@SuppressWarnings("deprecation")
public class AlarmSettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	public static final String DEFAULT_ALARM_SET = "0";
	public static final String SNOOZE_KEY = "snooze";
	public static final String NEXT_ALARM_KEY = "nextAlarm";
	public static final String DEFAULT_SNOOZE_TIME = "5";
	public static final int NO_ALARM_TIME_SET = 0;
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("HH:mm", Locale.GERMAN);
	
	private EditTextPreference snoozePreference;
	private Preference alarmSetPreference;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.alarm_preferences);

		snoozePreference = (EditTextPreference) getPreferenceScreen().findPreference(SNOOZE_KEY);
		alarmSetPreference = (Preference) getPreferenceScreen().findPreference(NEXT_ALARM_KEY);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		snoozePreference.setSummary(prefs.getString(SNOOZE_KEY, DEFAULT_SNOOZE_TIME));
		long time = Long.parseLong(prefs.getString(NEXT_ALARM_KEY, DEFAULT_ALARM_SET));
		alarmSetPreference.setSummary(time != NO_ALARM_TIME_SET ? DATE_FORMATTER.format(new Date(time)) : getString(R.string.not_set));

		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals(SNOOZE_KEY)) {
			snoozePreference.setSummary(sharedPreferences.getString(key, DEFAULT_SNOOZE_TIME));
		} else if (key.equals(NEXT_ALARM_KEY)) {
			long time = Long.parseLong(sharedPreferences.getString(key, DEFAULT_ALARM_SET));
			alarmSetPreference.setSummary(time != 0 ? DATE_FORMATTER.format(new Date(time)) : getString(R.string.not_set));
		}
	}
}
