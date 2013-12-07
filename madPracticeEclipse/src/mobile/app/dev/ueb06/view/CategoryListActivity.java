package mobile.app.dev.ueb06.view;

import java.sql.SQLException;
import java.util.List;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Category;
import mobile.app.dev.ueb06.orm.CategoryDBHelper;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryListActivity extends ListActivity {

	static final String CATEGORY_KEY = "CATEGORY";
	private CategoryDBHelper categoryDBHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);

		categoryDBHelper = new CategoryDBHelper();
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			List<Category> list = categoryDBHelper.getAll(this);
			ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, list);
			setListAdapter(adapter);
		} catch (SQLException e) {
			Log.e("CAT_ACTIVITY", "Fehler beim SQL ausfuehren", e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.category_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.new_category: {
				Intent intent = new Intent(this, CategoryDBActivity.class);
				startActivity(intent);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		categoryDBHelper.close();
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		Intent intent = new Intent(this, CategoryDBActivity.class);
		Log.d("ON_CLICK_CATEGORY", "Position: " + position);
		intent.putExtra(CATEGORY_KEY, (Category) listView.getItemAtPosition(position));
		startActivity(intent);
	}

}
