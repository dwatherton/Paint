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

	static void showHelp(ActionEvent e)
	{
		// Create A Frame For The Help Menu Option
		JFrame frame = new JFrame("Help");
		frame.setPreferredSize(new Dimension(750, 550));

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
					doc.insertString(doc.getLength(), "\n* To Begin Painting Simply Select A Color, Style, And Line Thickness, Then Begin Drawing With The Cursor!\n\n\n", body);

					doc.insertString(doc.getLength(), "* There Are Two Ways To Select A Paint Color: The Large Button With A Color Wheel And The Current Selected Color Preview, It Allows Any Custom Color To Be Selected, And The 8 Smaller Colored Buttons Are For Quickly Selecting A Simple Color.\n", body);
					doc.insertString(doc.getLength(), "* There Are Four Styles You Can Choose To Paint With: Point (Free-hand One Point At A Time), Line (A Simple Line), Square (A Simple Square/Rectangle), And Circle (A Simple Circle/Oval).\n", body);
					doc.insertString(doc.getLength(), "* There Are Four Line Thickness: 1 Through 4, With 1 Being The Thinnest And 4 Being The Thickest.\n\n\n", body);

					doc.insertString(doc.getLength(), "* When Using Any Paint Color, Custom Or A Quick Color, The Style And Thickness Selected Will Be Drawn In That Color.\n\n\n", body);

					doc.insertString(doc.getLength(), "* When Using The Various Paint Styles, Different Styles Will Exhibit Different Behaviors On Clicking, On Dragging With The Left Mouse Button Down, And On Releasing The Left Mouse Button.\n", body);
					doc.insertString(doc.getLength(), "* When Using The Point Style, A Simple Click Will Draw A Single Point, Dragging The Mouse With The Left Mouse Button Down Will Draw A New Point At Each Location Under The Cursor, And Releasing The Left Mouse Button Will Quit Drawing New Points As The Cursor Moves.\n", body);
					doc.insertString(doc.getLength(), "* When Using The Line Style, A Simple Click Will Draw A Single Point, Dragging The Mouse With The Left Mouse Button Down Will Show A Preview Of The Line To Be Drawn Upon Releasing The Left Mouse Button, And Releasing The Left Mouse Button Will Finish Drawing The Line From The Point The Left Mouse Button Went Down At To The Point The Left Mouse Button Went Back Up.\n", body);
					doc.insertString(doc.getLength(), "* When Using The Square Style, A Simple Click Will Draw A Single Point, Dragging The Mouse With The Left Mouse Button Down Will Show A Preview Of The Square To Be Drawn Upon Releasing The Left Mouse Button, And Releasing The Left Mouse Button Will Finish Drawing The Square From The Point The Left Mouse Button Went Down At To The Point The Left Mouse Button Went Back Up.\n", body);
					doc.insertString(doc.getLength(), "* When Using The Circle Style, A Simple Click Will Not Draw Anything, Dragging The Mouse With The Left Mouse Button Down Will Show A Preview Of The Circle To Be Drawn Upon Releasing The Left Mouse Button, And Releasing The Left Mouse Button Will Finish Drawing The Circle From The Point The Left Mouse Button Went Down At To The Point The Left Mouse Button Went Back Up.\n\n\n", body);

					doc.insertString(doc.getLength(), "* When Using Any Line Thickness, 1 All The Way Up To 4, The Color And Style Selected Will Be Drawn In That Thickness.\n\n\n", body);

					doc.insertString(doc.getLength(), "* Aside From Clicking The Left Mouse Button And/Or Dragging The Mouse With The Left Mouse Button Down, There Are A Few Other Ways To Modify What Is Being Painted With The Mouse. They Work For All Paint Styles EXCEPT Point, And Are Listed Below:\n", body);
					doc.insertString(doc.getLength(), "* While Holding The Left Mouse Button Down And Dragging To Draw A Shape, You May Chose To Cancel Drawing The Shape Being Previewed To You By Pressing The Right Mouse Button Down.\n", body);
					doc.insertString(doc.getLength(), "* Even After The Current Shape Has Been Drawn, It's Size (Width/Height) And Endpoint May Be Modified In Case The Mouse Left Button Was Accidentally Released. To Do This, Simply Hold Down The Shift Key On Your Keyboard, And Click The Left Mouse Button In A New Place Or Drag The Mouse To A New Place With The Left Mouse Button Down.\n\n\n", body);

					doc.insertString(doc.getLength(), "* On Top Of The Already Explained Functionality Above, This Simple Paint Program Contains Multiple Useful Keybinds And Menu Options. These Keybinds And Menu Options As Well As What They Do Can Be Seen Below: \n", body);
					doc.insertString(doc.getLength(), "* (Ctrl + N)/(Cmd + N) OR 'File' > 'New Window' - Discards Current Paint Program Window For A New Clean Paint Program Window.\n", body);
					doc.insertString(doc.getLength(), "* (Ctrl + Q)/(Cmd + Q) OR 'File' > 'Close' - Closes The Current Paint Program And Discards All Painted Shapes (No Save Functionality Yet).\n", body);
					doc.insertString(doc.getLength(), "* (Ctrl + Z)/(Cmd + Z) OR 'Edit' > 'Undo' - Un-does The Last Shape Drawn On The Paint Canvas (Can Be Done Until No Shapes Are Left On The Canvas).\n", body);
					doc.insertString(doc.getLength(), "* (Ctrl + Y)/(Cmd + Y) OR 'Edit' > 'Redo' - Re-does The Last Shape Un-done From Being Drawn On The Paint Canvas (Can Be Done Until No More Shapes Were Un-done Or The User Draws A New Shape).\n", body);
					doc.insertString(doc.getLength(), "* (Ctrl + I)/(Cmd + I) OR 'Help' > 'Instructions' - Displays The Instructions That You Are Reading Right Now.\n", body);
					doc.insertString(doc.getLength(), "* (Ctrl + A)/(Cmd + A) OR 'Help' > 'About' - Displays A Brief Background On How This Paint Program Came To Be In A New Popout Window.\n", body);
					if (Launcher.isDebugEnabled)
					{
						doc.insertString(doc.getLength(), "* (Ctrl + D)/(Cmd + D) OR 'Help' > 'Debug' - Prints Lots Of Useful Information About Each Shape Drawn To The Canvas And The Number Of Shapes On The Canvas, To The Console.\n", body);
					}
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
					doc.insertString(doc.getLength(), "\nThis Java Paint Application Was Created In My Spare Time During The Summer Of 2019, And Was Only The Second Java GUI Application I Had Ever Created. Due To My Relative Inexperience And Spare Time Growing Much More Limited As I Progressed, I Was Unable To Fully Implement The Functionality That I'd Have Liked To (Bucket Fill, Pencil Style, Save/Load, Etc). That Said, There Are Many Things I'd Change, Add, And Improve If I Were To Work On This Again, But I No Longer Have The Time To. I Am However Still Pretty Happy With How This Little Paint Application Went From A Pass-time To A Decently Useful, Minimalistic, Painting Program. \n\n\n", body);
					doc.insertString(doc.getLength(), "If You Are Reading This, And/Or Have Used This Paint Application, Thank You, And I Hope You Are Enjoying It Or Find It Interesting At The Least!\n -Daniel \n", body);
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
