package person;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonQuizLoader {
    public static List<PersonQuiz> load(String category, String difficulty, int count) {
        List<PersonQuiz> result = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();

            // JSON 배열을 List<PersonQuiz>로 바로 매핑
            List<PersonQuiz> allQuizzes = mapper.readValue(
                    new File("src/person/data/person.json"),
                    new TypeReference<List<PersonQuiz>>() {
                    });

            // 필터링
            for (PersonQuiz quiz : allQuizzes) {
                if (quiz.getCategory().equals(category) && quiz.getDifficulty().equals(difficulty)) {
                    result.add(quiz);
                }
            }

            Collections.shuffle(result);
            if (result.size() > count) {
                result = result.subList(0, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
