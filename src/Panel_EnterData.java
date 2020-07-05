import java.awt.*;

public class Panel_EnterData extends Panel_Data
{
    GridBagConstraints gc;

    Panel_EnterData()
    {
        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        // Insets create indents - 5 pixels from bottom and from
        // the right side
        gc.insets = new Insets(0, 5, 0, 5);
        gc.weightx = 50;
        gc.weighty = 1;
        gc.gridx = 0;
        gc.gridy = 0;
    }
}
