package danny.Paint;

import javax.swing.SwingUtilities;

class Launcher
{
	public static void main(String[] args)
	{
		// Create The Paint Application Window On The Event-Dispatch Thread To Ensure Thread Safety
		SwingUtilities.invokeLater(Window::new);
	}
}
