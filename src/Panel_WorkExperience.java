import org.jdatepicker.impl.JDatePanelImpl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

public class Panel_WorkExperience extends Panel_Objects
{
	JPanel dutiesPanel;

	Panel_WorkExperience()
	{
		// Panel borders
		setInnerBorder(" Doświadczenie zawodowe ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		// A list for work experience objects
		objectList = new ArrayList<>();
		
		dutiesPanel = new Panel_Duties();
		setAddButtonText("Dodaj doświadczenie");

		// Place in the sub-panel the GUI elements for entering data
		placeElementsInPanel((Panel_EnterData) enterDataPanel);
	}

	@Override
	public void addAdditionalComponents(Panel_EnterData panel)
	{
		// Next row
		panel.gc.gridy++;
		panel.gc.gridwidth = 2;
		panel.gc.fill = GridBagConstraints.BOTH;
		panel.add(dutiesPanel, panel.gc);
	}

	@Override
	public void addObject()
	{
		if(isPanelCompleted())
		{
			if(!doesObjectExist())
			{
				ArrayList<String> duties = ((Panel_Duties) dutiesPanel).collectInformation();

				final CV_WorkExperience work = new CV_WorkExperience(
						new Date(from.getModel().getYear(),from.getModel().getMonth(),from.getModel().getDay()),
						new Date(to.getModel().getYear(),to.getModel().getMonth(),to.getModel().getDay()),
						((JTextField) singleComponents.get(0)).getText(),
						((JTextField) singleComponents.get(1)).getText(), duties);

				((Panel_Duties) dutiesPanel).clearPanel();

				objectList.add(work);
				// Add work experience representation to the panel
				((Panel_SavedObjects) savedObjectsPanel).addObjectRepresentationToPanel(work, ((CV_WorkExperience) work).toString());
				// Clear the panel for entering data
				clearEnterDataPanel();
			}
			else
				JOptionPane.showMessageDialog(null, "Wpis o takich samych danych już istnieje.");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Wypełnij pola dla \"Doświadczenie zawodowe\"");
		}
	}

	// C:
	@Override
	public void clearPanel()
	{
		clearEnterDataPanel();
		objectList.clear();
		((Panel_SavedObjects) savedObjectsPanel).clearPanel();

		// Clear duties
		((Panel_Duties) dutiesPanel).clearPanel();
	}
	
	public ArrayList<Object> collectInformation()
	{
		ArrayList<Object> workList = new ArrayList<Object>(objectList);
		Collections.copy(workList, objectList);
		clearEnterDataPanel();
		objectList.clear();

		return workList;
	}

	// D:
	// Check whether a similar object already exists
	public boolean doesObjectExist()
	{
		String fromDate = from.getJFormattedTextField().getText().replace('-','.');
		String toDate = to.getJFormattedTextField().getText().replace('-','.');
		String occupation = ((JTextField) singleComponents.get(0)).getText();
		String workplace = ((JTextField) singleComponents.get(1)).getText();

		for(Object object : objectList)
		{
			CV_WorkExperience work = (CV_WorkExperience) object;

			if(work.getFrom().equals(fromDate) && work.getTo().equals(toDate) &&
					occupation.equals(work.getOccupation()) && workplace.equals(work.getWorkplace()))
				return true;
		}
		return false;
	}

	// I:
	@Override
	public void insertInformation(ArrayList<Object> list)
	{
		objectList = list;

		for(Object work : objectList)
		{
			((Panel_SavedObjects) savedObjectsPanel).addObjectRepresentationToPanel(work, ((CV_WorkExperience) work).toString());
		}
	}

	@Override
	public boolean isPanelCompleted()
	{
		if(from != null && to != null &&
				((JTextField) singleComponents.get(0)).getText().length() != 0 &&
				((JTextField) singleComponents.get(1)).getText().length() != 0)
			return true;
		else
			return false;
	}

	// S:
	@Override
	public Panel_SavedObjects setSavedObjectPanel()
	{
		return new Panel_SavedObjects()
		{
			@Override
			public void displayObject(Object object)
			{
				CV_WorkExperience work = (CV_WorkExperience) object;

				((JTextField) singleComponents.get(0)).setText(work.getOccupation());
				((JTextField) singleComponents.get(1)).setText(work.getWorkplace());

				// Clear duties panel from previously displayed duties
				((Panel_Duties) dutiesPanel).clearPanel();
				// Show duties
				showDuties(object);
			}

			@Override
			public void hideObject(Object object, JButton objectButton)
			{
				// Collect new information
				((CV_WorkExperience) object).setOccupation(((JTextField) singleComponents.get(0)).getText());
				((CV_WorkExperience) object).setWorkplace(((JTextField) singleComponents.get(1)).getText());
				((CV_WorkExperience) object).setDuties(((Panel_Duties) dutiesPanel).collectInformation());

				objectButton.setText(((CV_WorkExperience) object).toString());

				// Clear the panel for entering data
				clearEnterDataPanel();
				// Clear duties panel
				((Panel_Duties) dutiesPanel).clearPanel();
			}

			@Override
			public void removeObject(Object object)
			{
				// Remove work experience object
				objectList.remove((CV_WorkExperience) object);
			}
		};
	}

	@Override
	public void setOtherComponents()
	{
		singleLabels = new ArrayList<JLabel>(Arrays.asList(new JLabelStyle("Pełnione stanowisko"),
				new JLabelStyle("Miejsce pracy")));

		singleComponents = new ArrayList<>(Arrays.asList(new JTextFieldStyle(),new JTextFieldStyle()));
	}

	public void showDuties(Object object)
	{
		CV_WorkExperience work = (CV_WorkExperience) object;
		((Panel_Duties) dutiesPanel).insertInformation(work.getDuties());
	}
}












