package country;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import common.QuizContainer;
import common.StyledButton;

public class CountryResultPanel extends JPanel {
  public CountryResultPanel(int total, int correct, List<CountryQuizResult> results, QuizContainer container) {
    setLayout(new BorderLayout(20, 20));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    setBackground(Color.WHITE); // 필요 시 배경색 지정

    // 상단 제목
    JLabel resultLabel = new JLabel("퀴즈 결과", SwingConstants.CENTER);
    resultLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
    add(resultLabel, BorderLayout.NORTH);

    // 점수 요약
    JLabel scoreLabel = new JLabel("총 " + total + "문제 중 " + correct + "문제 정답!", SwingConstants.CENTER);
    scoreLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));

    // 결과 테이블
    String[] columns = { "번호", "내가 쓴 답", "정답" };
    DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

    for (CountryQuizResult result : results) {
      tableModel.addRow(new Object[] {
          result.getNumber(),
          result.getUserAnswer(),
          result.getCorrectAnswer()
      });
    }

    JTable resultTable = new JTable(tableModel);
    resultTable.setEnabled(false); // 수정 불가
    resultTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
    resultTable.setRowHeight(28);
    resultTable.setShowGrid(true);
    resultTable.setGridColor(Color.BLACK);

    resultTable.getTableHeader().setBackground(Color.DARK_GRAY);
    resultTable.getTableHeader().setForeground(Color.WHITE);
    resultTable.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));
    resultTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));

    JScrollPane tableScrollPane = new JScrollPane(resultTable);
    tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    tableScrollPane.setPreferredSize(new Dimension(700, 300));

    // 가운데 패널에 요약 + 표 묶기
    JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
    centerPanel.setOpaque(false);
    centerPanel.add(scoreLabel, BorderLayout.NORTH);
    centerPanel.add(tableScrollPane, BorderLayout.CENTER);
    add(centerPanel, BorderLayout.CENTER);

    // 하단 버튼
    JButton homeButton = new StyledButton("처음으로 돌아가기");
    homeButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
    homeButton.addActionListener(_ -> container.goBackTo("menu"));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setOpaque(false);
    buttonPanel.add(homeButton);
    add(buttonPanel, BorderLayout.SOUTH);
  }
}
