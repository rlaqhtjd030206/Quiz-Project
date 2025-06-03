package concat;

import common.QuizContainer;
import common.StyledButton;
import java.awt.*;
import javax.swing.*;

public class ConcatDifficultyPanel extends JPanel {
    public ConcatDifficultyPanel(QuizContainer container, String quizType, String category) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 상단 제목
        JLabel label = new JLabel("난이도를 선택하세요!", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        // 난이도 버튼
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        String[] levels = { "초급", "중급", "고급" };

        for (String level : levels) {
            JButton btn = new StyledButton(level);
            btn.addActionListener(_ -> {
                // 난이도 한글 → 영어 변환
                String difficulty = switch (level) {
                    case "초급" -> "easy";
                    case "중급" -> "medium";
                    case "고급" -> "hard";
                    default -> "easy";
                };

                // 난이도 선택 후 → 문항 수 선택 패널로 이동 (문제유형은 아직 선택 안함!)
                container.goToCount(quizType, category, difficulty);
            });
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // 뒤로가기 버튼
        JButton backButton = new StyledButton("← 뒤로가기");
        backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        backButton.addActionListener(_ -> {
            container.goToCategory(quizType);
        });

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);
    }
}
