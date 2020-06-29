
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Panel_ProfessionSummary extends Panel_Data
{
	JTextArea profSummary; 
	
	Panel_ProfessionSummary()
	{
		setInnerBorder("Opis");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		setLayout(new BorderLayout());
		profSummary = new JTextAreaStyle("");
		// Wraps the text
		profSummary.setLineWrap(true);
		add(new JScrollPane(profSummary), BorderLayout.CENTER);
		
		// Set width of the sub-panel
		Dimension dim = getPreferredSize();
		dim.height = 200;
		setPreferredSize(dim);
	}

	// C:
	@ Override
	public void clearPanel()
	{
		profSummary.setText("");
	}
	
	public String collectInformation()
	{		
		return this.profSummary.getText();
	}

	// I:
	public void insertInformation(String text)
	{
		profSummary.setText(text);
		// If the text is longer than the text field, the text fields shows the
		// beginning of the text
		profSummary.setCaretPosition(0);
	}

	@Override
	public boolean isPanelEmpty()
	{
		if(profSummary.getText().equals(""))
			return true;
		else
			return false;
	}
}
