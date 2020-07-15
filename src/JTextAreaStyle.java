
import java.awt.Font;

import javax.swing.JTextArea;

public class JTextAreaStyle extends JTextArea
{
	JTextAreaStyle(String text)
	{
		super(text);

		this.setFont(new Font("Arial", Font.TRUETYPE_FONT,14));
	}
	
}
