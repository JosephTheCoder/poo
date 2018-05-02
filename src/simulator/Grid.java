package simulator;

import java.util.*;

public class Grid {
	private int width;
	private int height;
	private Node map[][];
	
	/*Array de listas*/
		/*Lista de Nodes*/
	
	
	public Grid(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		map = new Node[width][height];
	}
	
	public void addNode(int[] position, int type, int[] edges)
	{
		Node newNode = new Node(position, edges, type);
	
		map[position[0]][position[1]] = newNode;
		
	}
	
	public Node getNode(int[] position)
	{
		return map[position[0]][position[1]];
	}
}
