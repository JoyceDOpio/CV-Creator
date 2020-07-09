
public class CV_Education
{
	Date from, to;
	String school, course, specialisation;
	
	CV_Education()
	{
		from = null;
		to = null;
		
		school = "";
		course = "";
		specialisation = "";		
	}
	
	CV_Education(Date from, Date to, String school, String course, String specialisation)
	{
		this.from = from;
		this.to = to;
		this.school = school;
		this.course = course;
		this.specialisation = specialisation;		
	}

	// G:
	public String getCourse(){return course;}
	
	public Date getFrom(){return from;}
	
	public String getSchool(){return school;} 

	public String getSpecialisation(){return specialisation;}

	public Date getTo(){return to;}
	
	// I:
	
	public String intoText()
	{
		return from.toString() + " - " + to.toString() +
				"\n" + school + "\n" + course + "\n" + specialisation;
	}
	
	// S:
	
	public void setCourse(String course){this.course = course;}
	
	public void setFrom(Date from)
	{
		this.from = from;
	}
	
	public void setSchool(String school){this.school = school;}
	
	public void setSpecialisation(String spec){this.specialisation = spec;}
	
	public void setTo(Date to)
	{
		this.to = to;
	}

	// T:
	public String toString()
	{	
		return getFrom().toString() + "-" + getTo().toString() + "; " + getCourse() + "; " +
				getSchool();
	}
}
