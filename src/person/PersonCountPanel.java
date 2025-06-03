
package person;

import javax.swing.*;
import java.awt.*;
import common.QuizContainer;
import common.StyledButton;

public class PersonCountPanel extends JPanel {
    private int count = 10;
    private JLabel countLabel;

    public PersonCountPanel(QuizContainer container, String category, String difficulty) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("문항 수를 선택하세요!", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(label, BorderLayout.NORTH);

        countLabel = new JLabel(count + " 문제", SwingConstants.CENTER);
        countLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        add(countLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton minusButton = new StyledButton("-");
        minusButton.addActionListener(_ -> adjustCount(-5));

        JButton plusButton = new StyledButton("+");
        plusButton.addActionListener(_ -> adjustCount(5));

        JButton startButton = new StyledButton("시작하기");
        startButton.addActionListener(_ -> {
            container.startQuiz("인물 퀴즈", category, difficulty, count, "none");
        });

        buttonPanel.add(minusButton);
        buttonPanel.add(plusButton);
        buttonPanel.add(startButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);

        // ← 뒤로가기 버튼
        JButton backButton = new StyledButton("← 뒤로가기");
        backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        backButton.addActionListener(_ -> container.goBackTo("difficulty"));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backButton);
        southPanel.add(backPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void adjustCount(int delta) {
        int newCount = count + delta;
        if (newCount >= 5 && newCount <= 50) {
            count = newCount;
            countLabel.setText(count + " 문제");
        }
    }
}
