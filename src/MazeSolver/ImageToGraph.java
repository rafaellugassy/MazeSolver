package MazeSolver;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageToGraph
{

	// private constructor so the class can't be instantiated
	private ImageToGraph()
	{
	}

	// creates a JFrame with a scaled version of the solved maze
	public static void display()
	{
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(mazeImage.getScaledInstance(800, 800, 0))));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static BufferedImage mazeImage;

	// will colour the bufferedImage's path, then will display it to the user
	public static void colorPath(Node node)
	{
		Node curNode = node;
		
		// will determine whether to skip drawing the first node, depending on if it's the finish line
		boolean skip = mazeImage.getRGB(curNode.x, curNode.y) == Color.RED.getRGB();
		
		while (curNode.parent != null)
		{
			// distance represents the amount of units away from the current 
			int distance = curNode.distance - curNode.parent.distance;
			boolean sameX;
			int direction = 1;
			
			// determines which direction to draw the path from one node to another
			if (curNode.x == curNode.parent.x)
			{
				sameX = true;
				if (curNode.y < curNode.parent.y)
					direction = -1;
			}
			else
			{
				sameX = false;
				if (curNode.x < curNode.parent.x)
					direction = -1;
			}
			
			// sets the pixels' color to blue for each pixel along the path
			for (int i = skip ? 1 : 0; i < distance; i++)
			{
				if (!sameX)
					setMazeColor(curNode.x - i * direction, curNode.y, Color.BLUE);
				else
					setMazeColor(curNode.x, curNode.y - i * direction, Color.BLUE);
			}
			skip = false;
			
			// now do the same for the parent node
			curNode = curNode.parent;
		}
		
		// display the completed maze
		display();
	}

	public static void setMazeColor(int x, int y, Color color)
	{
		mazeImage.setRGB(x, y, color.getRGB());
	}

	// this method takes an image, and returns an arraylist of nodes which will define the graph
	public static ArrayList<Node> createGraph()
	{
		// ask user for image (it will save into mazeImage)
		selectImage();

		ArrayList<Node> startNode = new ArrayList<Node>();
		int width = mazeImage.getWidth(), height = mazeImage.getHeight();

		int[] amount = new int[width];
		for (int i = 0; i < width; i++)
			amount[i] = 0;

		Node[][] node = new Node[width][height];
		Node lastNode = new Node(-1, -1, false);

		for (int i = 0; i < height; i++)
		{
			for (int g = 0; g < width; g++)
			{
				// System.out.println(mazeImage.getRGB(g, i) -
				// Color.GREEN.getRGB());
				if (mazeImage.getRGB(g, i) != Color.BLACK.getRGB())
				{
					boolean isFirst = mazeImage.getRGB(g, i) == Color.GREEN.getRGB(),
							isLast = mazeImage.getRGB(g, i) == Color.RED.getRGB();

					// System.out.println(isFirst);

					if (i > 0 && mazeImage.getRGB(g, i - 1) != Color.BLACK.getRGB())
					{
						if ((g > 0 && mazeImage.getRGB(g - 1, i) != Color.BLACK.getRGB())
								|| (g + 1 < width && mazeImage.getRGB(g + 1, i) != Color.BLACK.getRGB())
								|| (i + 1 >= height || mazeImage.getRGB(g, i + 1) == Color.BLACK.getRGB()))
						{
							Node curNode = new Node(g, i, isLast);
							if (amount[g] != 0)
								curNode.addConnected(node[g][amount[g] - 1]);
							node[g][amount[g]++] = curNode;
							if (lastNode.y == i && mazeImage.getRGB(g - 1, i) != Color.BLACK.getRGB())
								curNode.addConnected(lastNode);
							lastNode = curNode;
							if (isFirst)
								startNode.add(curNode);
						}
					}
					else if (i + 1 < height && mazeImage.getRGB(g, i + 1) != Color.BLACK.getRGB())
					{
						Node curNode = new Node(g, i, isLast);
						/*
						 * if (amount[g] != 0)
						 * curNode.addConnected(node[g][amount[g] - 1]);
						 */
						node[g][amount[g]++] = curNode;
						if (lastNode.y == i && mazeImage.getRGB(g - 1, i) != Color.BLACK.getRGB())
							curNode.addConnected(lastNode);
						lastNode = curNode;
						if (isFirst)
							startNode.add(curNode);

					}
					else if ((g + 1 >= width || mazeImage.getRGB(g + 1, i) == Color.BLACK.getRGB())
							|| (g - 1 < 0 || mazeImage.getRGB(g - 1, i) == Color.BLACK.getRGB()))
					{
						Node curNode = new Node(g, i, isLast);
						node[g][amount[g]++] = curNode;
						if (lastNode.y == i && mazeImage.getRGB(g - 1, i) != Color.BLACK.getRGB())
							curNode.addConnected(lastNode);
						lastNode = curNode;
						if (isFirst)
							startNode.add(curNode);
					}
				}
			}
		}
		return startNode;
	}

	public static void selectImage()
	{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
		}

		try
		{
			mazeImage = ImageIO.read(chooser.getSelectedFile());
		} catch (IOException e)
		{
			System.out.println("Failed to load Image.");
			selectImage();
			return;
		}

	}
}
