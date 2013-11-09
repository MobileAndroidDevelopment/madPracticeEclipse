package mobile.app.dev.ueb03;

import java.util.Collections;

import mobile.app.dev.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;


public class TodoSettingsActivity extends ListActivity {

	private TodoSettingsListArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Setting setting = new Setting(getString(R.string.fontSize));
		//TodoSettingsList.getInstance().add(setting);

		TodoSettingsList elements = TodoSettingsList.getInstance();
		elements.add(setting);		
		Collections.sort(elements, Collections.reverseOrder());
		adapter = new TodoSettingsListArrayAdapter(this, elements);		
		setListAdapter(adapter);
	}
}
