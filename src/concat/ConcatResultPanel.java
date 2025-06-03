package concat;

import common.QuizContainer;
import common.StyledButton;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ConcatResultPanel extends JPanel {

    // ✅ 오버로딩 생성자 추가: 기존 코드와 충돌 없게 처리
    public ConcatResultPanel(int correctCount, int totalCount, QuizContainer container) {
        this(correctCount, totalCount, new ArrayList<>(), container);
    }

    public ConcatResultPanel(int correctCount, int totalCount, List<ConcatQuiz> quizList, QuizContainer container) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        JLabel resultLabel = new JLabel("퀴즈 결과", SwingConstants.CENTER);
        resultLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        add(resultLabel, BorderLayout.NORTH);

        JLabel scoreLabel = new JLabel("총 " + totalCount + "문제 중 " + correctCount + "문제 정답!", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));

        String[] columns = { "번호", "문제", "정답" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (ConcatQuiz quiz : quizList) {
            tableModel.addRow(new Object[] {
                quiz.getNum(),
                quiz.getQuestion(),
                quiz.getAnswer()
            });
        }

        JTable resultTable = new JTable(tableModel);
        resultTable.setEnabled(false);
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

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(scoreLabel, BorderLayout.NORTH);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JButton homeButton = new StyledButton("처음으로 돌아가기");
        homeButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        homeButton.addActionListener(_ -> container.goBackTo("menu"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(homeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
