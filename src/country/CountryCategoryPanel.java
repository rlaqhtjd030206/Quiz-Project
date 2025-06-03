package country;

import javax.swing.*;
import java.awt.*;
import common.QuizContainer;
import common.StyledButton;

public class CountryCategoryPanel extends JPanel {
  public CountryCategoryPanel(QuizContainer container, String quizType) {
    setLayout(new BorderLayout(20, 20));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel label = new JLabel("카테고리를 선택하세요!", SwingConstants.CENTER);
    label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
    add(label, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 15, 15));

    String[][] categories = {
        { "국가", "countries" },
        { "수도", "capitals" }
    };

    for (String[] category : categories) {
      String displayName = category[0];
      String fileKey = category[1];

      JButton btn = new StyledButton(displayName);
      btn.addActionListener(_ -> {
        container.goToDifficulty(quizType, fileKey);
      });
      buttonPanel.add(btn);
    }

    add(buttonPanel, BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new BorderLayout());

    JButton backButton = new StyledButton("← 뒤로가기");
    backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
    backButton.addActionListener(_ -> container.goBackTo("menu"));

    JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    backPanel.add(backButton);
    bottomPanel.add(backPanel, BorderLayout.WEST);

    // ✅ 퀴즈 수정 버튼 - 선택된 카테고리로 이동
    JButton editStartButton = new StyledButton("퀴즈 수정");
    editStartButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
    editStartButton.addActionListener(_ -> container.goToEditCategorySelector("국가 퀴즈"));

    JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    editPanel.add(editStartButton);
    bottomPanel.add(editPanel, BorderLayout.EAST);

    add(bottomPanel, BorderLayout.SOUTH);
  }
}
