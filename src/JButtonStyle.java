
import java.awt.Font;

import javax.swing.JButton;

public class JButtonStyle extends JButton
{
	private static final long serialVersionUID = 1L;
	// A variable to know whether the user clicked on the button to view
	// content or to hide it, or rather to see whether the button is "on'
	// or "off"
	private boolean clicked = false;

	JButtonStyle(String text)
	{
		super(text);

		setFont(new Font("Arial", Font.TRUETYPE_FONT,14));
	}

	// C:
	public void click(){clicked = !clicked;}

	// G:
	public boolean getClicked(){return clicked;}

	// S:
	public void setClicked(boolean click){clicked = click;}
}
