package mobile.app.dev.ueb03;

import mobile.app.dev.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.widget.EditText;

public class SettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle instanceState) {
		super.onCreate(instanceState);

		/* Die in XML definierten Preferences
		werden hier der Activity hinzugefügt */
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
}
