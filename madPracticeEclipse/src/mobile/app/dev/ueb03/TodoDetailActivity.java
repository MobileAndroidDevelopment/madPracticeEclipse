package mobile.app.dev.ueb03;

import mobile.app.dev.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class TodoDetailActivity extends Activity {

	private Todo todo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);

		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(TodoListActivity.ENTRY)) {
			todo = (Todo) getIntent().getExtras().getSerializable(TodoListActivity.ENTRY);
			((EditText) findViewById(R.id.editTextTitle)).setText(todo.getTitle());
			((EditText) findViewById(R.id.editTextDesc)).setText(todo.getDesc());
			((Spinner) findViewById(R.id.spinnerPrio)).setSelection(todo.getPriority().getPosition());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	public void saveClicked(View v) {
		String title = ((EditText) findViewById(R.id.editTextTitle)).getText().toString();
		String desc = ((EditText) findViewById(R.id.editTextDesc)).getText().toString();
		String prioAsString = ((Spinner) findViewById(R.id.spinnerPrio)).getSelectedItem().toString();
		Priority priority = Priority.valueOf(prioAsString);

		if (todo == null) {
			// neuen Todo speichern
			todo = new Todo(title, desc, priority);
			TodoList.getInstance().add(todo);
		} else {
			// bisherigen Todo abändern
			todo.setTitle(title);
			todo.setDesc(desc);
			todo.setPriority(priority);
			TodoList.getInstance().overrideExisting(todo);
		}
		finish();
	}
}
