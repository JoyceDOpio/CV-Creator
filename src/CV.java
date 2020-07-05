
import java.util.ArrayList;
import java.util.HashMap;

public class CV 
{
	/* The CV is divided into several sections:
	 * - First name <-- 1 paragraph
	 * - Last name <-- 1 paragraph
	 * - Profession summary <-- 1 paragraph
	 * - Personal details: <-- a list
	 * 		name
	 * 		address (optional)
	 * 		email address
	 * 		phone number
	 * 		linkedin profile (optional)
	 * 		github profile (optional)
	 * - Work experience
	 * 		date(from-to)
	 * 		name of the occupation title
	 * 		name of the workplace 
	 * 		duty range
	 * - Education and Qualifications
	 * - Skills
	 * - Interests (optional)
	 * - Languages (optional)
	 * - References (optional)
	 * */

	String professionSummary;
	CV_PersonalDetails personalDetails;
	ArrayList<Object> workExp;
	ArrayList<Object> education;
	ArrayList<String> references;
	HashMap<String, String> skills, interests, languages;
	
	CV()
	{
		this.professionSummary = "";
		personalDetails = new CV_PersonalDetails();
		workExp = new ArrayList<Object>();
		education = new ArrayList<Object>();
		skills = new HashMap<String,String>();
		interests = new HashMap<String, String>();
		languages = new HashMap<String, String>();
		references = new ArrayList<String>();
	}

	// A:
	public void addWork(CV_WorkExperience work)
	{
		workExp.add(work);
	}
	public void addEducation(CV_Education edu)
	{
		education.add(edu);
	}
	public void addInterest(String key, String value)
	{
		interests.put(key,value);
	}
	public void addLanguage(String key, String value)
	{
		languages.put(key,value);
	}
	public void addReference(String ref)
	{
		references.add(ref);
	}
	public void addSkill(String key, String value)
	{
		skills.put(key,value);
	}

	// G
	public ArrayList<Object> getEducation(){return education;}
	public HashMap<String,String> getInterests(){return interests;}
	public HashMap<String,String> getLanguages(){return languages;}
	public CV_PersonalDetails getPersonalDetails(){return personalDetails;}
	public String getProfessionSummary(){return professionSummary;}
	public ArrayList<String> getReferences(){return references;}
	public HashMap<String,String> getSkills(){return skills;}
	public ArrayList<Object> getWorkExperience(){return workExp;}

	// S:
	public void setProfessionSummary(String summary)
	{
		this.professionSummary = summary;
	}
}
