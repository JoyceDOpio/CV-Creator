
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Reader_SimpleTemplate
{
    private static final long serialVersionUID = 1L;
    private static ArrayList<String> sectionHeaders = new ArrayList<String>
            (Arrays. asList("DOŚWIADCZENIE ZAWODOWE", "WYKSZTAŁCENIE",
                    "UMIEJĘTNOŚCI","JĘZYKI","ZAINTERESOWANIA","REFERENCJE"));


    public static CV convertToCV(StringBuilder text)
    {
        CV cv = new CV();

        String[] lines = text.toString().split("\n");

        if(lines.length != 0)
        {
            int i = 0;
            // As long as we're not reaching any of the section headers
            // and we haven't reached the end of file
            while(!sectionHeaders.contains(lines[i]) && i < lines.length)
            {
                // Get first- and lastname(s) from the first line
                if(i == 0 && lines[i].contains(" ") && !lines[i].matches("^\\s*$"))
                {
                    String[] names = lines[i].split(" ");

                    if(names.length == 2)
                    {
                        cv.personalDetails.setFirstName(names[0]);
                        cv.personalDetails.setLastName(names[1]);
                    }
                    else if(names.length == 3)
                    {
                        // If the middle word is uppercase consider it to be
                        // second name
                        if(Character.isUpperCase(names[1].charAt(0)))
                        {
                            cv.personalDetails.setFirstName(names[0] + " " + names[1]);
                            cv.personalDetails.setLastName(names[2]);
                        }
                        // Otherwise consider it to be part of the last name
                        else
                        {
                            cv.personalDetails.setFirstName(names[0] + " " + names[1]);
                            cv.personalDetails.setLastName(names[2]);
                        }
                    }
                    else if(names.length == 4)
                    {
                        if(Character.isUpperCase(names[2].charAt(0)))
                        {
                            cv.personalDetails.setFirstName(names[0] + " " + names[1] + " " + names[2]);
                            cv.personalDetails.setLastName(names[3]);
                        }
                        // Otherwise consider it to be part of the last name
                        else
                        {
                            cv.personalDetails.setFirstName(names[0] + " " + names[1]);
                            cv.personalDetails.setLastName(names[2] + " " + names[3]);
                        }
                    }
                    i++;
                }

                // Get e-mail address
                else if(lines[i].contains("@"))
                {
                    cv.personalDetails.setEmail(lines[i]);
                    i++;
                }
                // Get phone number - \+?\d+ regex matches String that optionally
                // starts with "+" followed by any number of digits only
                else if(lines[i].matches("\\+?\\d+"))
                {
                    cv.personalDetails.setPhone(lines[i]);
                    i++;
                }
                // Get LinkedIn profile
                else if(lines[i].startsWith("LinkedIn"))
                {
                    cv.personalDetails.setLinkedIn(lines[i].split(" ")[1]);
                    i++;
                }
                // Get GitHub profile
                else if(lines[i].startsWith("GitHub"))
                {
                    cv.personalDetails.setGithub(lines[i].split(" ")[1]);
                    i++;
                }
                // Get country
                else if(i <= 5 && (lines[i+1].matches("^\\s*$") || (i+1) == lines.length))
                {
                    cv.personalDetails.setCountry(lines[i]);
                    i++;
                }
                // If it's a new line
                else if(lines[i].matches("^\\s*$"))
                {
                    i++;
                }
                // Get profession summary
                else {
                    StringBuilder sectionText = new StringBuilder();
                    i++;

                    while (!sectionHeaders.contains(lines[i]) && i < lines.length) {
                        sectionText.append(lines[i]);
                        sectionText.append(" ");
                        i++;
                    }
                    if (sectionText.length() != 0)
                    {
                        // Delete last space character
                        sectionText.delete(sectionText.length()-1,sectionText.length());
                        cv.setProfessionSummary(sectionText.toString());
                    }
                }
            }

            // Get information from the sections
            while(i < lines.length)
            {
                // Workexperience
                if(lines[i].equals(sectionHeaders.get(0)))
                {
                    i++;

                    while(i < lines.length && !sectionHeaders.contains(lines[i]))
                    {
                        if(lines[i].contains(" - "))
                        {
                            CV_WorkExperience work =  new CV_WorkExperience();

                            // Get from and to dates
                            String from = lines[i].split(" - ")[0];
                            String to = lines[i].split(" - ")[1];
                            // - from
                            if(from.split("\\.").length == 3)
                            {
                                work.setFrom(new Date(Integer.parseInt(from.split("\\.")[0]),
                                        Integer.parseInt(from.split("\\.")[1]),
                                        Integer.parseInt(from.split("\\.")[2])));
                            }
                            else if(from.split("\\.").length == 2)
                            {
                                work.setFrom(new Date(Integer.parseInt(from.split("\\.")[0]),
                                        Integer.parseInt(from.split("\\.")[1])));
                            }
                            // - to
                            if(to.split("\\.").length == 3)
                            {
                                work.setTo(new Date(Integer.parseInt(to.split("\\.")[0]),
                                        Integer.parseInt(to.split("\\.")[1]),
                                        Integer.parseInt(to.split("\\.")[2])));
                            }
                            else if(to.split("\\.").length == 2)
                            {
                                work.setTo(new Date(Integer.parseInt(to.split("\\.")[0]),
                                        Integer.parseInt(to.split("\\.")[1])));
                            }

                            // Get occupation
                            i++;
                            work.setOccupation(lines[i]);

                            // Get workplace
                            i++;
                            work.setWorkplace(lines[i]);

                            i++;

                            // Get duties
                            if(i < lines.length && lines[i].startsWith("-"))
                            {
                                while(i < lines.length && lines[i].startsWith("-"))
                                {
                                    work.addDuty(lines[i].split("- ")[1]);
                                    i++;

                                }
                                // If line is a new line
                                if(i < lines.length && lines[i].matches("^\\s*$"))
                                {
                                    i++;
                                }
                            }

                            // Add work experience
                            cv.addWork(work);
                        }
                    }
                }
                // Education
                else if(lines[i].equals(sectionHeaders.get(1)))
                {
                    i++;

                    while(i < lines.length && !sectionHeaders.contains(lines[i]))
                    {
                        CV_Education edu =  new CV_Education();
                        if(lines[i].contains(" - "))
                        {
                            // Get from and to dates
                            String from = lines[i].split(" - ")[0];
                            String to = lines[i].split(" - ")[1];
                            // - from
                            if(from.split("\\.").length == 3)
                            {
                                edu.setFrom(new Date(Integer.parseInt(from.split("\\.")[0]),
                                        Integer.parseInt(from.split("\\.")[1]),
                                        Integer.parseInt(from.split("\\.")[2])));
                            }
                            else if(from.split("\\.").length == 2)
                            {
                                edu.setFrom(new Date(Integer.parseInt(from.split("\\.")[0]),
                                        Integer.parseInt(from.split("\\.")[1])));
                            }
                            // - to
                            if(to.split("\\.").length == 3)
                            {
                                edu.setTo(new Date(Integer.parseInt(to.split("\\.")[0]),
                                        Integer.parseInt(to.split("\\.")[1]),
                                        Integer.parseInt(to.split("\\.")[2])));
                            }
                            else if(to.split("\\.").length == 2)
                            {
                                edu.setTo(new Date(Integer.parseInt(to.split("\\.")[0]),
                                        Integer.parseInt(to.split("\\.")[1])));
                            }

                            // Get school name
                            i++;
                            edu.setSchool(lines[i]);

                            // Get workplace
                            i++;
                            edu.setCourse(lines[i]);

                            // Get specialisation
                            i++;
                            edu.setSpecialisation(lines[i]);

                            // Add education
                            cv.addEducation(edu);
                        }

                        i++;

                        // If line is a new line
                        if(i < lines.length && lines[i].matches("^\\s*$"))
                        {
                            i++;
                        }
                    }
                }
                // Skills
                else if(lines[i].equals(sectionHeaders.get(2)))
                {
                    while (++i < lines.length && lines[i].contains(": "))
                    {
                        String[] skill = lines[i].split(": ");
                        cv.addSkill(skill[0], skill[1]);
                    }
                }
                // Languages
                else if(lines[i].equals(sectionHeaders.get(3)))
                {
                    i++;

                    // If line is a new line
                    if(i < lines.length && lines[i].matches("^\\s*$"))
                    {
                        i++;
                    }

                    while(i < lines.length && lines[i].contains(": "))
                    {
                        String[] language = lines[i].split(": ");
                        cv.addLanguage(language[0],language[1]);
                        i++;
                    }
                }
                // Interests
                else if(lines[i].equals(sectionHeaders.get(4)))
                {
                    i++;

                    while(i < lines.length && lines[i].contains(": "))
                    {
                        String[] interest = lines[i].split(": ");
                        cv.addInterest(interest[0],interest[1]);
                        i++;
                    }
                }
                // References
                else if(lines[i].equals(sectionHeaders.get(5)))
                {
                    while(++i < lines.length)
                    {
                        cv.addReference(lines[i]);
                    }
                }
                // If the line matches no previous criteria,
                // go to the next line
                else
                {
                    i++;
                }
            }
        }

        return cv;
    }
}
