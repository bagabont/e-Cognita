package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.ActivityWithLogoutMenu;
import rwth.elearning.ecognita.client.ecognitaclient.courses.MyCoursesHomeFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;

/**
 * Created by ekaterina on 25.05.2015.
 */
public class CourseDetailsActivity extends ActivityWithLogoutMenu {
    private final static String FRAGMENT_TAG = "details_fragment";
    private CourseDetailsListFragment detailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        CourseListItem course = (CourseListItem) extras.getSerializable(MyCoursesHomeFragment.COURSE_ITEM);
        if (course != null) {
            setTitle(course.getCourseName());
        }

        setContentView(R.layout.activity_details);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        detailsFragment = (CourseDetailsListFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (detailsFragment == null) {
            // add the fragment
            detailsFragment = CourseDetailsListFragment.newInstance(course);
            fragmentManager.beginTransaction().add(R.id.details_fragment_container, detailsFragment, FRAGMENT_TAG).commit();
        }
    }
}
