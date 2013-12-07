package mobile.app.dev.ueb06.view;

import java.sql.SQLException;
import java.util.List;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Todo;
import mobile.app.dev.ueb06.orm.TodoDBHelper;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TodoListActivity extends ListActivity {

	public static final String TODO_KEY = "TODO";
	private TodoDBHelper todoDBHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_db);
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			todoDBHelper = new TodoDBHelper();
			List<Todo> list = todoDBHelper.getAll(this);
			ArrayAdapter<Todo> adapter = new ArrayAdapter<Todo>(this, android.R.layout.simple_list_item_1, list);
			setListAdapter(adapter);
		} catch (SQLException e) {
			Log.e("PRIO_ACTIVITY", "Fehler beim SQL ausfuehren", e);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.new_todo: {
				Intent intent = new Intent(this, TodoDBActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.priority_administration: {
				Intent intent = new Intent(this, PriorityListActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.category_administration: {
				Intent intent = new Intent(this, CategoryListActivity.class);
				startActivity(intent);
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo_list_db, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		Intent intent = new Intent(this, TodoDBActivity.class);
		Log.d("ON_CLICK_PRIORITY", "Position: " + position);
		intent.putExtra(TODO_KEY, (Todo) listView.getItemAtPosition(position));
		startActivity(intent);
	}

}
