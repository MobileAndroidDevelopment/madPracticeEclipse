package mobile.app.dev.ueb06.view;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mobile.app.dev.R;
import mobile.app.dev.ueb06.orm.Category;
import mobile.app.dev.ueb06.orm.CategoryDBHelper;
import mobile.app.dev.ueb06.orm.DatabaseHelper;
import mobile.app.dev.ueb06.orm.Priority;
import mobile.app.dev.ueb06.orm.PriorityDBHelper;
import mobile.app.dev.ueb06.orm.Todo;
import mobile.app.dev.ueb06.orm.TodoDBHelper;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class TodoDBActivity extends OrmLiteBaseActivity<DatabaseHelper> implements Timeable {

	private TodoDBHelper todoDBHelper = null;
	private PriorityDBHelper prioDBHelper = null;
	private CategoryDBHelper categoryDBHelper = null;
	private Todo todo;
	private long selectedTimeInMillis;
	private List<Category> allCategories;
	private List<Priority> allPriorities;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_db);
		todoDBHelper = new TodoDBHelper();
		prioDBHelper = new PriorityDBHelper();
		categoryDBHelper = new CategoryDBHelper();

		loadPriorities();
		loadCategories();

		ArrayAdapter<Priority> priorityAdapter = new ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, allPriorities);
		Spinner prioritySpinner = (Spinner) findViewById(R.id.spinnerPriority);
		prioritySpinner.setAdapter(priorityAdapter);

		ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, allCategories);
		Spinner categorySpinner = (Spinner) findViewById(R.id.spinnerCategory);
		categorySpinner.setAdapter(categoryAdapter);

		if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(TodoListActivity.TODO_KEY)) {
			todo = (Todo) getIntent().getExtras().get(TodoListActivity.TODO_KEY);
			((EditText) findViewById(R.id.todoTitle)).setText(todo.getTitle());
			((EditText) findViewById(R.id.todoDescription)).setText(todo.getDescription());
			int priorityPosition = getPositionInList(allPriorities, todo.getPriority());
			((Spinner) findViewById(R.id.spinnerPriority)).setSelection(priorityPosition);
			int categoryPosition = getPositionInList(allCategories, todo.getCategory());
			((Spinner) findViewById(R.id.spinnerCategory)).setSelection(categoryPosition);
			selectedTimeInMillis = todo.getDate();
		} else {
			selectedTimeInMillis = Calendar.getInstance().getTimeInMillis();
		}
		setFormattedDate();
	}

	private void setFormattedDate() {
		String formattedDate = sdf.format(new Date(selectedTimeInMillis));
		Log.d("TEST", formattedDate);
		((TextView) findViewById(R.id.todoChosenDateLabel)).setText(formattedDate);
	}

	/**
	 * Lädt die Prioritäten. Falls keine Prioritäten vorhanden sind, oder es zu Problemen beim Laden kam, muss die Activity beendet werden
	 */
	private void loadPriorities() {
		try {
			allPriorities = prioDBHelper.getAll(this);
			if (allPriorities.size() == 0)
				throw new SQLException("Keine Prioritäten da!");
		} catch (SQLException e) {
			Toast.makeText(this, "Fehler beim laden der Prioritäten. Kann kein neues Todo anlegen", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	/**
	 * Lädt die Kategorien. Falls keine Kategorien vorhanden sind, oder es zu Problemen beim Laden kam, muss die Activity beendet werden
	 */
	private void loadCategories() {
		try {
			allCategories = categoryDBHelper.getAll(this);
			if (allCategories.size() == 0)
				throw new SQLException("Keine Kategorien da!");
		} catch (SQLException e) {
			Toast.makeText(this, "Fehler beim laden der Kategorien. Kann kein neues Todo anlegen", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	/**
	 * Durchsucht eine Collection nach einem Objekt und gibt die Stelle zurück, an der dieses zu finden ist. 0-basiert.
	 * @param list
	 * @param objectToLookFor
	 * @return Stelle des Objekts. 0 falls nicht vorhanden.
	 */
	private <T> int getPositionInList(List<T> list, T objectToLookFor) {
		int i = 0;
		for (T nextObject : list) {
			if (nextObject.equals(objectToLookFor))
				return i;
			else
				i++;
		}
		return 0;
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
		return false;
	}

	public void save(View view) {
		if (todo == null) {
			todo = new Todo();
		}
		try {
			String title = ((EditText) findViewById(R.id.todoTitle)).getText().toString();
			if (title == null || title.isEmpty())
				throw new EmptyException(getString(R.string.EMPTY_TITLE_NOT_POSSIBLE));
			todo.setTitle(title);
			todo.setDescription(((EditText) findViewById(R.id.todoDescription)).getText().toString());
			todo.setPriority((Priority) ((Spinner) findViewById(R.id.spinnerPriority)).getSelectedItem());
			todo.setCategory((Category) ((Spinner) findViewById(R.id.spinnerCategory)).getSelectedItem());
			todo.setDate(selectedTimeInMillis);
			todoDBHelper.createOrUpdate(this, todo);
			finish();
		} catch (EmptyException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (SQLException e) {
			Toast.makeText(this, R.string.saving_not_possible, Toast.LENGTH_SHORT).show();
		}
	}

	public void delete(View view) {
		if (todo != null) {
			try {
				Log.d("DELETE_PRIORITY", "Loesche Todo " + todo);
				todoDBHelper.delete(this, todo);
				finish();
			} catch (SQLException e) {
				Toast.makeText(this, R.string.deleting_not_possible, Toast.LENGTH_SHORT).show();
			}
		} else {
			finish();
		}
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment(this, selectedTimeInMillis);
		newFragment.show(getFragmentManager(), "datePicker");
	}

	@Override
	public void setTime(long time) {
		Log.d("TIMEABLE", "Zeit wurde gesetzt: " + time);
		selectedTimeInMillis = time;
		setFormattedDate();
	}
}
