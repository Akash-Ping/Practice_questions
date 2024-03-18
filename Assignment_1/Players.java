import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Players {
    public static void main(String[] args) {
        String csvFile = "Scores.csv";
        String line;

        LinkedHashMap<String, Map<String, Integer>> playerScores = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                System.out.println("Processing line: " + line);
                String[] parts = line.split(",");
                String player = parts[0];

                for (int i = 1; i < parts.length; i++) {
                    String[] match = parts[i].split("_");
                    String country = match[0];
                    int score = Integer.parseInt(match[1]);

                    playerScores.putIfAbsent(player, new HashMap<>());
                    Map<String, Integer> scores = playerScores.get(player);
                    scores.put(country, Math.max(scores.getOrDefault(country, Integer.MIN_VALUE), score));
                }
            }

            for (Map.Entry<String, Map<String, Integer>> entry : playerScores.entrySet()) {
                String player = entry.getKey();
                Map<String, Integer> scores = entry.getValue();
                int maxScore = Integer.MIN_VALUE;
                String country = "";

                for (Map.Entry<String, Integer> scoreEntry : scores.entrySet()) {
                    if (scoreEntry.getValue() > maxScore) {
                        maxScore = scoreEntry.getValue();
                        country = scoreEntry.getKey();
                    }
                }

                System.out.println(player + ": " + country + " - " + maxScore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
