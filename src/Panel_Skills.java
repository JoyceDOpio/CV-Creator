
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

public class Panel_Skills extends Panel_Data
{
	JLabel skillsLabel, descriptionLabel;
	JTextField skill;
	JTextArea description;
	JButton addSkillButton;
	
	GridBagConstraints enterDataSubPanelgc;
	// Inner panel for organizing skills layout
	JPanel savedSkillsPanel, enterDataPanel;
	HashMap<String, String> skills;
	
	Panel_Skills()
	{
		setInnerBorder("Umiejętności");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		skills = new HashMap<String, String>();

		// GUI components
		skillsLabel = new JLabelStyle("Umiejętność");
		descriptionLabel = new JLabelStyle("Opis");
		
		skill = new JTextFieldStyle();

		description = new JTextAreaStyle("");
		// Wraps the text
		description.setLineWrap(true);
		description.setPreferredSize(new Dimension(100,50));
		
		addSkillButton = new JButtonStyle("Dodaj umiejętność");

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Inner panels for:
		// - saved skills
		savedSkillsPanel = new Panel_SavedStrings()
		{
			@Override
			public void removeObject(JTextField field)
			{
				// Remove skill
				skills.remove(field.getText());
			}
		};
		add(savedSkillsPanel);
		// - entering data
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
		skill.setText("");
		description.setText("");
	}

	@Override
	public void clearPanel()
	{
		clearEnterDataPanel();
		skills.clear();
		((Panel_SavedStrings) savedSkillsPanel).clearPanel();
	}

	public HashMap<String,String> collectInformation()
	{
		HashMap<String, String> skillsMap = new HashMap<String,String>(skills);

		for(HashMap.Entry<String,String> entry : skills.entrySet())
		{
			skillsMap.put(entry.getKey(),entry.getValue());
		}

		skills.clear();
		
		return skillsMap;
	}

	// I:
	public void insertInformation(HashMap<String,String> skillsMap)
	{
		skills = new HashMap<>(skillsMap);

		for(HashMap.Entry<String,String> entry : skillsMap.entrySet())
		{
			skills.put(entry.getKey(),entry.getValue());
			((Panel_SavedStrings) savedSkillsPanel).addStringRepresentationToPanel(entry.getKey());
		}
	}

	@Override
	public boolean isPanelEmpty()
	{
		// Return true, if there are no saved skills
		if(skills.isEmpty())
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
		gc.gridy++;
		panel.add(skillsLabel, gc);

		// Second row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(skill, gc);

		// Third row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		panel.add(descriptionLabel, gc);

		// Fourth row
		gc.weightx = 1;
		gc.weighty = 100;
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
//		panel.add(description, gc);
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
		panel.add(addSkillButton,gc);

		addSkillButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(skill.getText().length() != 0)
				{
					skills.put(skill.getText(), description.getText());
					// Add skill representation to the panel
					((Panel_SavedStrings) savedSkillsPanel).addStringRepresentationToPanel(skill.getText());

					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pole dla \"Umiejętności\"");
				}
			}
		});
	}
}