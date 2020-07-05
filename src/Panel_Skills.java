
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.*;

public class Panel_Skills extends Panel_Strings
{
	Panel_Skills()
	{
		// Panel borders
		setInnerBorder(" Umiejętności ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		// A hashmap for skills
		strings = new HashMap<String, String>();

		setAddButtonText("Dodaj umiejętność");
		setMessage("Wypełnij pola dla \"Umiejętności\"");

		// Place in the sub-panel the GUI elements for entering data
		placeElementsInPanel((Panel_EnterData) enterDataPanel);
	}

	// D:
	@Override
	public void displayInEnterDataPanel(String key, String value)
	{
		// Skill
		((JTextField)components.get(0)).setText(key);
		// Description
		((JTextAreaStyle)components.get(1)).setText(value);
	}

	// H:
	public void  hideFromEnterDataPanel()
	{
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