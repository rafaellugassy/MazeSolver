package MazeSolver;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Dijkstras
{

	// creating a private constructor so the class cannot be instantiated
	private Dijkstras()
	{
	}

	// this will return the shortest path from a node to the finish node
	public static int shortestPath(ArrayList<Node> startNode)
	{
		PriorityQueue<Vector2<Integer, Node>> queue = new PriorityQueue<Vector2<Integer, Node>>();
		for (Node curNode : startNode)
		{
			curNode.distance = 0;
			
			// for each node connected to this one
			for (Node connectedNode : curNode.connectedNode)
			{
				// gets the distance from one node to another
				int distance = curNode.distance + Math.abs(curNode.x - connectedNode.x)
						+ Math.abs(curNode.y - connectedNode.y);
				
				// if the current distance to the node is less than the distance that's set for it
				// then it will replace the distance to get to that node, and will put it in the
				// priority queue
				if (distance < connectedNode.distance)
				{
					connectedNode.distance = distance;
					connectedNode.parent = curNode;
					queue.add(new Vector2<Integer, Node>(connectedNode.distance, connectedNode));
				}
			}
		}
		
		// keep running until the queue is empty
		while (!queue.isEmpty())
		{
			
			Node curNode = queue.poll().secondVal;
			
			// if the current node is the end of the maze, then draw the path, and return the distance
			if (curNode.isLast())
			{
				ImageToGraph.colorPath(curNode);
				return curNode.distance;
			}
			
			// check each node connected to this node like above
			for (Node connectedNode : curNode.connectedNode)
			{
				int distance = curNode.distance + Math.abs(curNode.x - connectedNode.x)
						+ Math.abs(curNode.y - connectedNode.y);
				if (distance < connectedNode.distance)
				{
					connectedNode.distance = distance;
					connectedNode.parent = curNode;
					queue.add(new Vector2<Integer, Node>(connectedNode.distance, connectedNode));
				}
			}
		}
		// if there is no path return -1 to signal a fail
		return -1;
	}
}
