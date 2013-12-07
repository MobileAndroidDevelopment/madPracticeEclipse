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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PriorityListActivity extends ListActivity {

	private static final String ENTRY = "ENTRY";
	private PriorityDBHelper helper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority_list);

		helper = new PriorityDBHelper();
		try {
			List<Priority> list = helper.getAllPriorites(this);
			ArrayAdapter<Priority> adapter = new ArrayAdapter<Priority>(this, android.R.layout.simple_list_item_1, list);
			setListAdapter(adapter);
		} catch (SQLException e) {
			Log.e("PRIO_ACTIVITY", "Fehler beim SQL ausfuehren", e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.priority_list, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		Intent intent = new Intent(this, PriorityDBActivity.class);
		Log.d("ON_CLOCK", "Position: " + position);
		intent.putExtra(ENTRY, (Priority) listView.getItemAtPosition(position));
		startActivity(intent);
	}
}
