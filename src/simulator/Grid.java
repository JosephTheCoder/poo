package simulator;

import java.util.*;

/**
 * Class for the Map main representation
 * 
 * The map is a matrix of Node objects
 * 
 * @author menes
 *
 */

public class Grid {
	private int width;
	private int height;
	private Node map[][];
	
	
	public Grid(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		map = new Node[width][height];
	}
	
	public void addNode(int[] position, int type, int[] edges)
	{
		Node newNode = new Node(position, edges, type);
	
		map[position[1]][position[0]] = newNode;
		
	}
	
	public Node getNode(int[] position)
	{
		return map[position[1]][position[0]];
	}
	
	
	/*
	 * Method that returns the next adjacent available node to a given position
	 */
	public Node getNextAvailableNode(int[] position)
	{
		
	}
	
	public String toString(int line)
	{
		String str = new String();
		
		for(int i = 0; i < width; i++)
			str += Integer.toString(map[line][i].getType()) + " ";
		
		return str;
	}
	
	public void printGrid()
	{
		for(int i = 0; i < height; i++)
			System.out.println(toString(i));
	}
}