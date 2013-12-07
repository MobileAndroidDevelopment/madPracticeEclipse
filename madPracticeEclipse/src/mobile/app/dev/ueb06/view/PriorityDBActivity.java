package mobile.app.dev.ueb06.view;

import mobile.app.dev.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class PriorityDBActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority_db);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.priority_db, menu);
		return true;
	}

	public void save(View view) {
		
	}

	public void delete(View view) {

	}

}
