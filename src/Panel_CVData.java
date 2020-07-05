
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

public class Panel_CVData extends JPanel
{
	ArrayList<Panel_Data> subPanels;
	
	Panel_CVData()
	{
		// Sub-panels
		subPanels = new ArrayList<Panel_Data>(Arrays.asList(new Panel_PersonalDetails(),
				new Panel_ProfessionSummary(),new Panel_WorkExperience(),
				new Panel_Education(), new Panel_Skills(),
				new Panel_Languages(), new Panel_Interests(),
				new Panel_References()));
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.weightx = 100;
		gc.weighty = 1;
		
		gc.fill = GridBagConstraints.BOTH;

		// Place sub-panels in the main panel
		for(int i = 0; i < subPanels.size(); i++)
		{
			// gridy - specifies row in which the
			//         sub-panel will be placed
			gc.gridy = i;
			add(subPanels.get(i), gc);
		}
	}

	// A:
	public boolean arePanelsEmpty()
	{
		for(int i = 0; i < subPanels.size(); i++)
		{
			if(!subPanels.get(i).isPanelEmpty())
			{
				return false;
			}
		}
		return true;
	}

	// C:
	public void clearPanels()
	{
		// Clear all sub-panels
		for(int i = 0; i < subPanels.size(); i++)
		{
			subPanels.get(i).clearPanel();// <-- NOTE: when I added an empty
			                              //     clearPanel() method to the
			                              //     DataPanel() class, I don't have
			                              //     to downcast to specific panel
			                              //     types
		}
	}

	public CV collectInformation()
	{
		CV cvObject = new CV();

		cvObject.personalDetails = ((Panel_PersonalDetails)subPanels.get(0)).collectInformation();
		cvObject.professionSummary = ((Panel_ProfessionSummary)subPanels.get(1)).collectInformation();
		cvObject.workExp = ((Panel_WorkExperience)subPanels.get(2)).collectInformation();
		cvObject.education = ((Panel_Education) subPanels.get(3)).collectInformation();
		cvObject.skills = ((Panel_Skills) subPanels.get(4)).collectInformation();
		cvObject.languages = ((Panel_Languages) subPanels.get(5)).collectInformation();
		cvObject.interests = ((Panel_Interests) subPanels.get(6)).collectInformation();
		cvObject.references = ((Panel_References) subPanels.get(7)).collectInformation();

		return cvObject;
	}

	// I:
	public void insertInformation(CV cvObject)
	{
		// Personal details
		((Panel_PersonalDetails) subPanels.get(0)).insertInformation(cvObject.getPersonalDetails());
		// Profession summary
		((Panel_ProfessionSummary) subPanels.get(1)).insertInformation(cvObject.getProfessionSummary());
		// Work experience
		((Panel_WorkExperience) subPanels.get(2)).insertInformation(cvObject.getWorkExperience());
		// Education
		((Panel_Education) subPanels.get(3)).insertInformation(cvObject.getEducation());
		// Skills
		((Panel_Skills) subPanels.get(4)).insertInformation(cvObject.getSkills());
		// Languages
		((Panel_Languages) subPanels.get(5)).insertInformation(cvObject.getLanguages());
		// Interests
		((Panel_Interests) subPanels.get(6)).insertInformation(cvObject.getInterests());
		// References
		((Panel_References) subPanels.get(7)).insertInformation(cvObject.getReferences());
	}
}
