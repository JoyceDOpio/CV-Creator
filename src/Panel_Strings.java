import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;

// A panel that displays information saved in the form of String,
// such as skills, languages, and interests
public abstract class Panel_Strings extends Panel_Data
{
    ArrayList<JLabel> labels;
    ArrayList<JComponent> components;

    JButton addButton;
    // Inner panels for organizing layout
    JPanel savedStringsPanel, enterDataPanel;

    HashMap<String, String> strings;

    // Message to be displayed when not all information has been
    // provided
    String message;

    Panel_Strings()
    {
        // Layout of the main panel
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        addButton = new JButtonStyle("");
        setOtherComponents();

        // Inner panels for:
        // - saved Strings
        savedStringsPanel = new Panel_SavedStrings()
		{
		    // Display object in the panel
            @Override
            public void displayObject(String text)
            {
                String key = text;
                String value = strings.get(text);
                displayInEnterDataPanel(key,value);
            }

            // Hide object from the panel
            @Override
            public void hideObject()
            {
                hideFromEnterDataPanel();
            }

            @Override
            public void removeObject(String key)
			{
				// Remove String
				strings.remove(key);
			}
		};
        add(savedStringsPanel);
        // - entering data
        enterDataPanel = new Panel_EnterData();
        add(enterDataPanel);
    }

    // C:
    public void clearEnterDataPanel()
    {
        for(int i = 0; i < components.size(); i++)
        {
            if(components.get(i) instanceof JTextField)
                ((JTextField) components.get(i)).setText("");
            else if(components.get(i) instanceof JTextArea)
                ((JTextArea) components.get(i)).setText("");
        }
    }

    @Override
    public void clearPanel()
    {
        clearEnterDataPanel();
        strings.clear();
        ((Panel_SavedStrings) savedStringsPanel).clearPanel();
    }

    public HashMap<String,String> collectInformation()
    {
        HashMap<String, String> stringsMap = new HashMap<String,String>(strings);

        for(HashMap.Entry<String,String> entry : strings.entrySet())
        {
            stringsMap.put(entry.getKey(),entry.getValue());
        }

        strings.clear();

        return stringsMap;
    }

    // D:
    public abstract void displayInEnterDataPanel(String key, String value);

    // H:
    public abstract void hideFromEnterDataPanel();

    // I:
    public void insertInformation(HashMap<String,String> stringsMap)
    {
        strings = new HashMap<>(stringsMap);

        for(HashMap.Entry<String,String> entry : stringsMap.entrySet())
        {
            strings.put(entry.getKey(),entry.getValue());
            ((Panel_SavedStrings) savedStringsPanel).addStringRepresentationToPanel(entry.getKey());
        }
    }

    public boolean isPanelEmpty()
    {
        // Return true, if there are no saved skills
        if(strings.isEmpty())
            return true;
        else
            return false;
    }

    // P:
    public void placeElementsInPanel(Panel_EnterData panel)
    {
        panel.gc.gridx = 0;
        panel.gc.gridy = 0;

        panel.gc.weightx = 100;
        panel.gc.weighty = 1;

        panel.gc.anchor = GridBagConstraints.FIRST_LINE_START;

        for(int i = 0; i < labels.size(); i++)
        {
            // Add label
            panel.gc.gridy++;
            panel.gc.fill = GridBagConstraints.NONE;
            panel.add(labels.get(i), panel.gc);

            // Add component
            panel.gc.gridy++;
            panel.gc.fill = GridBagConstraints.BOTH;
            if(components.get(i) instanceof JTextArea)
                panel.add(new JScrollPane(components.get(i)), panel.gc);
            else
                panel.add(components.get(i), panel.gc);
        }

        // Add button
        panel.gc.weightx = 1;
        panel.gc.weighty = 100;
        panel.gc.gridy++;
        panel.gc.fill = GridBagConstraints.NONE;
        panel.gc.anchor = GridBagConstraints.LAST_LINE_END;
        // Insets create indents - 5 pixels from bottom and from
        // the right side
        panel.gc.insets = new Insets(10, 10, 10, 10);
        panel.add(addButton,panel.gc);

        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(((JTextField) (components.get(0))).getText().length() != 0)
                {
                    String key = ((JTextField) (components.get(0))).getText();

                    // If the key hasn't been inserted into the hashmap yet
                    if(!strings.containsKey(key))
                    {
                        if(components.get(1) instanceof JTextArea)
                        {
                            strings.put(((JTextField) (components.get(0))).getText(),
                                    ((JTextArea) (components.get(1))).getText());
                        }
                        else
                        {
                            strings.put(((JTextField) (components.get(0))).getText(),
                                    ((JTextField) (components.get(1))).getText());
                        }

                        // Add String representation to the panel
                        ((Panel_SavedStrings) savedStringsPanel).addStringRepresentationToPanel(((JTextField) (components.get(0))).getText());

                        clearEnterDataPanel();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Wpis o takiej nazwie już istnieje.");

                }
                else
                {
                    JOptionPane.showMessageDialog(null, message);
                }
            }
        });
    }


    // S:
    public void setAddButtonText(String text)
    {
        addButton.setText(text);
    }

    public void setMessage(String text)
    {
        message = text;
    }

    public abstract void setOtherComponents();
}
