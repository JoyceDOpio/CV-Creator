
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;

public class Panel_Education extends Panel_Objects
{
	Panel_Education()
	{
		// Panel borders
		setInnerBorder(" Wykształcenie ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		objectList = new ArrayList<>();

		setAddButtonText("Dodaj wykształcenie");

		// Place in the sub-panel the GUI elements for entering data
		placeElementsInPanel((Panel_EnterData) enterDataPanel);
	}

	// A:
	@Override
	public void addAdditionalComponents(Panel_EnterData panel){}

	@Override
	public void addObject()
	{
		if(isPanelCompleted())
		{
			if(!doesObjectExist())
			{
				final CV_Education edu = new CV_Education(
						new Date(from.getModel().getYear(),from.getModel().getMonth(),from.getModel().getDay()),
						new Date(to.getModel().getYear(),to.getModel().getMonth(),to.getModel().getDay()),
						// School name
						((JTextField) singleComponents.get(0)).getText(),
						// Course
						((JTextField) singleComponents.get(1)).getText(),
						// Specialisation
						((JTextField) singleComponents.get(2)).getText());

				objectList.add(edu);

				// Add education representation to the panel
				((Panel_SavedObjects) savedObjectsPanel).addObjectRepresentationToPanel(edu, ((CV_Education) edu).toString());
				// Clear the panel for entering data
				clearEnterDataPanel();
			}
			else
				JOptionPane.showMessageDialog(null, "Wpis o takich samych danych już istnieje.");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Wypełnij pola dla \"Wykształcenie\"");
		}
	}

	// C:
	public ArrayList<Object> collectInformation()
	{
		ArrayList<Object> eduList = new ArrayList<Object>(objectList);
		Collections.copy(eduList, objectList);
		clearEnterDataPanel();
		objectList.clear();

		return eduList;
	}

	// D:
	// Check whether a similar object already exists
	public boolean doesObjectExist()
	{
		String fromDate = from.getJFormattedTextField().getText().replace('-','.');
		String toDate = from.getJFormattedTextField().getText().replace('-','.');
		String school = ((JTextField) singleComponents.get(0)).getText();
		String course = ((JTextField) singleComponents.get(1)).getText();
		String specialisation = ((JTextField) singleComponents.get(2)).getText();

		for(Object object : objectList)
		{
			CV_Education edu = (CV_Education) object;

			if(edu.getFrom().equals(fromDate) && edu.getTo().equals(toDate) &&
					school.equals(edu.getSchool()) && course.equals(edu.getCourse()) &&
					specialisation.equals(edu.getSpecialisation()))
				return true;
		}

		return false;
	}

	// I:
	@Override
	public void insertInformation(ArrayList<Object> list)
	{
		objectList = list;

		for(Object edu : objectList)
		{
			((Panel_SavedObjects) savedObjectsPanel).addObjectRepresentationToPanel(edu, ((CV_Education) edu).toString());
		}
	}

	@Override
	public boolean isPanelCompleted()
	{
		if(from != null && to != null &&
				((JTextField) singleComponents.get(0)).getText().length() != 0 &&
				((JTextField) singleComponents.get(1)).getText().length() != 0 &&
				((JTextField) singleComponents.get(2)).getText().length() != 0)
			return true;
		else
			return false;
	}

	@Override
	public Panel_SavedObjects setSavedObjectPanel()
	{
		return new Panel_SavedObjects()
		{
			@Override
			public void displayObject(Object object)
			{
				CV_Education edu = (CV_Education) object;

				((JTextField) singleComponents.get(0)).setText(edu.getSchool());
				((JTextField) singleComponents.get(1)).setText(edu.getCourse());
				((JTextField) singleComponents.get(2)).setText(edu.getSpecialisation());
			}

			@Override
			public void hideObject(Object object, JButton objectButton)
			{
				((CV_Education) object).setSchool(((JTextField) singleComponents.get(0)).getText());
				((CV_Education) object).setCourse(((JTextField) singleComponents.get(1)).getText());
				((CV_Education) object).setSpecialisation(((JTextField) singleComponents.get(2)).getText());

				objectButton.setText(((CV_Education) object).toString());

				// Clear panel for entering data
				clearEnterDataPanel();
			}

			@Override
			public void removeObject(Object object)
			{
				// Remove education object
				objectList.remove((CV_Education) object);
			}
		};
	}

	@Override
	public void setOtherComponents()
	{
		singleLabels = new ArrayList<>(Arrays.asList(new JLabelStyle("Nazwa uczelni/szkoły"),
				new JLabelStyle("Kierunek"), new JLabelStyle("Specjalizacja")));

		// JTextFields for school name, course and specialisation
		singleComponents = new ArrayList<>(Arrays.asList(new JTextFieldStyle(),new JTextFieldStyle(),
				new JTextFieldStyle()));
	}
}
