
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.*;

public class Panel_Skills extends Panel_Strings
{
	String sameMessage;

	Panel_Skills()
	{
		// Panel borders
		setInnerBorder(" Umiejętności ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		// A hashmap for skills
		strings = new HashMap<String, String>();

		sameMessage = "Taka umiejętność już istnieje.";

		setAddButtonText("Dodaj umiejętność");
		setMessage("Wypełnij pola dla \"Umiejętności\"");

		// Place in the sub-panel the GUI elements for entering data
		placeElementsInPanel((Panel_EnterData) enterDataPanel);
	}

	// D:
	@Override
	public void displayInEnterDataPanel(JButton objectButton)
	{
		String key = objectButton.getText();
		String value = strings.get(key);

		// Skill
		((JTextField)components.get(0)).setText(key);
		// Description
		((JTextAreaStyle)components.get(1)).setText(value);
	}

	// H:
	public void  hideFromEnterDataPanel(JButton objectButton)
	{
		String oldKey = objectButton.getText();
		String newKey = ((JTextField)components.get(0)).getText();
		String newValue = ((JTextAreaStyle)components.get(1)).getText();

		// If the key has been changed by the user
		if(!newKey.equals("") && !oldKey.equals(newKey))
		{
			if(!strings.containsKey(((JTextField)components.get(0)).getText()))
			{

				// Remove the old entry
				strings.remove(oldKey);
				// Add the new entry
				strings.put(newKey,newValue);
				// Display new key on the object button
				objectButton.setText(newKey);
			}
			else
				JOptionPane.showMessageDialog(null, sameMessage);
		}
		else
		{
			// If only the value has been changed
			if(!strings.get(oldKey).equals(newKey))
			{
				strings.put(oldKey, newValue);
			}
		}

		// Skill
		((JTextField)components.get(0)).setText("");
		// Description
		((JTextAreaStyle)components.get(1)).setText("");
	}

	// S:
	@Override
	public void setOtherComponents()
	{
		labels = new ArrayList<JLabel>(Arrays.asList(new JLabelStyle("Umiejętność"),
				new JLabelStyle("Opis")));

		components = new ArrayList<>(Arrays.asList(new JTextFieldStyle(),new JTextAreaStyle("")));
	}
}