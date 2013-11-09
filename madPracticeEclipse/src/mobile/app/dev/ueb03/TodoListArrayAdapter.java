package mobile.app.dev.ueb03;

import java.util.LinkedList;

import mobile.app.dev.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TodoListArrayAdapter extends ArrayAdapter<Todo> {
    private final Context context;
    private final LinkedList<Todo> values;

    public TodoListArrayAdapter(Context context, LinkedList<Todo> values) {
        super(context, R.layout.todo_listrow, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.todo_listrow, parent, false);

        TextView title = (TextView)rowView.findViewById(R.id.textViewRowTitle);
        TextView prio = (TextView)rowView.findViewById(R.id.textViewRowPrio);
        
        title.setText(values.get(position).getTitle());
        prio.setText(values.get(position).getPriority());
        return rowView;
    }
}
