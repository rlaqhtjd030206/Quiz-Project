package initial;

public class InitialQuizResult {
  private final int number;
  private final String question;
  private final String userAnswer;
  private final String correctAnswer;

  public InitialQuizResult(int number, String question, String userAnswer, String correctAnswer) {
    this.number = number;
    this.question = question;
    this.userAnswer = userAnswer;
    this.correctAnswer = correctAnswer;
  }

  public int getNumber() {
    return number;
  }

  public String getQuestion() {
    return question;
  }

  public String getUserAnswer() {
    return userAnswer;
  }

  public String getCorrectAnswer() {
    return correctAnswer;
  }
}
