package mobile.app.dev.ueb05;

import mobile.app.dev.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;

@SuppressWarnings("deprecation")
public class AlarmSettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private static final String SNOOZE_KEY = "snooze";
	private static final String DEFAULT_SNOOZE_TIME = "5";
	private EditTextPreference snoozePreference;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.alarm_preferences);

		snoozePreference = (EditTextPreference) getPreferenceScreen().findPreference(SNOOZE_KEY);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		snoozePreference.setSummary(prefs.getString(SNOOZE_KEY, DEFAULT_SNOOZE_TIME));

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
		}
	}
}
