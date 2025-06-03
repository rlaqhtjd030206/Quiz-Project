package concat;

import common.QuizContainer;
import common.StyledButton;
import java.awt.*;
import javax.swing.*;

public class ConcatCategoryPanel extends JPanel {
    public ConcatCategoryPanel(QuizContainer container, String quizType) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 상단 안내 문구
        JLabel label = new JLabel("카테고리를 선택하세요!", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(label, BorderLayout.NORTH);

        // 카테고리 선택 버튼
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        String[][] categories = {
                { "속담", "proverbs" },
                { "사자성어", "idioms" },
                { "명언 / 명대사", "quotes" } // ✔ 축약하여 UI 안정
        };

        for (String[] category : categories) {
            String display = category[0];
            String value = category[1];
            JButton btn = new StyledButton(display);
            btn.addActionListener(_ -> {
                // 난이도 선택 화면으로 이동
                container.goToDifficultySelector(quizType, value);
            });
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // 하단 패널: 왼쪽 = 뒤로가기 / 오른쪽 = 수정하기
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton backButton = new StyledButton("← 뒤로가기");
        backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        backButton.addActionListener(_ -> container.goBackTo("menu"));

        JButton editButton = new StyledButton("수정하기");
        editButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        editButton.addActionListener(_ -> container.goToEditor("이어말하기", null));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        leftPanel.add(backButton);
        rightPanel.add(editButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
