package mobile.app.dev.ueb07;

import java.util.LinkedList;
import java.util.List;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Priority;
import mobile.app.dev.ueb06.orm.PriorityDBHelper;
import mobile.app.dev.ueb06.orm.Todo;
import mobile.app.dev.ueb06.orm.TodoDBHelper;
import mobile.app.dev.ueb07.TodoContentProvider.Priorities;
import mobile.app.dev.ueb07.TodoContentProvider.Todos;
import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class TodoReaderActivity extends ListActivity implements OnCheckedChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_reader);
		Switch todoSwitch = (Switch) findViewById(R.id.todoSwitch);
		todoSwitch.setOnCheckedChangeListener(this);
		loadTodos();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// Is the toggle on?
		boolean on = buttonView.isChecked();
		if (on) {
			loadTodos();
		} else {
			loadPriorites();
		}
	}

	private void loadTodos() {
		Cursor cursor = getTodos();

		List<Todo> list = new LinkedList<Todo>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex(TodoDBHelper.COL_ID));
			String title = cursor.getString(cursor.getColumnIndex(TodoDBHelper.COL_TITLE));
			String description = cursor.getString(cursor.getColumnIndex(TodoDBHelper.COL_DESCRIPTION));
			int priorityId = cursor.getInt(cursor.getColumnIndex(TodoDBHelper.COL_PRIORITY_ID));
			Todo todo = new Todo();
			todo.set_id(id);
			todo.setTitle(title);
			todo.setDescription(description);
			todo.setPriority(new Priority(priorityId, null));
			list.add(todo);
		}

		ArrayAdapter<Todo> adapter = new ArrayAdapter<Todo>(this, android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
	}

	private Cursor getTodos() {
		Uri uri = Todos.CONTENT_URI;
		Log.d("URI", "URI: " + uri);
		String[] projection = new String[] { TodoDBHelper.COL_ID, TodoDBHelper.COL_TITLE, TodoDBHelper.COL_DATE, TodoDBHelper.COL_DESCRIPTION,
				TodoDBHelper.COL_PRIORITY_ID };
		return managedQuery(uri, projection, null, null, null);
	}

	private void loadPriorites() {
		Cursor cursor = getPriorites();

		List<Priority> list = new LinkedList<Priority>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex(PriorityDBHelper.COL_ID));
			String name = cursor.getString(cursor.getColumnIndex(PriorityDBHelper.COL_NAME));
			Priority priority = new Priority();
			priority.set_id(id);
			priority.setName(name);
			list.add(priority);
		}

		ArrayAdapter<Priority> adapter = new ArrayAdapter<Priority>(this, android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
	}

	private Cursor getPriorites() {
		Uri uri = Priorities.CONTENT_URI;
		Log.d("URI", "URI: " + uri);
		String[] projection = new String[] { PriorityDBHelper.COL_ID, PriorityDBHelper.COL_NAME };

		return managedQuery(uri, projection, null, null, null);
	}

}
