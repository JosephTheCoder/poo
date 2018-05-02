package simulator;

public class Node {
	private int position[] = new int[2]; /*[x, y]*/
	
	private int edges[] = new int[4]; /*-1 for obstacles, all other int for weight*/
										/*[up, down, left, right]*/
	private int type;
	
	
	public Node(int[] position, int[] edges, int node_type) 
	{
		this.type = node_type;
		
		System.arraycopy(position, 0, this.position, 0, position.length);
		System.arraycopy(edges, 0, this.edges, 0, edges.length);
	}
	
	public int[] getPosition()
	{
		return position;
	}
	
	public int[] getEdges()
	{
		return this.edges;
	}
	
	public int getType()
	{
		return this.type;
	}
	
	public String toString()
	{
		String x = Integer.toString(position[1]);
		String y = Integer.toString(position[0]);
		String up = Integer.toString(edges[0]);
		String down = Integer.toString(edges[1]);
		String left = Integer.toString(edges[2]);
		String right = Integer.toString(edges[2]);
		
		return "Node coordinates: [" + x + "," + y + "]  Type: " + type + "  Edges: " + up + " " + down + " " + left + " " + right;
	}
	
}