package mobile.app.dev.ueb06.view;

import java.sql.SQLException;
import java.util.List;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Category;
import mobile.app.dev.ueb06.orm.CategoryDBHelper;
import mobile.app.dev.ueb06.orm.Todo;
import mobile.app.dev.ueb06.orm.TodoDBHelper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CategoryDBActivity extends Activity {

	private Category category;
	private CategoryDBHelper categoryDBHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_db);
		categoryDBHelper = new CategoryDBHelper();

		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(CategoryListActivity.CATEGORY_KEY)) {
			category = (Category) getIntent().getExtras().get(CategoryListActivity.CATEGORY_KEY);
			((EditText) findViewById(R.id.category_name)).setText(category.getName());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		categoryDBHelper.close();
	}

	public void save(View view) {
		if (category == null) {
			category = new Category();
		}
		try {
			String name = ((EditText) findViewById(R.id.category_name)).getText().toString();
			if (name == null || name.isEmpty())
				throw new EmptyException(getString(R.string.EMPTY_NAME_NOT_POSSIBLE));
			category.setName(name);
			categoryDBHelper.createOrUpdate(this, category);
			finish();
		} catch (SQLException e) {
			Toast.makeText(this, R.string.name_already_exists, Toast.LENGTH_SHORT).show();
		} catch (EmptyException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	public void delete(View view) {
		if (category != null) {
			Log.d("DELETE_CATEGORY", "Versuche Kategorie zu loeschen! " + category);
			TodoDBHelper todoDBHelper = new TodoDBHelper();
			try {
				List<Todo> todos = todoDBHelper.where(this, "category_id", category.getId());
				if (todos.size() != 0)
					throw new SQLException("Kann nicht loeschen, bestehende Referenzen!");

				Log.d("DELETE_PRIORITY", "Loesche Kategorie " + category);
				categoryDBHelper.delete(this, category);
				finish();
			} catch (SQLException e) {
				Toast.makeText(this, R.string.category_still_existing, Toast.LENGTH_SHORT).show();
			} finally {
				todoDBHelper.close();
			}
		} else {
			finish();
		}
	}
}
