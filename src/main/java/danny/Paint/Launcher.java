package danny.Paint;

import javax.swing.SwingUtilities;

class Launcher
{
	static boolean isDebugEnabled = false;

	public static void main(String[] args)
	{
		// Check If Any Arguments Were Passed In
		if (args != null)
		{
			// Loop Through The Arguments
			for (String string : args)
			{
				// If One Of The Arguments Is Debug
				if (string.equalsIgnoreCase("--debug"))
				{
					// Set Is Debug Enabled To True And Print Debug Mode To The Console
					isDebugEnabled = true;
					System.out.println("Running Paint Application In Debug Mode");
				}
			}
		}

		// Create The Paint Application Window On The Event-Dispatch Thread To Ensure Thread Safety
		SwingUtilities.invokeLater(Window::new);
	}
}
