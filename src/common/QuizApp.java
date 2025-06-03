package common;

import javax.swing.*;
import java.awt.Color;

// 퀴즈 화면 설정
public class QuizApp {
  public static void main(String[] args) {
    UIManager.put("Panel.background", Color.WHITE);
    UIManager.put("Table.background", Color.WHITE);
    UIManager.put("ScrollPane.background", Color.WHITE);
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("QuizOn!");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 600);
      frame.setContentPane(new QuizContainer());
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    });
  }
}
