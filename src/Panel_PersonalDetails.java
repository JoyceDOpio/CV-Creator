
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
//import javax.swing.JPanel;


public class Panel_PersonalDetails extends Panel_Data
{
	JLabel firstNameLabel, lastNameLabel, emailLabel, phoneLabel,
			linkedInLabel, githubLabel, countryLabel;
//	JTextField firstName, lastName, email, phone,
//			linkedIn, github,country;
	ArrayList<JTextFieldStyle> personalDetailsTextFields;
	
	Panel_PersonalDetails()
	{		
		setInnerBorder("Dane osobowe");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		personalDetailsTextFields = new ArrayList<JTextFieldStyle>(
				Arrays.asList(new JTextFieldStyle(),
				new JTextFieldStyle(),new JTextFieldStyle(),
				new JTextFieldStyle(),new JTextFieldStyle(),
				new JTextFieldStyle(),new JTextFieldStyle()));
		
		firstNameLabel = new JLabelStyle("Imię");
		lastNameLabel = new JLabelStyle("Nazwisko");
		emailLabel = new JLabelStyle("E-mail");
		phoneLabel = new JLabelStyle("Telefon");
		linkedInLabel = new JLabelStyle("LinkedIn");
		githubLabel = new JLabelStyle("Github");
		countryLabel = new JLabelStyle("Kraj");

		for(int i = 0; i < personalDetailsTextFields.size(); i++)
		{
			// Specifies how many columns the text field will occupy
			personalDetailsTextFields.get(i).setColumns(2);
		}
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.weightx = 100;
		gc.weighty = 1;
		
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;		
		// Insets create indents - 5 pixels from bottom and from 
		// the right side
		gc.insets = new Insets(0, 5, 0, 5);
		
		gc.gridwidth = 2;
						
		// First row
		add(firstNameLabel, gc);
		
		// Second row - first name
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		add(personalDetailsTextFields.get(0), gc);
		
		// Third row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		add(lastNameLabel, gc);
		
		// Fourth row - last name
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		add(personalDetailsTextFields.get(1), gc);
		
		gc.gridwidth = 1;

		// Fifth row
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		add(emailLabel, gc);
		gc.gridx = 1;
		add(phoneLabel, gc);
		
		// Sixth row - e-mail and phone
		gc.gridx = 0;
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		add(personalDetailsTextFields.get(2), gc);
		gc.gridx = 1;
		add(personalDetailsTextFields.get(3), gc);
		
		// Seventh row
		gc.gridx = 0;
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		add(linkedInLabel, gc);
		gc.gridx = 1;
		add(githubLabel, gc);
		
		// Eighth row		
		gc.gridx = 0;
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		add(personalDetailsTextFields.get(4), gc);
		gc.gridx = 1;
		add(personalDetailsTextFields.get(5), gc);
		
		// Ninth row
		gc.gridx = 0;
		gc.gridy++;
		gc.fill = GridBagConstraints.NONE;
		add(countryLabel, gc);
		
		// Tenth row
		gc.gridx = 0;
		gc.gridy++;
		gc.fill = GridBagConstraints.BOTH;
		add(personalDetailsTextFields.get(6), gc);
	}

	// C:
	@Override
	public void clearPanel()
	{
		for(int i = 0; i < personalDetailsTextFields.size(); i++)
			personalDetailsTextFields.get(i).setText("");
	}

	public CV_PersonalDetails collectInformation()
	{
		CV_PersonalDetails personalDetailsObject = new CV_PersonalDetails();
		
		personalDetailsObject.setFirstName(personalDetailsTextFields.get(0).getText());
		personalDetailsObject.setLastName(personalDetailsTextFields.get(1).getText());
		personalDetailsObject.setEmail(personalDetailsTextFields.get(2).getText());
		personalDetailsObject.setPhone(personalDetailsTextFields.get(3).getText());
		personalDetailsObject.setLinkedIn(personalDetailsTextFields.get(4).getText());
		personalDetailsObject.setGithub(personalDetailsTextFields.get(5).getText());
		personalDetailsObject.setCountry(personalDetailsTextFields.get(6).getText());
		
		return personalDetailsObject;
	}

	// I:
	public void insertInformation(CV_PersonalDetails detailsObject)
	{
		personalDetailsTextFields.get(0).setText(detailsObject.getFirstName());
		personalDetailsTextFields.get(1).setText(detailsObject.getLastName());
		personalDetailsTextFields.get(2).setText(detailsObject.getEmail());
		personalDetailsTextFields.get(3).setText(detailsObject.getPhone());
		personalDetailsTextFields.get(4).setText(detailsObject.getLinkedin());
		personalDetailsTextFields.get(5).setText(detailsObject.getGithub());
		personalDetailsTextFields.get(6).setText(detailsObject.getCountry());
		// If the text is longer than the text field, the text fields shows the
		// beginning of the text
		for(int i = 0; i < personalDetailsTextFields.size(); i++)
			personalDetailsTextFields.get(i).setCaretPosition(0);
	}

	@Override
	public boolean isPanelEmpty()
	{
		for(int i = 0; i < personalDetailsTextFields.size(); i++)
		{
			if(!personalDetailsTextFields.get(i).getText().equals(""))
				return false;
		}

		return true;
	}
}
