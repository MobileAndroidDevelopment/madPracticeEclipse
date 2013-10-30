package mobile.app.dev.ueb02;

import mobile.app.dev.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class CalculatorExtended extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator_extended);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator_extended, menu);
		return true;
	}

}
