package rwth.elearning.ecognita.client.ecognitaclient.courses;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails.CourseDetailsActivity;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;

/**
 * Created by ekaterina on 25.05.2015.
 */
public class MyCoursesHomeFragment extends ListFragment {
    public static final String COURSE_ITEM = "courseitem";
    private User loggedInUser;
    private CoursesListAdapter coursesListAdapter;

    public static MyCoursesHomeFragment newInstance(User loggedInUser) {
        Bundle args = new Bundle();
        args.putSerializable(LogInFragment.USER_TAG, loggedInUser);
        MyCoursesHomeFragment fragment = new MyCoursesHomeFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.loggedInUser = (User) getArguments().getSerializable(LogInFragment.USER_TAG);
        this.coursesListAdapter = new CoursesListAdapter(getActivity().getApplicationContext());
        addCourseItemsToAdapter();
        setListAdapter(this.coursesListAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView lv = getListView();

        // Set an setOnItemClickListener on the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object item = coursesListAdapter.getItem(position);
                if (item instanceof CourseListItem) {
                    CourseListItem courseListItem = (CourseListItem) item;
                    Intent intent = new Intent(getActivity(), CourseDetailsActivity.class);
                    intent.putExtra(COURSE_ITEM, courseListItem);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_courses_list_fragment, container, false);
    }

    private void addCourseItemsToAdapter() {
        if (this.coursesListAdapter != null) {
            //TODO: get course names from the server by having loggedInUser (see this.loggedInUser)
            this.coursesListAdapter.add(new CourseListItem("eLearning"));
            this.coursesListAdapter.add(new CourseListItem("Logical Programming"));
        }
    }
}
