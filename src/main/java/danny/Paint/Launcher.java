package danny.Paint;

import javax.swing.SwingUtilities;

public class Launcher
{
	public static void main(String[] args)
	{
		// Run The Paint Application On The Event-Dispatch Thread To Ensure Thread Safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				// Create Paint Application Window
				new Window();
			}
		});
	}
}
