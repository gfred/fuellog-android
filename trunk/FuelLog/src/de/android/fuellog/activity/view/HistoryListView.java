package de.android.fuellog.activity.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.android.fuellog.R;
import de.android.fuellog.model.FuelData;

public class HistoryListView extends ArrayAdapter<FuelData> {
    private List<FuelData> elements;
    private Context ctx;

    public HistoryListView(Context context, int textViewResourceId, List<FuelData> items) {
        super(context, textViewResourceId, items);
        this.ctx = context;
        elements = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listElement = convertView;

        if (listElement == null) {
            LayoutInflater vi = LayoutInflater.from(ctx);// (LayoutInflater)
                                                         // ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listElement = vi.inflate(R.layout.view_history_list, null);
        }

        FuelData data = elements.get(position);

        if (data != null) {
            TextView date = (TextView) listElement.findViewById(R.id.view_history_list_date);
            TextView info = (TextView) listElement.findViewById(R.id.view_history_list_info);
            date.setText(data.getDateString());
            info.setText(data.getInfoString());
        }
        return listElement;
    }
}
