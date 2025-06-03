package initial;

public class InitialQuiz {
  private String num;
  private String question;
  private String answer;

  // Getter, Setter
  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  @Override
  public String toString() {
    return "{" +
        " num='" + getNum() + "'" +
        ", question='" + getQuestion() + "'" +
        ", answer='" + getAnswer() + "'" +
        "}";
  }

}