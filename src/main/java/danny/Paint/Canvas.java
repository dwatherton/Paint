package danny.Paint;

import lombok.Getter;
import lombok.Setter;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Canvas extends JPanel implements MouseListener, MouseMotionListener
{
	@Getter
	@Setter
	private static Color paintColor = Color.BLACK;

	@Getter
	@Setter
	private static Shape shape = Shape.POINT;

	@Getter
	@Setter
	private static int brushSize = 1;

	private boolean isDrawing;
	private boolean isLeftMouseButtonDown;
	private ShapeToDraw shapeToDraw;
	private List<ShapeToDraw> shapesToDraw;
	private Stack<ShapeToDraw> removedShapes;

	Canvas()
	{
		// Initialize Canvas Variables And/Or Clear The Canvas For When The User Selects 'File' > 'New Window'
		clearCanvas();

		// Set Canvas Background Color And Add Mouse Listener For Painting
		setBackground(Color.WHITE);

		// Add Listeners For Drawing With The Mouse
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	private void clearCanvas()
	{
		isDrawing = false;
		isLeftMouseButtonDown = false;
		shapeToDraw = null;
		shapesToDraw = new ArrayList<>();
		removedShapes = new Stack<>();
	}

	private void paintInstructions(Graphics2D graphics2D)
	{
		// Set Image X and Image Y For Centering The Color Palette Image
		int imageX = (getWidth() / 2) - (Window.COLOR_PALETTE.getWidth(null) / 2);
		int imageY = (getHeight() / 2) - (Window.COLOR_PALETTE.getHeight(null) / 2);

		// Draw Color Palette Image To Center Of Canvas
		graphics2D.drawImage(Window.COLOR_PALETTE, imageX, imageY, null);

		// Get Font Metrics For Centering Instruction String
		FontMetrics fm = graphics2D.getFontMetrics();

		// Set Instruction Message, Width, And Height (Ascent)
		String instructions = "To Begin Painting Simply Select A Color, Style, And Line Thickness, Then Begin Drawing With The Cursor!";
		int instructionsWidth = fm.stringWidth(instructions);
		int instructionsHeight = fm.getAscent();

		// Set Instruction Message Width And Height To Center The Instruction String (Horizontally), And Just Above The Image (Vertically)
		int instructionsX = (getWidth() / 2 - instructionsWidth / 2);
		int instructionsY = imageY - (instructionsHeight * 3);

		// Set Color And Write The Instructions To The Canvas
		graphics2D.setColor(Color.BLACK);
		graphics2D.drawString(instructions, instructionsX, instructionsY);
	}

	@Override
	public void paintComponent(Graphics graphics)
	{
		// Paint The Canvas (JPanel Component)
		super.paintComponent(graphics);
		Graphics2D graphics2D = (Graphics2D) graphics;

		if (!isDrawing)
		{
			paintInstructions(graphics2D);
		}
		else
		{
			if (!shapesToDraw.isEmpty())
			{
				// Loop Through The Shapes To Draw
				for (ShapeToDraw shapeToBeDrawn : shapesToDraw)
				{
					// Set The Paint Color And Brush Size Based On Their Values When The User Began Drawing The Shape
					graphics2D.setColor(shapeToBeDrawn.getPaintColor());
					graphics2D.setStroke(new BasicStroke(shapeToBeDrawn.getBrushSize()));

					// Get The Shapes Start Coordinates (Pick The Smallest Of The Coordinates For Drawing Rectangles/Circles In ANY Direction - Shapes go TL -> BR)
					final int startX = Math.min(shapeToBeDrawn.getStartPoint().x, shapeToBeDrawn.getEndPoint().x);
					final int startY = Math.min(shapeToBeDrawn.getStartPoint().y, shapeToBeDrawn.getEndPoint().y);

					// Get The Shapes End Coordinates
					final int endX = shapeToBeDrawn.getEndPoint().x;
					final int endY = shapeToBeDrawn.getEndPoint().y;

					// Get The Shapes Dimensions (Pick The Largest Of The Coordinates For Drawing Rectangles/Circles In ANY Direction And Subtract The Start Coord)
					final int width = Math.max(shapeToBeDrawn.getStartPoint().x, shapeToBeDrawn.getEndPoint().x) - startX;
					final int height = Math.max(shapeToBeDrawn.getStartPoint().y, shapeToBeDrawn.getEndPoint().y) - startY;

					// Handle Drawing The Various Shapes
					switch (shapeToBeDrawn.getShape())
					{
						case POINT:
						case LINE:
						{
							// Use The Shape To Be Drawn Coordinates From When The Mouse Was Pressed For A Point/Line (They Can Already Be Drawn In ANY Direction)
							graphics2D.drawLine(shapeToBeDrawn.getStartPoint().x, shapeToBeDrawn.getStartPoint().y, endX, endY);
							break;
						}
						case RECTANGLE:
						{
							graphics2D.drawRect(startX, startY, width, height);
							break;
						}
						case CIRCLE:
						{
							graphics2D.drawOval(startX, startY, width, height);
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
		// If Right-Click And Shape To Draw Is Not Null, Remove Shape From The List Of Shapes To Draw And Null It Out (Stops Drawing The Dragged Shape Being Drawn)
		if (isLeftMouseButtonDown && e.getButton() == MouseEvent.BUTTON3 && shapeToDraw != null)
		{
			shapesToDraw.remove(shapeToDraw);
			shapeToDraw = null;
			repaint();
		}
	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e The MouseEvent that was performed by pressing a mouse button on the Paint Application's Canvas.
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			// Set Left Mouse Button Down To True
			isLeftMouseButtonDown = true;

			// Allow Drawing
			isDrawing = true;

			if (!e.isShiftDown())
			{
				// Create A Shape To Draw
				shapeToDraw = new ShapeToDraw(e.getPoint(), e.getPoint(), paintColor, shape, brushSize, false);
				addShapeToShapesToDraw();

				// If The User Has Hit Undo And Added Shapes To The Removed Shapes Stack
				if (removedShapes.size() >= 1)
				{
					// Create A New Stack Each Time A New Shape Is Drawn (Only Allow The Redo Feature AFTER The User Hits Undo)
					removedShapes = new Stack<>();
				}

				// Repaint The Canvas
				repaint();
			}
		}
	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e The MouseEvent that was performed by releasing a mouse button on the Paint Application's Canvas.
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			// Set Left Mouse Button Down To False
			isLeftMouseButtonDown = false;

			if (shapeToDraw != null)
			{
				// Update End Point So The User Can See Their Final Shape
				shapeToDraw.setEndPoint(e.getPoint());

				// For Drawing To The Canvas As The User Releases The Mouse Button!!! (IMPORTANT)
				repaint();
			}
		}
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
	 * @param e The MouseEvent that was performed by the mouse being dragged on the Paint Application's Canvas.
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		// Handle The Various Shapes As The Mouse Drags (To Show What You Are Drawing Before Mouse Releases)
		switch (shape)
		{
			case POINT:
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					// If The Current Paint Shape Is Point, Draw One Point Each Time The Mouse Drags (Free-Hand Painting)
					shapeToDraw = new ShapeToDraw(e.getPoint(), e.getPoint(), paintColor, shape, brushSize, false);
					addShapeToShapesToDraw();
				}
				break;
			}
			case LINE:
			case RECTANGLE:
			case CIRCLE:
			{
				// Make Sure The User Didn't Click The Right Mouse Button To Quit Drawing The Current Line/Rectangle/Circle (Shift + Drag Updates A Shapes Endpoint)
				if ((shapeToDraw != null) && (e.getButton() == MouseEvent.BUTTON1 || (e.isShiftDown() && e.getButton() == MouseEvent.BUTTON1)))
				{
					// If The Current Paint Shape Is Line/Rectangle/Circle, Update The End Point As The Mouse Moves
					shapeToDraw.setEndPoint(e.getPoint());
				}
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
	 * @param e The MouseEvent that was performed by the mouse being moved around on the Paint Application's Canvas.
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{

	}

	private void addShapeToShapesToDraw()
	{
		// Loop Through The List Of Shapes To Draw To Make Sure An Identical Shape Has Not Already Been Added To It (Save Memory For Performance)
		for (ShapeToDraw shape : shapesToDraw)
		{
			// Return If The Shape To Draw Is Already In The List
			if (shapeToDraw.equals(shape))
			{
				return;
			}
		}

		// Add The Unique Shape To The List Of Shapes To Draw
		shapesToDraw.add(shapeToDraw);
	}

	void undo()
	{
		// Make Sure There Are Shapes Currently Drawn On The Canvas, Otherwise Return
		if (shapesToDraw.size() == 0)
		{
			return;
		}

		// Get The Last Shape Added To The Shapes To Draw List
		shapeToDraw = shapesToDraw.get(shapesToDraw.size() - 1);

		// Add It To The Removed Shapes Stack
		removedShapes.push(shapeToDraw);

		// Remove The Shape To Draw From The List Of Shapes To Draw
		shapesToDraw.remove(shapeToDraw);

		// Set Shape To Draw To Null
		shapeToDraw = null;

		// Repaint The Canvas
		repaint();
	}

	void redo()
	{
		// Make Sure There Are Shapes That Have Been Removed Via The Undo Feature, Otherwise Return
		if (removedShapes.size() == 0)
		{
			return;
		}

		// Get The Last Shape Added To The Removed Shapes Stack
		shapeToDraw = removedShapes.pop();

		// Add The Shape To Draw Back To The List Of Shapes To Draw
		shapesToDraw.add(shapeToDraw);

		// Repaint The Canvas
		repaint();
	}

	void debug()
	{
		int i = 1;
		for (ShapeToDraw shape : shapesToDraw)
		{
			System.out.println(
					"\n" +
					"Shape Number: " + i++ + "\n" +
					"Start Point: " + shape.getStartPoint() + "\n" +
					"End Point: " + shape.getEndPoint() + "\n" +
					"Paint Color: " + shape.getPaintColor() + "\n" +
					"Shape: " + shape.getShape() + "\n" +
					"Brush Size: " + shape.getBrushSize() + "\n" +
					"Is Filled: " + shape.isFilled()
					+ "\n"
			);
		}
	}
}
