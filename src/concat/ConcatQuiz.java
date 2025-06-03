package concat;

import java.util.List;

public class ConcatQuiz {
    private String num;
    private String question;
    private String answer;
    private List<String> options; // 객관식 보기 목록

    // 기본 생성자 (Jackson이 JSON에서 객체로 만들 때 필요함)
    public ConcatQuiz() {}

    // 생성자 (직접 객체 만들 때 사용)
    public ConcatQuiz(String question, String answer, List<String> options) {
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "{" +
                "num='" + num + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", options=" + options +
                '}';
    }
}
