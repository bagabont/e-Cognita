package rwth.elearning.ecognita.client.ecognitaclient.tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;

/**
 * Created by ekaterina on 12.06.2015.
 */
public class JSONUserCoursesParser {
    private static final String ID_PROPERTY_NAME = "id";
    private static final String TITLE_PROPERTY_NAME = "title";
    private static final String DESCRIPTION_PROPERTY_NAME = "description";

    /**
     * Constructs a list of course items from the json array received from the server
     *
     * @param jsonString a json string with the course's data
     * @return a list of courses items
     * @throws JSONException
     */
    public static List<CourseListItem> parse(String jsonString) throws JSONException {
        List<CourseListItem> items = new ArrayList<>();
        JSONArray coursesList = new JSONArray(jsonString);
        for (int i = 0; i < coursesList.length(); i++) {
            JSONObject courseJSONObject = coursesList.getJSONObject(i);
            items.add(parseItem(courseJSONObject));
        }
        return items;
    }

    private static CourseListItem parseItem(JSONObject courseJSONObject) throws JSONException {
        String courseId = courseJSONObject.getString(ID_PROPERTY_NAME);
        String courseTitle = courseJSONObject.getString(TITLE_PROPERTY_NAME);
        String courseDescription = courseJSONObject.getString(DESCRIPTION_PROPERTY_NAME);
        return new CourseListItem(courseId, courseTitle, courseDescription);
    }
}
