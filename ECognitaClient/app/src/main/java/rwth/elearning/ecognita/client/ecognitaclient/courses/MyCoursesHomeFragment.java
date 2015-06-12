package rwth.elearning.ecognita.client.ecognitaclient.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails.CourseDetailsActivity;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.GetListOfCoursesTask;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;

/**
 * Created by ekaterina on 25.05.2015.
 */
public class MyCoursesHomeFragment extends ListFragment {
    public static final String COURSE_ITEM = "courseitem";
    private static final String TAG = "courses_home_tag";
    protected User loggedInUser;
    protected CoursesListAdapter coursesListAdapter;

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
        this.loggedInUser = LogInFragment.getConnectedUser();
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
        View view = inflater.inflate(R.layout.my_courses_list_fragment, container, false);
        return view;
    }

    protected void addCourseItemsToAdapter() {
        if (this.coursesListAdapter != null) {
            fetchListFromServer(ApiPathEnum.USER_COURSES);
        }
    }

    protected void fetchListFromServer(ApiPathEnum apiPathEnum) {
        GetListOfCoursesTask getListOfEnrolledCoursesTask = new GetListOfCoursesTask(apiPathEnum);
        getListOfEnrolledCoursesTask.setOnResponseListener(new OnResponseListener<List<CourseListItem>>() {

            @Override
            public void onResponse(List<CourseListItem> coursesList) {
                if (coursesList != null) {
                    for (CourseListItem course : coursesList) {
                        processItem(course);
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
        getListOfEnrolledCoursesTask.send(this.loggedInUser);
    }

    protected void processItem(CourseListItem course) {
        course.setIsInEnrolledList(true);
        coursesListAdapter.add(course);
    }
}
