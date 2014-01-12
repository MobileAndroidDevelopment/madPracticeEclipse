package mobile.app.dev.moneysac.Activities;

import mobile.app.dev.R;
import mobile.app.dev.R.layout;
import mobile.app.dev.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditEntryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_entry);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_entry, menu);
		return true;
	}

}
