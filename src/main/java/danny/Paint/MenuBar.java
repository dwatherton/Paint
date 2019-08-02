package danny.Paint;

import lombok.Getter;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

class MenuBar implements ActionListener
{
	private static final JMenu FILE = new JMenu("File");
	private static final JMenuItem NEW_WINDOW = new JMenuItem("New Window");
	private static final JMenuItem CLOSE = new JMenuItem("Close");
	private static final JMenu EDIT = new JMenu("Edit");
	private static final JMenuItem UNDO = new JMenuItem("Undo");
	private static final JMenuItem REDO = new JMenuItem("Redo");
	private static final JMenu HELP = new JMenu("Help");
	private static final JMenuItem INSTRUCTIONS = new JMenuItem("Instructions");
	private static final JMenuItem ABOUT = new JMenuItem("About");
	private static final JMenuItem DEBUG = new JMenuItem("Debug");

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

		// Create Edit Menu Structure And Add It To The MenuBar
		EDIT.add(UNDO);
		EDIT.add(REDO);
		menuBar.add(EDIT);

		// Create Help Menu Structure And Add It To The MenuBar
		HELP.add(INSTRUCTIONS);
		HELP.add(ABOUT);
		menuBar.add(HELP);

		// Add Action Listeners For The Menu Items The User Can Select
		NEW_WINDOW.addActionListener(this);
		CLOSE.addActionListener(this);
		UNDO.addActionListener(this);
		REDO.addActionListener(this);
		INSTRUCTIONS.addActionListener(this);
		ABOUT.addActionListener(this);

		// Set Accelerators For Each Menu Item (Keyboard Shortcuts For Quick Access)
		NEW_WINDOW.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		CLOSE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		UNDO.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		REDO.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		INSTRUCTIONS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		ABOUT.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// If Debug Is Enabled, Add The Debug Menu Option To The Help Menu
		if (Launcher.isDebugEnabled)
		{
			HELP.add(DEBUG);
			DEBUG.addActionListener(this);
			DEBUG.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		}
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
			// File Menu Options
			case "New Window":
			{
				System.out.println("User Selected 'File' > 'New Window'");

				// Remove Action Listener's From Old Window Menu Bar And Close The Window
				NEW_WINDOW.removeActionListener(this);
				CLOSE.removeActionListener(this);
				UNDO.removeActionListener(this);
				REDO.removeActionListener(this);
				INSTRUCTIONS.removeActionListener(this);
				ABOUT.removeActionListener(this);
				DEBUG.removeActionListener(this);
				Window.close();

				// Call Main In The Launcher Class To Run A New Paint Window On The Event-Dispatch Thread
				Launcher.main(null);
				break;
			}
			case "Close":
			{
				System.out.println("User Selected 'File' > 'Close'");

				// Close The Paint Application Window And Quit The Application
				Window.close();
				System.exit(0);
				break;
			}
			// Edit Menu Options
			case "Undo":
			{
				System.out.println("User Selected 'Edit' > 'Undo'");

				// Call The Undo Function In The Window Class
				Window.undo();
				break;
			}
			case "Redo":
			{
				System.out.println("User Selected 'Edit' > 'Redo'");

				// Call The Redo Function In The Window Class
				Window.redo();
				break;
			}
			// Help Menu Options
			case "Instructions":
			case "About":
			{
				System.out.println("User Selected 'Help' > '" + e.getActionCommand() + "'");

				// Call The Show Help Function In The Window Class
				Window.showHelp(e);
				break;
			}
			case "Debug":
			{
				System.out.println("User Selected 'Help' > 'Debug'");

				// Call The Debug Function In The Window Class
				Window.debug();
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
