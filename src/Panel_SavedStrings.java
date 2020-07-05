import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

// A panel for saving Strings
public abstract class Panel_SavedStrings extends Panel_Data
{
    GridBagConstraints gc;

    HashMap<JButton, JButton> stringFields;

    Panel_SavedStrings()
    {
        if((System.getProperty("os.name")).startsWith("Windows"))
        {
            setBackgroundColor(Color.white);
        }

        stringFields = new HashMap<JButton, JButton>();

        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        // Insets create indents - 5 pixels from bottom and from
        // the right side
        gc.insets = new Insets(0, 5, 5, 5);
        gc.weightx = 100;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
    }

    // A:
    // Adds a representation of the added object to the panel
    // - it's only for the user's view
    public void addStringRepresentationToPanel(String key)
    {
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 0;
        gc.gridy++;

        // A field representing the object in the GUI
        final JButton objectButton = new JButtonStyle(key);
        objectButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ((JButtonStyle) objectButton).click();

                if(((JButtonStyle) objectButton).getClicked())
                    displayObject(key);
                else
                    hideObject();
            }
        });
        // Set preffered size of the button
        objectButton.setPreferredSize(new Dimension(40, 40));
        add(objectButton, gc);

        // A button for removing GUI object representation
        final JButton removeButton = new JButtonStyle("Usuń");

        removeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                removeObjectRepresentation(key, objectButton, removeButton);
            }
        });
        gc.gridx = 1;

        gc.weightx = 1;
        gc.weighty = 100;

        gc.fill = GridBagConstraints.VERTICAL;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(removeButton, gc);
        // Repaint the panel
        revalidate();  // For JDK 1.7 or above.
        repaint();

        // Add the duty field and it's removal button to the
        // hash map <-- this will enable us later to remove them
        //              all at once
        stringFields.put(objectButton, removeButton);
    }

    // C:
    public void clearPanel()
    {
        for(HashMap.Entry<JButton, JButton> entry : stringFields.entrySet())
        {
            // Remove components from the panel for saved interests
            remove(entry.getKey());
            remove(entry.getValue());
        }
        stringFields.clear();

        // Repaint the panel
        revalidate();
        repaint();
    }

    // D:
    public void displayObject(String key){}

    // H:
    public void hideObject(){}

    // R:
    // The String in the HashMap is the object
    public abstract void removeObject(String key);

    // The buttons that display on GUI once the object is added,
    // are the object representation
    public void removeObjectRepresentation(String key, JButton objectButton, JButton removeButton)
    {
        // Remove object
        removeObject(key);
        // Remove components
        remove(objectButton);
        remove(removeButton);
        // Repaint the panel
        revalidate();
        repaint();
    }
}










