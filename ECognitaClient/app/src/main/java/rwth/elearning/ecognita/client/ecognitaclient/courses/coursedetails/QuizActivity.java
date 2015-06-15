package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.ActivityWithLogoutMenu;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class QuizActivity extends ActivityWithLogoutMenu {
    private final static String FRAGMENT_TAG = "quiz_question_fragment";
    private QuizQuestionFragment quizQuestionFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        QuizListItem quizListItem = (QuizListItem) extras.getSerializable(QuizesListAdapter.QUIZ_TAG);
        if (quizListItem != null) {
            setTitle(quizListItem.getTitle());
        }

        setContentView(R.layout.activity_quiz_question);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        quizQuestionFragment = (QuizQuestionFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (quizQuestionFragment == null) {
            // add the fragment
            quizQuestionFragment = QuizQuestionFragment.newInstance(quizListItem);
            fragmentManager.beginTransaction().add(R.id.quiz_fragment_container, quizQuestionFragment, FRAGMENT_TAG).commit();
        }
    }
}
