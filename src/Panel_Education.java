
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Panel_Education extends Panel_Data
{
	JLabel toLabel, fromLabel, schoolLabel, courseLabel, specialisationLabel;
	JDatePickerImpl from, to;
	JTextField school, course, specialisation;
	JButton addButton;
	
	ArrayList<CV_Education> educationList;
	
	GridBagConstraints savedEduSubPanelgc, enterDataSubPanelgc;
	JPanel savedEducationPanel, enterDataPanel;

	HashMap<JTextField, JButton> educationFields;
	
	Panel_Education()
	{
		setInnerBorder("Wykształcenie");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		educationList = new ArrayList<CV_Education>();
		educationFields = new HashMap<JTextField, JButton>();
		
		fromLabel = new JLabelStyle("Od");
		toLabel = new JLabelStyle("Do");
		schoolLabel = new JLabelStyle("Nazwa uczelni/szkoły");
		courseLabel = new JLabelStyle("Kierunek");
		specialisationLabel = new JLabelStyle("Specjalizacja");
		
		// We have to create two separate models for two
		// date pickers
		UtilDateModel model = new UtilDateModel();
		UtilDateModel model2 = new UtilDateModel();

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		// Two separate date panels for variables "from" and "to"
		JDatePanelImpl fromDatePanel = new JDatePanelImpl(model, p);
		JDatePanelImpl toDatePanel2 = new JDatePanelImpl(model2, p);
		
		from = new JDatePickerImpl(fromDatePanel, new DateLabelFormatter());
		to = new JDatePickerImpl(toDatePanel2, new DateLabelFormatter());
		school = new JTextFieldStyle();
		course = new JTextFieldStyle();
		specialisation = new JTextFieldStyle();
		addButton = new JButtonStyle("Dodaj wykształcenie");

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		// Inner panels for:
		// - saved education
		savedEducationPanel = new JPanel();
		savedEducationPanel.setLayout(new GridBagLayout());
		savedEduSubPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		savedEduSubPanelgc.insets = new Insets(0, 5, 0, 5);
		savedEduSubPanelgc.weightx = 50;
		savedEduSubPanelgc.weighty = 1;
		savedEduSubPanelgc.gridx = 0;
		savedEduSubPanelgc.gridy = 0;
		add(savedEducationPanel);
		// - entering data
		enterDataPanel = new JPanel();
		enterDataPanel.setLayout(new GridBagLayout());
		enterDataSubPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		enterDataSubPanelgc.insets = new Insets(0, 5, 0, 5);
		enterDataSubPanelgc.weightx = 50;
		enterDataSubPanelgc.weighty = 1;
		enterDataSubPanelgc.gridx = 0;
		enterDataSubPanelgc.gridy = 0;
		add(enterDataPanel);
		// Place in the sub-panel the GUI elements for enetering data
		placeElementsInPanel(enterDataPanel, enterDataSubPanelgc);
	}

	// A:
	public void addEducationToPanel(CV_Education edu)
	{
		savedEduSubPanelgc.fill = GridBagConstraints.BOTH;
		savedEduSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_START;
		savedEduSubPanelgc.gridx = 0;
		savedEduSubPanelgc.gridy++;

		String info = edu.getFrom() + "-" + edu.getTo() + "; " + edu.getCourse() + "; " +
				edu.getSchool();

		final JTextField educationField = new JTextFieldStyle(info);
		// If the text is longer than the text field, the text fields shows the
		// beginning of the text
		educationField.setCaretPosition(0);
		// Specifies how many columne the text field will occupy
		educationField.setColumns(2);
		savedEducationPanel.add(educationField, savedEduSubPanelgc);

		final JButton removeButton = new JButtonStyle("Usuń");

		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeEducation(edu, educationField, removeButton);
			}
		});

		savedEduSubPanelgc.gridx = 1;

		savedEduSubPanelgc.weightx = 1;
		savedEduSubPanelgc.weighty = 100;

		savedEduSubPanelgc.fill = GridBagConstraints.VERTICAL;
		savedEduSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_END;

		savedEducationPanel.add(removeButton, savedEduSubPanelgc);

		// Repaint the panel
		savedEducationPanel.revalidate();  // For JDK 1.7 or above.
		savedEducationPanel.repaint();

		// Add the duty field and it's removal button to the
		// hash map <-- this will enable us later to remove them
		//              all at once
		educationFields.put(educationField, removeButton);
	}

	// C:
	public String chooseDateFormat()
	{
		if(from.getModel().getYear() == to.getModel().getYear() && from.getModel().getMonth() == to.getModel().getMonth())
		{
			return "yyyy-MM-dd";
		}
		else
		{
			return "yyyy-MM";
		}
	}
	
	public void clearEnterDataPanel()
	{
		from.getModel().setValue(null);
		to.getModel().setValue(null);
		school.setText("");
		course.setText("");
		specialisation.setText("");
	}

	@Override
	public void clearPanel()
	{
		clearEnterDataPanel();
		clearSavedEduPanel();
	}

	public void clearSavedEduPanel()
	{
		for(HashMap.Entry<JTextField, JButton> entry : educationFields.entrySet())
		{
			// Remove components from the panel for the saved educations
			savedEducationPanel.remove(entry.getKey());
			savedEducationPanel.remove(entry.getValue());
		}
		educationFields.clear();
		educationList.clear();

		// Repaint the panel
		savedEducationPanel.revalidate();
		savedEducationPanel.repaint();
	}
	
	public ArrayList<CV_Education> collectInformation()
	{		
		ArrayList<CV_Education> eduList = new ArrayList<CV_Education>(educationList);
		Collections.copy(eduList, educationList);
		clearEnterDataPanel();
		educationList.clear();
		
		return eduList;
	}

	// I:
	public void insertInformation(ArrayList<CV_Education> list)
	{
		educationList = list;

		for(CV_Education edu : educationList)
		{
			addEducationToPanel(edu);
		}
	}

	public boolean isPanelcompleted()
	{
		if(from != null)
		{
			if(to != null)
			{
				if(school.getText().length() != 0)
				{
					if(course.getText().length() != 0)
					{
						if(specialisation.getText().length() != 0)
							return true;
						else
							return false;
					}
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean isPanelEmpty()
	{
		// Return true, if there are no education objects saved
		if(educationList.isEmpty())
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

		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		// First row
		gc.gridy++;
		panel.add(fromLabel, gc);
		gc.gridx = 1;
		panel.add(toLabel, gc);

		// Second row
		gc.gridx = 0;
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(from, gc);
		gc.gridx = 1;
		panel.add(to, gc);

		gc.gridx = 0;
		gc.gridwidth = 2;

		// Third row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		panel.add(schoolLabel, gc);

		// Fourth row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(school, gc);

		// Fifth row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		panel.add(courseLabel, gc);

		// Sixth row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(course, gc);

		// Seventh row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		panel.add(specialisationLabel, gc);

		// Eighth row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(specialisation, gc);

		// Ninth row
		gc.weightx = 1;
		gc.weighty = 100;
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		gc.insets = new Insets(10, 10, 10, 10);
		panel.add(addButton,gc);

		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String datePattern = chooseDateFormat();
				SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

				if(isPanelcompleted())
				{
					final CV_Education edu = new CV_Education(
							dateFormatter.format(from.getModel().getValue()),
							dateFormatter.format(to.getModel().getValue()),
							school.getText(),course.getText(),
							specialisation.getText());
					educationList.add(edu);

					// Add education representation to the panel
					addEducationToPanel(edu);
					// Clear the panel for entering data
					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pola dla \"Wykształcenie\"");
				}
			}
		});
	}

	// R:
	public void removeEducation(CV_Education education, JTextField field, JButton button)
	{
		// Remove duty
		educationList.remove(education);
		// Remove components
		savedEducationPanel.remove(field);
		savedEducationPanel.remove(button);
		// Repaint the panel
		savedEducationPanel.revalidate();
		savedEducationPanel.repaint();
	}
}
