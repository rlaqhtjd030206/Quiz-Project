package person;

import javax.swing.*;
import java.awt.*;
import common.QuizContainer;
import common.StyledButton;

public class PersonCategoryPanel extends JPanel {
    public PersonCategoryPanel(QuizContainer container, String quizType) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 제목 생성
        JLabel label = new JLabel("카테고리를 선택하세요!", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 30, 10));

        // 카테고리
        String[] categories = { "배우", "가수", "랜덤" };

        // 카테고리 버튼 생성
        for (String category : categories) {
            JButton btn = new StyledButton(category);
            btn.addActionListener(_ -> {
                container.goToDifficulty(quizType, category);
            });
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // 뒤로가기 버튼
        JButton backButton = new StyledButton("← 뒤로가기");
        backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        backButton.addActionListener(_ -> container.goBackTo("menu"));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backButton);
        bottomPanel.add(backPanel, BorderLayout.WEST);

        // 퀴즈 수정 버튼
        JButton editStartButton = new StyledButton("퀴즈 수정");
        editStartButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        editStartButton.addActionListener(_ -> container.goToEditor("인물 퀴즈", null));

        JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        editPanel.add(editStartButton);
        bottomPanel.add(editPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
