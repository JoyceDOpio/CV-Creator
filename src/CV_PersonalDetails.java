
public class CV_PersonalDetails
{
	String firstName, lastName, email, phone, linkedIn, github, country;
	
	CV_PersonalDetails()
	{
		firstName = "";
		lastName = "";
		email = "";
		phone = "";
		linkedIn = "";
		github = "";
		country = "";
	}
	
	// G:
	
	public String getCountry(){return country;}

	public String getEmail(){return email;}
	
	public String getFirstName(){return firstName;}

	public String getGithub(){return github;}

	public String getLastName(){return lastName;}

	public String getLinkedin(){return linkedIn;}

	public String getPhone(){return phone;}

	
	// S:
	
	public void setCountry(String country){this.country = country;}
	
	public void setEmail(String email){this.email = email;}
	
	public void setFirstName(String firstName){this.firstName = firstName;}
	
	public void setGithub(String github){this.github = github;}
	
	public void setLastName(String lastName){this.lastName = lastName;}
	
	public void setLinkedIn(String linkedIn){this.linkedIn = linkedIn;}
	
	public void setPhone(String phone){this.phone = phone;}
}