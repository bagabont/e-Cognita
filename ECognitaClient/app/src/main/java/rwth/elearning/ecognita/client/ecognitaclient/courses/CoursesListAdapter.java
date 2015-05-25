package rwth.elearning.ecognita.client.ecognitaclient.courses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rwth.elearning.ecognita.client.ecognitaclient.AbstractListAdapter;
import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.model.CourseListItem;

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
            CourseListItem item = (CourseListItem) getItem(position);
            courseName.setText(item.getCourseName());

            TextView courseProvider = (TextView) view.findViewById(R.id.course_provider_in_list);
            courseProvider.setText(item.getCourseProvider());

            TextView quizAvailableLabel = (TextView) view.findViewById(R.id.course_quiz_availability_in_list);
            quizAvailableLabel.setText(item.getCourseQuizAvailableLabel());
        }
        return view;
    }
}
