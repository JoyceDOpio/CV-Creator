
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class SimpleTemplate extends Document 
{
	BaseFont baseFont = null;{
		try 
		{
			baseFont = BaseFont.createFont("Fonts/arialuni.TTF",
					BaseFont.CP1250, BaseFont.EMBEDDED);

		} 
		catch (IOException | DocumentException e) 
		{
			e.printStackTrace();
		}  
	}
	
	Font font = new Font(baseFont, 12);
	Font bigFont = new Font(baseFont, 18);
	Font mediumFont = new Font(baseFont, 14);
	
	CV cv;
	
	SimpleTemplate(CV cv, String path)
	{
		this.cv = cv;

        saveAs(path);
	}
	
	// B:
	public void buildCV()
	{
        // Open document
        open();
        
        // Add content:
		// - Names
		buildName();
		// - Personal details
		buildPersonalDetails();
		// - Profession summary
		buildProfessionSummary();
		// - Work Experience
		buildWorkExp();
		// - Education
		buildEducation();
		// - Skills
		buildSkills();
		// - Languages
		buildLanguages();
		// - Interests
		buildInterests();
		// - References
		buildReferences();

        // Close document
        close();
	}
	
	// A function that builds education paragraph
	public boolean buildEducation()
	{
		if(!cv.education.isEmpty())
		{
			String text = "";

			for(Object edu : cv.education)
			{
				text = text + ((CV_Education) edu).intoText() + "\n";
				text = text + "\n";
			}
			// Remove last new line character
			text = text.substring(0, text.length() - 1);

			try
			{
				Paragraph p = new Paragraph("WYKSZTAŁCENIE", mediumFont);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				add(p);
				p = new Paragraph(text, font);
				add(p);
				// If adding the paragraph was successful,
				// return true
				return true;
			}
			catch(DocumentException e)
			{
				e.getMessage();
				return false;
			}

		}
		else
			return false;
	}
	
	// A function that builds interests paragraph
	public boolean buildInterests()
	{
		if(!cv.interests.isEmpty())
		{
			String text = "";

			for (HashMap.Entry<String, String> entry : cv.interests.entrySet())
			{
				text = text + entry.getKey() + ": " + entry.getValue() + "\n";
			}
			// Remove last new line character
			text = text.substring(0, text.length() - 1);

			try
			{
				Paragraph p = new Paragraph("ZAINTERESOWANIA", mediumFont);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				add(p);
				p = new Paragraph(text, font);
				add(p);
				// If adding the paragraph was successful,
				// return true
				return true;
			}
			catch(DocumentException e)
			{
				e.getMessage();
				return false;
			}
		}
		else
			return false;
	}
	
	// A function that builds languages paragraph
	public boolean buildLanguages()
	{
		if(!cv.languages.isEmpty())
		{
			String text = "";

			for (HashMap.Entry<String, String> entry : cv.languages.entrySet())
			{
				text = text + entry.getKey() + ": " + entry.getValue() + "\n";
			}
			// Remove last new line character
			text = text.substring(0, text.length() - 1);

			try
			{
				Paragraph title = new Paragraph("JĘZYKI", mediumFont);
				title.setAlignment(Paragraph.ALIGN_CENTER);
				add(title);
				Paragraph content = new Paragraph(text);
				add(content);
				// If adding the paragraph was successful,
				// return true
				return true;
			}
			catch(DocumentException e )
			{
				e.getMessage();
				return false;
			}
		}
		else
			return false;
	}
	
	// A function that builds name paragraph
	public boolean buildName()
	{
		String text = "";

		text = text + cv.personalDetails.getFirstName() +
				" " + cv.personalDetails.lastName;

		if(!text.equals(" "))
		{
			try
			{
				Paragraph p = new Paragraph(text, bigFont);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				add(p);
				// If adding the paragraph was successful,
				// return true
				return true;
			}
			catch(DocumentException e)
			{
				e.getMessage();
				return false;
			}
		}
		else
			return  false;
	}
	
	// A function that builds personal details paragraph
	public boolean buildPersonalDetails()
	{
		String text = "";
		
		if(!cv.personalDetails.email.equals(""))
		{
			text = text + cv.personalDetails.email;
		}
		if(!cv.personalDetails.phone.equals(""))
		{
			text = text + "\n" + cv.personalDetails.phone;
		}
		if(!cv.personalDetails.linkedIn.equals(""))
		{
			text = text + "\n" + "LinkedIn: " + cv.personalDetails.linkedIn;
		}
		if(!cv.personalDetails.github.equals(""))
		{
			text = text + "\n" + "GitHub: " + cv.personalDetails.github;
		}
		if(!cv.personalDetails.getCountry().equals(""))
		{
			text = text + "\n" + cv.personalDetails.getCountry();
		}

		if(!text.equals(""))
		{
			try
			{
				Paragraph p = new Paragraph(text, font);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				add(p);
				// If adding the paragraph was successful,
				// return true
				return true;
			}
			catch(DocumentException e)
			{
				e.getMessage();
				return false;
			}
		}
		else
			return false;
	}
	
	// A function that builds profession summary paragraph
	public boolean buildProfessionSummary()
	{
		if(!cv.professionSummary.equals(""))
		{
			try
			{
				Paragraph p = new Paragraph("\n" + cv.professionSummary + "\n", font);
				p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				add(p);
				// If adding the paragraph was successful,
				// return true
				return true;
			}
			catch(DocumentException e)
			{
				e.getMessage();
				return false;
			}
		}
		else
			return false;
	}

	public boolean buildReferences()
	{
		String text = "";

		if(!cv.references.isEmpty())
		{
			for(int i = 0; i < cv.references.size(); i++)
			{
				text = text + cv.references.get(i) + "\n";
			}

			try
			{
				Paragraph p = new Paragraph("REFERENCJE", mediumFont);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				add(p);
				p = new Paragraph(text, font);
				p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				add(p);
				// If adding the paragraph was successful,
				// return true
				return true;
			}
			catch (DocumentException e)
			{
				e.getMessage();
				return false;
			}
		}
		else
			return false;
	}
	
	// A function that builds skills paragraph
	public boolean buildSkills()
	{
		String text = "";
		
		if(!cv.skills.isEmpty())
		{
			for (HashMap.Entry<String, String> entry : cv.skills.entrySet())
			{
				text = text + entry.getKey() + ": " + entry.getValue() + "\n";
			}

			try
			{
				Paragraph p = new Paragraph("UMIEJĘTNOŚCI", mediumFont);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				add(p);
				p = new Paragraph(text);
				add(p);
				// If adding the paragraph was successfull,
				// return true
				return true;
			}
			catch(DocumentException e)
			{
				e.getMessage();
				return false;
			}
		}
		else
			return false;
	}
	
	// A function that builds work experience paragraph
	public boolean buildWorkExp()
	{
		if(!cv.workExp.isEmpty())
		{
			String text = "";

			// For every work experience in the list
			for(Object work : cv.workExp)
			{
				String fromDate = ((CV_WorkExperience) work).getFrom().toString();
				String toDate = ((CV_WorkExperience) work).getTo().toString();

				text = text + fromDate + " - " + toDate + "\n" + ((CV_WorkExperience) work).occupation +
						"\n" + ((CV_WorkExperience) work).workplace + "\n";

				for(String duty : ((CV_WorkExperience) work).duties)
				{
					text = text + "- " + duty + "\n";
				}

				text = text + "\n";
			}
			// Remove last new line character
			text = text.substring(0, text.length() - 1);

			try
			{
				Paragraph p = new Paragraph("\nDOŚWIADCZENIE ZAWODOWE", mediumFont);
				p.setAlignment(Paragraph.ALIGN_CENTER);
				add(p);
				p = new Paragraph(text, font);
				add(p);
				// If adding the paragraph was successfull,
				// return true
				return true;
			}
			catch(DocumentException e)
			{
				e.getMessage();
				return false;
			}
		}
		else
			return false;
	}

	// S:
	public void saveAs(String path)
	{
		try
		{
			PdfWriter.getInstance(this, new FileOutputStream(path));
		}
		catch (FileNotFoundException | DocumentException e)
		{
			e.printStackTrace();
		}
	}
}
