package rwth.elearning.ecognita.client.ecognitaclient;

import android.content.Context;
import android.widget.BaseAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;

/**
 * Created by ekaterina on 25.05.2015.
 */
public abstract class AbstractListAdapter<T extends Serializable> extends BaseAdapter {
    protected final List<T> items = new ArrayList<T>();
    protected final Context context;

    public AbstractListAdapter(Context context) {
        this.context = context;
    }

    /**
     * Adds the item into a list of items stored in adapter
     *
     * @param item a serializable item to be added to an adapter items
     */
    public void add(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return this.items;
    }

    /**
     * Clears the items from a list of items stored in adapter
     */
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int pos) {
        return items.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(List<T> items) {
        clear();
        for (T item : items) {
            add(item);
        }
    }
}
