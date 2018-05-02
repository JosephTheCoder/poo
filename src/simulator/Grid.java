package simulator;

import java.util.*;

public class Grid {
	private int width;
	private int height;
	List<Node> lines[];
	
	/*Array de listas*/
		/*Lista de Nodes*/
	
	
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
	
	public Node getNode(int[] position)
	{
		Node node = lines[position[1]].get(position[0]);
		
		return node;
	}
	
	public static void main(String[] args)
	{
		public Grid mapa = new Grid(10,10);
		
		mapa.addNode({0,0}, 1, {-1,-1,1,2});
		
		
	}
}
