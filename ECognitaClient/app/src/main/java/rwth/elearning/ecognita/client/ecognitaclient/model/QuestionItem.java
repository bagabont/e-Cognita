package rwth.elearning.ecognita.client.ecognitaclient.model;

import java.util.List;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class QuestionItem {
    private String id;
    private String text;
    private List<String> answers;
    private int correctAnswerIndex;

    public QuestionItem(String id, String text, List<String> answers, int correctAnswerIndex) {
        this.id = id;
        this.text = text;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
