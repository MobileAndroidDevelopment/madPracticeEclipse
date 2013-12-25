package mobile.app.dev.ueb06.view;

import java.sql.SQLException;
import java.util.List;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Priority;
import mobile.app.dev.ueb06.orm.PriorityDBHelper;
import mobile.app.dev.ueb06.orm.Todo;
import mobile.app.dev.ueb06.orm.TodoDBHelper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PriorityDBActivity extends Activity {

	private Priority priority;
	private PriorityDBHelper priorityDBHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority_db);
		priorityDBHelper = new PriorityDBHelper();

		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PriorityListActivity.PRIORITY_KEY)) {
			priority = (Priority) getIntent().getExtras().get(PriorityListActivity.PRIORITY_KEY);
			((EditText) findViewById(R.id.priority_name)).setText(priority.getName());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		priorityDBHelper.close();
	}

	public void save(View view) {
		if (priority == null) {
			priority = new Priority();
		}
		try {
			String name = ((EditText) findViewById(R.id.priority_name)).getText().toString();
			if(name==null || name.isEmpty())
				throw new EmptyException(getString(R.string.EMPTY_NAME_NOT_POSSIBLE));
			priority.setName(name);
			priorityDBHelper.createOrUpdate(this, priority);
			finish();
		} catch (SQLException e) {
			Toast.makeText(this, R.string.name_already_exists, Toast.LENGTH_SHORT).show();
		} catch(EmptyException e){
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	public void delete(View view) {
		if (priority != null) {
			Log.d("DELETE_PRIORITY", "Versuche Prioritaet zu loeschen! " + priority);
			TodoDBHelper todoDBHelper = new TodoDBHelper();
			try {
				List<Todo> todos = todoDBHelper.where(this, "category_id", priority.get_id());
				if (todos.size() != 0)
					throw new SQLException("Kann nicht loeschen, bestehende Referenzen!");

				Log.d("DELETE_PRIORITY", "Loesche Priorität " + priority);
				priorityDBHelper.delete(this, priority);
				finish();
			} catch (SQLException e) {
				Toast.makeText(this, R.string.priority_still_existing, Toast.LENGTH_SHORT).show();
			} finally {
				todoDBHelper.close();
			}
		} else {
			finish();
		}
	}
}
