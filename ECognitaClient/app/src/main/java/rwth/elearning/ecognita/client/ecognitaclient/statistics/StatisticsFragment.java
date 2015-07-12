package rwth.elearning.ecognita.client.ecognitaclient.statistics;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rwth.elearning.ecognita.client.ecognitaclient.R;

/**
 * Created by Albi on 6/25/2015.
 */
public class StatisticsFragment extends Fragment {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://e-cognita.herokuapp.com/api/account/statistics/avg";

    //JSON Node Names
    private static final String TAG_QUIZ = "quiz";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_USER_SCORE = "user_score";
    private static final String TAG_AVG_SCORE = "average_score";
    private static final String TAG_TOTAL = "total_solutions";

    JSONArray statisticDataList = null;
    Context cont;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.statistics_fragment, container, false);

        cont = getActivity();
        new GetStatistics(cont, view).execute();

        return view;
    }


    /**
     * Async task class to get json by making HTTP call
     * */

    private class GetStatistics extends AsyncTask<Void, Void, ArrayList<String>> {

        private Context mContext;
        private View rootView;

        public GetStatistics(Context context, View rootView){
            this.mContext=context;
            this.rootView=rootView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected ArrayList<String> doInBackground(Void... arg0) {
            // Creating service handler class instance
            GetStatisticsData sh = new GetStatisticsData();
            ArrayList<String> result = new ArrayList<String>();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, GetStatisticsData.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    statisticDataList = new JSONArray(jsonStr);

                    for (int i = 0; i < statisticDataList.length(); i++){
                        JSONObject sd = statisticDataList.getJSONObject(i);
                        JSONObject quiz = sd.getJSONObject(TAG_QUIZ);

                        String quizId = quiz.getString(TAG_ID);
                        String quizTitle = quiz.getString(TAG_TITLE);
                        String userScore = sd.getString(TAG_USER_SCORE);
                        String avgScore = sd.getString(TAG_AVG_SCORE);
                        String totalSolutions = sd.getString(TAG_TOTAL);

                        result.add(quizId);
                        result.add(quizTitle);
                        result.add(userScore);
                        result.add(avgScore);
                        result.add(totalSolutions);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            //super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.InnerRelativeLayout);

            GraphView graphView = null;

            for (int i = 0; i < result.size() / 5; i++) {
                graphView = new LineGraphView(
                        rootView.getContext() // context
                        , "Web Technologies" // heading
                );
            }

            GraphView.GraphViewData[] dataScores = new GraphView.GraphViewData[result.size() / 5];
            GraphView.GraphViewData[] dataAvg = new GraphView.GraphViewData[result.size() / 5];

            for (int i = 0; i < result.size() / 5; i++) {
                if (result.get(i) != null) {
                    dataScores[i] = new GraphView.GraphViewData(i+1, Double.parseDouble(result.get(i + 2))*100);
                    dataAvg[i] = new GraphView.GraphViewData(i+1, Double.parseDouble(result.get(i + 3))*100);
                } else {
                    dataScores[i] = new GraphView.GraphViewData(i+1, 0);
                    dataAvg[i] = new GraphView.GraphViewData(i+1, 0);
                }
            }

            GraphViewSeries seriesScores = new GraphViewSeries("Your Score", new GraphViewSeries.GraphViewSeriesStyle(getResources().getColor(R.color.material_blue_grey_800), 5),dataScores);
            GraphViewSeries seriesAvg = new GraphViewSeries("Avg Scores", new GraphViewSeries.GraphViewSeriesStyle(getResources().getColor(R.color.light_gray), 5),dataAvg);
            //graphView.setManualYAxisBounds(0, 10);
            graphView.addSeries(seriesScores);
            graphView.addSeries(seriesAvg);
            TextView text1 = (TextView) rootView.findViewById(R.id.textView);
            TextView text2 = (TextView) rootView.findViewById(R.id.textView2);
            TextView text3 = (TextView) rootView.findViewById(R.id.textView3);
            text1.setText("Your Score with blue");
            text2.setText("Average score with gray");
            text3.setText("Total Solutions: "+result.get(4));
            //graphView.setViewPort(1, 10);
            //graphView.setManualYAxisBounds(100, 0);
            graphView.setScrollable(true);
            layout.addView(graphView);


        }
    }
}
