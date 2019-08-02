package danny.Paint;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;

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

		// Set the Paint Toolbar To 61 Pixels Tall And The Canvas To Span The Rest Of The Paint Application Container
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

	// TODO: Modify The Placeholder Text On The Instructions And About Text Panes
	static void showHelp(ActionEvent e)
	{
		// Create A Frame For The Help Menu Option
		JFrame frame = new JFrame("Help");
		frame.setPreferredSize(new Dimension(500, 300));

		// Create A TextPane
		JTextPane pane = new JTextPane();

		// Get The Styled Document For The Text Pane
		StyledDocument doc = pane.getStyledDocument();

		// Create A Keyword Attribute Set
		SimpleAttributeSet keyword = new SimpleAttributeSet();
		StyleConstants.setAlignment(keyword, StyleConstants.ALIGN_CENTER);
		StyleConstants.setForeground(keyword, Color.BLACK);
		StyleConstants.setFontSize(keyword, 20);
		StyleConstants.setBold(keyword, true);
		StyleConstants.setUnderline(keyword, true);

		// Create A Body Text Attribute Set
		SimpleAttributeSet body = new SimpleAttributeSet();
		StyleConstants.setAlignment(body, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(body, Color.DARK_GRAY);
		StyleConstants.setFontSize(body, 12);

		switch (e.getActionCommand())
		{
			case "Instructions":
			{
				// Try To Write The Instructions On How To Paint To The Text Pane
				try
				{
					// Set Paragraph Attributes For A Keyword, And Write The Instructions Heading (Replacing Previous Styling With My Own)
					doc.setParagraphAttributes(doc.getLength(), doc.getLength(), keyword, true);
					doc.insertString(doc.getLength(), "Instructions\n", keyword);

					// Set Paragraph Attributes For Body Text, And Write The About Text To The Pane (Replacing Previous Styling)
					doc.setParagraphAttributes(doc.getLength(), doc.getLength(), body, true);
					doc.insertString(doc.getLength(), "\nThis Is A Random Line Explaining How To Paint.\n", body);
					doc.insertString(doc.getLength(), "This Is A Random Line Explaining How To Paint.\n", body);
					doc.insertString(doc.getLength(), "This Is A Random Line Explaining How To Paint.\n", body);
					doc.insertString(doc.getLength(), "This Is A Random Line Explaining How To Paint.\n\n\n", body);
				}
				catch(BadLocationException ex)
				{
					System.out.println(ex.getMessage());
				}

				break;
			}
			case "About":
			{
				// Try To Write The About Background Information To The Text Pane
				try
				{
					// Set Paragraph Attributes For A Keyword, And Write The About Heading (Replacing Previous Styling With My Own)
					doc.setParagraphAttributes(doc.getLength(), doc.getLength(), keyword, true);
					doc.insertString(doc.getLength(), "About\n", keyword);

					// Set Paragraph Attributes For Body Text, And Write The About Text To The Pane (Replacing Previous Styling)
					doc.setParagraphAttributes(doc.getLength(), doc.getLength(), body, true);
					doc.insertString(doc.getLength(), "\nThis Java Paint Application was created in my spare time during the Summer of 2019.\n", body);
					doc.insertString(doc.getLength(), "This Is A Random Line Explaining The Project.\n", body);
					doc.insertString(doc.getLength(), "This Is A Random Line Explaining The Project.\n", body);
					doc.insertString(doc.getLength(), "This Is A Random Line Explaining The Project.\n\n\n", body);
				}
				catch(BadLocationException ex)
				{
					System.out.println(ex.getMessage());
				}

				break;
			}
		}

		// After Writing To The Text Pane, Disable Editing
		pane.setEditable(false);

		// Place The Text Pane Into A Scroll Pane (In Case Of Text Overflow On Smaller Screens/Devices)
		JScrollPane scrollPane = new JScrollPane(pane);
		scrollPane.setSize(600, 350);

		// Add Text Pane To Container
		Container container = frame.getContentPane();
		container.add(scrollPane);

		// Set Frame Content Pane, Pack It, Center It, And Set It Visible
		frame.setContentPane(container);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	static void debug()
	{
		// Call Debug Method In The Canvas Class
		canvas.debug();
	}
}
