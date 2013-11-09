package mobile.app.dev.ueb03;

import java.util.LinkedList;

import mobile.app.dev.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TodoSettingsListArrayAdapter extends ArrayAdapter<Setting> {
	private final Context context;
    private final LinkedList<Setting> values;

    public TodoSettingsListArrayAdapter(Context context, TodoSettingsList values) {
        super(context, R.layout.todo_settings_listrow, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.todo_settings_listrow, parent, false);

        TextView title = (TextView)rowView.findViewById(R.id.textViewRowSettingsTitle);                
        title.setText(values.get(position).getTitle());
        
        return rowView;
    }
}
