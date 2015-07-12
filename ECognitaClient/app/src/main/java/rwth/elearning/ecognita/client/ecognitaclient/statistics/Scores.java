package rwth.elearning.ecognita.client.ecognitaclient.statistics;

/**
 * Created by Albi on 7/10/2015.
 */
public class Scores {
        private Double [] userScores = new Double[10];
        private Double [] avgScores = new Double[10];

        public Scores(Double[] a, Double[] b){
                userScores = a;
                avgScores = b;
        }

        public Double getUserScores(int i){
                if (userScores[i] != null)
                        return userScores[i];
                else return (double) 0;
        }

        public Double getAvgScores(int i){
                return avgScores[i];
        }

}
