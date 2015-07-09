package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuestionItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.UserAnswer;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz.GetListOfQuestionsTask;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz.SendAnswersTask;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class QuizQuestionFragment extends Fragment {
    public static final int TEXT_SIZE = 24;
    public static final int PADDING = 8;
    public static final int NUMBER_OF_OPTIONS_IN_THE_QUESTION = 4;
    private QuizListItem quizItem;
    private List<QuestionItem> listOfQuestions = new ArrayList<>();
    private int currentQuestionNumber = 0;
    private List<UserAnswer> userAnswers = new ArrayList<>();
    private RadioGroup radioGroup;
    private CountDownTimer timer;

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
        final Button okButton = (Button) getView().findViewById(R.id.ok_question_button);
        final Button skipButton = (Button) getView().findViewById(R.id.skip_question_button);

        GetListOfQuestionsTask getListOfQuestionsTask = new GetListOfQuestionsTask();
        getListOfQuestionsTask.setOnResponseListener(new OnResponseListener<List<QuestionItem>>() {

            @Override
            public void onResponse(List<QuestionItem> receivedListOfQuestions) {
                if (receivedListOfQuestions != null) {
                    listOfQuestions.addAll(receivedListOfQuestions);
                    timer = new CountDownTimer(10000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            skipButton.setText("SKIP (" + millisUntilFinished / 1000 + ")");
                        }

                        public void onFinish() {
                            skipButton.performClick();
                        }
                    }.start();
                    setContentViewForQuestion();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("question_fragment", errorMessage);
            }
        });
        getListOfQuestionsTask.send(this.quizItem);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionNumber++;
                if (currentQuestionNumber < listOfQuestions.size()) {
                    UserAnswer answer = new UserAnswer(listOfQuestions.get(currentQuestionNumber - 1).getId(),
                            (radioGroup.getCheckedRadioButtonId() - 1) % NUMBER_OF_OPTIONS_IN_THE_QUESTION);
                    userAnswers.add(answer);
                    setContentViewForQuestion();
                    timer.start();
                }

                if (currentQuestionNumber == listOfQuestions.size()) {
                    UserAnswer answer = new UserAnswer(listOfQuestions.get(currentQuestionNumber - 1).getId(),
                            (radioGroup.getCheckedRadioButtonId() - 1) % NUMBER_OF_OPTIONS_IN_THE_QUESTION);
                    userAnswers.add(answer);
                    sendUserAnswers();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionNumber++;
                if (currentQuestionNumber < listOfQuestions.size()) {
                    setContentViewForQuestion();
                    timer.start();
                }
                if (currentQuestionNumber == listOfQuestions.size()) {
                    sendUserAnswers();
                }
            }
        });
    }

    private void sendUserAnswers() {
        SendAnswersTask sendAnswersTask = new SendAnswersTask(this.quizItem.getId());
        sendAnswersTask.setOnResponseListener(new OnResponseListener<Boolean>() {

            @Override
            public void onResponse(Boolean answersSuccessfullySent) {
                if (answersSuccessfullySent) {
                    Toast.makeText(getActivity(),
                            "Thank you for taking the quiz! The results will be available after " + quizItem.getPublished(),
                            Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("answers_send", errorMessage);
            }
        });
        sendAnswersTask.send(this.userAnswers);
    }

    private void setContentViewForQuestion() {
        LinearLayout questionArea = (LinearLayout) getView().findViewById(R.id.area_for_options);
        questionArea.removeAllViews();
        QuestionItem question1 = listOfQuestions.get(currentQuestionNumber);
        this.radioGroup = new RadioGroup(getActivity());

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
