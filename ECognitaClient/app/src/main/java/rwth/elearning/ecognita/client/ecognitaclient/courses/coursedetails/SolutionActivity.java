package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.ActivityWithLogoutMenu;
import rwth.elearning.ecognita.client.ecognitaclient.courses.MyCoursesHomeFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;

/**
 * Created by ekaterina on 02.07.2015.
 */
public class SolutionActivity extends ActivityWithLogoutMenu {
    private SolutionFragment solutionFragment;
    private static final String FRAGMENT_TAG = "solutions";
    private QuizListItem quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_solution);
        Bundle extras = getIntent().getExtras();
        QuizListItem quiz = (QuizListItem) extras.getSerializable(QuizesListAdapter.QUIZ_TAG);
        if (quiz != null) {
            setTitle(quiz.getTitle());
            this.quiz = quiz;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        solutionFragment = (SolutionFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (solutionFragment == null) {
            // add the fragment
            solutionFragment = SolutionFragment.newInstance(quiz);
            fragmentManager.beginTransaction().add(R.id.see_solution_fragment_container, solutionFragment, FRAGMENT_TAG).commit();
        }
    }
}
