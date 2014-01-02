package mobile.app.dev.moneysac.Activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import mobile.app.dev.R;
import mobile.app.dev.moneysac.Adapters.DatePickerFragment;
import mobile.app.dev.moneysac.Adapters.ListViewAdapter;
import mobile.app.dev.moneysac.Helpers.LocalPersistence;
import mobile.app.dev.moneysac.Helpers.SegmentedRadioGroup;
import mobile.app.dev.moneysac.Model.BankAccountList;
import mobile.app.dev.moneysac.Model.BankAccountMonth;
import mobile.app.dev.moneysac.Model.ListEntry;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MoneySac extends Activity {

    SimpleDateFormat sdfOut = new SimpleDateFormat("MMMM yyyy", Locale.GERMAN);
    SimpleDateFormat sdfIn = new SimpleDateFormat("yyyyMM", Locale.GERMAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneysac);

        load();
    }

    private void load() {
        ListView listView = loadListView();
        Spinner monthSpinner = loadSpinner();
        SegmentedRadioGroup segmentedButton = loadSegmentedRadioGroup();
    }

    private SegmentedRadioGroup loadSegmentedRadioGroup() {
        return (SegmentedRadioGroup)findViewById(R.id.segmentedRadioGroup);
    }

    public void segmentedButtonClicked(View v){

        if(v.getId() == R.id.segmentedRadioButtonIn){

        } else if(v.getId() == R.id.segmentedRadioButtonOut){
            //if Out clicked

        } else {
            //represents all other states, showing both.

        }
    }

    private ListView loadListView(){
        ListView listView = (ListView)findViewById(R.id.listViewEntries);
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        Date yesterday = calendar.getTime();

        LinkedList<ListEntry> accountList = new LinkedList<ListEntry>();
        accountList.add(new ListEntry(yesterday, "Schuhe", 100, false));
        accountList.add(new ListEntry(yesterday, "Gehalt", 2000, true));
        accountList.add(new ListEntry(today, "Laptop", 1000, false));
        accountList.add(new ListEntry(today, "Taschengeld", 100, true));
        accountList.add(new ListEntry(today, "Essen", 200, false));

        Date thisMonth = calendar.getTime();

        //save a month just for example
        BankAccountMonth bankAccountMonth = new BankAccountMonth(accountList, thisMonth);
        BankAccountList.addMonthToStorage(this, bankAccountMonth);

        //and load it again
        BankAccountMonth currentMonth = BankAccountList.getMonthFromStorage(this, thisMonth);
        ListViewAdapter listAdapter = new ListViewAdapter(this, currentMonth.getEntries());
        listView.setAdapter(listAdapter);
        return listView;
    }

    private Spinner loadSpinner(){
        Spinner monthSpinner = (Spinner)findViewById(R.id.spinnerMonths);
        ArrayAdapter<String> monthSpinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        monthSpinnerAdapter.addAll(getMonths());
        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        monthSpinner.setAdapter(monthSpinnerAdapter);
        return monthSpinner;
    }

    private List<String> getMonths() {
        //TODO load directly from using method at LocalPersistence.java and get the name in the spinner from the date in the entry !

        List<String> list = LocalPersistence.getAllFilesNames(this);
        List<String> listToReturn = new LinkedList<String>();

        for(int i = 0; i < list.size(); i++){
            Log.d("filename"+i, list.get(i));
            try {
                if(list.get(i).startsWith("20")){
                    listToReturn.add(sdfOut.format(sdfIn.parse(list.get(i))));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return listToReturn;
    }


    public void showMoneySacDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu); //menu is toggled off, turn on if needed!
        return false;
    }

    public void addMonthToSpinner(String date) {

        Spinner spinner = (Spinner) findViewById(R.id.spinnerMonths);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>)spinner.getAdapter();
        Log.d("DATE STRING",date);
        try {
            boolean found = false;
            for(int i = 0; i < adapter.getCount(); i++){
                if(adapter.getItem(i).equals(sdfOut.format(sdfIn.parse(date)))){
                    found = true;
                }
            }
            if(!found){
                adapter.add(sdfOut.format(sdfIn.parse(date)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
