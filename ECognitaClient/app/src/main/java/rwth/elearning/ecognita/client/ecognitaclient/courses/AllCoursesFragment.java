package rwth.elearning.ecognita.client.ecognitaclient.courses;

import rwth.elearning.ecognita.client.ecognitaclient.tasks.ApiPathEnum;

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
}
