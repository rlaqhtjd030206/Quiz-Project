package concat;

import common.QuizContainer;
import common.StyledButton;
import java.awt.*;
import javax.swing.*;

public class ConcatTypePanel extends JPanel {
    public ConcatTypePanel(QuizContainer container, String quizType, String category, String difficulty) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("문제 유형을 선택하세요!", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 20));

        JButton subjectiveBtn = createStyledButton("주관식");
        subjectiveBtn.addActionListener(_ -> {
            int count = container.getCurrentQuizCount(); // 사용자가 설정한 문항 수 반영
            System.out.println(">>> [주관식] 클릭됨, quizType = " + quizType + ", category = " + category + ", difficulty = "
                    + difficulty + ", count = " + count);
            container.startQuiz(quizType, category, difficulty, count, "주관식");
        });

        JButton objectiveBtn = createStyledButton("객관식");
        objectiveBtn.addActionListener(_ -> {
            int count = container.getCurrentQuizCount(); // 문항 수 반영
            System.out.println(">>> [객관식] 클릭됨, quizType = " + quizType + ", category = " + category + ", difficulty = "
                    + difficulty + ", count = " + count);
            container.startQuiz(quizType, category, difficulty, count, "객관식");
        });

        buttonPanel.add(subjectiveBtn);
        buttonPanel.add(objectiveBtn);
        add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new StyledButton("← 뒤로가기");
        backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        backButton.addActionListener(_ -> container.goBackTo("difficulty"));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backButton);
        add(backPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton btn = new StyledButton(text);
        btn.setOpaque(false); // 둥근 버튼 효과 살리기 위해 false
        btn.setContentAreaFilled(false); // 배경 채우기 제거 (paintComponent에서 직접 그림)
        btn.setBorderPainted(false); // 테두리 제거
        btn.setFocusPainted(false); // 포커스 테두리 제거
        return btn;
    }

}
