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
	private Color paintColor;

	@Getter
	@Setter
	private Shape shape;

	@Getter
	@Setter
	private int brushSize;

	@Getter
	@Setter
	private boolean isFilled;

	ShapeToDraw(Point startPoint, Point endPoint, Color paintColor, Shape shape, int brushSize, boolean isFilled)
	{
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.paintColor = paintColor;
		this.shape = shape;
		this.brushSize = brushSize;
		this.isFilled = isFilled;
	}
}
