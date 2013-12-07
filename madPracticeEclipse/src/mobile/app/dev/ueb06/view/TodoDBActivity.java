package mobile.app.dev.ueb06.view;

import java.sql.SQLException;
import java.util.List;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Category;
import mobile.app.dev.ueb06.orm.CategoryDBHelper;
import mobile.app.dev.ueb06.orm.DatabaseHelper;
import mobile.app.dev.ueb06.orm.Priority;
import mobile.app.dev.ueb06.orm.PriorityDBHelper;
import mobile.app.dev.ueb06.orm.Todo;
import mobile.app.dev.ueb06.orm.TodoDBHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class TodoDBActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	private TodoDBHelper todoDBHelper = null;
	private PriorityDBHelper prioDBHelper = null;
	private CategoryDBHelper categoryDBHelper = null;
	private Todo todo;
	private List<Category> allCategories;
	private List<Priority> allPriorities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_db);
		todoDBHelper = new TodoDBHelper();
		prioDBHelper = new PriorityDBHelper();
		categoryDBHelper = new CategoryDBHelper();

		try {
			allPriorities = prioDBHelper.getAll(this);
			ArrayAdapter<Priority> adapter = new ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, allPriorities);
			Spinner spinner = (Spinner) findViewById(R.id.spinnerPriority);
			spinner.setAdapter(adapter);
		} catch (SQLException e) {
			// TODO Fehlerbehandlung Prioritaeten
			e.printStackTrace();
		}

		try {
			allCategories = categoryDBHelper.getAll(this);
			ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, allCategories);
			Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
			spinner.setAdapter(adapter);
		} catch (SQLException e) {
			// TODO Fehlerbehandlung Categories
			e.printStackTrace();
		}

		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(TodoListActivity.TODO_KEY)) {
			todo = (Todo) getIntent().getExtras().get(TodoListActivity.TODO_KEY);
			((EditText) findViewById(R.id.todoTitle)).setText(todo.getTitle());
			((EditText) findViewById(R.id.todoDescription)).setText(todo.getDescription());
			int priorityPosition = getPriorityPosition(todo.getPriority());
			((Spinner) findViewById(R.id.spinnerPriority)).setSelection(priorityPosition);
			int categoryPosition = getCategoryPosition(todo.getCategory());
			((Spinner) findViewById(R.id.spinnerCategory)).setSelection(categoryPosition);
		}
	}

	private int getPriorityPosition(Priority priority) {
		int i = 0;
		for (Priority nextPrio : allPriorities) {
			if (nextPrio.equals(priority))
				return i;
			else
				i++;
		}
		throw new RuntimeException("Nicht gefunden");
	}

	private int getCategoryPosition(Category category) {
		int i = 0;
		for (Category nextCategory : allCategories) {
			if (nextCategory.equals(category))
				return i;
			else
				i++;
		}
		throw new RuntimeException("Nicht gefunden");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		todoDBHelper.close();
		prioDBHelper.close();
		categoryDBHelper.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo_db, menu);
		return true;
	}

	public void save(View view) {
		if (todo == null) {
			todo = new Todo();
		}
		todo.setTitle(((EditText) findViewById(R.id.todoTitle)).getText().toString());
		todo.setDescription(((EditText) findViewById(R.id.todoDescription)).getText().toString());
		todo.setPriority((Priority) ((Spinner) findViewById(R.id.spinnerPriority)).getSelectedItem());
		todo.setCategory((Category) ((Spinner) findViewById(R.id.spinnerCategory)).getSelectedItem());
		todoDBHelper.createOrUpdate(this, todo);
		finish();
	}

	public void delete(View view) {
		if (todo != null) {
			Log.d("DELETE_PRIORITY", "Loesche Todo " + todo);
			todoDBHelper.delete(this, todo);
		}
		finish();
	}
}
