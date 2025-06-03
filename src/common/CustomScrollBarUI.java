package common;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

// 스크롤바 디자인
public class CustomScrollBarUI extends BasicScrollBarUI {
  // 검정과 하얀색 디자인 사용
  @Override
  protected void configureScrollBarColors() {
    this.thumbColor = Color.BLACK;
    this.trackColor = Color.WHITE;
  }

  // 위아래 버튼 제거
  @Override
  protected JButton createDecreaseButton(int orientation) {
    return createZeroButton();
  }

  @Override
  protected JButton createIncreaseButton(int orientation) {
    return createZeroButton();
  }

  private JButton createZeroButton() {
    JButton button = new JButton();
    button.setPreferredSize(new Dimension(0, 0));
    button.setMinimumSize(new Dimension(0, 0));
    button.setMaximumSize(new Dimension(0, 0));
    return button;
  }
}
