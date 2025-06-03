package initial;

import javax.swing.*;
import java.awt.*;
import common.QuizContainer;
import common.StyledButton;

public class InitialQuizEditCategoryPanel extends JPanel {
  public InitialQuizEditCategoryPanel(QuizContainer container) {
    setLayout(new BorderLayout(20, 20));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel title = new JLabel("수정할 카테고리를 선택하세요", SwingConstants.CENTER);
    title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
    add(title, BorderLayout.NORTH);

    String[][] categories = {
        { "드라마", "dramas" },
        { "영화", "movies" },
        { "스포츠", "sports" }
    };

    JPanel buttons = new JPanel(new GridLayout(3, 1, 10, 30));
    for (String[] cat : categories) {
      String display = cat[0];
      String key = cat[1];

      JButton btn = new StyledButton(display);
      btn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
      btn.addActionListener(_ -> {
        container.goToEditor("초성 퀴즈", key); // category 전달
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
