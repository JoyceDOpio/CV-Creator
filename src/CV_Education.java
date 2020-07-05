
public class CV_Education
{
	String from, to;
	String school, course, specialisation;
	
	CV_Education()
	{
		from = null;
		to = null;
		
		school = "";
		course = "";
		specialisation = "";		
	}
	
	CV_Education(String from, String to, String school, String course, String specialisation)
	{
		this.from = from;
		this.to = to;
		this.school = school;
		this.course = course;
		this.specialisation = specialisation;		
	}

	// G:
	public String getCourse(){return course;}
	
	public String getFrom(){return from;}
	
	public String getSchool(){return school;} 

	public String getSpecialisation(){return specialisation;}

	public String getTo(){return to;}
	
	// I:
	
	public String intoText()
	{
		return from + " - " + to + "\n" + school + "\n" + course + "\n" + specialisation;
	}
	
	// S:
	
	public void setCourse(String course){this.course = course;}
	
	public void setFrom(String from){this.from = from;}
	
	public void setSchool(String school){this.school = school;}
	
	public void setSpecialisation(String spec){this.specialisation = spec;}
	
	public void setTo(String to){this.to = to;}

	// T:
	
	public String toString()
	{	
		return getFrom() + "-" + getTo() + "; " + getCourse() + "; " +
				getSchool();
	}
}
