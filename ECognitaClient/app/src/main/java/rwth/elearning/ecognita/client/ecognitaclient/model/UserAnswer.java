package rwth.elearning.ecognita.client.ecognitaclient.model;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class UserAnswer {
    private String questionId;
    private int choice;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public UserAnswer(String questionId, int choice) {
        this.questionId = questionId;
        this.choice = choice;
    }
}
