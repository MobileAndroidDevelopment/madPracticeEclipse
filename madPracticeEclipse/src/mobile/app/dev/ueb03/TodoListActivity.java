package mobile.app.dev.ueb03;

import java.util.LinkedList;

import mobile.app.dev.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class TodoListActivity extends ListActivity {

	public static final String ENTRY = "entry";
	private TodoListArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new TodoListArrayAdapter(this, new LinkedList<Todo>());
		setListAdapter(adapter);
		setTitle(R.string.title_activity_todo_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo_list_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, TodoDetailActivity.class);
    	startActivity(intent);
		return super.onOptionsItemSelected(item);
	}

	/* wird aufgerufen, wenn ein Element aus der Liste ausgewählt wurde */
	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		Toast.makeText(this, listView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
	}

}
