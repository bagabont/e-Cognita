package rwth.elearning.ecognita.client.ecognitaclient.model;

/**
 * Created by ekaterina on 25.05.2015.
 */
public class CourseListItem implements IListItem {
    private String id;
    private String description;
    private String courseName;
    private String courseProvider;
    private String courseQuizAvailableLabel;
    private boolean isInEnrolledList;

    public CourseListItem(String id, String courseName, String description) {
        this.courseName = courseName;
        this.id = id;
        this.description = description;
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

    public String getDescription() {
        return this.description;
    }

    public String getId() {
        return this.id;
    }

    public boolean isInEnrolledList() {
        return this.isInEnrolledList;
    }

    public void setIsInEnrolledList(boolean isInEnrolledList) {
        this.isInEnrolledList = isInEnrolledList;
    }
}
