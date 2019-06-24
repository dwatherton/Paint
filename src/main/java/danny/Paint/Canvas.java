package danny.Paint;

import lombok.Getter;
import lombok.Setter;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

class Canvas extends JPanel implements MouseListener, MouseMotionListener
{
	// Image To Add Color To Canvas Until Drawing Begins (Temporary)
	private static final Image COLOR_PALETTE = new ImageIcon(Canvas.class.getResource("/colorpalette.png")).getImage();

	private boolean drawing;
	private ShapeToDraw shapeToDraw;
	private List<ShapeToDraw> shapesToDraw;

	@Getter
	@Setter
	private static Color paintColor = Color.BLACK;

	@Getter
	@Setter
	private static Shape shape = Shape.POINT;

	Canvas()
	{
		// Initialize Canvas Variables And/Or Clear The Canvas For When The User Selects 'File' > 'New Window'
		clearCanvas();

		// Set Canvas Background Color And Add Mouse Listener For Painting
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	private void clearCanvas()
	{
		drawing = false;
		shapeToDraw = null;
		shapesToDraw = new ArrayList<>();
		paintColor = Color.BLACK;
		shape = Shape.POINT;
	}

	private void paintInstructions(Graphics graphics)
	{
		// Set Image X and Image Y For Centering The Color Palette Image
		int imageX = (getWidth() / 2) - (COLOR_PALETTE.getWidth(null) / 2);
		int imageY = (getHeight() / 2) - (COLOR_PALETTE.getHeight(null) / 2);

		// Draw Color Palette Image To Center Of Canvas
		graphics.drawImage(COLOR_PALETTE, imageX, imageY, null);

		// Get Font Metrics For Centering Instruction String
		FontMetrics fm = graphics.getFontMetrics();

		// Set Instruction Message, Width, And Height (Ascent)
		String instructions = "To Begin Painting Simply Select A Style And Begin Drawing!";
		int instructionsWidth = fm.stringWidth(instructions);
		int instructionsHeight = fm.getAscent();

		// Set Instruction Message Width And Height To Center The Instruction String (Horizontally), And Just Above The Image (Vertically)
		int instructionsX = (getWidth() / 2 - instructionsWidth / 2);
		int instructionsY = imageY - (instructionsHeight * 3);

		// Set Color And Write The Instructions To The Canvas
		graphics.setColor(Color.BLACK);
		graphics.drawString(instructions, instructionsX, instructionsY);
	}

	@Override
	public void paintComponent(Graphics graphics)
	{
		// Paint The Canvas (JPanel Component)
		super.paintComponent(graphics);
		graphics.setColor(paintColor);

		if (!drawing)
		{
			paintInstructions(graphics);
		}
		else
		{
			// TODO: Add Support For Drawing Various Size Dots, Lines, And Shapes As Well As Drawing As The Mouse Moves

			if (!shapesToDraw.isEmpty())
			{
				// Loop Through The Shapes To Draw
				for (ShapeToDraw shapeToBeDrawn : shapesToDraw)
				{
					// Set The Color Based On The Color Of The Shape When The User Began Drawing It
					graphics.setColor(shapeToBeDrawn.getColor());

					// Handle various shapes
					switch (shapeToBeDrawn.getShape())
					{
						case POINT:
						{
							graphics.fillRoundRect(shapeToBeDrawn.getStartPoint().x, shapeToBeDrawn.getStartPoint().y, 3, 3, 2, 2);
							break;
						}
						case LINE:
						{
							graphics.drawLine(shapeToBeDrawn.getStartPoint().x, shapeToBeDrawn.getStartPoint().y, shapeToBeDrawn.getEndPoint().x, shapeToBeDrawn.getEndPoint().y);
							break;
						}
						case RECTANGLE:
						{
							graphics.drawRect(shapeToBeDrawn.getStartPoint().x, shapeToBeDrawn.getStartPoint().y, shapeToBeDrawn.getEndPoint().x - shapeToBeDrawn.getStartPoint().x, shapeToBeDrawn.getEndPoint().y - shapeToBeDrawn.getStartPoint().y);
							break;
						}
						case CIRCLE:
						{
							graphics.drawOval(shapeToBeDrawn.getStartPoint().x, shapeToBeDrawn.getStartPoint().y, shapeToBeDrawn.getEndPoint().x - shapeToBeDrawn.getStartPoint().x, shapeToBeDrawn.getEndPoint().y - shapeToBeDrawn.getStartPoint().y);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed
	 * and released) on a component.
	 *
	 * @param e The MouseEvent that was performed by clicking a mouse button on the Paint Application's Canvas.
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e The MouseEvent that was performed by pressing a mouse button on the Paint Application's Canvas.
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		// Allow Drawing
		drawing = true;

		// Create A Shape To Draw
		shapeToDraw = new ShapeToDraw(e.getPoint(), paintColor, shape);
		shapesToDraw.add(shapeToDraw);
	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e The MouseEvent that was performed by releasing a mouse button on the Paint Application's Canvas.
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		// Update End Point So The User Can See Their Final Shape
		shapeToDraw.setEndPoint(e.getPoint());

		// Save The Shape To Draw
		shapesToDraw.add(shapeToDraw);

		// For Drawing To The Canvas As The User Releases The Mouse Button!!! (IMPORTANT)
		repaint();
	}

	/**
	 * Invoked when the mouse enters a component.
	 *
	 * @param e The MouseEvent that was performed by the mouse entering the Paint Application's Canvas.
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	/**
	 * Invoked when the mouse exits a component.
	 *
	 * @param e The MouseEvent that was performed by the mouse exiting the Paint Application's Canvas.
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	/**
	 * Invoked when a mouse button is pressed on a component and then
	 * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
	 * delivered to the component where the drag originated until the
	 * mouse button is released (regardless of whether the mouse position
	 * is within the bounds of the component).
	 * <p>
	 * Due to platform-dependent Drag&amp;Drop implementations,
	 * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
	 * Drag&amp;Drop operation.
	 *
	 * @param e
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		// Handle The Various Shapes As The Mouse Drags (To Show What You Are Drawing Before Mouse Releases)
		switch (shape)
		{
			case POINT:
			{
				// If The Current Paint Shape Is Point, Draw One Point Each Time The Mouse Drags (Free-Hand Painting)
				shapeToDraw = new ShapeToDraw(e.getPoint(), paintColor, shape);
				if (!shapesToDraw.contains(shapeToDraw))
				{
					shapesToDraw.add(shapeToDraw);
				}
				break;
			}
			case LINE:
			case RECTANGLE:
			case CIRCLE:
			{
				// If The Current Paint Shape Is Line/Rectangle/Circle, Update The End Point As The Mouse Moves
				shapeToDraw.setEndPoint(e.getPoint());
				break;
			}
		}

		// For Drawing To The Canvas As The User Drags The Mouse!!! (IMPORTANT)
		repaint();
	}

	/**
	 * Invoked when the mouse cursor has been moved onto a component
	 * but no buttons have been pushed.
	 *
	 * @param e
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{

	}
}
