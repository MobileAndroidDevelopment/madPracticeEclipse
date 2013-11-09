package mobile.app.dev.ueb03;

import mobile.app.dev.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Spinner;

public class TodoDetailActivity extends Activity {

	private Spinner spinner;
	private EditText editTextTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);
		
		spinner = (Spinner)findViewById(R.id.spinnerPrio);
		editTextTitle = (EditText)findViewById(R.id.editTextTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_detail, menu);
		return true;
	}

}
