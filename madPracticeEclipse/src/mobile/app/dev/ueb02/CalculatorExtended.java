package mobile.app.dev.ueb02;

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
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class CalculatorExtended extends Activity {

	private static final String TITLE = "Rechner";
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator_extended);
		setTitle(TITLE);

		textView = (TextView) findViewById(R.id.textView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Configuration conf = getResources().getConfiguration();
		switch (conf.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
			case Configuration.SCREENLAYOUT_SIZE_NORMAL: {
				getMenuInflater().inflate(R.menu.calculator_extended_menu, menu);
				return true;
			}
			default:
				return false;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_sin:
				appendToText(SIN);
				return true;
			case R.id.menu_cos:
				appendToText(COS);
				return true;
			case R.id.menu_tan:
				appendToText(TAN);
				return true;
			case R.id.menu_root:
				appendToText(SQUARE_ROOT);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void appendToText(String string) {
		textView.append(string);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_0:
				appendToText(_0);
				break;
			case R.id.button_1:
				appendToText(_1);
				break;
			case R.id.button_2:
				appendToText(_2);
				break;
			case R.id.button_3:
				appendToText(_3);
				break;
			case R.id.button_4:
				appendToText(_4);
				break;
			case R.id.button_5:
				appendToText(_5);
				break;
			case R.id.button_6:
				appendToText(_6);
				break;
			case R.id.button_7:
				appendToText(_7);
				break;
			case R.id.button_8:
				appendToText(_8);
				break;
			case R.id.button_9:
				appendToText(_9);
				break;
			case R.id.button_add:
				appendToText(ADD);
				break;
			case R.id.button_mult:
				appendToText(MULTIPLY);
				break;
			case R.id.button_sub:
				appendToText(SUBTRACT);
				break;
			case R.id.button_back:
				if (textView.length() > 0) {
					textView.setText(textView.getText().subSequence(0,
							textView.getText().length() - 1));
				}
				break;
			case R.id.button_div:
				appendToText(DIVIDE);
				break;
			case R.id.button_dot:
				appendToText(DOT);
				break;
			case R.id.button_eq:
				calculate();
				break;
			case R.id.button_bracketOpen:
				appendToText(OPEN_BRACKET);
				break;
			case R.id.button_bracketClose:
				appendToText(CLOSE_BRACKET);
				break;
			case R.id.button_clear:
				textView.setText("");
				break;
			case R.id.button_sin:
				appendToText(SIN);
				break;
			case R.id.button_cos:
				appendToText(COS);
				break;
			case R.id.button_tan:
				appendToText(TAN);
				break;
			case R.id.button_root:
				appendToText(SQUARE_ROOT);
				break;
		}
	}

	/**
	 * fuehrt die Berechnung aus. Bei fehlerhafter Berechnung wird eine Nachricht auf dem Display angezeigt.
	 * Der eingegebene Text bleibt dabei erhalten.
	 */
	private void calculate() {
		try {
			String expression = textView.getText().toString();
			Calculable calc = new ExpressionBuilder(expression).build();
			String result = calc.calculate() + "";
			textView.setText(result);
		} catch (Exception e) {
			Toast toast = Toast.makeText(this, "Fehlerhafte Berechnung", Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
