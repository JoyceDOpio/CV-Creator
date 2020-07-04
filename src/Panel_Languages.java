
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
	
	GridBagConstraints enterDataSubPanelgc;
	// Inner panel for organizing languages layout
	JPanel savedLanguagesPanel, enterDataPanel;
	HashMap<String, String> languages;
	
	Panel_Languages()
	{
		setInnerBorder("Języki");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		languages = new HashMap<String, String>();
		
		languageLabel = new JLabelStyle("Język");
		levelLabel = new JLabelStyle("Poziom");
		
		language = new JTextFieldStyle(); 
		level = new JTextFieldStyle();
		addLanguageButton = new JButtonStyle("Dodaj język");

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Inner panels for:
		// - saved languages
		savedLanguagesPanel = new Panel_SavedStrings()
		{
			@Override
			public void removeObject(JTextField field)
			{
				// Remove language
				languages.remove(language.getText());
			}
		};
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
		add(enterDataPanel);
		// Place in the sub-panel the GUI elements for enetering data
		placeElementsInPanel(enterDataPanel, enterDataSubPanelgc);
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
		languages.clear();
		((Panel_SavedStrings) savedLanguagesPanel).clearPanel();
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
			((Panel_SavedStrings) savedLanguagesPanel).addStringRepresentationToPanel(entry.getKey());
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
					((Panel_SavedStrings) savedLanguagesPanel).addStringRepresentationToPanel(language.getText());

					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pole dla \"Języki\"");
				}
			}
		});
	}
}
