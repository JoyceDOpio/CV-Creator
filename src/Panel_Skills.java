
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
	
	GridBagConstraints gc, savedSkillsSubPanelgc, enterDataSubPanelgc;
	// Inner panel for organizing skills layout
	JPanel savedSkillsPanel, enterDataPanel;
	HashMap<String, String> skills;
	HashMap<JTextField, JButton> skillFields;
	
	Panel_Skills()
	{
		setInnerBorder("Umiejętności");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		skills = new HashMap<String, String>();
		skillFields = new HashMap<JTextField, JButton>();

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
		savedSkillsPanel = new JPanel();
		savedSkillsPanel.setLayout(new GridBagLayout());
		savedSkillsSubPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		savedSkillsSubPanelgc.insets = new Insets(0, 5, 0, 5);
		savedSkillsSubPanelgc.weightx = 100;
		savedSkillsSubPanelgc.weighty = 1;
		savedSkillsSubPanelgc.gridx = 0;
		savedSkillsSubPanelgc.gridy = 0;
		savedSkillsSubPanelgc.gridwidth = 1;
//		add(savedSkillsPanel, gc);
		add(savedSkillsPanel);
		// entering data
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
//		add(savedSkillsPanel, gc);
		add(enterDataPanel);
		// Place in the sub-panel the GUI elements for enetering data
		placeElementsInPanel(enterDataPanel, enterDataSubPanelgc);
	}

	// A:
	public void addSkillToPanel(String text)
	{
		savedSkillsSubPanelgc.fill = GridBagConstraints.HORIZONTAL;
		savedSkillsSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_START;
		savedSkillsSubPanelgc.gridx = 0;
		savedSkillsSubPanelgc.gridy++;

		final JTextField skillField = new JTextFieldStyle(text);
		// If the text is longer than the text field, the text fields shows the
		// beginning of the text
		skillField.setCaretPosition(0);
		// Specifies how many columne the text field will occupy
		skillField.setColumns(2);
		savedSkillsPanel.add(skillField, savedSkillsSubPanelgc);

//		savedSkillsSubPanelgc.gridwidth = 1;


		final JButton removeButton = new JButtonStyle("Usuń");

		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeSkill(skillField, removeButton);
			}
		});

		savedSkillsSubPanelgc.gridx = 1;

		savedSkillsSubPanelgc.weightx = 1;
		savedSkillsSubPanelgc.weighty = 100;

		savedSkillsSubPanelgc.fill = GridBagConstraints.VERTICAL;
		savedSkillsSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_END;

		savedSkillsPanel.add(removeButton, savedSkillsSubPanelgc);
		// Repaint the panel
		savedSkillsPanel.revalidate();  // For JDK 1.7 or above.
		savedSkillsPanel.repaint();

		// Add the duty field and it's removal button to the
		// hash map <-- this will enable us later to remove them
		//              all at once
		skillFields.put(skillField, removeButton);
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
		clearSavedSkillsPanel();
	}

	public void clearSavedSkillsPanel()
	{
		for(HashMap.Entry<JTextField, JButton> entry : skillFields.entrySet())
		{
			// Remove components from the panel for saved skills
			savedSkillsPanel.remove(entry.getKey());
			savedSkillsPanel.remove(entry.getValue());
		}
		skillFields.clear();
		skills.clear();

		// Repaint the panel
		savedSkillsPanel.revalidate();
		savedSkillsPanel.repaint();
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
			addSkillToPanel(entry.getKey());
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
					addSkillToPanel(skill.getText());

					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pole dla \"Umiejętności\"");
				}
			}
		});
	}

	// R:
	public void removeSkill(JTextField field, JButton button)
	{
		// Remove duty
		skills.remove(field.getText());
		// Remove components
		savedSkillsPanel.remove(field);
		savedSkillsPanel.remove(button);
		// Repaint the panel
		savedSkillsPanel.revalidate();
		savedSkillsPanel.repaint();
	}
}