package person;

import java.util.List;

public class PersonQuizResult {
  private final int number;
  private final String userAnswer;
  private final List<String> correctAnswer;

  public PersonQuizResult(int number, String userAnswer, List<String> correctAnswer) {
    this.number = number;
    this.userAnswer = userAnswer;
    this.correctAnswer = correctAnswer;
  }

  public int getNumber() {
    return number;
  }

  public String getUserAnswer() {
    return userAnswer;
  }

  public List<String> getCorrectAnswer() {
    return correctAnswer;
  }
}
