package mobile.app.dev.moneysac.Adapters;

import java.util.LinkedList;

import mobile.app.dev.R;
import mobile.app.dev.moneysac.Model.Category;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryListViewAdapter extends ArrayAdapter<Category> {
    private final Context context;
    private final LinkedList<Category> values;

    public CategoryListViewAdapter(Context context, LinkedList<Category> values) {
        super(context, R.layout.moneysac_category_listrow, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.moneysac_category_listrow, parent, false);
        TextView titleCat = (TextView) rowView.findViewById(R.id.moneysac_category__title_listrow);
        TextView type = (TextView) rowView.findViewById(R.id.moneysac_category__type_listrow);
        ImageView icon = (ImageView) rowView.findViewById(R.id.moneysac_category_img_listrow);
        titleCat.setText(values.get(position).getName());
        type.setText(values.get(position).getType().getName());
        int iconID = values.get(position).getType().getIcon();
        icon.setImageResource(iconID);

        return rowView;
    }
}
