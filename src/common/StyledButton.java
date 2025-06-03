package common;

import javax.swing.*;
import java.awt.*;

// 버튼 디자인
public class StyledButton extends JButton {
  public StyledButton(String text) {
    super(text);
    setFont(new Font("맑은 고딕", Font.BOLD, 16));
    setForeground(Color.WHITE);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setOpaque(false);
    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    setRolloverEnabled(true);
  }

  @Override
  protected void paintComponent(Graphics g) {
    // 마우스 호버 시 회색
    if (getModel().isRollover()) {
      g.setColor(Color.GRAY);
    } else { // 기본 검정색
      g.setColor(Color.BLACK);
    }
    g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
    super.paintComponent(g);
  }

  @Override
  protected void paintBorder(Graphics g) {
    // 테두리 없음
  }
}
