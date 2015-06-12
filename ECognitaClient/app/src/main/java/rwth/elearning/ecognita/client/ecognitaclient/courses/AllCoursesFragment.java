package rwth.elearning.ecognita.client.ecognitaclient.courses;

import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.courses.GetListOfCoursesTask;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;

/**
 * Created by ekaterina on 12.06.2015.
 */
public class AllCoursesFragment extends MyCoursesHomeFragment {

    @Override
    protected void addCourseItemsToAdapter() {
        if (this.coursesListAdapter != null) {
            fetchListFromServer(ApiPathEnum.ALL_COURSES);
        }
    }

    @Override
    protected void processItem(final CourseListItem course) {
        GetListOfCoursesTask getListOfEnrolledCoursesTask = new GetListOfCoursesTask(ApiPathEnum.USER_COURSES);
        getListOfEnrolledCoursesTask.setOnResponseListener(new OnResponseListener<List<CourseListItem>>() {

            @Override
            public void onResponse(List<CourseListItem> coursesList) {
                boolean isInList = false;
                if (coursesList != null) {
                    for (CourseListItem courseItem : coursesList) {
                        if (course.getId().equals(courseItem.getId())) {
                            isInList = true;
                            break;
                        }
                    }
                }
                course.setIsInEnrolledList(isInList);
                coursesListAdapter.add(course);
            }

            @Override
            public void onError(String errorMessage) {
                //
            }
        });
        getListOfEnrolledCoursesTask.send(this.loggedInUser);
    }
}
