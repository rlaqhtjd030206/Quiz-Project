package initial;

import javax.swing.*;
import java.awt.*;

import common.QuizContainer;
import common.StyledButton;

public class InitialDifficultyPanel extends JPanel {
    public InitialDifficultyPanel(QuizContainer container, String quizType, String category) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("난이도를 선택하세요!", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        String[] levels = { "초급", "중급", "고급" };

        for (String level : levels) {
            JButton btn = new StyledButton(level);
            btn.addActionListener(_ -> {
                container.goToCount(quizType, category, level); // 다음 단계로 전환
            });
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // ← 뒤로가기 버튼
        JButton backButton = new StyledButton("← 뒤로가기");
        backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        backButton.addActionListener(_ -> container.goBackTo("category"));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);
    }
}
