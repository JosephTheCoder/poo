/*
 * File name: Node.java
 * Package: grid
 * 
 * Description: This file class implements the node object.
 * 				Each node has information on his position, edges and type (if its an obstacle or an empty 
 * 				available node).
 * 
 * Authors: Jose Correia
 * 			Pedro Soares
 * 			Tiago Santos
 * 
 * Date: 11th may 2018
 */

package grid;


public class Node {
	//position of the node  -> [x, y]
	private int[] position = new int[2];
	
	/* edges vector to store the weight of each edge   [up, down, left, right]*/
	private int[] edges = new int[4];
	
	//type of the node
	private int type;
	
	/*
	 * Constructor that creates a new node, and sets he's position and type
	 * the edges are set by default as. [1, 1, 1, 1] (all with weight 1)
	 */
	public Node(int[] position, int node_type) 
	{
		this.type = node_type;
		
		System.arraycopy(position, 0, this.position, 0, position.length);
		
		for(int i = 0; i < 4; i++)
			edges[i] = 1;
	}
	
	/*
	 * Getter for the node's position
	 */
	public int[] getPosition()
	{
		return position;
	}
	
	/*
	 * Getter for the node's edges vector
	 */
	public int[] getEdges()
	{
		return this.edges;
	}
	
	/*
	 * Getter for the node's type
	 */
	public int getType()
	{
		return this.type;
	}
	
	//The next method represent setters for the edges of the node
	public void setUpperEdge(int value)
	{
		//The bigger weight always has priority!!
		if(edges[0] < value || value == -1)
			edges[0] = value;
	}
	
	public void setBottomEdge(int value)
	{
		if(edges[1] < value || value == -1)
			edges[1] = value;
	}

	public void setLeftEdge(int value)
	{
		if(edges[2] < value || value == -1)
			edges[2] = value;
	}
	
	public void setRightEdge(int value)
	{
		if(edges[3] < value || value == -1)
			edges[3] = value;
	}
	
	/*
	 * Setter for the node's type
	 */
	public void setType(int type)
	{
		this.type = type;
	}
	
	/*
	 * Setter for the node's position
	 */
	public void setPosition(int[] position) {
		this.position[0] = position[0];
		this.position[1] = position[1];
	}
}
