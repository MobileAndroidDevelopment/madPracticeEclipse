package mobile.app.dev.ueb03;

import mobile.app.dev.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;

@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private EditTextPreference fontSizePreference;
	
	@Override
	protected void onCreate(Bundle instanceState) {
		super.onCreate(instanceState);

		addPreferencesFromResource(R.xml.preferences);

		fontSizePreference = (EditTextPreference) getPreferenceScreen().findPreference("fontSize");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		fontSizePreference.setSummary(prefs.getString("fontSize", "20"));
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
		if(key.equals("fontSize")){
			fontSizePreference.setSummary(sharedPreferences.getString(key, "20"));
		}
		
	}
	
}
