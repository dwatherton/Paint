package danny.Paint;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

class Canvas extends JPanel implements MouseListener
{
	// Image To Add Color To Canvas Until Drawing Begins (Temporary)
	private static final Image COLOR_PALETTE = new ImageIcon(Canvas.class.getResource("../../colorpalette.png")).getImage();

	private boolean draw;
	private Point startPoint;
	private Point endPoint;
	private List<Point> pointsToDraw;

	Canvas()
	{
		// Initialize Canvas Variables And/Or Clear The Canvas For When The User Selects 'File' > 'New Window'
		clearCanvas();

		// Set Canvas Background Color And Add Mouse Listener For Painting
		setBackground(Color.WHITE);
		addMouseListener(this);
	}

	private void clearCanvas()
	{
		draw = false;
		startPoint = null;
		endPoint = null;
		pointsToDraw = new ArrayList<>();
	}

	@Override
	public void paintComponent(Graphics graphics)
	{
		// Paint The Canvas (JPanel Component)
		super.paintComponent(graphics);

		if (!draw)
		{
			// Draw Color Palette Image
			graphics.drawImage(COLOR_PALETTE, (int) (this.getWidth() / 2.6), (int) (this.getHeight() / 5.5), null);

			// Print Instructions If Not Drawing Yet
			graphics.setColor(Color.BLACK);
			graphics.drawString("To Begin Painting Simply Select A Style And Begin Drawing!", (int) (this.getWidth() / 2.93), (int) (this.getHeight() / 1.2));

		}
		else
		{
			// TODO: Add Support For Drawing Various Size Dots, Lines, And Shapes As Well As Drawing As The Mouse Moves

			// Draws Rectangle Properly
			graphics.drawRect(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y);

			// Draws Individual Pixels Properly
			for (Point point : pointsToDraw)
			{
				graphics.drawRect(point.x, point.y, 1, 1);
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
		draw = true;

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

		// Update The Canvas Component's UI For Drawing Anything
		updateUI();
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
}
