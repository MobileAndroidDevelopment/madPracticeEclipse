package mobile.app.dev.moneysac.Activities;


import java.sql.SQLException;
import java.util.List;

import mobile.app.dev.R;
import mobile.app.dev.moneysac.Model.Category;
import mobile.app.dev.moneysac.Model.SacEntryType;
import mobile.app.dev.moneysac.Model.SacEntryTypeDBHelper;
import mobile.app.dev.ueb03.TodoListActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class CategoryDetailActivity extends Activity {

	
	private Button btSave;
	private Button btCancel;
	private Spinner typeSpinner;
	private EditText edName;
	private Category category;
	
	private List<SacEntryType> typeList;
	private SacEntryTypeDBHelper typeDBHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_detail);
		
		typeDBHelper = new SacEntryTypeDBHelper();
		

		category = (Category) getIntent().getExtras().getSerializable(CategoryListView.CATEGORY_EXTRA_ID);

		initViews();
		setSpinnerValues();
		setListeners();

	}
	
	private void setSpinnerValues(){
		
		try {
			typeList = typeDBHelper.getAll(this);
		} catch (SQLException e) {
			Log.e("Get Types:", e.toString());
		}
		
		ArrayAdapter<SacEntryType> adapter = new ArrayAdapter<SacEntryType>(this, android.R.layout.simple_spinner_item, typeList);
		typeSpinner.setAdapter(adapter);
	}
	
	private void initViews(){
		btSave = (Button) findViewById(R.id.moneysac_detail_categoryl_bt_save);
		btCancel = (Button) findViewById(R.id.moneysac_detail_category_bt_cancel);
		typeSpinner = (Spinner) findViewById(R.id.moneysac_detail_category_typ_spinner);
		edName = (EditText) findViewById(R.id.moneysac_detail_category_edit_title);
		edName.setText(category.getName());
	}
	
	private void setListeners(){
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category_detail, menu);
		return true;
	}

}
