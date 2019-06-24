package danny.Paint;

import lombok.Getter;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuBar implements ActionListener
{
	private static final JMenu FILE = new JMenu("File");
	private static final JMenuItem NEW_WINDOW = new JMenuItem("New Window");
	private static final JMenuItem CLOSE = new JMenuItem("Close");
	private static final JMenu HELP = new JMenu("Help");
	private static final JMenuItem ABOUT = new JMenuItem("About");

	@Getter
	private JMenuBar menuBar;

	MenuBar()
	{
		// Create A Menu Bar
		menuBar = new JMenuBar();

		// Create File Menu Structure And Add It To The MenuBar
		FILE.add(NEW_WINDOW);
		FILE.add(CLOSE);
		menuBar.add(FILE);

		// Create Help Menu Structure And Add It To The MenuBar
		HELP.add(ABOUT);
		menuBar.add(HELP);

		// Add Action Listeners For The Menu Items The User Can Select
		NEW_WINDOW.addActionListener(this);
		CLOSE.addActionListener(this);
		ABOUT.addActionListener(this);
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e The ActionEvent that was performed by clicking on one of the MenuBars' MenuItems.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Handle The Various Menu Item Actions
		switch (e.getActionCommand())
		{
			case "New Window":
			{
				System.out.println("User Clicked 'File' > 'New Window'");

				// Remove Action Listener's From Old Window Menu Bar And Dispose Of The Window
				NEW_WINDOW.removeActionListener(this);
				CLOSE.removeActionListener(this);
				ABOUT.removeActionListener(this);
				Window.getWindow().dispose();

				// Call Main In The Launcher Class To Run A New Paint Window On The Event-Dispatch Thread
				Launcher.main(null);
				break;
			}
			case "Close":
			{
				System.out.println("User Clicked 'File' > 'Close'");

				// Dispose Of The Paint Application Window And Quit The Application
				Window.getWindow().dispose();
				System.exit(0);
				break;
			}
			case "About":
			{
				System.out.println("User Clicked 'Help' > 'About'");

				// Todo: Create and display a Window explaining the Paint Application when the user clicks "Help" > "About"
				break;
			}
			default:
			{
				// Print The Un-Recognized Menu Item That Was Selected
				System.out.println(e.getActionCommand());
				break;
			}
		}
	}
}
