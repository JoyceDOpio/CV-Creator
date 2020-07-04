
import java.util.ArrayList;

class CV_WorkExperience
{
	// Name of the occupation title and the workplace
	String from, to, occupation, workplace;
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
	
	CV_WorkExperience(String from, String to, String occupation, String workplace, ArrayList<String> duties)
	{
		this.from = formatDate(from);
		this.to = formatDate(to);	
		this.occupation = occupation;
		this.workplace = workplace;
		this.duties = duties;		
	}

	// A:
	public void addDuty(String duty)
	{
		duties.add(duty);
	}
	
	// F:
	public String formatDate(String date)
	{
		// Replace "-" with "."
		return date.replace('-','.');
	}
	
	
	// G:
	
	public String getFrom(){return from;}
	
	public String getOccupation(){return occupation;}
	
	public String getTo(){return to;}
	
	public String getWorkplace(){return workplace;}
	
	// R:
	
	public void removeDuty(int index)
	{
		duties.remove(index);
	}
	
	public void removeDuty(String duty)
	{
		duties.remove(duty);
	}
	
	// S:
	
	public void setDuties(ArrayList<String> list)
	{
		this.duties = list;
	}
	
	public void setFrom(String from){this.from = from;}
	
	public void setOccupation(String occupation){this.occupation = occupation;}
	
	public void setTo(String to){this.to = to;}
	
	public void setWorkplace(String workplace){this.workplace = workplace;}	
	
	// T:
	
	public String toString()
	{	
		return getFrom() + "-" + getTo() + "; " + getOccupation() + "; " +
				getWorkplace();
	}
}