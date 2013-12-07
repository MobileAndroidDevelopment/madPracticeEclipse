package mobile.app.dev.ueb06.view;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Priority;
import mobile.app.dev.ueb06.orm.PriorityDBHelper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class PriorityDBActivity extends Activity {

	private Priority priority;
	private PriorityDBHelper helper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority_db);
		helper = new PriorityDBHelper();

		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PriorityListActivity.PRIORITY)) {
			priority = (Priority) getIntent().getExtras().get(PriorityListActivity.PRIORITY);
			((EditText) findViewById(R.id.priority_name)).setText(priority.getName());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.priority_db, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
	}

	public void save(View view) {
		if (priority == null) {
			priority = new Priority();
		}
		String name = ((EditText) findViewById(R.id.priority_name)).getText().toString();
		priority.setName(name);
		helper.createOrUpdate(this, priority);
		finish();
	}

	public void delete(View view) {
		if (priority != null) {
			Log.d("DELETE_PRIORITY", "Loesche Prioritaet " + priority);
			helper.delete(this, priority);
		}
		finish();
	}

}
