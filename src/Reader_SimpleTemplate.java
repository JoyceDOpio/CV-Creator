
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

        for(int i = 0; i < lines.length; i++)
        {
            System.out.println(lines[i]);
//            System.out.println(lines[i].matches("^\\s*$"));
            System.out.println(sectionHeaders.contains(lines[i]));
        }

        System.out.println("\n");

        System.out.println("JĘZYKI " + sectionHeaders.contains("JĘZYKI") + "\n");

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
                    System.out.println("firstname: " + cv.personalDetails.firstName);
                    System.out.println("last name: " + cv.personalDetails.lastName);
                    i++;
                }

                // Get e-mail address
                else if(lines[i].contains("@"))
                {
                    cv.personalDetails.setEmail(lines[i]);
                    System.out.println("email: " + cv.personalDetails.email);
                    i++;
                }
                // Get phone number - \+?\d+ regex matches String that optionally
                // starts with "+" followed by any number of digits only
                else if(lines[i].matches("\\+?\\d+"))
                {
                    cv.personalDetails.setPhone(lines[i]);
                    System.out.println("phone: " + cv.personalDetails.phone);
                    i++;
                }
                // Get LinkedIn profile
                else if(lines[i].startsWith("LinkedIn"))
                {
                    cv.personalDetails.setLinkedIn(lines[i].split(" ")[1]);
                    System.out.println("linkedin: " + cv.personalDetails.linkedIn);
                    i++;
                }
                // Get GitHub profile
                else if(lines[i].startsWith("GitHub"))
                {
                    cv.personalDetails.setGithub(lines[i].split(" ")[1]);
                    System.out.println("github: " + cv.personalDetails.github);
                    i++;
                }
                // Get country
                else if(i <= 5 && (lines[i+1].matches("^\\s*$") || (i+1) == lines.length))
                {
                    cv.personalDetails.setCountry(lines[i]);
                    System.out.println("country: " + cv.personalDetails.getCountry());
                    i++;
                }
                // Get profession summary
                else if(lines[i].matches("^\\s*$"))
                {
                    System.out.println("line 113: " + lines[i]);
                    StringBuilder sectionText = new StringBuilder();
                    i++;

                    while(!sectionHeaders.contains(lines[i]) && i < lines.length)
                    {
                        sectionText.append(lines[i]);
                        System.out.println("line 121: " + lines[i]);
                        i++;
                    }
                    cv.setProfessionSummary(sectionText.toString());
                    System.out.println("summary: " + cv.professionSummary);
                }

                System.out.println("line 128: " + lines[i]);
                if(lines[i].equals(""))
                    System.out.println("true");
//                i++;
            }

            // Get information from the sections
            while(i < lines.length)
            {
                // Workexperience
                if(lines[i].equals(sectionHeaders.get(0)))
                {
                    System.out.println("line: " + lines[i]);
                    i++;

                    while(!sectionHeaders.contains(lines[i]) && i < lines.length)
                    {
                        CV_WorkExperience work =  new CV_WorkExperience();
                        if(lines[i].contains(" - "))
                        {
                            // Get from and to dates
                            work.setFrom(lines[i].split(" - ")[0]);
                            work.setTo(lines[i].split(" - ")[1]);
                            System.out.println("from: " + work.getFrom());
                            System.out.println("to: " + work.getTo());
                            System.out.println("line: " + lines[i]);

                            // Get occupation
                            i++;
                            System.out.println("line: " + lines[i]);
                            work.setOccupation(lines[i]);
                            System.out.println("occupation: " + work.getOccupation());

                            // Get workplace
                            i++;
                            work.setWorkplace(lines[i]);
                            System.out.println("line: " + lines[i]);
                            System.out.println("workplace: " + work.getWorkplace());

                            // Get duties
                            if(lines[i+1].startsWith("-"))
                            {
                                i++;

                                while(lines[i].startsWith("-"))
                                {
                                    System.out.println("line: " + lines[i]);
                                    System.out.println("duty: " +lines[i].split("- ")[1]);
                                    work.addDuty(lines[i].split("- ")[1]);
                                    i++;

                                }
                                // If line is a new line
                                if(lines[i].matches("^\\s*$"))
                                {
                                    i++;
                                    System.out.println("line: " + lines[i]);
                                }
                            }

                            // Add work experience
                            cv.addWork(work);
                        }

                        System.out.println("line: " + lines[i]);
                    }
                }
                // Education
                else if(lines[i].equals(sectionHeaders.get(1)))
                {
//                System.out.println("line: " + lines[i]);
                    i++;

                    while(i < lines.length && !sectionHeaders.contains(lines[i]))
                    {
                        CV_Education edu =  new CV_Education();
                        if(lines[i].contains(" - "))
                        {
                            // Get from and to dates
                            edu.setFrom(lines[i].split(" - ")[0]);
                            edu.setTo(lines[i].split(" - ")[1]);
                            System.out.println("from: " + edu.getFrom());
                            System.out.println("to: " + edu.getTo());
                            System.out.println("line: " + lines[i]);

                            // Get school name
                            i++;
                            System.out.println("line: " + lines[i]);
                            edu.setSchool(lines[i]);
                            System.out.println(edu.getSchool());

                            // Get workplace
                            i++;
                            edu.setCourse(lines[i]);
                            System.out.println("line: " + lines[i]);
                            System.out.println(edu.getCourse());

                            // Get specialisation
                            i++;
                            edu.setSpecialisation(lines[i]);
                            System.out.println("line: " + lines[i]);
                            System.out.println(edu.getSpecialisation());

                            // Add work experience
                            cv.addEducation(edu);
                        }

                        i++;
                        System.out.println("line: " + lines[i]);

                        // If line is a new line
                        if(i < lines.length && lines[i].matches("^\\s*$"))
                        {
                            i++;
                            System.out.println("line: " + lines[i]);
                        }
                    }
                }
                // Skills
                else if(lines[i].equals(sectionHeaders.get(2))) {
//                System.out.println("line: " + lines[i]);
//                    i++;

                    while (++i < lines.length && lines[i].contains(": ")) {
                        String[] skill = lines[i].split(": ");
                        cv.addSkill(skill[0], skill[1]);
                        System.out.println("line 260: " + lines[i]);
//                        i++;
                    }

                    java.util.Iterator it = cv.skills.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry pair = (Map.Entry)it.next();
                        System.out.println(pair.getKey() + " = " + pair.getValue());
                    }
                }
                // Languages
                else if(lines[i].equals(sectionHeaders.get(3)))
                {
                    System.out.println("line: " + lines[i]);
                    i++;

                    // If line is a new line
                    if(i < lines.length && lines[i].matches("^\\s*$"))
                    {
                        i++;
                        System.out.println("line: " + lines[i]);
                    }

                    while(i < lines.length && lines[i].contains(": "))
                    {
                        String[] language = lines[i].split(": ");
                        cv.addLanguage(language[0],language[1]);
                        System.out.println("line: " + lines[i]);
                        i++;
                    }

                    java.util.Iterator it = cv.languages.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry pair = (Map.Entry)it.next();
                        System.out.println(pair.getKey() + " = " + pair.getValue());
                    }
                }
                // Interests
                else if(lines[i].equals(sectionHeaders.get(4)))
                {
                    System.out.println("line: " + lines[i]);
                    i++;

                    while(i < lines.length && lines[i].contains(": "))
                    {
                        String[] interest = lines[i].split(": ");
                        cv.addInterest(interest[0],interest[1]);
                        System.out.println("line: " + lines[i]);
                        i++;
                    }

                    java.util.Iterator it = cv.interests.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry pair = (Map.Entry)it.next();
                        System.out.println(pair.getKey() + " = " + pair.getValue());
                    }
                }
                // References
                else if(lines[i].equals(sectionHeaders.get(5)))
                {
                    System.out.println("line: " + lines[i]);

                    while(++i < lines.length)
                    {
                        cv.addReference(lines[i]);
                        System.out.println("line: " + lines[i]);
//                        i++;
                    }

                    for(String ref : cv.references)
                    {
                        System.out.println(ref);
                    }
                }
                // If the line matches no previous criteria,
                // go to the next line
                else
                {
                    System.out.println("line 300: " + lines[i]);
                    i++;
                }
            }
        }

        return cv;
    }
}
