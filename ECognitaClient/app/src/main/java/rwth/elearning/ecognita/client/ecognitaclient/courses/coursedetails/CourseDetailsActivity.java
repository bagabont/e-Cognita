package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import rwth.elearning.ecognita.client.ecognitaclient.courses.MyCoursesActivity;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;

/**
 * Created by ekaterina on 25.05.2015.
 */
public class CourseDetailsActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        CourseListItem course = (CourseListItem) extras.getSerializable(MyCoursesActivity.COURSE_ITEM);
        if (course != null) {
            setTitle(course.getCourseName());
        }
    }
}
