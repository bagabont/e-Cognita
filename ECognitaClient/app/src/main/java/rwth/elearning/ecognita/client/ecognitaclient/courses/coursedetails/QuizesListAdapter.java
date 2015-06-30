package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import rwth.elearning.ecognita.client.ecognitaclient.AbstractListAdapter;
import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;

/**
 * Created by ekaterina on 15.06.2015.
 */
public class QuizesListAdapter extends AbstractListAdapter<QuizListItem> {
    public static final String QUIZ_TAG = "quiz_question_tag";

    public QuizesListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.quiz_list_item, parent, false);
        } else {
            view = convertView;
        }

        if (view != null) {
            final QuizListItem item = (QuizListItem) getItem(position);

            TextView courseName = (TextView) view.findViewById(R.id.quiz_title_in_list);
            courseName.setText(item.getTitle());

            TextView courseDescription = (TextView) view.findViewById(R.id.quiz_description_in_list);
            courseDescription.setText(item.getDescription());

            TextView courseCreated = (TextView) view.findViewById(R.id.quiz_created_in_list);
            courseCreated.setText(item.getCreated());

            Button startquizButton = (Button) view.findViewById(R.id.start_quiz_button);
          //  startquizButton.setVisibility(item.getResolved() != null && !item.getResolved().isEmpty() ? View.INVISIBLE : View.VISIBLE);
            startquizButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startQuiz = new Intent(context, QuizActivity.class);
                    startQuiz.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startQuiz.putExtra(QUIZ_TAG, item);
                    context.startActivity(startQuiz);
                }
            });
        }
        return view;
    }
}
