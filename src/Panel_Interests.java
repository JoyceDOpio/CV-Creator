
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

public class Panel_Interests extends Panel_Data
{
	JLabel interestLabel, descriptionLabel;
	JTextField interest;
	JTextArea description;
	JButton addInterestButton;
	
	GridBagConstraints enterDataSubPanelgc;
	// Inner panel for organizing skills layout
	JPanel savedInterestsPanel, enterDataPanel;

	HashMap<String, String> interests;
	
	Panel_Interests()
	{		
		setInnerBorder("Zainteresowania");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		interests = new HashMap<>();

		// GUI componenets
		interestLabel = new JLabelStyle("Zainteresowanie");
		descriptionLabel = new JLabelStyle("Opis");
		
		interest = new JTextFieldStyle();

		description = new JTextAreaStyle("");
		description.setLineWrap(true);
		description.setPreferredSize(new Dimension(100,50));

		addInterestButton = new JButtonStyle("Dodaj zainteresowanie");

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		// Inner panels  for:
		// - saved interests
		savedInterestsPanel = new Panel_SavedStrings()
		{
			@Override
			public void removeObject(JTextField field)
			{
				// Remove duty
				interests.remove(field.getText());
			}
		};
		add(savedInterestsPanel);
		// - enetring data
		enterDataPanel = new JPanel();
		enterDataPanel.setLayout(new GridBagLayout());
		enterDataSubPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		enterDataSubPanelgc.insets = new Insets(0, 5, 0, 5);
		enterDataSubPanelgc.weightx = 100;
		enterDataSubPanelgc.weighty = 1;
		enterDataSubPanelgc.gridx = 0;
		enterDataSubPanelgc.gridy = 0;
		enterDataSubPanelgc.gridwidth = 1;
		add(enterDataPanel);
		// Place in the sub-panel the GUI elements for entering data
		placeElementsInPanel(enterDataPanel, enterDataSubPanelgc);
	}

	// C:
	public void clearEnterDataPanel()
	{
		interest.setText("");
		description.setText("");
	}

	@Override
	public void clearPanel()
	{
		clearEnterDataPanel();
		interests.clear();
		((Panel_SavedStrings) savedInterestsPanel).clearPanel();
	}

	public HashMap<String,String> collectInformation()
	{		
		HashMap<String, String> interestsMap = new HashMap<String,String>(interests);

		for(HashMap.Entry<String,String> entry : interests.entrySet())
		{
			interestsMap.put(entry.getKey(),entry.getValue());
		}

		interests.clear();
		
		return interestsMap;
	}

	// I:
	public void insertInformation(HashMap<String,String> interestsMap)
	{
		interests = new HashMap<>(interestsMap);

		for(HashMap.Entry<String,String> entry : interestsMap.entrySet())
		{
			interests.put(entry.getKey(),entry.getValue());
			((Panel_SavedStrings) savedInterestsPanel).addStringRepresentationToPanel(entry.getKey());
		}
	}

	@Override
	public boolean isPanelEmpty()
	{
		// Return true, if there are no interests saved
		if(interests.isEmpty())
			return true;
		else
			return false;
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
		panel.add(interestLabel, gc);

		// Second row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(interest, gc);

		// Third row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		panel.add(descriptionLabel, gc);

		// Fourth row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(new JScrollPane(description), gc);

		// Fifth row
		gc.weightx = 1;
		gc.weighty = 100;
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		gc.insets = new Insets(10, 10, 10, 10);
		panel.add(addInterestButton,gc);

		addInterestButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(interest.getText().length() != 0)
				{
					// Add the interest to the map
					interests.put(interest.getText(), description.getText());
					// Add interest representation to the panel
					((Panel_SavedStrings) savedInterestsPanel).addStringRepresentationToPanel(interest.getText());

					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pole dla \"Zainteresowania\"");
				}
			}
		});
	}
}
