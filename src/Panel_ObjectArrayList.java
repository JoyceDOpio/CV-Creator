
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import javax.swing.*;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

// A panel that displays information saved as ArrayList<Object>,
// i.e. in the form of the work experience object and the education object
public abstract class Panel_ObjectArrayList extends Panel_Data
{
    JLabel toLabel, fromLabel;

    DatePicker from ,to;
    DatePickerSettings fromDateSettings, toDateSettings;

    ArrayList<JLabel> singleLabels;
    ArrayList<JComponent> singleComponents;

    JButton addButton;

    ArrayList<Object> objectList;

    JPanel savedObjectsPanel, enterDataPanel;

    Panel_ObjectArrayList()
    {
        // Layout of the main panel
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        fromLabel = new JLabelStyle("Od");
        toLabel = new JLabelStyle("Do");
        // The exact labels and components are initially unknown,
        // so they have to be specifically set
        setOtherComponents();

        // Date picker settings:
        // - from
        fromDateSettings = new DatePickerSettings();
        fromDateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        fromDateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        // - to
        toDateSettings = new DatePickerSettings();
        toDateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        toDateSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
        // Date pickers
        from = new DatePicker(fromDateSettings);
        to = new DatePicker(toDateSettings);

        addButton = new JButtonStyle("");

        // Inner panels for:
        // - saved objects
        savedObjectsPanel = setSavedObjectPanel();
        add(savedObjectsPanel);
        // - entering data
        enterDataPanel = new Panel_EnterData();
        add(enterDataPanel);
    }

    // A:
    public abstract void addAdditionalComponents(Panel_EnterData panel);

    public abstract void addObject();

    // C:
    public void clearEnterDataPanel()
    {
        // Clear the date picker text
        from.clear();
        to.clear();

        for(int i = 0; i < singleComponents.size(); i++)
        {
            if(singleComponents.get(i) instanceof JTextField)
                ((JTextField) singleComponents.get(i)).setText("");
        }
    }

    @Override
    public void clearPanel()
    {
        clearEnterDataPanel();
        objectList.clear();
        ((Panel_SavedObjects) savedObjectsPanel).clearPanel();
    }

    public abstract ArrayList<Object> collectInformation();

    // I:
    public abstract void insertInformation(ArrayList<Object> list);

    public abstract boolean isPanelCompleted();

    @Override
    public boolean isPanelEmpty()
    {
        // Return true, if there are no objects saved
        if(objectList.isEmpty())
            return true;
        else
            return false;
    }

    // P:
    public void placeDoubleElementsInPanel(Panel_EnterData panel)
    {
        panel.gc.gridx = 0;
        panel.gc.gridy = 0;

        panel.gc.weightx = 100;
        panel.gc.weighty = 1;

        panel.gc.anchor = GridBagConstraints.FIRST_LINE_START;
        // First row
        panel.gc.gridy++;
        panel.add(fromLabel, panel.gc);
        panel.gc.gridx = 1;
        panel.add(toLabel, panel.gc);

        // Second row
        panel.gc.gridx = 0;
        panel.gc.gridy++;
        panel.gc.fill = GridBagConstraints.BOTH;
        panel.add(from, panel.gc);
        panel.gc.gridx = 1;
        panel.add(to, panel.gc);
    }

    public void placeElementsInPanel(Panel_EnterData panel)
    {
        // Place elements that are to be arranged double in a row
        placeDoubleElementsInPanel(panel);
        // Place elements that are to be arranged single in a row
        placeSingleElementsInPanel(panel);
    }

    public void placeSingleElementsInPanel(Panel_EnterData panel)
    {
        panel.gc.gridx = 0;
        panel.gc.gridwidth = 2;

        for(int i = 0; i < singleLabels.size(); i++)
        {
            // Add label
            panel.gc.gridy++;
            panel.gc.fill = GridBagConstraints.NONE;
            panel.add(singleLabels.get(i), panel.gc);

            // Add component
            panel.gc.gridy++;
            panel.gc.fill = GridBagConstraints.BOTH;
            panel.add(singleComponents.get(i), panel.gc);
        }

        // Add additional components
        addAdditionalComponents(panel);

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
                // Create and add object
                addObject();
            }
        });
    }

    // S:
    public void setAddButtonText(String text)
    {
        addButton.setText(text);
    }

    public void setFrom(Date date)
    {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        from.setDate(LocalDate.of(year, month, day));
    }

    public abstract void setOtherComponents();

    public abstract Panel_SavedObjects setSavedObjectPanel();

    public void setTo(Date date)
    {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        to.setDate(LocalDate.of(year, month, day));
    }
}













