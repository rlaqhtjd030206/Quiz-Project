package person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
  private JButton button;
  private String fileName;

  public ButtonEditor(JCheckBox checkBox) {
    super(checkBox);
    button = new JButton("보기");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showImagePopup(fileName);
        fireEditingStopped();
      }
    });
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    fileName = (String) table.getValueAt(row, 0); // 이미지 경로가 첫 번째 열
    button.setText("보기");
    return button;
  }

  private void showImagePopup(String imageName) {
    JDialog dialog = new JDialog();
    dialog.setTitle("이미지 보기: " + imageName);
    dialog.setSize(400, 400);
    dialog.setLayout(new BorderLayout());

    JLabel label = new JLabel();
    label.setHorizontalAlignment(SwingConstants.CENTER);
    ImageIcon icon = new ImageIcon("src/person/images/" + imageName);
    label.setIcon(icon);

    dialog.add(label, BorderLayout.CENTER);
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);
  }
}
