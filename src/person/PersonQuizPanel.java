
package person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import common.QuizContainer;
import common.StyledButton;

public class PersonQuizPanel extends JPanel {
    private List<PersonQuizResult> resultList = new ArrayList<>();
    private List<PersonQuiz> questions;
    private int currentIndex = 0;
    private int score = 0;
    private int timeRemaining;
    private Timer timer;

    private JLabel imageLabel;
    private JTextField answerField;
    private JLabel timerLabel;

    private QuizContainer container;
    private String difficulty;

    public PersonQuizPanel(QuizContainer container, List<PersonQuiz> questions, String category, String difficulty,
            int count) {
        this.container = container;
        this.questions = questions;
        this.difficulty = difficulty;

        setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        answerField = new JTextField();
        bottomPanel.add(answerField, BorderLayout.CENTER);

        timerLabel = new JLabel("제한시간: ");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(timerLabel, BorderLayout.NORTH);

        StyledButton submitButton = new StyledButton("제출");
        bottomPanel.add(submitButton, BorderLayout.EAST);

        submitButton.addActionListener(_ -> checkAnswer());

        add(bottomPanel, BorderLayout.SOUTH);

        startQuestion();
    }

    private void startQuestion() {
        if (currentIndex >= questions.size()) {
            container.showPersonResult(resultList, score, questions.size());
            return;
        }

        PersonQuiz quiz = questions.get(currentIndex);
        imageLabel.setIcon(new ImageIcon("src/person/images/" + quiz.getImage()));
        answerField.setText("");

        switch (difficulty) {
            case "초급":
                timeRemaining = 20;
                break;
            case "중급":
                timeRemaining = 15;
                break;
            case "고급":
                timeRemaining = 10;
                break;
            default:
                timeRemaining = 15;
        }

        timerLabel.setText("제한시간: " + timeRemaining + "초");
        if (timer != null && timer.isRunning())
            timer.stop();

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("제한시간: " + timeRemaining + "초");
                if (timeRemaining <= 0) {
                    timer.stop();
                    checkAnswer(); // 시간 초과 시 자동 제출
                }
            }
        });
        timer.start();
    }

    private void checkAnswer() {
        if (timer != null && timer.isRunning())
            timer.stop();

        String userAnswer = answerField.getText().trim();
        List<String> correctAnswer = questions.get(currentIndex).getAnswers();
        for (String ans : correctAnswer) {
            if (userAnswer.equalsIgnoreCase(ans)) {
                score++;
                break;
            }
        }
        resultList.add(new PersonQuizResult(
                currentIndex + 1,
                userAnswer,
                correctAnswer));

        currentIndex++;
        startQuestion();
    }
}
