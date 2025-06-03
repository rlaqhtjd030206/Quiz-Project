package initial;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class InitialQuizLoader {
  public static List<InitialQuiz> loadRandomQuestions(String category, int count) {
    try {
      if ("random".equals(category)) {
        String[] categories = { "dramas", "movies", "sports" };
        int index = new Random().nextInt(categories.length);
        category = categories[index];
        System.out.println("🔀 랜덤 카테고리 선택됨: " + category);
      }

      String filePath = "src/initial/data/" + category.toLowerCase() + ".json";
      ObjectMapper mapper = new ObjectMapper();
      File file = new File(filePath);
      InitialQuiz[] all = mapper.readValue(file, InitialQuiz[].class);

      List<InitialQuiz> allList = Arrays.asList(all);
      Collections.shuffle(allList);

      if (count > allList.size()) {
        System.out.println("⚠ 선택한 문항 수보다 파일 내 문제가 적습니다. " + allList.size() + "문제만 출제됩니다.");
      }

      return allList.subList(0, Math.min(count, allList.size()));

    } catch (Exception e) {
      e.printStackTrace();
      return List.of();
    }
  }
}
