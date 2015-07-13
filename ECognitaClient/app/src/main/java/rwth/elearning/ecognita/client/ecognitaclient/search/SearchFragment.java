package rwth.elearning.ecognita.client.ecognitaclient.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.courses.AllCoursesFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;

/**
 * Created by Albi on 7/13/2015.
 */
public class SearchFragment extends AllCoursesFragment {
    private List<CourseListItem> initialList = new ArrayList<>();
    private boolean firstTime = true;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        final EditText searchText = (EditText) view.findViewById(R.id.searchText);
        Button searchButton = (Button) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterResults(searchText.getText().toString());
            }
        });

        return view;
    }

    private void filterResults(String s) {
        if (firstTime) {
            for (CourseListItem clistItem : this.coursesListAdapter.getItems()) {
                initialList.add(clistItem);
            }
            firstTime = false;
        }
        List<CourseListItem> result = new ArrayList<>();
        for (CourseListItem courseItem : initialList) {
            if (courseItem.getCourseName().toLowerCase().contains(s.toLowerCase())) {
                result.add(courseItem);
            }
        }

        this.coursesListAdapter.setItems(result);
    }
}
