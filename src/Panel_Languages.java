
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

public class Panel_Languages extends Panel_Data
{
	JLabel languageLabel, levelLabel;
	JTextField language, level;
	JButton addLanguageButton;
	
	GridBagConstraints gc, savedLanguagesSubPanelgc, enterDataSubPanelgc;
	// Inner panel for organizing languages layout
	JPanel savedLanguagesPanel, enterDataPanel;
	HashMap<String, String> languages;
	HashMap<JTextField, JButton> languageFields;
	
	Panel_Languages()
	{
		setInnerBorder("Języki");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		languages = new HashMap<String, String>();
		languageFields = new HashMap<JTextField, JButton>();
		
		languageLabel = new JLabelStyle("Język");
		levelLabel = new JLabelStyle("Poziom");
		
		language = new JTextFieldStyle(); 
		level = new JTextFieldStyle();
		addLanguageButton = new JButtonStyle("Dodaj język");

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Inner panels for:
		// - saved languages
		savedLanguagesPanel = new JPanel();
		savedLanguagesPanel.setLayout(new GridBagLayout());
		savedLanguagesSubPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		savedLanguagesSubPanelgc.insets = new Insets(0, 5, 0, 5);
		savedLanguagesSubPanelgc.weightx = 100;
		savedLanguagesSubPanelgc.weighty = 1;
		savedLanguagesSubPanelgc.gridx = 0;
		savedLanguagesSubPanelgc.gridy = 0;
		savedLanguagesSubPanelgc.gridwidth = 1;
//		add(savedLanguagesPanel, gc);
		add(savedLanguagesPanel);
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
//		add(enterDataPanel, gc);
		add(enterDataPanel);
		// Place in the sub-panel the GUI elements for enetering data
		placeElementsInPanel(enterDataPanel, enterDataSubPanelgc);
	}

	// A:
	public void addLanguageToPanel(String text)
	{
//		subPanelgc.fill = GridBagConstraints.BOTH;
		savedLanguagesSubPanelgc.fill = GridBagConstraints.HORIZONTAL;
		savedLanguagesSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_START;
		savedLanguagesSubPanelgc.gridx = 0;
		savedLanguagesSubPanelgc.gridy++;

		final JTextField languageField = new JTextFieldStyle(text);
		// If the text is longer than the text field, the text fields shows the
		// beginning of the text
		languageField.setCaretPosition(0);
		// Specifies how many columne the text field will occupy
		languageField.setColumns(2);
		savedLanguagesPanel.add(languageField, savedLanguagesSubPanelgc);

		final JButton removeButton = new JButtonStyle("Usuń");

		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeLanguage(languageField, removeButton);
			}
		});

		savedLanguagesSubPanelgc.gridx = 1;

		savedLanguagesSubPanelgc.weightx = 1;
		savedLanguagesSubPanelgc.weighty = 100;

		savedLanguagesSubPanelgc.fill = GridBagConstraints.VERTICAL;
		savedLanguagesSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_END;

		savedLanguagesPanel.add(removeButton, savedLanguagesSubPanelgc);

		// Repaint the panel
		savedLanguagesPanel.revalidate();  // For JDK 1.7 or above.
		savedLanguagesPanel.repaint();

		// Add the duty field and it's removal button to the
		// hash map <-- this will enable us later to remove them
		//              all at once
		languageFields.put(languageField, removeButton);
	}

	// C:
	public void clearEnterDataPanel()
	{
		language.setText("");
		level.setText("");
	}

	@Override
	public void clearPanel()
	{
		clearEnterDataPanel();
		clearSavedLangPanel();
	}

	public void clearSavedLangPanel()
	{
		for(HashMap.Entry<JTextField, JButton> entry : languageFields.entrySet())
		{
			// Remove components from the panel for saved languages
			savedLanguagesPanel.remove(entry.getKey());
			savedLanguagesPanel.remove(entry.getValue());
		}
		languageFields.clear();
		languages.clear();

		// Repaint the panel
		savedLanguagesPanel.revalidate();
		savedLanguagesPanel.repaint();
	}

	public HashMap<String, String> collectInformation()
	{
		HashMap<String, String> lang = new HashMap<String,String>(languages);
		
		for(HashMap.Entry<String,String> entry : languages.entrySet())
		{
			lang.put(entry.getKey(),entry.getValue());
		}
				
		languages.clear();
		
		return lang;
	}

	// I:
	public void insertInformation(HashMap <String,String> languagesMap)
	{
		languages = new HashMap<>(languagesMap);

		for(HashMap.Entry<String,String> entry : languagesMap.entrySet())
		{
			languages.put(entry.getKey(),entry.getValue());
			addLanguageToPanel(entry.getKey());
		}
	}

	@Override
	public boolean isPanelEmpty()
	{
		// Return true, if there are no languages saved
		if(languages.isEmpty())
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
		// Places the elements at the beginning of the line
		gc.anchor = GridBagConstraints.FIRST_LINE_START;

		// First row
		gc.gridy++;
		panel.add(languageLabel, gc);

		// Second row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(language, gc);

		// Third row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		panel.add(levelLabel, gc);

		// Fourth row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(level, gc);

		// Fifth row
		gc.weightx = 1;
		gc.weighty = 100;
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		gc.insets = new Insets(10, 10, 10, 10);
		panel.add(addLanguageButton,gc);

		addLanguageButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(language.getText().length() != 0)
				{
					languages.put(language.getText(), level.getText());

					// Add language epresentation to the panel
					addLanguageToPanel(language.getText());

					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pole dla \"Języki\"");
				}
			}
		});
	}

	// R:
	public void removeLanguage(JTextField field, JButton button)
	{
		// Remove duty
		languages.remove(language.getText());
		// Remove components
		savedLanguagesPanel.remove(field);
		savedLanguagesPanel.remove(button);
		// Repaint the panel
		savedLanguagesPanel.revalidate();
		savedLanguagesPanel.repaint();
	}
}
