package mobile.app.dev.ueb06.view;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Category;
import mobile.app.dev.ueb06.orm.CategoryDBHelper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CategoryDBActivity extends Activity {

	private Category category;
	private CategoryDBHelper helper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_db);
		helper = new CategoryDBHelper();

		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CategoryListActivity.CATEGORY_KEY)) {
			category = (Category) getIntent().getExtras().get(CategoryListActivity.CATEGORY_KEY);
			((EditText) findViewById(R.id.category_name)).setText(category.getName());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.category_db, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
	}

	public void save(View view) {
		if (category == null) {
			category = new Category();
		}
		String name = ((EditText) findViewById(R.id.category_name)).getText().toString();
		category.setName(name);
		helper.createOrUpdate(this, category);
		finish();
	}

	public void delete(View view) {
		if (category != null) {
			Log.d("DELETE_PRIORITY", "Loesche Kategorie " + category);
			helper.delete(this, category);
		}
		finish();
	}
}
