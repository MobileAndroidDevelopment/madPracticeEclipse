package mobile.app.dev.ueb06;

import mobile.app.dev.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class TodoListDBActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_db);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_db, menu);
		return true;
	}

}
