package rwth.elearning.ecognita.client.ecognitaclient.courses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.AbstractListAdapter;
import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.StateChangeRequest;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;
import rwth.elearning.ecognita.client.ecognitaclient.tasks.OnResponseListener;

/**
 * Created by ekaterina on 25.05.2015.
 */
public class CoursesListAdapter extends AbstractListAdapter<CourseListItem> {
    public CoursesListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.course_list_item, parent, false);
        } else {
            view = convertView;
        }

        if (view != null) {
            TextView courseName = (TextView) view.findViewById(R.id.course_name_in_list);
            final CourseListItem item = (CourseListItem) getItem(position);
            courseName.setText(item.getCourseName());

            TextView courseProvider = (TextView) view.findViewById(R.id.course_provider_in_list);
            courseProvider.setText(item.getDescription());

            TextView quizAvailableLabel = (TextView) view.findViewById(R.id.course_quiz_availability_in_list);
            quizAvailableLabel.setText(item.getCourseQuizAvailableLabel());

            CheckBox star = (CheckBox) view.findViewById(R.id.star);
            star.setChecked(item.isInEnrolledList());
            star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        performEnrollForTheCourse(item);
                    } else {
                        performLoseTheCourse();
                    }
                }
            });
        }
        return view;
    }

    private void performLoseTheCourse() {
        //TODO: API NOT IMPLEMENTED
    }

    private void performEnrollForTheCourse(final CourseListItem item) {
        final boolean oldState = item.isInEnrolledList();
        item.setIsInEnrolledList(true);
        StateChangeRequest stateChangeRequest = new StateChangeRequest(item);
        stateChangeRequest.setOnResponseListener(new OnResponseListener<Boolean>() {

            @Override
            public void onResponse(Boolean responseOK) {
                if (!responseOK) {
                    item.setIsInEnrolledList(oldState);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                //
            }
        });
        stateChangeRequest.send();
    }
}
