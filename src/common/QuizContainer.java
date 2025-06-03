package common;

import javax.swing.*;

import initial.*;
import person.*;
import country.*;
import concat.*;
import java.awt.*;
import java.util.List;

public class QuizContainer extends JPanel {
  private CardLayout layout;
  private int currentQuizCount = 5;

  public void setCurrentQuizCount(int count) {
    this.currentQuizCount = count;
  }

  public int getCurrentQuizCount() {
    return currentQuizCount;
  }

  // 홈 화면
  public QuizContainer() {
    layout = new CardLayout();
    setLayout(layout);

    add(new MainMenuPanel(this), "menu");
    layout.show(this, "menu");
  }

  // 뒤로가기
  public void goBackTo(String panelName) {
    layout.show(this, panelName);
  }

  // 카테고리 화면
  public void goToCategory(String quizType) {
    if ("초성 퀴즈".equals(quizType)) {
      InitialCategoryPanel categoryPanel = new InitialCategoryPanel(this, quizType);
      add(categoryPanel, "category");
      layout.show(this, "category");
    } else if ("인물 퀴즈".equals(quizType)) {
      PersonCategoryPanel categoryPanel = new PersonCategoryPanel(this, quizType);
      add(categoryPanel, "category");
      layout.show(this, "category");
    } else if ("국가 퀴즈".equals(quizType)) {
      CountryCategoryPanel categoryPanel = new CountryCategoryPanel(this, quizType);
      add(categoryPanel, "category");
      layout.show(this, "category");
    } else if ("이어말하기".equals(quizType)) {
      ConcatCategoryPanel categoryPanel = new ConcatCategoryPanel(this, quizType);
      add(categoryPanel, "category");
      layout.show(this, "category");
    }
  }

  // 난이도 선택 화면
  public void goToDifficulty(String quizType, String category) {
    if ("초성 퀴즈".equals(quizType)) {
      InitialDifficultyPanel difficultyPanel = new InitialDifficultyPanel(this, quizType, category);
      add(difficultyPanel, "difficulty");
      layout.show(this, "difficulty");
    } else if ("인물 퀴즈".equals(quizType)) {
      PersonDifficultyPanel panel = new PersonDifficultyPanel(this, category);
      add(panel, "personDifficulty");
      layout.show(this, "personDifficulty");
    } else if ("국가 퀴즈".equals(quizType)) {
      CountryDifficultyPanel difficultyPanel = new CountryDifficultyPanel(this, quizType, category);
      add(difficultyPanel, "difficulty");
      layout.show(this, "difficulty");
    } else if ("이어말하기".equals(quizType)) {
      ConcatDifficultyPanel difficultyPanel = new ConcatDifficultyPanel(this, quizType, category);
      add(difficultyPanel, "difficulty");
      layout.show(this, "difficulty");
    }
  }

  // 문제 갯수 선택 화면
  public void goToCount(String quizType, String category, String difficulty) {
    if ("초성 퀴즈".equals(quizType)) {
      InitialCountPanel countPanel = new InitialCountPanel(this, quizType, category, difficulty);
      add(countPanel, "count");
      layout.show(this, "count");
    } else if ("인물 퀴즈".equals(quizType)) {
      PersonCountPanel panel = new PersonCountPanel(this, category, difficulty);
      add(panel, "personCount");
      layout.show(this, "personCount");
    } else if ("국가 퀴즈".equals(quizType)) {
      CountryCountPanel countPanel = new CountryCountPanel(this, quizType, category, difficulty);
      add(countPanel, "count");
      layout.show(this, "count");
    } else if ("이어말하기".equals(quizType)) {

      ConcatCountPanel panel = new ConcatCountPanel(this, quizType, category, difficulty, null);
      add(panel, "concatCount");
      layout.show(this, "concatCount");
    }
  }

  // 퀴즈 화면
  public void startQuiz(String quizType, String category, String difficulty, int count, String questionType) {
    if ("초성 퀴즈".equals(quizType)) {
      List<InitialQuiz> selectedQuestions = InitialQuizLoader.loadRandomQuestions(category, count);
      InitialQuizPanel quizPanel = new InitialQuizPanel(selectedQuestions, this, difficulty);
      add(quizPanel, "quiz");
      layout.show(this, "quiz");
    } else if ("인물 퀴즈".equals(quizType)) {
      List<PersonQuiz> questions = PersonQuizLoader.load(category, difficulty, count);
      PersonQuizPanel panel = new PersonQuizPanel(this, questions, category, difficulty, count);
      add(panel, "personQuiz");
      layout.show(this, "personQuiz");
    } else if ("국가 퀴즈".equals(quizType)) {
      if ("capitals".equals(category)) {
        List<CountryQuiz> selectedQuestions = CountryQuizLoader.loadRandomQuestions("capitals", difficulty, count);
        CapitalQuizPanel quizPanel = new CapitalQuizPanel(selectedQuestions, this, difficulty);
        add(quizPanel, "capitalQuiz");
        layout.show(this, "capitalQuiz");
      } else {
        List<CountryQuiz> selectedQuestions = CountryQuizLoader.loadRandomQuestions("countries", difficulty, count);
        CountryQuizPanel quizPanel = new CountryQuizPanel(selectedQuestions, this, difficulty);
        add(quizPanel, "quiz");
        layout.show(this, "quiz");
      }
    } else if ("이어말하기".equals(quizType)) {
      List<ConcatQuiz> selectedQuestions = ConcatQuizLoader.load(category, difficulty, count);
      System.out.println("▶ [DEBUG] startQuiz() → 이어말하기 문제 수: " + selectedQuestions.size());

      ConcatQuizPanel panel = new ConcatQuizPanel(selectedQuestions, this, difficulty, questionType);
      add(panel, "concatQuiz");
      layout.show(this, "concatQuiz");
    }
  }

  // 초성 퀴즈 결과 화면
  public void showInitialResult(String quizType, int total, int correct, List<InitialQuizResult> results) {
    InitialResultPanel resultPanel = new InitialResultPanel(total, correct, results, this);
    add(resultPanel, "result");
    layout.show(this, "result");
  }

  // 인물 퀴즈 결과 화면
  public void showPersonResult(List<PersonQuizResult> results, int score, int total) {
    PersonResultPanel panel = new PersonResultPanel(this, score, total, results);
    add(panel, "personResult");
    layout.show(this, "personResult");
  }

  // 국가 퀴즈 결과 화면
  public void showCountryResult(String quizType, int total, int correct, List<CountryQuizResult> results) {
    if ("수도 퀴즈".equals(quizType)) {
      CapitalResultPanel resultPanel = new CapitalResultPanel(total, correct, results, this);
      add(resultPanel, "capitalResult");
      layout.show(this, "capitalResult");
    } else {
      CountryResultPanel resultPanel = new CountryResultPanel(total, correct, results, this);
      add(resultPanel, "countryResult");
      layout.show(this, "countryResult");
    }
  }

  // 퀴즈 수정 카테고리 선택 화면
  public void goToEditCategorySelector(String quizType) {
    if ("초성 퀴즈".equals(quizType)) {
      add(new InitialQuizEditCategoryPanel(this), "edit-category");
      layout.show(this, "edit-category");
    } else if ("국가 퀴즈".equals(quizType)) {
      add(new CountryQuizEditCategoryPanel(this), "edit-country-category");
      layout.show(this, "edit-country-category");
    }
  }

  // 퀴즈 수정 난이도 선택 화면
  public void goToEditDifficultySelector(String category) {
    CountryEditDifficultyPanel panel = new CountryEditDifficultyPanel(this, category);
    add(panel, "edit-difficulty");
    layout.show(this, "edit-difficulty");
  }

  // 퀴즈 수정 화면
  public void goToEditor(String quizType, String category) {
    if ("초성 퀴즈".equals(quizType)) {
      InitialQuizEditorPanel editor = new InitialQuizEditorPanel(this, category);
      add(editor, "editor");
      layout.show(this, "editor");
    } else if ("인물 퀴즈".equals(quizType)) {
      PersonQuizEditorPanel editor = new PersonQuizEditorPanel(this);
      add(editor, "editor");
      layout.show(this, "editor");
    } else if ("이어말하기".equals(quizType)) {
      ConcatQuizEditorPanel editor = new ConcatQuizEditorPanel(this);
      add(editor, "editor");
      layout.show(this, "editor");
    }
  }

  // 국가 퀴즈 수정 화면
  public void goToCountryEditor(String category, String difficulty) {
    CountryQuizEditorPanel editor = new CountryQuizEditorPanel(this, category, difficulty);
    add(editor, "editor");
    layout.show(this, "editor");
  }

  // 이어말하기 문제 유형 선택 화면
  public void goToTypeSelection(String quizType, String category, String difficulty) {
    ConcatTypePanel typePanel = new ConcatTypePanel(this, quizType, category, difficulty);
    add(typePanel, "typeSelection");
    layout.show(this, "typeSelection");
  }

  // 이어말하기 퀴즈 결과 화면
  public void goToResult(int correctCount, int totalCount, List<ConcatQuiz> quizList) {
    ConcatResultPanel resultPanel = new ConcatResultPanel(correctCount, totalCount, quizList, this);
    add(resultPanel, "concatResult");
    layout.show(this, "concatResult");
  }

  // 이어말하기 난이도 선택 화면
  public void goToDifficultySelector(String quizType, String category) {
    ConcatDifficultyPanel panel = new ConcatDifficultyPanel(this, quizType, category);
    add(panel, "difficulty");
    layout.show(this, "difficulty");
  }

}