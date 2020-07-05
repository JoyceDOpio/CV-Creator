
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

// A panel that displays information saved as objects, i.e. in the
// form of the work experience object and the education object
public abstract class Panel_Objects extends Panel_Data
{
    JLabel toLabel, fromLabel;
    JDatePickerImpl from, to;

    ArrayList<JLabel> singleLabels;
    ArrayList<JComponent> singleComponents;

    JButton addButton;

    ArrayList<Object> objectList;

    JPanel savedObjectsPanel, enterDataPanel;

    Panel_Objects()
    {
        // Layout of the main panel
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        fromLabel = new JLabelStyle("Od");
        toLabel = new JLabelStyle("Do");
        // The exact labels and components are initially unknown,
        // so they have to be specifically set
        setOtherComponents();

        // We have to create two separate models for two
        // date pickers
        UtilDateModel model = new UtilDateModel();
        UtilDateModel model2 = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        // Two separate date panels for variables "from" and "to"
        JDatePanelImpl fromDatePanel = new JDatePanelImpl(model, p);
        JDatePanelImpl toDatePanel2 = new JDatePanelImpl(model2, p);

        from = new JDatePickerImpl(fromDatePanel, new DateLabelFormatter());
        to = new JDatePickerImpl(toDatePanel2, new DateLabelFormatter());

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

    public abstract void addObject(SimpleDateFormat dateFormatter);

    // C:
    public String chooseDateFormat()
    {
        if(from.getModel().getYear() == to.getModel().getYear() && from.getModel().getMonth() == to.getModel().getMonth())
        {
            return "yyyy-MM-dd";
        }
        else
        {
            return "yyyy-MM";
        }
    }

    public void clearEnterDataPanel()
    {
        from.getModel().setValue(null);
        to.getModel().setValue(null);

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
        // Return true, if there are no education objects saved
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
                String datePattern =  "yyyy-MM-dd";
                SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

                // Create and add object
                addObject(dateFormatter);
            }
        });
    }

    // S:
    public void setAddButtonText(String text)
    {
        addButton.setText(text);
    }

    public abstract Panel_SavedObjects setSavedObjectPanel();

    public abstract void setOtherComponents();
}













