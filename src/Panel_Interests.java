
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.*;

public class Panel_Interests extends Panel_Strings
{
	String sameMessage;

	Panel_Interests()
	{
		// Panel borders
		setInnerBorder(" Zainteresowania ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		// A hashmap for interests
		strings = new HashMap<String, String>();
		sameMessage = "Takie zainteresowanie już istnieje.";

		setAddButtonText("Dodaj zainteresowanie");
		setMessage("Wypełnij pola dla \"Zainteresowania\"");

		// Place in the sub-panel the GUI elements for entering data
		placeElementsInPanel((Panel_EnterData) enterDataPanel);
	}

	// D:
	@Override
	public void displayInEnterDataPanel(JButton objectButton)
	{
		String key = objectButton.getText();
		String value = strings.get(key);

		// Interest
		((JTextField)components.get(0)).setText(key);
		// Description
		((JTextAreaStyle)components.get(1)).setText(value);
	}

	@Override
	public void hideFromEnterDataPanel(JButton objectButton)
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

		// Interest
		((JTextField)components.get(0)).setText("");
		// Description
		((JTextAreaStyle)components.get(1)).setText("");
	}

	// S:
	@Override
	public void setOtherComponents()
	{
		labels = new ArrayList<JLabel>(Arrays.asList(new JLabelStyle("Zainteresowanie"),
				new JLabelStyle("Opis")));

		components = new ArrayList<>(Arrays.asList(new JTextFieldStyle(),new JTextAreaStyle("")));
	}
}
