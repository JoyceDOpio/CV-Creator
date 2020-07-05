
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.*;

public class Panel_Interests extends Panel_Strings
{
	Panel_Interests()
	{
		// Panel borders
		setInnerBorder(" Zainteresowania ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		// A hashmap for interests
		strings = new HashMap<String, String>();

		setAddButtonText("Dodaj zainteresowanie");
		setMessage("Wypełnij pola dla \"Zainteresowania\"");

		// Place in the sub-panel the GUI elements for entering data
		placeElementsInPanel((Panel_EnterData) enterDataPanel);
	}

	// D:
	@Override
	public void displayInEnterDataPanel(String key, String value)
	{
		// Interest
		((JTextField)components.get(0)).setText(key);
		// Description
		((JTextAreaStyle)components.get(1)).setText(value);
	}

	@Override
	public void hideFromEnterDataPanel()
	{
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
