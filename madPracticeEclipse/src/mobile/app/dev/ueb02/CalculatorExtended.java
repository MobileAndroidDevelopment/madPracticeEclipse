package mobile.app.dev.ueb02;

import mobile.app.dev.R;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class CalculatorExtended extends Activity {

	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator_extended);
		setTitle("Rechner");

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
			case Configuration.SCREENLAYOUT_SIZE_XLARGE: {
				return false;
			}
			default: {
				return false;
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_sin:
			appendToText("sin(");
			return true;
		case R.id.menu_cos:
			appendToText("cos(");
			return true;
		case R.id.menu_tan:
			appendToText("tan(");
			return true;
		case R.id.menu_root:
			appendToText("sqrt(");
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
			appendToText("0");
			break;
		case R.id.button_1:
			appendToText("1");
			break;
		case R.id.button_2:
			appendToText("2");
			break;
		case R.id.button_3:
			appendToText("3");
			break;
		case R.id.button_4:
			appendToText("4");
			break;
		case R.id.button_5:
			appendToText("5");
			break;
		case R.id.button_6:
			appendToText("6");
			break;
		case R.id.button_7:
			appendToText("7");
			break;
		case R.id.button_8:
			appendToText("8");
			break;
		case R.id.button_9:
			appendToText("9");
			break;
		case R.id.button_add:
			appendToText("+");
			break;
		case R.id.button_mult:
			appendToText("*");
			break;
		case R.id.button_sub:
			appendToText("-");
			break;
		case R.id.button_back:
			if (textView.length() > 0) {
				textView.setText(textView.getText().subSequence(0,
						textView.getText().length() - 1));
			}
			break;
		case R.id.button_div:
			appendToText("/");
			break;
		case R.id.button_dot:
			appendToText(".");
			break;
		case R.id.button_eq:
			calculate();
			break;
		case R.id.button_bracketOpen:
			appendToText("(");
			break;
		case R.id.button_bracketClose:
			appendToText(")");
			break;
		case R.id.button_clear:
			textView.setText("");
			break;
		case R.id.button_sin:
			appendToText("sin(");
			break;
		case R.id.button_cos:
			appendToText("cos(");
			break;
		case R.id.button_tan:
			appendToText("tan(");
			break;
		case R.id.button_root:
			appendToText("sqrt(");
			break;
		}
	}

	private void calculate() {
		try {
			String expression = textView.getText().toString();
			Calculable calc = new ExpressionBuilder(expression).build();
			String result = calc.calculate() + "";
			textView.setText(result);
		} catch (Exception e) {
			textView.setText("Fehlerhafte Eingabe");
		}
	}
}
