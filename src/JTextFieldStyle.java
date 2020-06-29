
import java.awt.*;

import javax.swing.JTextField;

public class JTextFieldStyle extends JTextField
{
	JTextFieldStyle()
	{
		this.setFont(new Font("Arial", Font.TRUETYPE_FONT,14));
	}

	JTextFieldStyle(String text)
	{
		super(text);
		
		this.setFont(new Font("Arial", Font.TRUETYPE_FONT,14));
	}
}
