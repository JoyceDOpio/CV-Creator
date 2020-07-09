
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class Panel_Duties extends Panel_Data
{
	JLabel dutyLabel;
	JTextField duty;
	ArrayList<String> duties;
	JButton addDutyButton;

	// Inner panel for organizing saved duties
	JPanel savedDutiesPanel, enterDataPanel;

	String sameMessage, emptyMessage;
	
	Panel_Duties()
	{
		// Panel borders
		setInnerBorder(" Obowiązki ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		duties = new ArrayList<String>();
		
		dutyLabel = new JLabelStyle("Obowiązek");
		duty = new JTextFieldStyle();
		addDutyButton = new JButtonStyle("Dodaj obowiązek");

		sameMessage = "Taki obowiązek już istnieje.";
		emptyMessage = "Wypełnij pole dla \"Obowiązki\".";

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Inner panels for:
		// - saved duties
		savedDutiesPanel = new Panel_SavedStrings()
		{
			@Override
			public void displayObject(JButton objectButton)
			{
				String value = objectButton.getText();

				// Show reference value in the JButton
				duty.setText(value);
			}

			@Override
			public void hideObject(JButton objectButton)
			{
				String value = objectButton.getText();

				// If the user entered different value
				if(!duty.getText().equals("") && !value.equals(duty.getText()))
				{
					if(!duties.contains(duty.getText()))
					{
						// Remove the old value
						removeObject(value);
						// Save the new value
						duties.add(duty.getText());
						// Display new key on the object button
						objectButton.setText(duty.getText());
					}
					else
						JOptionPane.showMessageDialog(null, sameMessage);

				}
				duty.setText("");
			}

			@Override
			public void removeObject(String value)
			{
				// Remove duty
				duties.remove(value);
			}
		};
		add(savedDutiesPanel);
		// - entering data
		enterDataPanel = new Panel_EnterData();
		add(enterDataPanel);
		// Place in the sub-panel the GUI elements for enetering data
		placeElementsInPanel((Panel_EnterData) enterDataPanel);
	}
	
	// C:
	public void clearEnterDataPanel(){duty.setText("");}
	
	public void clearPanel()
	{
		clearEnterDataPanel();
		duties.clear();
		((Panel_SavedStrings) savedDutiesPanel).clearPanel();
	}
	
	public ArrayList<String> collectInformation()
	{
		ArrayList<String> dutyList = new ArrayList<String>(duties);
		Collections.copy(dutyList, duties);
		clearPanel();
		
		return dutyList;
	}

	// I:
	public void insertInformation(ArrayList<String> dutyList)
	{
		setDuties(dutyList);

		for(String duty : duties)
		{
			((Panel_SavedStrings) savedDutiesPanel).addStringRepresentationToPanel(duty);
		}
	}

	// P:
	public void placeElementsInPanel(Panel_EnterData panel)
	{
		panel.gc.gridx = 0;
		panel.gc.gridy = 0;

		panel.gc.weightx = 100;
		panel.gc.weighty = 1;

		panel.gc.fill = GridBagConstraints.NONE;
		panel.gc.anchor = GridBagConstraints.FIRST_LINE_START;

		// First row
		panel.gc.gridy++;
		panel.gc.fill = GridBagConstraints.BOTH;
		panel.add(duty, panel.gc);

		// Second row
		panel.gc.weightx = 1;
		panel.gc.weighty = 100;
		panel.gc.gridy++;
		panel.gc.fill = GridBagConstraints.NONE;
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		panel.gc.insets = new Insets(10, 5, 5, 5);
		panel.add(addDutyButton,panel.gc);

		addDutyButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(duty.getText().length() != 0)
				{
					// Add the duty to the list
					duties.add(duty.getText());
					// Add duty representation to the panel
					((Panel_SavedStrings) savedDutiesPanel).addStringRepresentationToPanel(duty.getText());

					clearEnterDataPanel();
				}
				else
					JOptionPane.showMessageDialog(null, emptyMessage);
			}
		});
	}

	// S:
	public void setDuties(ArrayList<String> dutyList)
	{
		duties = new ArrayList<String>(dutyList);
		Collections.copy(duties,dutyList);
	}
}
