
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
	
	GridBagConstraints gc, savedLanguagesSubPanelgc, enterDataSubPanelgc;
	// Inner panel for organizing skills layout
	JPanel savedInterestsPanel, enterDataPanel;

	HashMap<String, String> interests;
	HashMap<JTextField, JButton> interestFields;
	
	Panel_Interests()
	{		
		setInnerBorder("Zainteresowania");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		interests = new HashMap<String, String>();
		interestFields = new HashMap<JTextField, JButton>();

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
		savedInterestsPanel = new JPanel();
		savedInterestsPanel.setLayout(new GridBagLayout());
		savedLanguagesSubPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		savedLanguagesSubPanelgc.insets = new Insets(0, 5, 0, 5);
		savedLanguagesSubPanelgc.weightx = 100;
		savedLanguagesSubPanelgc.weighty = 1;
		savedLanguagesSubPanelgc.gridx = 0;
		savedLanguagesSubPanelgc.gridy = 0;
		savedLanguagesSubPanelgc.gridwidth = 1;
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

	// A:
	public void addInterestToPanel(String text)
	{
		savedLanguagesSubPanelgc.fill = GridBagConstraints.BOTH;
		savedLanguagesSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_START;
		savedLanguagesSubPanelgc.gridx = 0;
		savedLanguagesSubPanelgc.gridy++;

		final JTextField interestField = new JTextFieldStyle(text);
		// If the text is longer than the text field, the text fields shows the
		// beginning of the text
		interestField.setCaretPosition(0);
		// Specifies how many columne the text field will occupy
		interestField.setColumns(2);
		savedInterestsPanel.add(interestField, savedLanguagesSubPanelgc);

//		savedLanguagesSubPanelgc.gridwidth = 1;


		final JButton removeButton = new JButtonStyle("Usuń");

		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeInterest(interestField, removeButton);
			}
		});
		savedLanguagesSubPanelgc.gridx = 1;

		savedLanguagesSubPanelgc.weightx = 1;
		savedLanguagesSubPanelgc.weighty = 100;

		savedLanguagesSubPanelgc.fill = GridBagConstraints.VERTICAL;
		savedLanguagesSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_END;
		savedInterestsPanel.add(removeButton, savedLanguagesSubPanelgc);
		// Repaint the panel
		savedInterestsPanel.revalidate();  // For JDK 1.7 or above.
		savedInterestsPanel.repaint();

		// Add the duty field and it's removal button to the
		// hash map <-- this will enable us later to remove them
		//              all at once
		interestFields.put(interestField, removeButton);
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
		clearSavedInterestsPanel();
	}

	public void clearSavedInterestsPanel()
	{
		for(HashMap.Entry<JTextField, JButton> entry : interestFields.entrySet())
		{
			// Remove components from the panel for saved interests
			savedInterestsPanel.remove(entry.getKey());
			savedInterestsPanel.remove(entry.getValue());
		}
		interestFields.clear();
		interests.clear();

		// Repaint the panel
		savedInterestsPanel.revalidate();
		savedInterestsPanel.repaint();
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
			addInterestToPanel(entry.getKey());
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
					interests.put(interest.getText(), description.getText());
					// Add interest representation to the panel
					addInterestToPanel(interest.getText());

					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pole dla \"Zainteresowania\"");
				}
			}
		});
	}

	// R:
	public void removeInterest(JTextField field, JButton button)
	{
		// Remove duty
		interests.remove(field.getText());
		// Remove components
		savedInterestsPanel.remove(field);
		savedInterestsPanel.remove(button);
		// Repaint the panel
		savedInterestsPanel.revalidate();
		savedInterestsPanel.repaint();
	}
}
