package mobile.app.dev.ueb03;

import java.util.Collections;

import mobile.app.dev.R;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;


public class TodoSettingsActivity extends ListActivity {
	
	private static boolean IS_INSTANCED = false;
	private TodoSettingsListArrayAdapter adapter;	
	public static final int FONTSIZE_REQUEST_CODE = 1988;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TodoSettingsList elements = TodoSettingsList.getInstance();
		
		if(!IS_INSTANCED)
		{
			IS_INSTANCED = true;
			Setting setting = new Setting(getString(R.string.fontSize));			
			elements.add(setting);				
		}
		
		Collections.sort(elements, Collections.reverseOrder());
		adapter = new TodoSettingsListArrayAdapter(this, elements);		
		setListAdapter(adapter);	
	}	
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		
		switch (position) {
		case 0:			
			Intent intent = new Intent(this, TodoNumberPickerActivity.class);
			startActivityForResult(intent, FONTSIZE_REQUEST_CODE);		
		default:
			
		}
	}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	 	 
		if (requestCode == FONTSIZE_REQUEST_CODE) {	 
			String resultData = 
			data.getStringExtra(TodoNumberPickerActivity.RESULT_KEY);
		}	 
	 }
}
