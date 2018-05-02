package simulator;

import java.util.*;

import jdk.internal.jimage.ImageReader.Node;

public class Grid {
	int width;
	int height;
	List<Node> lines[];
	
	/*Lista de Nodes*/
	/*Lista de listas de Nodes -> linhas*/
	
	
	public Grid(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		lines = new List<Node>[height];;
	}
	
	public void addNode(int[] position, int type, int[] edges)
	{
		Node newNode = new Node(position, edges, type);
	
		lines[position[1]].add(newNode);
		
	}
	
	

}
