
package person;

import java.util.List;

public class PersonQuiz {
    private String image;
    private List<String> answers;
    private String category;
    private String difficulty;
    private int time_limit;

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getTime_limit() {
        return this.time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }

}
