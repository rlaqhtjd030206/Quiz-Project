package concat;

import common.QuizContainer;
import common.StyledButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;

public class ConcatQuizPanel extends JPanel {
    private List<ConcatQuiz> quizList;
    private QuizContainer container;
    private String difficulty;
    private String questionType;

    private int currentIndex = 0;
    private int correctCount = 0;
    private JLabel questionLabel;
    private JLabel timerLabel;
    private JLabel feedbackLabel;
    private JButton nextButton;
    private Timer timer;
    private int timeLeft;

    private JTextField answerField; // 주관식
    private JPanel optionsPanel; // 객관식
    private ButtonGroup optionsGroup;

    public ConcatQuizPanel(List<ConcatQuiz> quizList, QuizContainer container, String difficulty, String questionType) {
        this.quizList = quizList;
        this.container = container;
        this.difficulty = difficulty;
        this.questionType = questionType;

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        add(questionLabel, BorderLayout.NORTH);

        answerField = new JTextField();
        answerField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        answerField.addActionListener(this::handleSubmit);

        optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        add(timerLabel, BorderLayout.EAST);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        nextButton = new StyledButton("다음 문제");
        nextButton.setVisible(false);
        nextButton.addActionListener(_ -> showNextQuestion());

        JButton submitButton = new StyledButton("제출");
        submitButton.addActionListener(e -> {
            try {
                handleSubmit(e);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "퀴즈 진행 중 오류 발생: " + ex.getMessage());
            }
        });

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        if ("주관식".equals(questionType)) {
            centerPanel.add(answerField, BorderLayout.CENTER);
        } else {
            centerPanel.add(optionsPanel, BorderLayout.CENTER);
        }
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(4, 1, 5, 5));
        bottom.add(submitButton);
        bottom.add(feedbackLabel);
        bottom.add(nextButton);
        add(bottom, BorderLayout.SOUTH);

        showNextQuestion();
    }

    private void showNextQuestion() {

        if (timer != null)
            timer.stop();

        if (currentIndex >= quizList.size()) {
            container.goToResult(correctCount, quizList.size(), quizList);
            return;
        }

        ConcatQuiz quiz = quizList.get(currentIndex);
        questionLabel.setText("Q" + (currentIndex + 1) + ": " + quiz.getQuestion());
        feedbackLabel.setText("");
        nextButton.setVisible(false);

        timeLeft = switch (difficulty) {
            case "초급" -> 10;
            case "중급" -> 7;
            case "고급" -> 5;
            default -> 10;
        };
        timerLabel.setText("남은 시간: " + timeLeft + "초");
        timer = new Timer(1000, _ -> {
            timeLeft--;
            timerLabel.setText("남은 시간: " + timeLeft + "초");
            if (timeLeft <= 0) {
                timer.stop();
                autoSubmit();
            }
        });
        timer.start();

        if ("주관식".equals(questionType)) {
            answerField.setText("");
            answerField.setEnabled(true);
            answerField.requestFocus();
        } else {
            showOptions(quiz.getOptions());
        }
    }

    private void autoSubmit() {
        if ("주관식".equals(questionType)) {
            answerField.setText("");
        }
        checkAnswer(null);
    }

    private void handleSubmit(ActionEvent e) {
        if (timer != null)
            timer.stop();

        String userAnswer;
        if ("주관식".equals(questionType)) {
            userAnswer = answerField.getText().trim();
        } else {
            ButtonModel selected = optionsGroup.getSelection();
            if (selected == null) {
                feedbackLabel.setText("❗ 보기 중 하나를 선택하세요.");
                return;
            }
            userAnswer = selected.getActionCommand();
        }
        checkAnswer(userAnswer);
    }

    private void checkAnswer(String userAnswer) {
        ConcatQuiz quiz = quizList.get(currentIndex);
        String correct = quiz.getAnswer();

        if (correct.equals(userAnswer)) {
            feedbackLabel.setText(" 정답입니다!");
            correctCount++;
        } else {
            feedbackLabel.setText("<html><div style='text-align:center;'>❌ 오답!<br>정답: " + correct + "</html>");
        }

        if ("주관식".equals(questionType)) {
            answerField.setEnabled(false);
        } else {
            for (AbstractButton btn : java.util.Collections.list(optionsGroup.getElements())) {
                btn.setEnabled(false);
            }
        }

        nextButton.setVisible(true);
        currentIndex++;
    }

    private void showOptions(List<String> options) {
        optionsPanel.removeAll();
        optionsGroup = new ButtonGroup();

        for (String option : options) {
            JRadioButton radio = new JRadioButton(option);
            radio.setActionCommand(option);
            radio.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
            optionsGroup.add(radio);
            optionsPanel.add(radio);
        }
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }
}
