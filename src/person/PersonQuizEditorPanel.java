package person;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import common.CustomScrollBarUI;
import common.QuizContainer;
import common.StyledButton;

public class PersonQuizEditorPanel extends JPanel {
  private DefaultTableModel tableModel;
  private JTable table;
  private final String[] columns = { "정답", "보기" };
  private List<PersonQuiz> quizList = new ArrayList<>();

  public PersonQuizEditorPanel(QuizContainer container) {
    setLayout(new BorderLayout(15, 15));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // 입력 영역 (위쪽)
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel inputLine1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel inputLine2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JTextField answerField = new JTextField(40);
    answerField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    answerField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

    JButton imageButton = new JButton("이미지 선택");
    JTextField imagePathField = new JTextField(20);
    imagePathField.setEditable(false);

    imageButton.addActionListener(_ -> {
      JFileChooser chooser = new JFileChooser();
      int result = chooser.showOpenDialog(this);
      if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = chooser.getSelectedFile();
        try {
          File destDir = new File("src/person/images");
          destDir.mkdirs();
          String ext = selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.') + 1).toLowerCase();
          File destFile = new File(destDir, selectedFile.getName());

          // ✅ BufferedImage로 로드
          BufferedImage original = ImageIO.read(selectedFile);

          // ✅ 리사이즈 수행
          BufferedImage resized = new BufferedImage(512, 512,
              ext.equals("png") ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
          Graphics2D g2d = resized.createGraphics();
          g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
          g2d.drawImage(original, 0, 0, 512, 512, null);
          g2d.dispose();

          // ✅ 저장
          ImageIO.write(resized, ext, destFile);

          imagePathField.setText(selectedFile.getName());
        } catch (Exception ex) {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(this, "이미지 복사 또는 리사이즈 실패");
        }
      }
    });

    JComboBox<String> categoryBox = new JComboBox<>(new String[] { "배우", "가수" });
    JComboBox<String> difficultyBox = new JComboBox<>(new String[] { "초급", "중급", "고급" });
    JButton addButton = new StyledButton("문제 추가");

    addButton.addActionListener(_ -> {
      String answerRaw = answerField.getText().trim();
      String imageFile = imagePathField.getText().trim();
      if (!answerRaw.isEmpty() && !imageFile.isEmpty()) {
        List<String> answers = new ArrayList<>();
        for (String s : answerRaw.split(",")) {
          if (!s.trim().isEmpty())
            answers.add(s.trim());
        }

        PersonQuiz quiz = new PersonQuiz();
        quiz.setImage(imageFile);
        quiz.setAnswers(answers);
        quiz.setCategory((String) categoryBox.getSelectedItem());
        quiz.setDifficulty((String) difficultyBox.getSelectedItem());
        int timeLimit = switch (quiz.getDifficulty()) {
          case "초급" -> 20;
          case "중급" -> 15;
          case "고급" -> 10;
          default -> 15;
        };
        quiz.setTime_limit(timeLimit);

        quizList.add(quiz);
        tableModel.addRow(new Object[] { String.join(", ", answers), "보기" });
        answerField.setText("");
        imagePathField.setText("");
      }
    });

    inputLine1.add(new JLabel("정답 (,로 구분):"));
    inputLine1.add(answerField);
    inputLine1.add(imageButton);
    inputLine1.add(imagePathField);

    inputLine2.add(new JLabel("카테고리:"));
    inputLine2.add(categoryBox);
    inputLine2.add(new JLabel("난이도:"));
    inputLine2.add(difficultyBox);
    inputLine2.add(addButton);

    topPanel.add(inputLine1, BorderLayout.NORTH);
    topPanel.add(inputLine2, BorderLayout.SOUTH);
    add(topPanel, BorderLayout.NORTH);

    // 테이블 설정
    tableModel = new DefaultTableModel(columns, 0);
    table = new JTable(tableModel);
    table.setShowGrid(true);
    table.setGridColor(Color.BLACK);
    table.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.BLACK));
    table.getTableHeader().setBackground(Color.DARK_GRAY);
    table.getTableHeader().setForeground(Color.WHITE);
    table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 14));

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
    scrollPane.setPreferredSize(new Dimension(800, 400));
    add(scrollPane, BorderLayout.CENTER);

    loadData();

    table.getColumn("보기").setCellRenderer(new ButtonRenderer());
    table.getColumn("보기").setCellEditor(new ButtonEditor(new JCheckBox()));

    // 하단 버튼
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton deleteButton = new StyledButton("선택 삭제");
    JButton saveButton = new StyledButton("저장");
    JButton backButton = new StyledButton("← 뒤로가기");

    deleteButton.addActionListener(_ -> {
      int row = table.getSelectedRow();
      if (row != -1) {
        PersonQuiz quiz = quizList.get(row);
        String imageName = quiz.getImage();
        File imageFile = new File("src/person/images/" + imageName);
        if (imageFile.exists())
          imageFile.delete();

        tableModel.removeRow(row);
        quizList.remove(row);
      }
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
      File file = new File("src/person/data/person.json");
      PersonQuiz[] quizzes = mapper.readValue(file, PersonQuiz[].class);

      for (PersonQuiz quiz : quizzes) {
        quizList.add(quiz);
        String answersStr = String.join(", ", quiz.getAnswers());
        tableModel.addRow(new Object[] { answersStr, "보기" });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void saveData() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      File file = new File("src/person/data/person.json");
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, quizList);
      JOptionPane.showMessageDialog(this, "저장되었습니다.");
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "저장 중 오류 발생!");
    }
  }

  private static class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
      setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus,
        int row, int column) {
      setText("보기");
      return this;
    }
  }

  private class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private int selectedRow;

    public ButtonEditor(JCheckBox checkBox) {
      super(checkBox);
      button = new JButton("보기");
      button.addActionListener(_ -> {
        PersonQuiz quiz = quizList.get(selectedRow);
        String imageFile = quiz.getImage();

        JDialog dialog = new JDialog();
        dialog.setTitle("이미지 보기");
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = new ImageIcon("src/person/images/" + imageFile);
        imageLabel.setIcon(icon);

        dialog.add(imageLabel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(PersonQuizEditorPanel.this);
        dialog.setVisible(true);

        fireEditingStopped();
      });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
      this.selectedRow = row;
      return button;
    }
  }
}
