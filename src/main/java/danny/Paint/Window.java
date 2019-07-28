package danny.Paint;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;

class Window
{
	static final Image COLOR_PALETTE = new ImageIcon(Window.class.getResource("/colorpalette.png")).getImage();

	private static final String TITLE = "Java Paint Program by Daniel Atherton";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int TOOLBAR_HEIGHT = 61;

	// Components Of The Paint Application
	private static JFrame window;
	private static JMenuBar menuBar;
	private static JToolBar paintToolbar;
	private static Canvas canvas;

	Window()
	{
		// Create A New Paint Application Window
		window = new JFrame(TITLE);

		// Color Palette Icon For Windows/Linux User's Only -- Does NOT Work Natively On Mac
		window.setIconImage(COLOR_PALETTE);

		// Set Window Size And Close Operation
		window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create And Add Paint Application's MenuBar
		menuBar = new MenuBar().getMenuBar();
		window.setJMenuBar(menuBar);

		// Get The Paint Application Window's Content Pane
		Container container = window.getContentPane();

		// Create A Group Layout And Add It To The Paint Application Container
		GroupLayout layout = new GroupLayout(container);
		container.setLayout(layout);

		// Create A Paint Toolbar And Canvas For Painting
		paintToolbar = new PaintToolbar().getPaintToolbar();
		canvas = new Canvas();

		// Set The Paint Toolbar And Canvas To Be Horizontally Parallel
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(paintToolbar)
				.addComponent(canvas)
		);

		// Set the Paint Toolbar To 60 Pixels Tall And The Canvas To Span The Rest Of The Paint Application Container
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(paintToolbar, TOOLBAR_HEIGHT, TOOLBAR_HEIGHT, TOOLBAR_HEIGHT)
				.addComponent(canvas, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);

		// Set Paint Application Window Content Pane
		window.setContentPane(container);

		// Pack, Center, And Set The Window To Be Visible
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	static void close()
	{
		// Dispose Of The Paint Application Window
		window.dispose();
	}

	static void undo()
	{
		// Call Undo Method In The Canvas Class
		canvas.undo();
	}

	static void redo()
	{
		// Call Redo Method In The Canvas Class
		canvas.redo();
	}

	static void debug()
	{
		// Call Debug Method In The Canvas Class
		canvas.debug();
	}
}
