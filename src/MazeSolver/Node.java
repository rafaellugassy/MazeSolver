package MazeSolver;

import java.util.ArrayList;

public class Node
{
	// distance stores the lowest value found so far to get to it from the start node 
	// (starts at a huge number so it can be replaced)
	int x, y, distance;
	
	// checked determines if the node has already been addressed
	boolean checked;
	
	// last shows whether or not this is the destination node
	boolean last;
	
	// used when redrawing a path from the destination to the start
	Node parent;
	
	// a list of connected nodes
	ArrayList<Node> connectedNode;
	
	Node (int x, int y, boolean last){
		distance = Integer.MAX_VALUE;
		this.x = x;
		this.y = y;
		this.checked = false;
		this.last = last;
		this.connectedNode = new ArrayList<Node>();
	}
	
	// adds a node to the connected node list
	void addConnected(Node node){
		if (node == null){
			System.out.println("Attempt to connect a null node");
			return;
		}
		this.connectedNode.add(node);
		node.connectedNode.add(this);
	}
	
	// returns true if the node is the final node
	boolean isLast(){
		return last;
	}
}
