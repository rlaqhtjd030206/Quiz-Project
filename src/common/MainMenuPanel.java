package common;

import javax.swing.*;
import java.awt.*;

// 메인 화면 패널 설정
public class MainMenuPanel extends JPanel {
  public MainMenuPanel(QuizContainer container) {
    setLayout(new BorderLayout(20, 20));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // 제목
    JLabel titleLabel = new JLabel("QUIZ ON!");
    titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 36));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(titleLabel, BorderLayout.NORTH);

    // 퀴즈 선택 패널
    JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 15, 15));
    String[] quizTypes = { "인물 퀴즈", "초성 퀴즈", "국가 퀴즈", "이어말하기" };
    for (String type : quizTypes) {
      JButton btn = new StyledButton(type);
      btn.addActionListener(_ -> container.goToCategory(type));
      buttonPanel.add(btn);
    }

    add(buttonPanel, BorderLayout.CENTER);
  }
}
