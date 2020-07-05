
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

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
		// Panel borders
		setInnerBorder(" Referencje ");
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
			public void displayObject(String text)
			{
				// Show reference value in the JButton
				reference.setText(text);
			}

			@Override
			public void hideObject()
			{
				reference.setText("");
			}

			@Override
//			public void removeObject(JTextField field)
			public void removeObject(String value)
			{
				// Remove reference
				references.remove(value);
			}
		};
		add(savedReferencesPanel);
		// - for entering data
		enterDataPanel = new Panel_EnterData();
		add(enterDataPanel);
		// Place in the sub-panel the GUI elements for entering data
		placeElementsInPanel((Panel_EnterData) enterDataPanel);
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
	public void placeElementsInPanel(Panel_EnterData panel)
	{
		panel.gc.gridx = 0;
		panel.gc.gridy = 0;

		panel.gc.weightx = 100;
		panel.gc.weighty = 1;

		panel.gc.fill = GridBagConstraints.BOTH;
		panel.gc.anchor = GridBagConstraints.FIRST_LINE_START;

		// First row
		panel.gc.gridy++;
		panel.add(reference, panel.gc);

		// Second row
		panel.gc.weightx = 1;
		panel.gc.weighty = 100;
		panel.gc.gridy++;
		panel.gc.fill = GridBagConstraints.NONE;
		panel.gc.anchor = GridBagConstraints.LAST_LINE_END;
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		panel.gc.insets = new Insets(10, 10, 10, 10);
		panel.add(addReferenceButton, panel.gc);

		addReferenceButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(reference.getText().length() != 0)
				{
					String value = reference.getText();

					// If the array list doesn't contain this value yet
					if(!references.contains(value))
					{
						references.add(reference.getText());
						// Add reference representation to the panel
						((Panel_SavedStrings) savedReferencesPanel).addStringRepresentationToPanel(reference.getText());

						clearEnterDataPanel();
					}
					else
						JOptionPane.showMessageDialog(null, "Wpis o takiej nazwie już istnieje.");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pole dla \"Referencje\"");
				}
			}
		});
	}
}
