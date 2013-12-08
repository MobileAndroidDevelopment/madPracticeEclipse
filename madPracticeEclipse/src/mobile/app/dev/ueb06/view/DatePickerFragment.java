package mobile.app.dev.ueb06.view;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	private Timeable timeable;
	private long prefilledDateAsMillis;

	public DatePickerFragment() {
	}

	public DatePickerFragment(Timeable timeable, long prefilledDateAsMillis) {
		this.timeable = timeable;
		this.prefilledDateAsMillis = prefilledDateAsMillis;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		if (prefilledDateAsMillis != 0) {
			c.setTimeInMillis(prefilledDateAsMillis);
		}
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		timeable.setTime(calendar.getTimeInMillis());
	}
}
