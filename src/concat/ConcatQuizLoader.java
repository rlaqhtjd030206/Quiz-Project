package concat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class ConcatQuizLoader {

    public static List<ConcatQuiz> load(String category, String difficulty, int count) {
        try {
            // 한글 난이도 → 영어로 매핑
            String diff = switch (difficulty) {
                case "초급" -> "easy";
                case "중급" -> "medium";
                case "고급" -> "hard";
                default -> "easy";
            };

            String fileName = String.format("src/concat/data/%s_%s.json", category, diff);

            File file = new File(fileName);
            ObjectMapper mapper = new ObjectMapper();
            ConcatQuiz[] all = mapper.readValue(file, ConcatQuiz[].class);

            List<ConcatQuiz> quizList = Arrays.asList(all);
            Collections.shuffle(quizList);

            return quizList.subList(0, Math.min(count, quizList.size()));
        } catch (Exception e) {
            System.out.println("⚠ [ERROR] 문제 로딩 중 예외 발생:");
            e.printStackTrace();
            return List.of();
        }
    }
}
