
import javax.swing.*;

public class Panel_References extends Panel_StringArrayList
{
	Panel_References()
	{
		// Panel borders
		setInnerBorder(" Referencje ");
		setOuterBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setEmptyMessage("Wypełnij pole dla \"Referencje\".");
		setSameMessage("Taka referencja została już zapisana.");
		setAddButtonText("Dodaj referencję");
	}

	@Override
	public void setLabel()
	{
		label = null;
	}
}
