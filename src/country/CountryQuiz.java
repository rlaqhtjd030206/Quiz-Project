package country;

public class CountryQuiz {
  private String num;
  private String question;
  private Object answer; // 수도 이름 또는 국가 이름 (String 또는 List)
  private String country; // 국기 이미지 파일명 (예: "korea.png")
  private Object capital; // 랜드마크 이미지 파일명 또는 List (예: "seoul.jpg")

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

  public Object getAnswer() {
    return answer;
  }

  public void setAnswer(Object answer) {
    this.answer = answer;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Object getCapital() {
    return capital;
  }

  public void setCapital(Object capital) {
    this.capital = capital;
  }

  @Override
  public String toString() {
    return "CountryQuiz{" +
        "num='" + num + '\'' +
        ", question='" + question + '\'' +
        ", answer=" + answer +
        ", country='" + country + '\'' +
        ", capital=" + capital +
        '}';
  }
}
