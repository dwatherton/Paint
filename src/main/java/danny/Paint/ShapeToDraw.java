package danny.Paint;

import lombok.Getter;
import lombok.Setter;
import java.awt.Color;
import java.awt.Point;

class ShapeToDraw
{
	@Getter
	@Setter
	private Point startPoint;

	@Getter
	@Setter
	private Point endPoint;

	@Getter
	@Setter
	private Color color;

	@Getter
	@Setter
	private Shape shape;

	@Getter
	@Setter
	private boolean isFilled;

	// Constructor For The Information We Know When The User Presses The Mouse Button To Draw (Info From Paint Toolbar And Mouse)
	ShapeToDraw(Point startPoint, Color color, Shape shape)
	{
		this.startPoint = startPoint;
		this.color = color;
		this.shape = shape;
	}
}
