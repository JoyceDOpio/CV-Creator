
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.*;

public class Panel_References extends Panel_Data
{
	JTextField reference;
	JButton addReferenceButton;
	
	GridBagConstraints gc, savedReferencesSubPanelgc, enterDataSubPanelgc;
	// Inner panel for organizing references layout
	JPanel savedReferencesPanel, enterDataPanel;
	ArrayList<String> references;

	HashMap<JTextField, JButton> refFields;
	
	Panel_References()
	{
		setInnerBorder("Referencje");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		references = new ArrayList<String>();
		refFields = new HashMap<JTextField, JButton>();

		reference = new JTextField();
		addReferenceButton = new JButtonStyle("Dodaj referencję");

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Inner panels for:
		// - saved references
		savedReferencesPanel = new JPanel();
		savedReferencesPanel.setLayout(new GridBagLayout());
		savedReferencesSubPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		savedReferencesSubPanelgc.insets = new Insets(0, 5, 5, 5);
		savedReferencesSubPanelgc.weightx = 100;
		savedReferencesSubPanelgc.weighty = 1;
		savedReferencesSubPanelgc.gridx = 0;
		savedReferencesSubPanelgc.gridy = 0;
		savedReferencesSubPanelgc.gridwidth = 1;
		add(savedReferencesPanel);
		// - for entering data
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
	public void addReferenceToPanel(String text)
	{
		savedReferencesSubPanelgc.fill = GridBagConstraints.HORIZONTAL;
		savedReferencesSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_START;
		savedReferencesSubPanelgc.gridx = 0;
		savedReferencesSubPanelgc.gridy++;

		final JTextField referenceField = new JTextFieldStyle(text);
		// If the text is longer than the text field, the text fields shows the
		// beginning of the text
		referenceField.setCaretPosition(0);
		// Specifies how many columne the text field will occupy
		referenceField.setColumns(2);
		savedReferencesPanel.add(referenceField, savedReferencesSubPanelgc);

		savedReferencesSubPanelgc.gridx = 1;

		final JButton removeButton = new JButtonStyle("Usuń");

		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeReference(referenceField, removeButton);
			}
		});
		savedReferencesSubPanelgc.weightx = 1;
		savedReferencesSubPanelgc.weighty = 100;

		savedReferencesSubPanelgc.fill = GridBagConstraints.VERTICAL;
		savedReferencesSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_END;

		savedReferencesPanel.add(removeButton, savedReferencesSubPanelgc);
		// Repaint the panel
		savedReferencesPanel.revalidate();  // For JDK 1.7 or above.
		savedReferencesPanel.repaint();

		// Add the duty field and it's removal button to the
		// hash map <-- this will enable us later to remove them
		//              all at once
		refFields.put(referenceField, removeButton);
	}

	// C:
	public void clearEnterDataPanel()
	{
		reference.setText("");
	}

	@Override
	public void clearPanel()
	{
		clearEnterDataPanel();
		clearSavedReferencePanel();
	}

	public void clearSavedReferencePanel()
	{
		for(HashMap.Entry<JTextField, JButton> entry : refFields.entrySet())
		{
			// Remove components from the panel for saved references
			savedReferencesPanel.remove(entry.getKey());
			savedReferencesPanel.remove(entry.getValue());
		}
		refFields.clear();
		references.clear();

		// Repaint the panel
		savedReferencesPanel.revalidate();
		savedReferencesPanel.repaint();
	}

	public ArrayList<String> collectInformation()
	{
		ArrayList<String> referenceList = new ArrayList<String>(references);
		Collections.copy(referenceList, references);
		
		return referenceList;
	}

	// I:
	public void insertInformation(ArrayList<String> refList)
	{
		references = refList;

		for(String ref : references)
		{
			addReferenceToPanel(ref);
		}
	}

	@Override
	public boolean isPanelEmpty()
	{
		if(references.isEmpty())
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

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;

		// First row
		gc.gridy++;
		panel.add(reference, gc);

		// Second row
		gc.weightx = 1;
		gc.weighty = 100;
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		gc.insets = new Insets(10, 10, 10, 10);
		panel.add(addReferenceButton, gc);

		addReferenceButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(reference.getText().length() != 0)
				{
					references.add(reference.getText());
					// Add reference representation to the panel
					addReferenceToPanel(reference.getText());

					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pole dla \"Referencje\"");
				}
			}
		});
	}

	// R:
	public void removeReference(JTextField field, JButton button)
	{
		// Remove duty
		references.remove(field.getText());
		// Remove components
		savedReferencesPanel.remove(field);
		savedReferencesPanel.remove(button);
		// Repaint the panel
		savedReferencesPanel.revalidate();
		savedReferencesPanel.repaint();
	}
}
