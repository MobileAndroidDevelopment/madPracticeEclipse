package mobile.app.dev.ueb06.view;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.DatabaseHelper;
import mobile.app.dev.ueb06.orm.Todo;
import mobile.app.dev.ueb06.orm.TodoDBHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class TodoDBActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	private TodoDBHelper helper = null;
	private Todo todo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_db);
		helper = new TodoDBHelper();

		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(PriorityListActivity.PRIORITY)) {
			todo = (Todo) getIntent().getExtras().get(TodoListActivity.TODO_KEY);
			//			((EditText) findViewById(R.id.priority_name)).setText(todo.getTitle());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
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
		helper.createOrUpdate(this, todo);
		finish();
	}

	public void delete(View view) {
		if (todo != null) {
			Log.d("DELETE_PRIORITY", "Loesche Todo " + todo);
			helper.delete(this, todo);
		}
		finish();
	}
}
