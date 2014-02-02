package mobile.app.dev.moneysac.Activities;

import java.util.LinkedList;

import mobile.app.dev.R;
import mobile.app.dev.moneysac.Adapters.CategoryListViewAdapter;
import mobile.app.dev.moneysac.Model.Category;
import mobile.app.dev.moneysac.Model.SacEntryType;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CategoryListView extends Activity {

	public static final String CATEGORY_EXTRA_ID = "CATEGORY_ID";
	private ListView catList;
	private Button btAddCat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moneysac_category_list_view);
		initViewElements();
		setListener();
		showList();
	}

	private void setListener() {
		btAddCat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), CategoryDetailActivity.class));

			}
		});
	}

	private void showList() {

		//fills a list with some test data

		LinkedList<Category> values = new LinkedList<Category>();

		SacEntryType type1 = new SacEntryType();
		type1.setName("Einkommen");

		SacEntryType type2 = new SacEntryType();
		type2.setName("Ausgaben");

		Category cat1 = new Category();
		Category cat2 = new Category();
		Category cat3 = new Category();
		Category cat4 = new Category();
		Category cat5 = new Category();

		cat1.setName("Gehalt");
		cat1.setType(type1);

		cat2.setName("Sonstiges");
		cat2.setType(type1);

		cat3.setName("Steuern");
		cat3.setType(type2);

		cat4.setName("Eink�ufe");
		cat4.setType(type2);

		cat5.setName("Steuern");
		cat5.setType(type2);

		values.add(cat1);
		values.add(cat2);
		values.add(cat3);
		values.add(cat4);
		values.add(cat5);

		CategoryListViewAdapter adapter = new CategoryListViewAdapter(this, values);
		catList.setAdapter(adapter);
		catList.setOnItemClickListener(new CategoryItemClickListener(adapter));
	}

	private void initViewElements() {
		catList = (ListView) findViewById(R.id.moneysac_cetegory_listview);
		btAddCat = (Button) findViewById(R.id.addCategoryButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	private class CategoryItemClickListener implements OnItemClickListener {

		private ArrayAdapter<Category> adapter;

		public CategoryItemClickListener(ArrayAdapter<Category> adapter) {
			this.adapter = adapter;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Intent categoryDetailIntend = new Intent(CategoryListView.this, CategoryDetailActivity.class);
			categoryDetailIntend.putExtra(CATEGORY_EXTRA_ID, adapter.getItem(position));
			startActivity(categoryDetailIntend);
		}
	}
}
