package MazeSolver;

import java.util.ArrayList;

/*
 * This program was made to take in a maze, and display a solved version of the maze.  The input of the 
 * maze will be formatted as such: Black pixels will be walls, White pixels will be paths, must contain
 * at least one Green pixel, which will determine where the maze starts, and must contain one Red pixel,
 * which will determine the end of the maze.  The input will be in image format (jpg, png, gif...) and
 * the output will be displayed in a window if the maze is solvable.
 * 
 * Rafael Lugassy
 * Last Date Modified: 2018-09-27
 */

public class MazeSolver
{

	static ArrayList<Node> start;

	public static void main(String[] args)
	{
		// create the graph, and get the start nodes
		start = ImageToGraph.createGraph();
		
		// do algorithm to get the shortest path and display the amount of moves, and the solved maze
		System.out.println("The Shortest Path is: " + Dijkstras.shortestPath(start) + " Moves.");
	}

}
