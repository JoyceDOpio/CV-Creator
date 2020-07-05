
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
	
	GridBagConstraints enterDataSubPanelgc;
	// Inner panel for organizing saved duties
	JPanel savedDutiesPanel, enterDataPanel;
	
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

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Inner panels for:
		// - saved duties
		savedDutiesPanel = new Panel_SavedStrings()
		{
			@Override
			public void removeObject(String value)
			{
				// Remove duty
				duties.remove(value);
			}
		};
		add(savedDutiesPanel);
		// - entering data
		enterDataPanel = new Panel_Data();
		enterDataPanel.setLayout(new GridBagLayout());
		enterDataSubPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		enterDataSubPanelgc.insets = new Insets(5, 5, 5, 5);
		enterDataSubPanelgc.weightx = 100;
		enterDataSubPanelgc.weighty = 1;
		enterDataSubPanelgc.gridx = 0;
		enterDataSubPanelgc.gridy = 0;
		enterDataSubPanelgc.gridwidth = 1;
		add(enterDataPanel);
		// Place in the sub-panel the GUI elements for enetering data
		placeElementsInPanel(enterDataPanel, enterDataSubPanelgc);
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

	// P:
	public void placeElementsInPanel(JPanel panel, GridBagConstraints gc)
	{
		gc.gridx = 0;
		gc.gridy = 0;

		gc.weightx = 100;
		gc.weighty = 1;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;

		// First row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(duty, gc);

		// Second row
		gc.weightx = 1;
		gc.weighty = 100;
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		gc.insets = new Insets(10, 5, 5, 5);
		panel.add(addDutyButton,gc);

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
			}
		});
	}
}
