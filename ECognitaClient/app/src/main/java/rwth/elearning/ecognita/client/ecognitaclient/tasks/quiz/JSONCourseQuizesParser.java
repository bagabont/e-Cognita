package rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class JSONCourseQuizesParser {
    private static final String ID_PROPERTY_NAME = "id";
    private static final String TITLE_PROPERTY_NAME = "title";
    private static final String DESCRIPTION_PROPERTY_NAME = "description";
    private static final String CREATED_PROPERTY_NAME = "created";
    private static final String COURSE_ID_PROPERTY_NAME = "course_id";

    public static List<QuizListItem> parse(String jsonString) throws JSONException {
        List<QuizListItem> items = new ArrayList<>();
        JSONArray quizList = new JSONArray(jsonString);
        for (int i = 0; i < quizList.length(); i++) {
            JSONObject courseJSONObject = quizList.getJSONObject(i);
            items.add(parseItem(courseJSONObject));
        }
        return items;
    }

    private static QuizListItem parseItem(JSONObject quizJsonObject) throws JSONException {
        String quizId = quizJsonObject.getString(ID_PROPERTY_NAME);
        String quizTitle = quizJsonObject.getString(TITLE_PROPERTY_NAME);
        String quizDescription = quizJsonObject.getString(DESCRIPTION_PROPERTY_NAME);
        String quizCreated = quizJsonObject.getString(CREATED_PROPERTY_NAME);
        String quizCourseId = quizJsonObject.getString(COURSE_ID_PROPERTY_NAME);
        return new QuizListItem(quizDescription, quizTitle, quizCourseId, quizCreated, quizId);
    }
}
