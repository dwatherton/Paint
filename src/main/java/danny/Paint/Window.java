package danny.Paint;

import lombok.Getter;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;

class Window
{
	private static final Image COLOR_PALETTE = new ImageIcon(Window.class.getResource("/colorpalette.png")).getImage();
	private static final String TITLE = "Java Paint Program by Daniel Atherton";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int TOOLBAR_HEIGHT = 60;

	@Getter
	private static JFrame window;

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
		window.setJMenuBar(new MenuBar().getMenuBar());

		// Get The Paint Application Window's Content Pane
		Container container = window.getContentPane();

		// Create A Group Layout And Add It To The Paint Application Container
		GroupLayout layout = new GroupLayout(container);
		container.setLayout(layout);

		// Create A Paint Toolbar And Canvas For Painting
		JToolBar paintToolbar = new PaintToolbar().getPaintToolbar();
		Canvas canvas = new Canvas();

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
}
