
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Panel_Data extends JPanel
{
	Border innerBorder, outerBorder;
	static final String osName = System.getProperty("os.name");
	
	Panel_Data()
	{
		if(osName.startsWith("Windows"))
		{
			setBackgroundColor(Color.white);
		}
	}

	// C:
	public void clearPanel(){}

	// I:
	public boolean isPanelEmpty(){return false;}

	// S:
	public void setInnerBorder(String text)
	{
		innerBorder = BorderFactory.createTitledBorder(text);
		((TitledBorder) innerBorder).setTitleFont(new Font("Arial", Font.ITALIC, 16));
	}
	
	public void setOuterBorder(int top, int left, int bottom, int right)
	{
		outerBorder = BorderFactory.createEmptyBorder(top, left, bottom, right);
	}
	
	public void setBackgroundColor(Color color)
	{
		this.setBackground(color);
	}
}
