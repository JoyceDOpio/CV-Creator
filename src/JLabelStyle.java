
import java.awt.Font;

import javax.swing.JLabel;

public class JLabelStyle extends JLabel
{
	JLabelStyle(String text)
	{
		super(text);
		
		setFont(new Font("Arial", Font.TRUETYPE_FONT,14));
	}
}
