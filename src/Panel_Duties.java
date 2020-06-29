
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.*;

public class Panel_Duties extends Panel_Data
{
	JLabel dutyLabel;
	JTextField duty;
	ArrayList<String> duties;
	JButton addDutyButton;
	
	GridBagConstraints gc, savedDutiesPanelgc, enterDataSubPanelgc;
	// Inner panel for organizing saved duties
	JPanel savedDutiesPanel, enterDataPanel;

	HashMap<JTextField, JButton> dutyFields;

	
	Panel_Duties()
	{
		// Pane borders
		setInnerBorder("Obowiązki");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		duties = new ArrayList<String>();
		dutyFields = new HashMap<JTextField, JButton>();
		
		dutyLabel = new JLabelStyle("Obowiązek");
		duty = new JTextFieldStyle();
		addDutyButton = new JButtonStyle("Dodaj obowiązek");

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Inner panels for:
		// - saved duties
		savedDutiesPanel = new JPanel();
		savedDutiesPanel.setLayout(new GridBagLayout());
		savedDutiesPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		savedDutiesPanelgc.insets = new Insets(5, 5, 5, 5);
		savedDutiesPanelgc.weightx = 100;
		savedDutiesPanelgc.weighty = 1;
		savedDutiesPanelgc.gridx = 0;
		savedDutiesPanelgc.gridy = 0;
		savedDutiesPanelgc.gridwidth = 1;
		add(savedDutiesPanel);
		// - entering data
		enterDataPanel = new JPanel();
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
	
	public void clearPanel()
	{
		for(HashMap.Entry<JTextField, JButton> entry : dutyFields.entrySet())
		{
			// Remove components
			savedDutiesPanel.remove(entry.getKey());
			// Remove components
			savedDutiesPanel.remove(dutyFields.get(entry.getKey()));
		}
		dutyFields.clear();
		duties.clear();
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
					savedDutiesPanelgc.fill = GridBagConstraints.BOTH;
					savedDutiesPanelgc.anchor = GridBagConstraints.FIRST_LINE_START;
					savedDutiesPanelgc.gridx = 0;
					savedDutiesPanelgc.gridy++;

					final JTextField dutyField = new JTextFieldStyle(duty.getText());
					// If the text is longer than the text field, the text fields shows the
					// beginning of the text
					dutyField.setCaretPosition(0);
					// Specifies how many columne the text field will occupy
					dutyField.setColumns(2);
					savedDutiesPanel.add(dutyField, savedDutiesPanelgc);

					savedDutiesPanelgc.gridwidth = 1;

					duties.add(duty.getText());
					duty.setText("");



					final JButton removeButton = new JButtonStyle("Usuń");

					removeButton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							removeDuty(dutyField);
						}
					});

					savedDutiesPanelgc.gridx = 1;

					savedDutiesPanelgc.weightx = 1;
					savedDutiesPanelgc.weighty = 100;

					savedDutiesPanelgc.fill = GridBagConstraints.VERTICAL;
					savedDutiesPanelgc.anchor = GridBagConstraints.FIRST_LINE_END;

					savedDutiesPanel.add(removeButton, savedDutiesPanelgc);
					// Repaint the panel
					savedDutiesPanel.revalidate();  // For JDK 1.7 or above.
					savedDutiesPanel.repaint();

					// Add the duty field and it's removal button to the
					// hash map <-- this will enable us later to remove them
					//              all at once
					dutyFields.put(dutyField, removeButton);
				}
			}
		});
	}

	// R:
	public void removeDuty(JTextField field)
	{
		// Remove duty
		duties.remove(field.getText());
		
		// Remove components
		savedDutiesPanel.remove(field);
		// Remove components
		savedDutiesPanel.remove(dutyFields.get(field));

		dutyFields.remove(field); 
	
		// Repaint the panel
		savedDutiesPanel.revalidate();
		savedDutiesPanel.repaint();
	}
}
