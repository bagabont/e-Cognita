package rwth.elearning.ecognita.client.ecognitaclient.courses;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails.CourseDetailsActivity;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;

/**
 * Created by ekaterina on 22.05.2015.
 */
public class MyCoursesActivity extends ListActivity {
    public static final String COURSE_ITEM = "courseitem";
    private CoursesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        User loggedInUser = (User) extras.getSerializable(LogInFragment.USER_TAG);

        this.adapter = new CoursesListAdapter(getApplicationContext());
        addCourseItemsToAdapter(loggedInUser);
        setListAdapter(this.adapter);

        ListView lv = getListView();

        // Enable filtering when the user types in the virtual keyboard
        lv.setTextFilterEnabled(true);

        // Set an setOnItemClickListener on the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object item = adapter.getItem(position);
                if (item instanceof CourseListItem) {
                    CourseListItem courseListItem = (CourseListItem) item;
                    Intent intent = new Intent(MyCoursesActivity.this, CourseDetailsActivity.class);
                    intent.putExtra(COURSE_ITEM, courseListItem);
                    startActivity(intent);
                }
            }
        });
    }

    private void addCourseItemsToAdapter(User loggedInUser) {
        if (this.adapter != null) {
            //TODO: get course names from the server by having loggedInUser (see arguments)
            this.adapter.add(new CourseListItem("eLearning"));
            this.adapter.add(new CourseListItem("Logical Programming"));
        }
    }
}
