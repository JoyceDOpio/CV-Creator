
import javax.swing.*;

public class Panel_Duties extends Panel_StringArrayList
{
	Panel_Duties()
	{
		// Panel borders
		setInnerBorder(" Obowiązki ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setEmptyMessage("Wypełnij pole dla \"Obowiązki\".");
		setSameMessage("Taki obowiązek został już zapisany.");
		setAddButtonText("Dodaj obowiązek");
	}

	@Override
	public void setLabel()
	{
		label = new JLabel("Obowiązki");
	}
}
