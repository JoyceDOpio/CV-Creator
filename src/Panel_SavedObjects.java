import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

// A panel for saving objects
public abstract class Panel_SavedObjects extends Panel_Data
{
    GridBagConstraints gc;

    HashMap<JButton, JButton> objectFields;

    Panel_SavedObjects()
    {
        if((System.getProperty("os.name")).startsWith("Windows"))
        {
            setBackgroundColor(Color.white);
        }

        objectFields = new HashMap<JButton, JButton>();

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
    public void addObjectRepresentationToPanel(Object object, String text)
    {
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 0;
        gc.gridy++;

        final JButton objectButton = new JButtonStyle(text);
        objectButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ((JButtonStyle) objectButton).click();

                if(((JButtonStyle) objectButton).getClicked())
                    displayObject(object);
                else
                    hideObject();
            }
        });
        // Set preffered size of the button
        objectButton.setPreferredSize(new Dimension(40, 40));
        add(objectButton, gc);

        final JButton removeButton = new JButtonStyle("Usuń");

        removeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                removeObjectRepresentation(object, objectButton, removeButton);
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

        // Add the object field and it's removal button to the
        // hash map <-- this will enable us later to remove them
        //              all at once
        objectFields.put(objectButton, removeButton);
    }

    // C:
    public void clearPanel()
    {
        for(HashMap.Entry<JButton, JButton> entry : objectFields.entrySet())
        {
            // Remove components from the panel for saved interests
            remove(entry.getKey());
            remove(entry.getValue());
        }
        objectFields.clear();

        // Repaint the panel
        revalidate();
        repaint();
    }

    // D:
    public abstract void displayObject(Object object);

    // H:
    public abstract void hideObject();

    // R:
    public abstract void removeObject(Object object);

    // The buttons that display on GUI once the object is added,
    // are the object representation
    public void removeObjectRepresentation(Object object, JButton objectButton, JButton removebutton)
    {
        // Remove object
        removeObject(object);
        // Remove components
        remove(objectButton);
        remove(removebutton);
        // Repaint the panel
        revalidate();
        repaint();
    }
}
