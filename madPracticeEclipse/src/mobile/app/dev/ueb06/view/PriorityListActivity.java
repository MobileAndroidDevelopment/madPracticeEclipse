package mobile.app.dev.ueb06.view;

import java.sql.SQLException;
import java.util.List;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Priority;
import mobile.app.dev.ueb06.orm.PriorityDBHelper;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PriorityListActivity extends ListActivity {

	public static final String PRIORITY_KEY = "PRIORITY";
	private PriorityDBHelper priorityDBHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority_list);
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			priorityDBHelper = new PriorityDBHelper();
			List<Priority> list = priorityDBHelper.getAll(this);
			ArrayAdapter<Priority> adapter = new ArrayAdapter<Priority>(this, android.R.layout.simple_list_item_1, list);
			setListAdapter(adapter);
		} catch (SQLException e) {
			Log.e("PRIO_ACTIVITY", "Fehler beim SQL ausfuehren", e);
			Toast.makeText(this, R.string.not_able_to_show_priorities, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.priority_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.new_priority: {
				Intent intent = new Intent(this, PriorityDBActivity.class);
				startActivity(intent);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		priorityDBHelper.close();
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		Intent intent = new Intent(this, PriorityDBActivity.class);
		Log.d("ON_CLICK_PRIORITY", "Position: " + position);
		intent.putExtra(PRIORITY_KEY, (Priority) listView.getItemAtPosition(position));
		startActivity(intent);
	}
}
