package country;

import javax.swing.*;
import java.awt.*;
import common.QuizContainer;
import common.StyledButton;

public class CountryQuizEditCategoryPanel extends JPanel {
  public CountryQuizEditCategoryPanel(QuizContainer container) {
    setLayout(new BorderLayout(20, 20));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel title = new JLabel("수정할 카테고리를 선택하세요", SwingConstants.CENTER);
    title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
    add(title, BorderLayout.NORTH);

    String[][] categories = {
        { "국가", "countries" },
        { "수도", "capitals" }
    };

    JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 30));
    for (String[] cat : categories) {
      String display = cat[0];
      String key = cat[1];

      JButton btn = new StyledButton(display);
      btn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
      btn.addActionListener(_ -> {
        container.goToEditDifficultySelector(key); // 🔄 난이도 선택으로 이동
      });
      buttons.add(btn);
    }

    add(buttons, BorderLayout.CENTER);

    JButton backButton = new StyledButton("← 뒤로가기");
    backButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
    backButton.addActionListener(_ -> container.goBackTo("menu"));

    JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    backPanel.add(backButton);
    add(backPanel, BorderLayout.SOUTH);
  }
}
