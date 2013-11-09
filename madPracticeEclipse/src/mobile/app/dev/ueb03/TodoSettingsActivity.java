package mobile.app.dev.ueb03;

import mobile.app.dev.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class TodoSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_settings, menu);
		return true;
	}

}
