package mobile.app.dev.ueb03;

import mobile.app.dev.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TodoListActivity extends ListActivity {

	private static final String[] BIG_BANG = new String[] {
			"Leonard Hofstadter", "Sheldon Cooper", "Penny", "Howard Wolowitz",
			"Dr. Rajesh Koothrappali", "Bernadette Rostenkowski", "Amy Farrah Fowler"
	};

	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		for (int i = 0; i < BIG_BANG.length; i++) {
			adapter.add(BIG_BANG[i]);
		}
		setListAdapter(adapter);
		setTitle(R.string.title_activity_todo_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list, menu);
		return true;
	}

	/* wird aufgerufen, wenn ein Element aus der Liste ausgewählt wurde */
	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		Toast.makeText(this, listView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
	}

}
