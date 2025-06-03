package concat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import common.QuizContainer;
import common.StyledButton;

public class ConcatQuizEditorPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> categoryBox;
    private JComboBox<String> difficultyBox;
    private String categoryKey;

    public ConcatQuizEditorPanel(QuizContainer container) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("이어말하기 퀴즈 편집기", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // 카테고리 및 난이도 선택
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryBox = new JComboBox<>(new String[] { "idioms", "proverbs", "quotes" });
        categoryBox.setSelectedItem(categoryKey);
        controlPanel.add(new JLabel("카테고리:"));
        controlPanel.add(categoryBox);

        difficultyBox = new JComboBox<>(new String[] { "easy", "medium", "hard" });
        controlPanel.add(new JLabel("난이도:"));
        controlPanel.add(difficultyBox);

        JButton loadBtn = new StyledButton("불러오기");
        loadBtn.addActionListener(_ -> loadData());
        controlPanel.add(loadBtn);

        add(controlPanel, BorderLayout.NORTH);

        // 문제 테이블
        String[] columns = { "문제", "정답", "보기1", "보기2", "보기3", "보기4" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 하단 버튼
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton addBtn = new StyledButton("문제 추가");
        addBtn.addActionListener(_ -> {
            tableModel.addRow(new String[] { "", "", "", "", "", "" });
        });

        JButton deleteBtn = new StyledButton("선택 삭제");
        deleteBtn.addActionListener(_ -> {
            int row = table.getSelectedRow();
            if (row != -1)
                tableModel.removeRow(row);
        });

        JButton saveBtn = new StyledButton("저장");
        saveBtn.addActionListener(_ -> saveData());

        JButton backBtn = new StyledButton("← 뒤로가기");
        backBtn.addActionListener(_ -> container.goBackTo("category"));

        bottomPanel.add(addBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(saveBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            String category = (String) categoryBox.getSelectedItem();
            String difficulty = (String) difficultyBox.getSelectedItem();
            String filePath = "src/concat/data/" + category + "_" + difficulty + ".json";

            ObjectMapper mapper = new ObjectMapper();
            File file = new File(filePath);
            List<ConcatQuiz> quizzes = mapper.readValue(file, new TypeReference<>() {
            });
            tableModel.setRowCount(0); // 초기화

            for (ConcatQuiz quiz : quizzes) {
                List<String> options = quiz.getOptions();
                while (options.size() < 4)
                    options.add(""); // 빈 보기 채우기
                tableModel.addRow(new Object[] {
                        quiz.getQuestion(),
                        quiz.getAnswer(),
                        options.get(0),
                        options.get(1),
                        options.get(2),
                        options.get(3)
                });
            }

            JOptionPane.showMessageDialog(this, "불러오기 완료!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "불러오기 실패!");
        }
    }

    private void saveData() {
        try {
            String category = (String) categoryBox.getSelectedItem();
            String difficulty = (String) difficultyBox.getSelectedItem();
            String filePath = "src/concat/data/" + category + "_" + difficulty + ".json";

            List<ConcatQuiz> newList = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String question = (String) tableModel.getValueAt(i, 0);
                String answer = (String) tableModel.getValueAt(i, 1);
                List<String> options = new ArrayList<>();
                for (int j = 2; j < 6; j++) {
                    String opt = (String) tableModel.getValueAt(i, j);
                    if (opt != null && !opt.isEmpty())
                        options.add(opt);
                }

                ConcatQuiz quiz = new ConcatQuiz();
                quiz.setNum(String.valueOf(i + 1));
                quiz.setQuestion(question);
                quiz.setAnswer(answer);
                quiz.setOptions(options);
                newList.add(quiz);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), newList);
            JOptionPane.showMessageDialog(this, "저장 완료!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "저장 중 오류 발생!");
        }
    }
}
