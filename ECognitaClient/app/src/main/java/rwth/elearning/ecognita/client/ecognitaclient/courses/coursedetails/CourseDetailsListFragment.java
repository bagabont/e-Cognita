package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.quiz.GetListOfQuizesTask;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class CourseDetailsListFragment extends ListFragment {
    private static final String COURSE_TAG = "course_detail_list_quiz";
    private CourseListItem course;
    private QuizesListAdapter adapter;

    public static CourseDetailsListFragment newInstance(CourseListItem course) {
        Bundle args = new Bundle();
        args.putSerializable(COURSE_TAG, course);
        CourseDetailsListFragment fragment = new CourseDetailsListFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.course = (CourseListItem) getArguments().getSerializable(COURSE_TAG);
        this.adapter = new QuizesListAdapter(getActivity().getApplicationContext());
        addQuizesToAdapter();
        setListAdapter(this.adapter);
    }

    private void addQuizesToAdapter() {
        if (this.adapter != null) {
            fetchListFromServer();
        }
    }

    private void fetchListFromServer() {
        GetListOfQuizesTask getListOfQuizesTask = new GetListOfQuizesTask();
        getListOfQuizesTask.setOnResponseListener(new OnResponseListener<List<QuizListItem>>() {

            @Override
            public void onResponse(List<QuizListItem> listOfQuizes) {
                if (listOfQuizes != null) {
                    for (QuizListItem quiz : listOfQuizes) {
                        adapter.add(quiz);
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("course_details", errorMessage);
            }
        });
        getListOfQuizesTask.send(this.course);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_quizes_list_fragment, container, false);
        return view;
    }
}
