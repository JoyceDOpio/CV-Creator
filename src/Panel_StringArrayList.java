import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

// A panel that displays information saved in the form of
// ArrayList<String>, such as duties and references
public abstract class Panel_StringArrayList extends Panel_Data
{
    JTextField dataField;
    JButton addButton;

    JLabel label;

    // Inner panel for organizing references layout
    JPanel savedDataPanel, enterDataPanel;
    ArrayList<String> dataList;

    String sameMessage, emptyMessage = "";

    Panel_StringArrayList()
    {
        dataList = new ArrayList<String>();

        dataField = new JTextField();
        addButton = new JButtonStyle("");

        // Layout of the main panel
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        // Inner panels for:
        // - saved references
        savedDataPanel = new Panel_SavedStrings()
        {
            @Override
            public void displayObject(JButton objectButton)
            {
                String value = objectButton.getText();

                // Show reference value in the data JTextField
                dataField.setText(value);
            }

            @Override
            public void hideObject(JButton objectButton)
            {
                String value = objectButton.getText();

                // If the user entered different value
                if(!dataField.getText().equals("") && !value.equals(dataField.getText()))
                {
                    if(!dataList.contains(dataField.getText()))
                    {
                        // Remove the old value
                        removeObject(value);
                        // Save the new value
                        dataList.add(dataField.getText());
                        // Display new key on the object button
                        objectButton.setText(dataField.getText());
                    }
                    else
                        JOptionPane.showMessageDialog(null, sameMessage);
                }
                dataField.setText("");
            }

            @Override
            public void removeObject(String value)
            {
                // Remove data entry
                dataList.remove(value);
            }
        };
        add(savedDataPanel);
        // - for entering data
        enterDataPanel = new Panel_EnterData();
        add(enterDataPanel);

        setLabel();
        // Place in the sub-panel the GUI elements for entering data
        placeElementsInPanel((Panel_EnterData) enterDataPanel);
    }

    // C:
    public void clearEnterDataPanel()
    {
        dataField.setText("");
    }

    @Override
    public void clearPanel()
    {
        clearEnterDataPanel();
        dataList.clear();
        ((Panel_SavedStrings) savedDataPanel).clearPanel();
    }

    public ArrayList<String> collectInformation()
    {
        ArrayList<String> list = new ArrayList<String>(dataList);
        Collections.copy(list, dataList);

        return list;
    }

    // I:
    public void insertInformation(ArrayList<String> list)
    {
        dataList = list;

        for(String data : dataList)
        {
            ((Panel_SavedStrings) savedDataPanel).addStringRepresentationToPanel(data);
        }
    }

    @Override
    public boolean isPanelEmpty()
    {
        if(dataList.isEmpty())
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

        panel.gc.fill = GridBagConstraints.BOTH;
        panel.gc.anchor = GridBagConstraints.FIRST_LINE_START;

        // Add label
        if(label != null)
        {
            panel.gc.gridy++;
            panel.gc.fill = GridBagConstraints.NONE;
            panel.add(label, panel.gc);
        }

        // Add component
        panel.gc.gridy++;
        panel.gc.fill = GridBagConstraints.BOTH;
        panel.add(dataField, panel.gc);

        // Add button
        panel.gc.weightx = 1;
        panel.gc.weighty = 100;
        panel.gc.gridy++;
        panel.gc.fill = GridBagConstraints.NONE;
        panel.gc.anchor = GridBagConstraints.LAST_LINE_END;
        // Insets create indents - 5 pixels from bottom and from
        // the right side
        panel.gc.insets = new Insets(10, 10, 10, 10);
        panel.add(addButton, panel.gc);

        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(dataField.getText().length() != 0)
                {
                    String value = dataField.getText();

                    // If the array list doesn't contain this value yet
                    if(!dataList.contains(value))
                    {
                        dataList.add(dataField.getText());
                        // Add reference representation to the panel
                        ((Panel_SavedStrings) savedDataPanel).addStringRepresentationToPanel(dataField.getText());

                        clearEnterDataPanel();
                    }
                    else
                        JOptionPane.showMessageDialog(null, sameMessage);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, emptyMessage);
                }
            }
        });
    }

    // S:
    public void setAddButtonText(String text)
    {
        addButton.setText(text);
    }

    public void setEmptyMessage(String text)
    {
        emptyMessage = text;
    }

    public abstract void setLabel();

    public void setSameMessage(String text)
    {
        sameMessage = text;
    }
}
