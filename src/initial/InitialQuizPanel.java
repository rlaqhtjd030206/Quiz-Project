package initial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import common.QuizContainer;
import common.StyledButton;

public class InitialQuizPanel extends JPanel {
  private List<InitialQuiz> quizList;
  private List<InitialQuizResult> resultList = new ArrayList<>();
  private int currentIndex = 0;
  private int correctCount = 0;
  private JLabel questionLabel;
  private JTextField answerField;
  private JLabel feedbackLabel;
  private JLabel timerLabel;
  private JButton nextButton;
  private QuizContainer container;
  private Timer questionTimer;
  private int timeLeft;
  private int timeLimit;

  public InitialQuizPanel(List<InitialQuiz> quizList, QuizContainer container, String difficulty) {
    this.quizList = quizList;
    this.container = container;
    this.timeLimit = switch (difficulty) {
      case "초급" -> 10;
      case "중급" -> 7;
      case "고급" -> 5;
      default -> 10;
    };

    setLayout(new BorderLayout(20, 20));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    questionLabel = new JLabel("", SwingConstants.CENTER);
    questionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
    add(questionLabel, BorderLayout.NORTH);

    answerField = new JTextField();
    answerField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
    answerField.addActionListener(this::handleSubmit);
    add(answerField, BorderLayout.CENTER);

    timerLabel = new JLabel("", SwingConstants.CENTER);
    timerLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
    add(timerLabel, BorderLayout.EAST);

    feedbackLabel = new JLabel("", SwingConstants.CENTER);
    feedbackLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

    nextButton = new StyledButton("다음 문제");
    nextButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
    nextButton.setVisible(false);
    nextButton.addActionListener(_ -> showQuestion());

    JButton submitButton = new StyledButton("제출");
    submitButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
    submitButton.addActionListener(this::handleSubmit);

    JPanel southPanel = new JPanel(new GridLayout(4, 1, 10, 10));
    southPanel.add(submitButton);
    southPanel.add(feedbackLabel);
    southPanel.add(nextButton);
    add(southPanel, BorderLayout.SOUTH);

    showQuestion();
  }

  private void showQuestion() {
    if (questionTimer != null && questionTimer.isRunning()) {
      questionTimer.stop();
    }

    if (currentIndex < quizList.size()) {
      InitialQuiz quiz = quizList.get(currentIndex);
      questionLabel.setText("Q: " + quiz.getQuestion());
      answerField.setText("");
      answerField.setEnabled(true);
      feedbackLabel.setText("");
      nextButton.setVisible(false);

      timeLeft = timeLimit;
      timerLabel.setText("남은 시간: " + timeLeft + "초");

      questionTimer = new Timer(1000, _ -> {
        timeLeft--;
        timerLabel.setText("남은 시간: " + timeLeft + "초");
        if (timeLeft <= 0) {
          questionTimer.stop();
          checkAnswerAndShowFeedback();
        }
      });
      questionTimer.start();
    } else {
      container.showInitialResult("초성 퀴즈", quizList.size(), correctCount, resultList);
    }
  }

  private void handleSubmit(ActionEvent e) {
    if (answerField.getText().trim().isEmpty()) {
      feedbackLabel.setText("❗ 답을 입력해주세요.");
      return;
    }
    if (questionTimer != null && questionTimer.isRunning()) {
      questionTimer.stop();
    }
    checkAnswerAndShowFeedback();
  }

  private void checkAnswerAndShowFeedback() {
    InitialQuiz quiz = quizList.get(currentIndex);
    String userAnswer = answerField.getText().trim();
    String correctAnswer = quiz.getAnswer();

    if (correctAnswer.equals(userAnswer)) {
      feedbackLabel.setText("✅ 정답입니다!");
      correctCount++;
    } else {
      feedbackLabel.setText("<html><div style='text-align:center;'>❌ 오답!<br>정답: " + correctAnswer + "</html>");
    }

    // ✅ 결과 리스트에 추가
    resultList.add(new InitialQuizResult(
        currentIndex + 1,
        quiz.getQuestion(),
        userAnswer,
        correctAnswer));

    answerField.setEnabled(false);
    nextButton.setVisible(true);
    currentIndex++;
  }

}