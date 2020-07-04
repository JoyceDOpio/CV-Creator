
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
	
	GridBagConstraints enterDataSubPanelgc;
	// Inner panel for organizing references layout
	JPanel savedReferencesPanel, enterDataPanel;
	ArrayList<String> references;
	
	Panel_References()
	{
		setInnerBorder("Referencje");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		references = new ArrayList<String>();

		reference = new JTextField();
		addReferenceButton = new JButtonStyle("Dodaj referencję");

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Inner panels for:
		// - saved references
		savedReferencesPanel = new Panel_SavedStrings()
		{
			@Override
			public void removeObject(JTextField field)
			{
				// Remove reference
				references.remove(field.getText());
			}
		};
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

	// C:
	public void clearEnterDataPanel()
	{
		reference.setText("");
	}

	@Override
	public void clearPanel()
	{
		clearEnterDataPanel();
		references.clear();
		((Panel_SavedStrings) savedReferencesPanel).clearPanel();
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
			((Panel_SavedStrings) savedReferencesPanel).addStringRepresentationToPanel(ref);
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
					((Panel_SavedStrings) savedReferencesPanel).addStringRepresentationToPanel(reference.getText());

					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pole dla \"Referencje\"");
				}
			}
		});
	}
}
