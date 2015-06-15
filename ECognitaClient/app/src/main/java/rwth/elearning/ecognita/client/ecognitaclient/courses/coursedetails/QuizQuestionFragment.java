package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuestionItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz.GetListOfQuestionsTask;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class QuizQuestionFragment extends Fragment {
    public static final int TEXT_SIZE = 24;
    public static final int PADDING = 8;
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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questions_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetListOfQuestionsTask getListOfQuestionsTask = new GetListOfQuestionsTask();
        getListOfQuestionsTask.setOnResponseListener(new OnResponseListener<List<QuestionItem>>() {

            @Override
            public void onResponse(List<QuestionItem> receivedListOfQuestions) {
                if (receivedListOfQuestions != null) {
                    listOfQuestions.addAll(receivedListOfQuestions);
                    LinearLayout questionArea = (LinearLayout) getView().findViewById(R.id.area_for_options);
                    QuestionItem question1 = listOfQuestions.get(0);
                    RadioGroup radioGroup = new RadioGroup(getActivity());

                    TextView questionText = new TextView(getActivity());
                    questionText.setText(question1.getText());
                    questionText.setTextSize(TEXT_SIZE);
                    questionText.setPadding(PADDING, PADDING, PADDING, PADDING);

                    for (String possibleAnswer : question1.getAnswers()) {
                        RadioButton radioButton = new RadioButton(getActivity());
                        radioButton.setTextSize(TEXT_SIZE);
                        radioButton.setPadding(PADDING, PADDING, PADDING, PADDING);

                        radioButton.setText(possibleAnswer);
                        radioGroup.addView(radioButton);
                    }

                    questionArea.addView(questionText);
                    questionArea.addView(radioGroup);
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
