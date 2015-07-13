package rwth.elearning.ecognita.client.ecognitaclient.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import rwth.elearning.ecognita.client.ecognitaclient.R;

/**
 * Created by Albi on 7/13/2015.
 */
public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment, container, false);
        EditText searchText = (EditText) view.findViewById(R.id.searchText);
        Button searchButton = (Button) view.findViewById(R.id.search_button);


        return view;
    }
}
