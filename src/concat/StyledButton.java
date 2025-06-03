package concat;

import java.awt.Font;
import javax.swing.JButton;

public class StyledButton extends JButton {
    public StyledButton(String text) {
        super(text);
        setFont(new Font("맑은 고딕", Font.PLAIN, 14));
    }
}
