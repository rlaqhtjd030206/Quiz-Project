package country;

public class CountryQuizResult {
  private final int number;
  private final String question;
  private final String userAnswer;
  private final String correctAnswer;

  public CountryQuizResult(int number, Object question, String userAnswer, Object correctAnswer) {
    this.number = number;
    this.question = question.toString();
    this.userAnswer = userAnswer;
    this.correctAnswer = correctAnswer.toString();
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
