package country;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import common.QuizContainer;
import common.StyledButton;

public class CountryQuizPanel extends JPanel {
  private List<CountryQuiz> quizList;
  private List<CountryQuizResult> resultList = new ArrayList<>();
  private int currentIndex = 0;
  private int correctCount = 0;
  private JLabel imageLabel;
  private JTextField answerField;
  private JLabel feedbackLabel;
  private JLabel timerLabel;
  private JButton nextButton;
  private QuizContainer container;
  private Timer questionTimer;
  private int timeLeft;
  private int timeLimit;

  public CountryQuizPanel(List<CountryQuiz> quizList, QuizContainer container, String difficulty) {
    this.quizList = quizList;
    this.container = container;
    this.timeLimit = switch (difficulty) {
      case "초급" -> 20;
      case "중급" -> 30;
      case "고급" -> 40;
      default -> 20;
    };

    setLayout(new BorderLayout(20, 20));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    imageLabel = new JLabel("", SwingConstants.CENTER);
    imageLabel.setPreferredSize(new Dimension(400, 300));
    add(imageLabel, BorderLayout.NORTH);

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
      CountryQuiz quiz = quizList.get(currentIndex);

      try {
        String fileName = quiz.getCountry();
        String imagePath = "src/country/images/국가/" + getLevelFolder() + "/" + fileName;
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaled = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));
      } catch (Exception e) {
        imageLabel.setText("이미지를 불러올 수 없습니다.");
      }

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
      container.showCountryResult("국가 퀴즈", quizList.size(), correctCount, resultList);
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
    CountryQuiz quiz = quizList.get(currentIndex);
    String userAnswer = answerField.getText().trim();
    Object correctAnswerObj = quiz.getAnswer();
    String correctAnswer = (correctAnswerObj instanceof String)
        ? (String) correctAnswerObj
        : ((List<?>) correctAnswerObj).get(0).toString();

    if (correctAnswer.equalsIgnoreCase(userAnswer)) {
      feedbackLabel.setText("✅ 정답입니다!");
      correctCount++;
    } else {
      feedbackLabel.setText("<html><div style='text-align:center;'>❌ 오답!<br>정답: " + correctAnswer + "</html>");
    }

    resultList.add(new CountryQuizResult(
        currentIndex + 1,
        quiz.getAnswer(),
        userAnswer,
        correctAnswer));

    answerField.setEnabled(false);
    nextButton.setVisible(true);
    currentIndex++;
  }

  private String getLevelFolder() {
    return switch (timeLimit) {
      case 20 -> "하";
      case 30 -> "중";
      case 40 -> "상";
      default -> "하";
    };
  }
}