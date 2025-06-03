package country;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.CustomScrollBarUI;
import common.QuizContainer;
import common.StyledButton;

public class CountryQuizEditorPanel extends JPanel {
  private final String category;
  private final String difficulty;
  private final String difficultyKey;
  private final DefaultTableModel tableModel;
  private final JTable table;
  private final String[] countryColumns = { "문제", "정답" };
  private final String[] capitalColumns = { "문제", "정답", "국기", "랜드마크" };
  private final String filePath;

  public CountryQuizEditorPanel(QuizContainer container, String category, String difficulty) {
    this.category = category;
    this.difficulty = difficulty;
    this.difficultyKey = switch (difficulty) {
      case "하" -> "easy";
      case "중" -> "normal";
      case "상" -> "hard";
      default -> "easy";
    };
    this.filePath = String.format("src/country/data/%s_%s.json", category.toLowerCase(), difficultyKey);

    setLayout(new BorderLayout(15, 15));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    String[] columns = category.equals("capitals") ? capitalColumns : countryColumns;

    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public Class<?> getColumnClass(int column) {
        if (category.equals("capitals") && (column == 2 || column == 3))
          return ImageIcon.class;
        if (!category.equals("capitals") && column == 0)
          return ImageIcon.class;
        return String.class;
      }

      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    table = new JTable(tableModel);
    table.setShowGrid(true);
    table.setGridColor(Color.BLACK);
    table.setRowHeight(60);
    table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    table.getTableHeader().setBackground(Color.DARK_GRAY);
    table.getTableHeader().setForeground(Color.WHITE);
    table.getTableHeader().setFont(new Font("\uB9C8\uB871 \uACE0\uB515", Font.BOLD, 14));

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));

    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.setPreferredSize(new Dimension(800, 200));
    tablePanel.add(scrollPane, BorderLayout.CENTER);
    add(tablePanel, BorderLayout.CENTER);

    loadData();

    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    JTextField questionField = new JTextField(4);
    JTextField answerField = new JTextField(4);

    final String[] flagPath = { null };
    final String[] landmarkPath = { null };

    JButton flagButton = new JButton("국기 이미지 선택");
    JLabel flagPathLabel = new JLabel("X");
    JButton landmarkButton = new JButton("랜드마크 이미지 선택");
    JLabel landmarkPathLabel = new JLabel("X");

    flagButton.addActionListener(_ -> {
      JFileChooser chooser = new JFileChooser();
      chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("이미지 파일", "png", "jpg", "jpeg"));
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        File selected = chooser.getSelectedFile();
        flagPath[0] = copyImageToQuizFolder(selected);
        flagPathLabel.setText("O");
      }
    });

    landmarkButton.addActionListener(_ -> {
      JFileChooser chooser = new JFileChooser();
      chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("이미지 파일", "png", "jpg", "jpeg"));
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        File selected = chooser.getSelectedFile();
        landmarkPath[0] = copyImageToQuizFolder(selected);
        landmarkPathLabel.setText("O");
      }
    });

    JButton addButton = new StyledButton("문제 추가");

    addButton.addActionListener(_ -> {
      String q = questionField.getText().trim();
      String a = answerField.getText().trim();

      if (!a.isEmpty()) {
        if (category.equals("capitals")) {
          if (!q.isEmpty() && flagPath[0] != null && landmarkPath[0] != null) {
            ImageIcon flagIcon = new ImageIcon(getImagePath(flagPath[0]));
            ImageIcon landmarkIcon = new ImageIcon(getImagePath(landmarkPath[0]));
            flagIcon.setDescription(flagPath[0]);
            landmarkIcon.setDescription(landmarkPath[0]);
            tableModel.addRow(new Object[] { q, a, flagIcon, landmarkIcon });
          }
        } else {
          if (flagPath[0] != null) {
            ImageIcon flagIcon = new ImageIcon(getImagePath(flagPath[0]));
            flagIcon.setDescription(flagPath[0]);
            tableModel.addRow(new Object[] { flagIcon, a });
          }
        }
        questionField.setText("");
        answerField.setText("");
        flagPathLabel.setText("X");
        landmarkPathLabel.setText("X");
        flagPath[0] = null;
        landmarkPath[0] = null;
      }
    });

    if (category.equals("capitals")) {
      inputPanel.add(new JLabel("국가:"));
      inputPanel.add(questionField);
      inputPanel.add(new JLabel("수도:"));
      inputPanel.add(answerField);
      inputPanel.add(flagButton);
      inputPanel.add(flagPathLabel);
      inputPanel.add(landmarkButton);
      inputPanel.add(landmarkPathLabel);
    } else {
      inputPanel.add(new JLabel("정답:"));
      inputPanel.add(answerField);
      inputPanel.add(flagButton);
      inputPanel.add(flagPathLabel);
    }

    inputPanel.add(addButton);
    add(inputPanel, BorderLayout.NORTH);

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton deleteButton = new StyledButton("선택 삭제");
    JButton saveButton = new StyledButton("저장");
    JButton backButton = new StyledButton("← 뒤로가기");

    deleteButton.addActionListener(_ -> {
      int row = table.getSelectedRow();
      if (row != -1) {
        tableModel.removeRow(row);
      }
    });

    saveButton.addActionListener(_ -> saveData());
    backButton.addActionListener(_ -> container.goBackTo("edit-difficulty"));

    bottomPanel.add(deleteButton);
    bottomPanel.add(saveButton);
    bottomPanel.add(backButton);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  private String copyImageToQuizFolder(File selectedFile) {
    try {
      String korCategory = category.equals("capitals") ? "수도" : "국가";
      String korLevel = switch (difficulty) {
        case "하" -> "하";
        case "중" -> "중";
        case "상" -> "상";
        default -> "하";
      };
      String fileName = selectedFile.getName();
      File targetDir = new File("src/country/images/" + korCategory + "/" + korLevel + "/");
      if (!targetDir.exists())
        targetDir.mkdirs();
      File targetFile = new File(targetDir, fileName);
      Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
      return fileName;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private String getImagePath(String relativePath) {
    String korCategory = category.equals("capitals") ? "수도" : "국가";
    String korLevel = switch (difficulty) {
      case "하" -> "하";
      case "중" -> "중";
      case "상" -> "상";
      default -> "하";
    };
    return "src/country/images/" + korCategory + "/" + korLevel + "/" + relativePath;
  }

  private void loadData() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      File file = new File(filePath);
      if (!file.exists())
        return;

      CountryQuiz[] quizzes = mapper.readValue(file, CountryQuiz[].class);

      for (CountryQuiz quiz : quizzes) {
        String answerStr = quiz.getAnswer() instanceof List<?>
            ? String.join(", ", ((List<?>) quiz.getAnswer()).stream().map(Object::toString).toList())
            : quiz.getAnswer().toString();

        if (category.equals("capitals")) {
          String flagPath = getImagePath(quiz.getCountry());
          String landmarkPath = getImagePath(quiz.getCapital().toString());
          ImageIcon flagIcon = new ImageIcon(flagPath);
          ImageIcon landmarkIcon = new ImageIcon(landmarkPath);
          flagIcon.setDescription(quiz.getCountry());
          landmarkIcon.setDescription(quiz.getCapital().toString());
          tableModel.addRow(new Object[] { quiz.getQuestion(), answerStr, flagIcon, landmarkIcon });
        } else {
          String path = getImagePath(quiz.getCountry());
          ImageIcon icon = new ImageIcon(path);
          icon.setDescription(quiz.getCountry());
          tableModel.addRow(new Object[] { icon, answerStr });
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void saveData() {
    try {
      Set<String> currentImages = new HashSet<>();
      List<CountryQuiz> updatedList = new ArrayList<>();
      for (int i = 0; i < tableModel.getRowCount(); i++) {
        CountryQuiz quiz = new CountryQuiz();
        if (category.equals("capitals")) {
          String question = tableModel.getValueAt(i, 0).toString();
          String answer = tableModel.getValueAt(i, 1).toString();
          ImageIcon flagIcon = (ImageIcon) tableModel.getValueAt(i, 2);
          ImageIcon landmarkIcon = (ImageIcon) tableModel.getValueAt(i, 3);
          String flagDesc = flagIcon.getDescription();
          String landmarkDesc = landmarkIcon.getDescription();
          quiz.setQuestion(question);
          quiz.setAnswer(answer);
          quiz.setCountry(flagDesc);
          quiz.setCapital(landmarkDesc);
          currentImages.add(flagDesc);
          currentImages.add(landmarkDesc);
        } else {
          String answer = tableModel.getValueAt(i, 1).toString();
          ImageIcon icon = (ImageIcon) tableModel.getValueAt(i, 0);
          String desc = icon.getDescription();
          quiz.setQuestion("");
          quiz.setAnswer(answer);
          quiz.setCountry(desc);
          currentImages.add(desc);
        }
        quiz.setNum(String.valueOf(i));
        updatedList.add(quiz);
      }

      // 삭제된 이미지 정리
      String korCategory = category.equals("capitals") ? "수도" : "국가";
      String korLevel = switch (difficulty) {
        case "하" -> "하";
        case "중" -> "중";
        case "상" -> "상";
        default -> "하";
      };
      File imageDir = new File("src/country/images/" + korCategory + "/" + korLevel + "/");
      if (imageDir.exists()) {
        for (File img : imageDir.listFiles()) {
          if (!currentImages.contains(img.getName())) {
            img.delete();
          }
        }
      }

      ObjectMapper mapper = new ObjectMapper();
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), updatedList);
      JOptionPane.showMessageDialog(this, "저장되었습니다.");
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "저장 중 오류 발생!");
    }
  }
}
