package danny.Paint;

import lombok.Setter;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

class Canvas extends JPanel implements MouseListener, MouseMotionListener
{
	// Image To Add Color To Canvas Until Drawing Begins (Temporary)
	private static final Image COLOR_PALETTE = new ImageIcon(Canvas.class.getResource("../../colorpalette.png")).getImage();

	private boolean drawing;
	private Point startPoint;
	private Point currentPoint;
	private Point endPoint;
	private List<Point> pointsToDraw;

	@Setter
	private Color paintColor;

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
		startPoint = null;
		endPoint = null;
		pointsToDraw = new ArrayList<>();
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

			// Draws Rectangle Properly (Top Left To Bottom Right ONLY!)
			if (startPoint != null && endPoint != null)
			{
				graphics.drawRect(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y);
			}

			// Draws Individual Pixels Properly
			for (Point point : pointsToDraw)
			{
				graphics.drawRoundRect(point.x, point.y, 3, 3, 2, 2);
				graphics.fillRoundRect(point.x, point.y, 3, 3, 2, 2);
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

		// Get The Point The User Pressed The Mouse Button At
		startPoint = e.getPoint();

		// Track The Points The User Has Pressed The Mouse Button At
		pointsToDraw.add(startPoint);
	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e The MouseEvent that was performed by releasing a mouse button on the Paint Application's Canvas.
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		// Get The Point The User Released The Mouse Button At (For Drawing Shapes)
		endPoint = e.getPoint();

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
		// Get The Point The User Is Dragging The Mouse At
		currentPoint = e.getPoint();

		// If The Points To Draw Does Not Already Contain The Point The User Is Dragging The Mouse At
		if (!pointsToDraw.contains(currentPoint))
		{
			// Add The Current Point To Be Drawn
			pointsToDraw.add(currentPoint);
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
