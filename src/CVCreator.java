
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class CVCreator extends JFrame
{
	CV cv = new CV();
	SimpleTemplate document;
	Panel_CVData enterDataPanel;

	// Using this process to invoke the contructor,
	// JFileChooser points to user's default directory
	JFileChooser fileChooser;

	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem1, menuItem2, menuItem3;

    PdfReader reader;

	CVCreator()
	{
		super("CV Creator");

		try
        {
        	// UIManager is an object that changes the appearance
        	// of the user interface.
			// The setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
			// method will make the interface look more like the one of
			// the computer's operating system (e.g. Windows).
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

		setLayout(new BorderLayout());

		fileChooser = new JFileChooser();

		// Setting menu colors:
		UIManager.put("Menu.background", new java.awt.Color(96,96,96));
		UIManager.put("Menu.opaque", true);
		UIManager.put("Menu.foreground", new java.awt.Color(255,255,255));

		// Create a menu bar
        menuBar = new JMenuBar();
        // Create a menu 
        menu = new JMenu("Plik");
        // Create menu items
        menuItem1 = new JMenuItem("Nowy...");
        menuItem2 = new JMenuItem("Otwórz...");
        menuItem3 = new JMenuItem("Zapisz...");
        // Add icons to the menu items
        ImageIcon image = new ImageIcon(new ImageIcon("Images/new_file_icon.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        menuItem1.setIcon(image);
        image = new ImageIcon(new ImageIcon("Images/open_file_icon.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        menuItem2.setIcon(image);
        image = new ImageIcon(new ImageIcon("Images/save_file_icon.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        menuItem3.setIcon(image);

        // Add action listeners to the menu items:
        // - New file menu item
        menuItem1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("User wants new file");

                if(!enterDataPanel.arePanelsEmpty())
                {
                    String message = "Zapisać zmiany?";
                    int userChoice = JOptionPane.showConfirmDialog(null, message);

                    // If user chooses yes
                    if(userChoice == 0)
                    {
                        saveFile();
                    }
                    // If user chooses no
                    else if( userChoice == 1)
                    {
                        enterDataPanel.clearPanels();
                    }
                }
            }

        });
        // - Open file menu item
        menuItem2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
            {
				// Set extension filter
                fileChooser.setFileFilter(new FileFilter()
                {
                    @Override
                    public boolean accept(File file) {
                        // Without "|| file.isDirectory()" the program will
                        // not allow the user to open folders while searching
                        // for the right file to choose
                        return file.getName().toLowerCase().endsWith(".pdf")
                                || file.isDirectory();
                    }

                    @Override
                    public String getDescription()
                    {
                        return "Wybierz plik (*.pdf)";
                    }
                });

				// Open the open file dialog
				int userSelection = fileChooser.showOpenDialog(null);
                // If user chooses a file
                if(userSelection == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                    	reader = new PdfReader(fileChooser.getSelectedFile().getAbsolutePath());
						int numberOfPages = reader.getNumberOfPages();
						StringBuilder text = new StringBuilder();

						// For every page
						for(int i = 0; i < numberOfPages; i++)
						{
							text.append(PdfTextExtractor.getTextFromPage(reader,i+1));
							text.append("\n");
						}
						// Remove last new line
						text.delete(text.length()-1,text.length());

						cv = Reader_SimpleTemplate.convertToCV(text);

                        // Clear panels
                        enterDataPanel.clearPanels();

                        // Insert the cv information into panel text fields
                        enterDataPanel.insertInformation(cv);
                    }
                    catch(IOException ex)
                    {
                        ex.getMessage();
                        JOptionPane.showMessageDialog(null, "Dokument jest pusty.");
                    }
				}
			}
        });
        // - Save file menu item
        menuItem3.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
            {
                saveFile();
			}
        });

        // Add menu items to menu
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        // Add menu to menu bar 
        menuBar.add(menu);
        // Add menu bar to frame 
        setJMenuBar(menuBar);

		enterDataPanel = new Panel_CVData();
		// Create a scroll or the panel
		JScrollPane scrollPane = new JScrollPane(enterDataPanel);
		// Set the scroll direction
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);

		setSize(500,700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);// It's very important to set the visibility
                         // AFTER ADDING THE PANEL TO THE FRAME
	}

	// S:
    public void saveFile()
    {
        // Create CV object
        cv = enterDataPanel.collectInformation();

        // Set extension filter
        fileChooser.setFileFilter(new FileFilter()
        {
            @Override
            public boolean accept(File file) {
                // Without "|| file.isDirectory()" the program will
                // not allow the user to open folders while searching
                // for the right file to choose
                return file.getName().toLowerCase().endsWith(".pdf")
                        || file.isDirectory();
            }

            @Override
            public String getDescription()
            {
                return "Plik PDF";
            }
        });
        // Open the save file dialog
        int userSelection = fileChooser.showSaveDialog(null);


        // If user chooses a file
        if(userSelection == JFileChooser.APPROVE_OPTION)
        {
            // JFileChooser gets the absolute path under which the file
            // will be later saved
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if(!path.endsWith(".pdf"))
            {
                path = path + ".pdf";
            }

            // Create PDF file based on the CV object
            document = new SimpleTemplate(cv,path);
            // Build and save the CV file
            document.buildCV();
            // Clear panels
            enterDataPanel.clearPanels();
        }
    }

	public static void main(String[] args)
	{
		new CVCreator();
	}
}
















