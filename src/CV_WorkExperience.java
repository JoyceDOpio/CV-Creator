
import java.util.ArrayList;
import java.util.Collections;

class CV_WorkExperience
{
	// Name of the occupation title and the workplace
	Date from, to;
	String occupation, workplace;
	// Duty range
	ArrayList<String> duties;
	
	CV_WorkExperience()
	{
		from = null;
		to = null;
		
		occupation = "";
		workplace = "";
		duties = new ArrayList<String>();		
	}
	
	CV_WorkExperience(Date from, Date to, String occupation, String workplace, ArrayList<String> duties)
	{
		this.from = from;
		this.to = to;
		this.occupation = occupation;
		this.workplace = workplace;
		this.duties = duties;		
	}

	// A:
	public void addDuty(String duty)
	{
		duties.add(duty);
	}

	
	// G:
	public ArrayList<String> getDuties(){return duties;}

	public Date getFrom(){return from;}
	
	public String getOccupation(){return occupation;}
	
	public Date getTo(){return to;}
	
	public String getWorkplace(){return workplace;}
	
	// S:
	public void setDuties(ArrayList<String> list)
	{
//		duties = new ArrayList<String>(list);
//		Collections.copy(duties, list);// <-- ALWAYS COPY - NEVER use the array of interest
		                               //     directly, because it changes it's content
		duties = list;
	}
	
	public void setFrom(Date from){this.from = from;}
	
	public void setOccupation(String occupation){this.occupation = occupation;}
	
	public void setTo(Date to){this.to = to;}
	
	public void setWorkplace(String workplace){this.workplace = workplace;}	
	
	// T:
	public String toString()
	{	
		return getFrom().toString() + "-" + getTo().toString() + "; " + getOccupation() + "; " +
				getWorkplace();
	}
}