package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;

import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.model.QuestionItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz.SeeSolutionsTask;

/**
 * Created by ekaterina on 02.07.2015.
 */
public class SolutionFragment extends ListFragment {
    private QuizListItem quizItem;
    private SolutionsListAdapter adapter;

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
        this.adapter = new SolutionsListAdapter(getActivity().getApplicationContext());
        addSolutionsToAdapter();
        setListAdapter(this.adapter);
    }

    private void addSolutionsToAdapter() {
        if (this.adapter != null) {
            fetchListFromServer();
        }
    }

    private void fetchListFromServer() {
        SeeSolutionsTask seeSolutionsTask = new SeeSolutionsTask();
        seeSolutionsTask.setOnResponseListener(new OnResponseListener<List<QuestionItem>>() {

            @Override
            public void onResponse(List<QuestionItem> listOfQuestions) {
                if (listOfQuestions != null) {
                    for (QuestionItem question : listOfQuestions) {
                        adapter.add(question);
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("solutions", errorMessage);
            }
        });
        seeSolutionsTask.send(this.quizItem);
    }
}
