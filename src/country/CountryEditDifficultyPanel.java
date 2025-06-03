// CountryEditDifficultyPanel.java
package country;

import javax.swing.*;
import java.awt.*;
import common.QuizContainer;
import common.StyledButton;

public class CountryEditDifficultyPanel extends JPanel {
  public CountryEditDifficultyPanel(QuizContainer container, String category) {
    setLayout(new BorderLayout(20, 20));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel label = new JLabel("수정할 난이도를 선택하세요!", SwingConstants.CENTER);
    label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
    add(label, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 15));
    String[] levels = { "하", "중", "상" };

    for (String level : levels) {
      JButton btn = new StyledButton(level);
      btn.addActionListener(_ -> {
        container.goToCountryEditor(category, level); // 수정 전용 메서드
      });
      buttonPanel.add(btn);
    }

    add(buttonPanel, BorderLayout.CENTER);

    JButton backButton = new StyledButton("← 뒤로가기");
    backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
    backButton.addActionListener(_ -> container.goBackTo("edit-country-category"));

    JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    backPanel.add(backButton);
    add(backPanel, BorderLayout.SOUTH);
  }
}
