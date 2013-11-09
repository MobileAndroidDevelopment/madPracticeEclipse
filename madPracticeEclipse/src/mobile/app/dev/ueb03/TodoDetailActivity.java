package mobile.app.dev.ueb03;

import mobile.app.dev.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class TodoDetailActivity extends Activity {

	private boolean isNew;
	private Todo todo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);
		
		todo = (Todo) getIntent().getExtras().getSerializable(TodoListActivity.ENTRY);
		if(todo == null){
			isNew = true;
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
	
	public void saveClicked(View v){
		String title = ((EditText)findViewById(R.id.editTextTitle)).getText().toString();
		String desc = ((EditText)findViewById(R.id.editTextDesc)).getText().toString();
		String prio = ((Spinner)findViewById(R.id.spinnerPrio)).getSelectedItem().toString();
		
		if(isNew){
			Todo todo = new Todo(title, desc, prio);
			TodoList.getInstance().add(todo);
		} else {
			todo.setTitle(title);
			todo.setDesc(desc);
			todo.setPriority(prio);
		}
		
	}
}
