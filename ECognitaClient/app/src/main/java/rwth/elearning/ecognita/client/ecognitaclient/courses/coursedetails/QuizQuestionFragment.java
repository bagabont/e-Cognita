package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.model.QuestionItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz.GetListOfQuestionsTask;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class QuizQuestionFragment extends Fragment {
    private QuizListItem quizItem;
    private List<QuestionItem> listOfQuestions = new ArrayList<>();

    public static QuizQuestionFragment newInstance(QuizListItem quizListItem) {
        Bundle args = new Bundle();
        args.putSerializable(QuizesListAdapter.QUIZ_TAG, quizListItem);
        QuizQuestionFragment fragment = new QuizQuestionFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.quizItem = (QuizListItem) getArguments().getSerializable(QuizesListAdapter.QUIZ_TAG);
        GetListOfQuestionsTask getListOfQuestionsTask = new GetListOfQuestionsTask();
        getListOfQuestionsTask.setOnResponseListener(new OnResponseListener<List<QuestionItem>>() {

            @Override
            public void onResponse(List<QuestionItem> receivedListOfQuestions) {
                if (receivedListOfQuestions != null) {
                    listOfQuestions.addAll(receivedListOfQuestions);
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("question_fragment", errorMessage);
            }
        });
        getListOfQuestionsTask.send(this.quizItem);
    }
}
