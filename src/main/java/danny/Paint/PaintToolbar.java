package danny.Paint;

import lombok.Getter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

class PaintToolbar
{
	// Standard Button Width, Height, Border Thickness, And Separator Dimensions
	private static final int LARGE_BUTTON_WIDTH = 50;
	private static final int LARGE_BUTTON_HEIGHT = 53;
	private static final int MEDIUM_BUTTON_WIDTH = 25;
	private static final int MEDIUM_BUTTON_HEIGHT = 25;
	private static final int SMALL_BUTTON_WIDTH = 50;
	private static final int SMALL_BUTTON_HEIGHT = 11;
	private static final int BORDER_THICKNESS = 1;
	private static final Dimension SEPARATOR_DIMENSIONS = new Dimension(9, 56);
	private static final Image COLOR_WHEEL = new ImageIcon(PaintToolbar.class.getResource("/colorwheel.png")).getImage();

	// Custom Paint Color Selection Button (Slow, But Can Select Any Custom Color)
	private JButton customColor;

	// Paint Color Selection Buttons (Quick Color Selection)
	private JButton black;
	private JButton white;
	private JButton red;
	private JButton orange;
	private JButton yellow;
	private JButton green;
	private JButton blue;
	private JButton pink;

	// Shape Selection Buttons
	private JButton point;
	private JButton line;
	private JButton rectangle;
	private JButton circle;

	// Paint Brush Size Buttons
	private JButton brushSize1;
	private JButton brushSize2;
	private JButton brushSize3;
	private JButton brushSize4;

	// Currently Selected Buttons (Color, Shape, Size) - For Highlighting The Actively Selected Button(s)
	private JButton currentPaintColor;
	private JButton currentShape;
	private JButton currentBrushSize;

	@Getter
	private JToolBar paintToolbar;

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

		// Add Generic Custom Color Selection Button
		addCustomColorButton(Color.BLACK);
		addCustomColorButtonListener();

		// Add A Separator Between Groups Of Button Containers
		paintToolbar.addSeparator(new Dimension(1, 0));

		// Add Generic Quick Selection Color Buttons
		addQuickColorButtons();
		addQuickColorButtonListeners();

		// Set Current Color Button To Black And Highlight It's Border
		Canvas.setPaintColor(Color.BLACK);
		currentPaintColor = customColor;
		customColor.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));

		// Add A Separator Between Groups Of Button Containers
		paintToolbar.addSeparator(SEPARATOR_DIMENSIONS);

		// Add Generic Quick Shape Selection Buttons
		addQuickShapeButtons();
		addQuickShapeButtonListeners();

		// Set Current Shape To Point And Highlight It's Border
		Canvas.setShape(Shape.POINT);
		currentShape = point;
		point.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));

		// Add A Separator Between Groups Of Button Containers
		paintToolbar.addSeparator(SEPARATOR_DIMENSIONS);

		// Add Generic Brush Size Selection Buttons
		addQuickSizeButtons();
		addQuickSizeButtonListeners();

		// Set Current Brush Size To Brush Size 1 And Highlight It's Border
		Canvas.setBrushSize(1);
		currentBrushSize = brushSize1;
		brushSize1.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));

		// Add A Separator Between Groups Of Button Containers
		paintToolbar.addSeparator(SEPARATOR_DIMENSIONS);
	}

	private JButton createCustomColorButton(Color previewColor)
	{
		// Create Large White Image
		BufferedImage customColorImage = new BufferedImage(LARGE_BUTTON_WIDTH, LARGE_BUTTON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = customColorImage.createGraphics();
		graphics2D.setColor(Color.WHITE);
		graphics2D.fillRect(0, 0, LARGE_BUTTON_WIDTH, LARGE_BUTTON_HEIGHT);

		// Draw Color Wheel On Image
		graphics2D.drawImage(COLOR_WHEEL, 10, 5, LARGE_BUTTON_WIDTH - 20, LARGE_BUTTON_HEIGHT - 20, null);

		// Check If Custom Color Is Bright Or Dark For Modifying The Preview Rectangle Color
		Color borderRectangleColor = isCustomColorBright(previewColor) ? Color.BLACK : Color.GRAY;
		graphics2D.setColor(borderRectangleColor);

		// Draw Custom Color Preview Rectangle Below The Color Wheel Image
		graphics2D.setStroke(new BasicStroke(BORDER_THICKNESS * 2));
		graphics2D.drawRect( 4, 43, (LARGE_BUTTON_WIDTH - 10) + (BORDER_THICKNESS * 2), LARGE_BUTTON_HEIGHT / 10);
		graphics2D.setColor(previewColor);
		graphics2D.fillRect(5, 44, LARGE_BUTTON_WIDTH - 10, (LARGE_BUTTON_HEIGHT / 10) - (BORDER_THICKNESS * 2));
		graphics2D.dispose();

		// Create The Actual Button With The Image Created Above
		JButton customColorButton = new JButton(new ImageIcon(customColorImage));
		customColorButton.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
		customColorButton.setSize(LARGE_BUTTON_WIDTH, LARGE_BUTTON_HEIGHT);

		return customColorButton;
	}

	private void addCustomColorButton(Color color)
	{
		customColor = createCustomColorButton(color);
		customColor.setToolTipText("Custom Color");

		// Add Custom Color Button At Index 0
		paintToolbar.add(customColor, 0);
	}

	private void addCustomColorButtonListener()
	{
		customColor.addActionListener(e ->
		{
			// Let The User Select Any Custom Color They Want Via JColorChooser
			Color color = JColorChooser.showDialog(new JFrame(), "Choose A Color", Canvas.getPaintColor());
			if (color == null)
			{
				// User Selected Cancel, Return (Leaves The Color As The Previously Selected Color)
				return;
			}

			// User Selected A New Color, Update The Paint Color
			Canvas.setPaintColor(color);

			// Update The Custom Color Button's Color Preview
			updateCustomColorButton(color);

			// Highlight Custom Color Button And Set Previous Color Button Border Back To Normal
			currentPaintColor.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			customColor.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentPaintColor = customColor;
		});
	}

	private boolean isCustomColorBright(Color color)
	{
		// A Calculation That Returns True For Colors With A Brightness Above 130, Or False If Under 130.
		return Math.sqrt(color.getRed() * color.getRed() * .241 +
				color.getGreen() * color.getGreen() * .691 +
				color.getBlue() * color.getBlue() * .068) >= 130;
	}

	private void updateCustomColorButton(Color color)
	{
		// Remove Old Custom Color Button
		paintToolbar.remove(customColor);

		// Add New Custom Color Button And Action Listener
		addCustomColorButton(color);
		addCustomColorButtonListener();

		// Update Paint Toolbar UI After Removing And Adding The Button
		paintToolbar.updateUI();
	}

	private JButton createColorButton(Color color)
	{
		BufferedImage image = new BufferedImage(MEDIUM_BUTTON_WIDTH, MEDIUM_BUTTON_HEIGHT, ColorSpace.TYPE_RGB);
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(color);
		graphics.fillRect(0, 0, MEDIUM_BUTTON_WIDTH, MEDIUM_BUTTON_HEIGHT);
		JButton colorButton = new JButton(new ImageIcon(image));
		colorButton.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
		colorButton.setSize(MEDIUM_BUTTON_WIDTH, MEDIUM_BUTTON_HEIGHT);
		return colorButton;
	}

	private void addQuickColorButtons()
	{
		// Create 8 Generic Color Buttons With Tool Tips For Quick Access
		black = createColorButton(Color.BLACK);
		black.setToolTipText("Black");
		white = createColorButton(Color.WHITE);
		white.setToolTipText("White");
		red = createColorButton(Color.RED);
		red.setToolTipText("Red");
		orange = createColorButton(Color.ORANGE);
		orange.setToolTipText("Orange");
		yellow = createColorButton(Color.YELLOW);
		yellow.setToolTipText("Yellow");
		green = createColorButton(Color.GREEN);
		green.setToolTipText("Green");
		blue = createColorButton(Color.BLUE);
		blue.setToolTipText("Blue");
		pink = createColorButton(Color.PINK);
		pink.setToolTipText("Pink");

		// Create A Quick Color Container For Holding A 2x4 Row Of Generic Quick Access Colors
		Container quickColorContainer = new Container();
		quickColorContainer.setLayout(new GridLayout(2, 4, BORDER_THICKNESS, BORDER_THICKNESS));

		// Add Color Buttons To The Container
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

	private void addQuickColorButtonListeners()
	{
		black.addActionListener(e ->
		{
			Canvas.setPaintColor(Color.BLACK);
			updateCustomColorButton(Color.BLACK);
			currentPaintColor.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			black.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentPaintColor = black;
		});

		white.addActionListener(e ->
		{
			Canvas.setPaintColor(Color.WHITE);
			updateCustomColorButton(Color.WHITE);
			currentPaintColor.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			white.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentPaintColor = white;
		});

		red.addActionListener(e ->
		{
			Canvas.setPaintColor(Color.RED);
			updateCustomColorButton(Color.RED);
			currentPaintColor.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			red.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentPaintColor = red;
		});

		orange.addActionListener(e ->
		{
			Canvas.setPaintColor(Color.ORANGE);
			updateCustomColorButton(Color.ORANGE);
			currentPaintColor.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			orange.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentPaintColor = orange;
		});

		yellow.addActionListener(e ->
		{
			Canvas.setPaintColor(Color.YELLOW);
			updateCustomColorButton(Color.YELLOW);
			currentPaintColor.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			yellow.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentPaintColor = yellow;
		});

		green.addActionListener(e ->
		{
			Canvas.setPaintColor(Color.GREEN);
			updateCustomColorButton(Color.GREEN);
			currentPaintColor.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			green.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentPaintColor = green;
		});

		blue.addActionListener(e ->
		{
			Canvas.setPaintColor(Color.BLUE);
			updateCustomColorButton(Color.BLUE);
			currentPaintColor.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			blue.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentPaintColor = blue;
		});

		pink.addActionListener(e ->
		{
			Canvas.setPaintColor(Color.PINK);
			updateCustomColorButton(Color.PINK);
			currentPaintColor.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			pink.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentPaintColor = pink;
		});
	}

	private JButton createShapeButton(String shape)
	{
		BufferedImage image = new BufferedImage(LARGE_BUTTON_WIDTH, LARGE_BUTTON_HEIGHT, ColorSpace.TYPE_RGB);
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, LARGE_BUTTON_WIDTH, LARGE_BUTTON_HEIGHT);
		graphics.setColor(Color.BLACK);
		switch (shape)
		{
			case "Point":
				graphics.fillOval(20, 20, 10, 10);
				break;
			case "Line":
				graphics.drawLine(10, 10, 40, 40);
				break;
			case "Rectangle":
				graphics.drawRect(10, 10, 30, 30);
				break;
			case "Circle":
				graphics.drawOval(10, 10, 30, 30);
				break;
		}
		JButton shapeButton = new JButton(new ImageIcon(image));
		shapeButton.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		shapeButton.setSize(LARGE_BUTTON_WIDTH, LARGE_BUTTON_HEIGHT);
		shapeButton.setToolTipText(shape);
		return shapeButton;
	}

	private void addQuickShapeButtons()
	{
		// Create The Generic Shape Buttons With Tool Tips
		point = createShapeButton("Point");
		line = createShapeButton("Line");
		rectangle = createShapeButton("Rectangle");
		circle = createShapeButton("Circle");

		// Create A Shape Button Container For Holding A 1x4 Row Of Generic Shape Buttons
		Container shapeContainer = new Container();
		shapeContainer.setLayout(new GridLayout(1, 4, BORDER_THICKNESS, 0));

		// Add Shape Buttons To The Container
		shapeContainer.add(point);
		shapeContainer.add(line);
		shapeContainer.add(rectangle);
		shapeContainer.add(circle);

		// Add The Container With 1 Row Of 4 Shape Buttons To The Paint Toolbar
		paintToolbar.add(shapeContainer);
	}

	private void addQuickShapeButtonListeners()
	{
		point.addActionListener(e ->
		{
			Canvas.setShape(Shape.POINT);
			currentShape.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			point.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentShape = point;
		});

		line.addActionListener(e ->
		{
			Canvas.setShape(Shape.LINE);
			currentShape.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			line.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentShape = line;
		});

		rectangle.addActionListener(e ->
		{
			Canvas.setShape(Shape.RECTANGLE);
			currentShape.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			rectangle.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentShape = rectangle;
		});

		circle.addActionListener(e ->
		{
			Canvas.setShape(Shape.CIRCLE);
			currentShape.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			circle.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentShape = circle;
		});
	}

	private JButton createQuickSizeButton(int brushSize)
	{
		BufferedImage image = new BufferedImage(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, ColorSpace.TYPE_RGB);
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
		graphics.setStroke(new BasicStroke(brushSize));
		graphics.setColor(Color.BLACK);
		graphics.drawLine(10, 5, 40, 5);
		JButton brushSizeButton = new JButton(new ImageIcon(image));
		brushSizeButton.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		brushSizeButton.setSize(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);
		brushSizeButton.setToolTipText("Brush Size: " + brushSize);
		return brushSizeButton;
	}

	private void addQuickSizeButtons()
	{
		// Create The Generic Brush Size Buttons With Tool Tips
		brushSize1 = createQuickSizeButton(1);
		brushSize2 = createQuickSizeButton(2);
		brushSize3 = createQuickSizeButton(3);
		brushSize4 = createQuickSizeButton(4);

		// Create A Brush Size Button Container For Holding A 4x1 Row Of Generic Brush Size Buttons
		Container sizeContainer = new Container();
		sizeContainer.setLayout(new GridLayout(4, 1, 0, BORDER_THICKNESS));

		// Add Brush Size Buttons To The Container
		sizeContainer.add(brushSize1);
		sizeContainer.add(brushSize2);
		sizeContainer.add(brushSize3);
		sizeContainer.add(brushSize4);

		// Add The Container With 4 Rows Of Brush Size Buttons To The Paint Toolbar
		paintToolbar.add(sizeContainer);
	}

	private void addQuickSizeButtonListeners()
	{
		brushSize1.addActionListener(e ->
		{
			Canvas.setBrushSize(1);
			currentBrushSize.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			brushSize1.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentBrushSize = brushSize1;
		});

		brushSize2.addActionListener(e ->
		{
			Canvas.setBrushSize(2);
			currentBrushSize.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			brushSize2.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentBrushSize = brushSize2;
		});

		brushSize3.addActionListener(e ->
		{
			Canvas.setBrushSize(3);
			currentBrushSize.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			brushSize3.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentBrushSize = brushSize3;
		});

		brushSize4.addActionListener(e ->
		{
			Canvas.setBrushSize(4);
			currentBrushSize.setBorder(new LineBorder(Color.DARK_GRAY, BORDER_THICKNESS));
			brushSize4.setBorder(new LineBorder(Color.YELLOW, BORDER_THICKNESS));
			currentBrushSize = brushSize4;
		});
	}
}
