package mobile.app.dev.ueb06;

import mobile.app.dev.R;
import android.os.Bundle;
import android.view.Menu;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class TodoDBActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_db);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_db, menu);
		return true;
	}
}
