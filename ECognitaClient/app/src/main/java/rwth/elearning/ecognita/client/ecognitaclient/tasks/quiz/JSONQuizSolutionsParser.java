package rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.model.QuestionItem;

/**
 * Created by ekaterina on 02.07.2015.
 */
public class JSONQuizSolutionsParser {
    private static final String TEXT_PROPERTY_NAME = "question";
    private static final String ANSWERS_PROPERTY_NAME = "choices";
    private static final String CORRECT_PROPERTY_NAME = "correct";
    private static final String SELECTED_PROPERTY_NAME = "selected";

    public static List<QuestionItem> parse(String jsonString) throws JSONException {
        List<QuestionItem> items = new ArrayList<>();
        JSONArray questionList = new JSONArray(jsonString);
        for (int i = 0; i < questionList.length(); i++) {
            JSONObject questionListJSONObject = questionList.getJSONObject(i);
            items.add(parseItem(questionListJSONObject));
        }
        return items;
    }

    private static QuestionItem parseItem(JSONObject questionJsonObject) throws JSONException {
        String questionText = questionJsonObject.getString(TEXT_PROPERTY_NAME);
        List<String> questionAnswers = new ArrayList<>();
        JSONArray answers = questionJsonObject.getJSONArray(ANSWERS_PROPERTY_NAME);
        for (int i = 0; i < answers.length(); i++) {
            questionAnswers.add(answers.getString(i));
        }
        int selectedAnswer = questionJsonObject.getInt(SELECTED_PROPERTY_NAME);
        int correctAnswer = questionJsonObject.getInt(CORRECT_PROPERTY_NAME);
        return new QuestionItem("", questionText, questionAnswers, correctAnswer, selectedAnswer);
    }
}
