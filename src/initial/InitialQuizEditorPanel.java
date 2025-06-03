package initial;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import common.CustomScrollBarUI;
import common.QuizContainer;
import common.StyledButton;

public class InitialQuizEditorPanel extends JPanel {
  private DefaultTableModel tableModel;
  private JTable table;
  private String category;
  private final String[] columns = { "문제", "정답" };

  public InitialQuizEditorPanel(QuizContainer container, String category) {
    this.category = category;

    setLayout(new BorderLayout(15, 15));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // 문제 테이블
    tableModel = new DefaultTableModel(columns, 0);
    table = new JTable(tableModel);
    table.setShowGrid(true); // 셀 테두리 보이기
    table.setGridColor(Color.BLACK); // 셀 테두리 색상 설정
    table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    table.getTableHeader().setBackground(Color.DARK_GRAY);
    table.getTableHeader().setForeground(Color.WHITE);
    table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14)); // 선택 사항

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
    add(scrollPane, BorderLayout.CENTER);

    loadData(); // JSON 불러오기

    // 입력 폼
    JPanel inputPanel = new JPanel(new FlowLayout());
    JTextField questionField = new JTextField(20);
    questionField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    questionField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
    JTextField answerField = new JTextField(20);
    answerField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    answerField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
    JButton addButton = new StyledButton("문제 추가");

    addButton.addActionListener(_ -> {
      String q = questionField.getText().trim();
      String a = answerField.getText().trim();
      if (!q.isEmpty() && !a.isEmpty()) {
        tableModel.addRow(new String[] { q, a });
        questionField.setText("");
        answerField.setText("");
      }
    });

    inputPanel.add(new JLabel("문제:"));
    inputPanel.add(questionField);
    inputPanel.add(new JLabel("정답:"));
    inputPanel.add(answerField);
    inputPanel.add(addButton);
    add(inputPanel, BorderLayout.NORTH);

    // 버튼들
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton deleteButton = new StyledButton("선택 삭제");
    JButton saveButton = new StyledButton("저장");
    JButton backButton = new StyledButton("← 뒤로가기");

    deleteButton.addActionListener(_ -> {
      int row = table.getSelectedRow();
      if (row != -1)
        tableModel.removeRow(row);
    });

    saveButton.addActionListener(_ -> saveData());

    backButton.addActionListener(_ -> container.goBackTo("category"));

    bottomPanel.add(deleteButton);
    bottomPanel.add(saveButton);
    bottomPanel.add(backButton);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  private void loadData() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      File file = new File("src/initial/data/" + category + ".json");
      InitialQuiz[] quizzes = mapper.readValue(file, InitialQuiz[].class);
      for (InitialQuiz quiz : quizzes) {
        tableModel.addRow(new String[] { quiz.getQuestion(), quiz.getAnswer() });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void saveData() {
    try {
      List<InitialQuiz> updatedList = new ArrayList<>();
      for (int i = 0; i < tableModel.getRowCount(); i++) {
        String q = (String) tableModel.getValueAt(i, 0);
        String a = (String) tableModel.getValueAt(i, 1);
        InitialQuiz quiz = new InitialQuiz();
        quiz.setQuestion(q);
        quiz.setAnswer(a);
        quiz.setNum(String.valueOf(i)); // 순번 재지정
        updatedList.add(quiz);
      }

      ObjectMapper mapper = new ObjectMapper();
      File file = new File("src/initial/data/" + category + ".json");
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, updatedList);
      JOptionPane.showMessageDialog(this, "저장되었습니다.");
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "저장 중 오류 발생!");
    }
  }
}
