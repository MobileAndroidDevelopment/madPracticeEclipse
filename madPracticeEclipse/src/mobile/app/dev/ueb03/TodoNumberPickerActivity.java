package mobile.app.dev.ueb03;

import static mobile.app.dev.ueb02.CalcOperators.ADD;
import static mobile.app.dev.ueb02.CalcOperators.CLOSE_BRACKET;
import static mobile.app.dev.ueb02.CalcOperators.COS;
import static mobile.app.dev.ueb02.CalcOperators.DIVIDE;
import static mobile.app.dev.ueb02.CalcOperators.DOT;
import static mobile.app.dev.ueb02.CalcOperators.MULTIPLY;
import static mobile.app.dev.ueb02.CalcOperators.OPEN_BRACKET;
import static mobile.app.dev.ueb02.CalcOperators.SIN;
import static mobile.app.dev.ueb02.CalcOperators.SQUARE_ROOT;
import static mobile.app.dev.ueb02.CalcOperators.SUBTRACT;
import static mobile.app.dev.ueb02.CalcOperators.TAN;
import static mobile.app.dev.ueb02.CalcOperators._0;
import static mobile.app.dev.ueb02.CalcOperators._1;
import static mobile.app.dev.ueb02.CalcOperators._2;
import static mobile.app.dev.ueb02.CalcOperators._3;
import static mobile.app.dev.ueb02.CalcOperators._4;
import static mobile.app.dev.ueb02.CalcOperators._5;
import static mobile.app.dev.ueb02.CalcOperators._6;
import static mobile.app.dev.ueb02.CalcOperators._7;
import static mobile.app.dev.ueb02.CalcOperators._8;
import static mobile.app.dev.ueb02.CalcOperators._9;
import mobile.app.dev.R;
import mobile.app.dev.R.layout;
import mobile.app.dev.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class TodoNumberPickerActivity extends Activity {
	
	public static final String RESULT_KEY = "my.result.key";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_number_picker);		
	    
	    ((NumberPicker) findViewById(R.id.numberPicker1)).setMinValue(9);
		((NumberPicker) findViewById(R.id.numberPicker1)).setMaxValue(35);
		((NumberPicker) findViewById(R.id.numberPicker1)).setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_number_picker, menu);
		return true;
	}
	
	private void handleOkResult()
	{
		Intent resultData = new Intent();
		resultData.putExtra(RESULT_KEY, ((NumberPicker) findViewById(R.id.numberPicker1)).getValue());		 
		setResult(RESULT_OK, resultData);
	}
	
	private void handleAbbortResult()
	{
		Intent resultData = new Intent();
		resultData.putExtra(RESULT_KEY, ((NumberPicker) findViewById(R.id.numberPicker1)).getValue());		 
		setResult(RESULT_CANCELED);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_abbort:	
				handleOkResult();
				break;
			case R.id.button_ok:
				handleAbbortResult();
				break;			
		}
	}

}
