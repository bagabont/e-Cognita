package rwth.elearning.ecognita.client.ecognitaclient.model;

/**
 * Created by ekaterina on 25.05.2015.
 */
public class CourseListItem implements IListItem {
    private String courseName;
    private String courseProvider;
    private String courseQuizAvailableLabel;

    public CourseListItem(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public String getCourseProvider() {
        return this.courseProvider == null ? "No information about chair" : this.courseProvider;
    }

    public String getCourseQuizAvailableLabel() {
        return this.courseQuizAvailableLabel == null ? "No new quiz active for the moment" : this.courseQuizAvailableLabel;
    }
}
