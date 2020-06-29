
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

//import com.itextpdf.kernel.color.Color;

public class Panel_WorkExperience extends Panel_Data
{
	JLabel toLabel, fromLabel, titleLabel, workplaceLabel;
	JDatePickerImpl from, to;
	JTextField title, workplace;
	JButton addButton;
	
	ArrayList<CV_WorkExperience> workExperienceList;
	
	GridBagConstraints savedWorkExpSubPanelgc, enterDataSubPanelgc;
	// Panels for representing saved work experiences and entering data
	JPanel savedWorkExperiencePanel, enterDataPanel;
	final Panel_Duties dutiesPanel;

	HashMap<JTextField, JButton> workExpFields;

	Panel_WorkExperience()
	{
		setInnerBorder("Doświadczenie zawodowe");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		workExperienceList = new ArrayList<CV_WorkExperience>();
		workExpFields = new HashMap<JTextField, JButton>();

		fromLabel = new JLabelStyle("Od");
		toLabel = new JLabelStyle("Do");
		titleLabel = new JLabelStyle("Pełnione stanowisko");
		workplaceLabel = new JLabelStyle("Miejsce pracy");
		
		dutiesPanel = new Panel_Duties();
		
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
		JDatePanelImpl toDatePanel = new JDatePanelImpl(model2, p);
				
		from = new JDatePickerImpl(fromDatePanel, new DateLabelFormatter());
		to = new JDatePickerImpl(toDatePanel, new DateLabelFormatter());
		title = new JTextFieldStyle();
		workplace = new JTextFieldStyle();
		addButton = new JButtonStyle("Dodaj doświadczenie");

		// Layout of the main panel
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		// Inner panels for:
		// - saved work experience
		savedWorkExperiencePanel = new JPanel();
		savedWorkExperiencePanel.setLayout(new GridBagLayout());
		savedWorkExpSubPanelgc = new GridBagConstraints();
		// Insets create indents - 5 pixels from bottom and from
		// the right side
		savedWorkExpSubPanelgc.insets = new Insets(0, 5, 0, 5);
		savedWorkExpSubPanelgc.weightx = 50;
		savedWorkExpSubPanelgc.weighty = 1;
		savedWorkExpSubPanelgc.gridx = 0;
		savedWorkExpSubPanelgc.gridy = 0;
//		add(savedWorkExperiencePanel, gc);
		add(savedWorkExperiencePanel);
		// - enter data
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
//		add(enterDataPanel, gc);
		add(enterDataPanel);
		// Place in the sub-panel the GUI elements for enetering data
		placeElementsInPanel(enterDataPanel,enterDataSubPanelgc);
	}

	// A:
	public void addWorkExperienceToPanel(CV_WorkExperience work)
	{
		savedWorkExpSubPanelgc.fill = GridBagConstraints.BOTH;
		savedWorkExpSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_START;
		savedWorkExpSubPanelgc.gridx = 0;
		savedWorkExpSubPanelgc.gridy++;

		String info = work.getFrom() + "-" + work.getTo() + "; " + work.getOccupation() + "; " +
				work.getWorkplace();

		final JTextField workExperienceField = new JTextFieldStyle(info);
		// If the text is longer than the text field, the text fields shows the
		// beginning of the text
		workExperienceField.setCaretPosition(0);
		// Specifies how many columne the text field will occupy
		workExperienceField.setColumns(2);
		savedWorkExperiencePanel.add(workExperienceField, savedWorkExpSubPanelgc);

		final JButton removeButton = new JButtonStyle("Usuń");

		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeWorkExperience(work, workExperienceField, removeButton);
			}
		});

		savedWorkExpSubPanelgc.gridx = 1;

		savedWorkExpSubPanelgc.weightx = 1;
		savedWorkExpSubPanelgc.weighty = 100;

		savedWorkExpSubPanelgc.fill = GridBagConstraints.VERTICAL;
		savedWorkExpSubPanelgc.anchor = GridBagConstraints.FIRST_LINE_END;

		savedWorkExperiencePanel.add(removeButton, savedWorkExpSubPanelgc);

		// Repaint the panel
		savedWorkExperiencePanel.revalidate();  // For JDK 1.7 or above.
		savedWorkExperiencePanel.repaint();

		// Add the duty field and it's removal button to the
		// hash map <-- this will enable us later to remove them
		//              all at once
		workExpFields.put(workExperienceField, removeButton);
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
		to.getModel().setValue(null);
		from.getModel().setValue(null);
		title.setText("");
		workplace.setText("");
	}

	@Override
	public void clearPanel()
	{
		clearEnterDataPanel();
		clearSavedWorkExpPanel();
	}

	public void clearSavedWorkExpPanel()
	{
		for(HashMap.Entry<JTextField, JButton> entry : workExpFields.entrySet())
		{
			// Remove components from the panel for saved work experiences
			savedWorkExperiencePanel.remove(entry.getKey());
			savedWorkExperiencePanel.remove(entry.getValue());
		}
		workExpFields.clear();
		workExperienceList.clear();

		// Repaint the panel
		savedWorkExperiencePanel.revalidate();
		savedWorkExperiencePanel.repaint();
	}
	
	public ArrayList<CV_WorkExperience> collectInformation()
	{
		ArrayList<CV_WorkExperience> workList = new ArrayList<CV_WorkExperience>(workExperienceList);
		Collections.copy(workList, workExperienceList);
		clearEnterDataPanel();
		workExperienceList.clear();
		
		return workList;
	}
	
	// I:

	public void insertInformation(ArrayList<CV_WorkExperience> list)
	{
		workExperienceList = list;

		for(CV_WorkExperience work : workExperienceList)
		{
			addWorkExperienceToPanel(work);
		}
	}
	
	public boolean isPanelCompleted()
	{
		if(from != null)
		{
			if(to != null)
			{
				if(title.getText().length() != 0)
				{
					if(workplace.getText().length() != 0)
					{
						return true;
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
		// Return true, if there are no work experience
		// objects saved
		if(workExperienceList.isEmpty())
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

		// Third row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		panel.add(titleLabel, gc);

		gc.gridwidth = 2;
		// Fourth row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(title, gc);

		// Fifth row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		panel.add(workplaceLabel, gc);

		// Sixth row
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(workplace, gc);

		// Seventh row
		gc.gridy++;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(dutiesPanel, gc);

		// Eighth row
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		gc.gridy++;
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

				if(isPanelCompleted())
				{
					ArrayList<String> duties = dutiesPanel.collectInformation();

					final CV_WorkExperience work = new CV_WorkExperience(
							dateFormatter.format(from.getModel().getValue()),
							dateFormatter.format(to.getModel().getValue()),
							title.getText(), workplace.getText(), duties);

					dutiesPanel.clearPanel();

					workExperienceList.add(work);
					// Add work experience representation to the panel
					addWorkExperienceToPanel(work);
					// Clear panel for entering data
					clearEnterDataPanel();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Wypełnij pola dla \"Doświadczenie zawodowe\"");
				}
			}
		});
	}
		
	// R:
	public void removeWorkExperience(CV_WorkExperience workExp, JTextField field, JButton button)
	{
		// Remove duty
		workExperienceList.remove(workExp);
		// Remove components
		savedWorkExperiencePanel.remove(field);
		savedWorkExperiencePanel.remove(button);
		// Repaint the panel
		savedWorkExperiencePanel.revalidate();
		savedWorkExperiencePanel.repaint();
	}
}












