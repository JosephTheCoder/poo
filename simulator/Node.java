package simulator;

public class Node {
	private int edges[4];
	private int type;
	
	
	public Node(int[] edges, int node_type) 
	{
		this.type = node_type;

		System.arraycopy(edges, 0, this.edges, 0, edges.length);
	}
	
	public int[] getEdges()
	{
		return this.edges;
	}
	
	public int getType()
	{
		return this.type;
	}
	
}