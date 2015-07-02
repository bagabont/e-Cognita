package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;

/**
 * Created by ekaterina on 02.07.2015.
 */
public class SolutionFragment extends Fragment {
    private QuizListItem quizItem;

    public static SolutionFragment newInstance(QuizListItem quiz) {
        Bundle args = new Bundle();
        args.putSerializable(QuizesListAdapter.QUIZ_TAG, quiz);
        SolutionFragment fragment = new SolutionFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.quizItem = (QuizListItem) getArguments().getSerializable(QuizesListAdapter.QUIZ_TAG);

    }
}
