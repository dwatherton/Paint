package danny.Paint;

import lombok.Getter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

class PaintToolbar
{
	@Getter
	public JToolBar paintToolbar;

	PaintToolbar()
	{
		// Create A Paint Toolbar
		paintToolbar = new JToolBar();

		// Don't Allow Floating Toolbar
		paintToolbar.setFloatable(false);

		// Set Paint Toolbar Color
		paintToolbar.setBackground(Color.LIGHT_GRAY);

		// Set Paint Toolbar Layout To Flow (Left -> Right) With A Gap Of 1 Pixel On All Sides Of Contents
		paintToolbar.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));

		// Add Generic Quick Selection Color Buttons
		addQuickColorButtons();

		// Add A Seperator Between Groups Of Button Containers
		paintToolbar.addSeparator(new Dimension(10, 50));

		// TODO: Add Buttons to Paint Toolbar for brush size, shape, color, etc.
	}

	private void addQuickColorButtons()
	{
		// Create 8 Generic Color Buttons For Quick Access
		JButton black = createColorButton(Color.BLACK);
		JButton white = createColorButton(Color.WHITE);
		JButton red = createColorButton(Color.RED);
		JButton orange = createColorButton(Color.ORANGE);
		JButton yellow = createColorButton(Color.YELLOW);
		JButton green = createColorButton(Color.GREEN);
		JButton blue = createColorButton(Color.BLUE);
		JButton pink = createColorButton(Color.PINK);

		// Create A Quick Color Container For Holding A 2x4 Row Of Generic Quick Access Colors
		Container quickColorContainer = new Container();
		quickColorContainer.setLayout(new GridLayout(2, 4));

		// Add Buttons To The Container
		quickColorContainer.add(black);
		quickColorContainer.add(white);
		quickColorContainer.add(red);
		quickColorContainer.add(orange);
		quickColorContainer.add(yellow);
		quickColorContainer.add(green);
		quickColorContainer.add(blue);
		quickColorContainer.add(pink);

		// Add The Container With 2 Rows Of 4 Color Buttons To The Paint Toolbar
		paintToolbar.add(quickColorContainer);
	}

	private JButton createColorButton(Color color)
	{
		BufferedImage image = new BufferedImage(25, 25, ColorSpace.TYPE_RGB);
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(color);
		graphics.fillRect(0, 0, 25, 25);
		JButton colorButton = new JButton(new ImageIcon(image));
		colorButton.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		colorButton.setSize(25, 25);
		return colorButton;
	}
}