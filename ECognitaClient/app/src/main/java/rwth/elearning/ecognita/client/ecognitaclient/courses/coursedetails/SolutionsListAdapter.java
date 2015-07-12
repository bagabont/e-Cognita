package rwth.elearning.ecognita.client.ecognitaclient.courses.coursedetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.AbstractListAdapter;
import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuestionItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.QuizListItem;

/**
 * Created by ekaterina on 09.07.2015.
 */
public class SolutionsListAdapter extends AbstractListAdapter<QuestionItem> {
    public SolutionsListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.quiz_solution_item, parent, false);
        } else {
            view = convertView;
        }

        if (view != null) {
            final QuestionItem item = (QuestionItem) getItem(position);
            TextView questionText = (TextView) view.findViewById(R.id.question);
            questionText.setText(item.getText());

            int correctAnswerIndex = item.getCorrectAnswerIndex();
            int selectedAnswerIndex = item.getSelectedAnswerIndex();

            List<TextView> options = new ArrayList<>();
            List<ImageView> optionImages = new ArrayList<>();
            TextView option1 = (TextView) view.findViewById(R.id.option1);
            option1.setText(item.getAnswers().size() > 0 ? item.getAnswers().get(0) : "");
            options.add(option1);
            TextView option2 = (TextView) view.findViewById(R.id.option2);
            option2.setText(item.getAnswers().size() > 1 ? item.getAnswers().get(1) : "");
            options.add(option2);
            TextView option3 = (TextView) view.findViewById(R.id.option3);
            option3.setText(item.getAnswers().size() > 2 ? item.getAnswers().get(2) : "");
            options.add(option3);
            TextView option4 = (TextView) view.findViewById(R.id.option4);
            option4.setText(item.getAnswers().size() > 3 ? item.getAnswers().get(3) : "");
            options.add(option4);

            ImageView optionImage1 = (ImageView) view.findViewById(R.id.option1_image);
            ImageView optionImage2 = (ImageView) view.findViewById(R.id.option2_image);
            ImageView optionImage3 = (ImageView) view.findViewById(R.id.option3_image);
            ImageView optionImage4 = (ImageView) view.findViewById(R.id.option4_image);
            optionImages.add(optionImage1);
            optionImages.add(optionImage2);
            optionImages.add(optionImage3);
            optionImages.add(optionImage4);
            int rightColor = view.getResources().getColor(R.color.correct_answer);
            int wrongColor = view.getResources().getColor(R.color.wrong_answer);
            if (correctAnswerIndex != selectedAnswerIndex) {
                if (selectedAnswerIndex != -1) {
                    options.get(selectedAnswerIndex).setBackgroundColor(wrongColor);
                    optionImages.get(selectedAnswerIndex).setBackgroundColor(wrongColor);
                    optionImages.get(selectedAnswerIndex).setImageResource(R.drawable.wrontick);
                } else {
                    //show all red
                    for (int i = 0; i < options.size(); i++) {
                        options.get(i).setBackgroundColor(wrongColor);
                        optionImages.get(i).setBackgroundColor(wrongColor);
                        optionImages.get(i).setImageResource(R.drawable.wrontick);
                    }
                }
            }
            options.get(correctAnswerIndex).setBackgroundColor(rightColor);
            optionImages.get(correctAnswerIndex).setBackgroundColor(rightColor);
            optionImages.get(correctAnswerIndex).setImageResource(R.drawable.cortick);
        }
        return view;
    }
}
