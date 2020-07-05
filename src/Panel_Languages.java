
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.*;

public class Panel_Languages extends Panel_Strings
{
	Panel_Languages()
	{
		// Panel borders
		setInnerBorder(" Języki ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		// A hashmap for languages
		strings = new HashMap<String, String>();

		setAddButtonText("Dodaj język");
		setMessage("Wypełnij pola dla \"Języki\"");

		// Place in the sub-panel the GUI elements for entering data
		placeElementsInPanel((Panel_EnterData) enterDataPanel);
	}

	// D:
	@Override
	public void displayInEnterDataPanel(String key, String value)
	{
		// Language
		((JTextField)components.get(0)).setText(key);
		// Level
		((JTextField)components.get(1)).setText(value);
	}

	@Override
	public void hideFromEnterDataPanel()
	{
		// Language
		((JTextField)components.get(0)).setText("");
		// Level
		((JTextField)components.get(1)).setText("");
	}

	// S:
	@Override
	public void setOtherComponents()
	{
		labels = new ArrayList<JLabel>(Arrays.asList(new JLabelStyle("Język"),
				new JLabelStyle("Poziom")));

		components = new ArrayList<>(Arrays.asList(new JTextFieldStyle(),new JTextFieldStyle()));
	}
}
