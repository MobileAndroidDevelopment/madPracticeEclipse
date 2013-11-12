package mobile.app.dev.ueb03;

import java.util.Collections;

import mobile.app.dev.R;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class TodoListActivity extends ListActivity {

	public static final String ENTRY = "entry";
	private TodoListArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.title_activity_todo_list);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TodoList elements =  TodoList.getInstance();
		Collections.sort(elements, Collections.reverseOrder());
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int fontSize = Integer.parseInt(prefs.getString(SettingsActivity.FONT_SIZE_KEY, SettingsActivity.DEFAULT_FONT_SIZE));

		adapter = new TodoListArrayAdapter(this, elements, fontSize);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo_list_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
    	
		switch (item.getItemId()) {
		case R.id.todo_new:
			intent = new Intent(this, TodoDetailActivity.class);
			startActivity(intent);
			return super.onOptionsItemSelected(item);
		case R.id.todo_settings:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return super.onOptionsItemSelected(item);		
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		Intent intent = new Intent(this, TodoDetailActivity.class);
		intent.putExtra(ENTRY, (Todo)listView.getItemAtPosition(position));
    	startActivity(intent);
	}
}
