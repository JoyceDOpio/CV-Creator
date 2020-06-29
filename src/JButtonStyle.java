
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class JButtonStyle extends JButton
{
	private static final long serialVersionUID = 1L;

	JButtonStyle(String text)
	{
		super(text);

		setFont(new Font("Arial", Font.TRUETYPE_FONT,14));
	}
}
