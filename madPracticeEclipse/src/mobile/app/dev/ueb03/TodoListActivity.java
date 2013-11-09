package mobile.app.dev.ueb03;

import mobile.app.dev.R;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

		Log.d("CREATE", "Bin wieder hier");
		adapter = new TodoListArrayAdapter(this, TodoList.getInstance());
		setListAdapter(adapter);
		setTitle(R.string.title_activity_todo_list);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("RESUME", "Bin wieder hier");
		adapter = new TodoListArrayAdapter(this, TodoList.getInstance());
		setListAdapter(adapter);
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

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		Intent intent = new Intent(this, TodoDetailActivity.class);
		intent.putExtra(ENTRY, (Todo)listView.getItemAtPosition(position));
    	startActivity(intent);
	}
}
