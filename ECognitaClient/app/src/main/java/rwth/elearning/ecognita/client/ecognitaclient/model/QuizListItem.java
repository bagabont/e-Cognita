package rwth.elearning.ecognita.client.ecognitaclient.model;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class QuizListItem implements IListItem {
    private String description;
    private String title;
    private String courseId;
    private String created;
    private String id;
    private String resolved;
    private String published;

    public QuizListItem(String description, String title, String courseId, String created, String id, String published, String resolved) {
        this.description = description;
        this.title = title;
        this.courseId = courseId;
        this.created = created;
        this.id = id;
        this.published = published;
        this.resolved = resolved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }
}
