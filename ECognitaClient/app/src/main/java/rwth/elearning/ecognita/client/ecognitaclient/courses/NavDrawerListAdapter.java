package rwth.elearning.ecognita.client.ecognitaclient.courses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import rwth.elearning.ecognita.client.ecognitaclient.AbstractListAdapter;
import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.model.NavDrawerItem;

/**
 * Created by ekaterina on 25.05.2015.
 */
public class NavDrawerListAdapter extends AbstractListAdapter<NavDrawerItem> {

    public NavDrawerListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        NavDrawerItem item = (NavDrawerItem) getItem(position);
        imgIcon.setImageResource(item.getIcon());
        txtTitle.setText(item.getTitle());

        return convertView;
    }
}
