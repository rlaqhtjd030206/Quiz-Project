package country;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class CountryQuizLoader {

  /**
   * 난이도별 국가 퀴즈 JSON 파일에서 무작위 문제를 count만큼 불러온다.
   *
   * @param category   카테고리 ("countries" 또는 "capitals")
   * @param difficulty 난이도 ("초급", "중급", "고급")
   * @param count      출제할 문제 수
   * @return 무작위로 섞인 CountryQuiz 리스트
   */
  public static List<CountryQuiz> loadRandomQuestions(String category, String difficulty, int count) {
    try {
      // 난이도 매핑 (한글 → 영어)
      String difficultyKey = switch (difficulty) {
        case "초급" -> "easy";
        case "중급" -> "normal";
        case "고급" -> "hard";
        default -> "easy"; // 기본값
      };

      // JSON 파일 경로 생성 (예: capitals_easy.json)
      String filePath = String.format("src/country/data/%s_%s.json", category.toLowerCase(), difficultyKey);

      // JSON 파일에서 퀴즈 목록 불러오기
      ObjectMapper mapper = new ObjectMapper();
      CountryQuiz[] all = mapper.readValue(new File(filePath), CountryQuiz[].class);

      // 무작위 섞고 count만큼 잘라서 반환
      List<CountryQuiz> quizList = Arrays.asList(all);
      Collections.shuffle(quizList);
      return quizList.subList(0, Math.min(count, quizList.size()));

    } catch (Exception e) {
      e.printStackTrace();
      return List.of(); // 실패 시 빈 리스트 반환
    }
  }
}
