package grid;

public class Node {
	private int[] position = new int[2]; /*[x, y]*/
	
	private int[] edges = new int[4]; /* weight		[up, down, left, right]*/
	private int type;
	
	
	public Node(int[] position, int node_type) 
	{
		this.type = node_type;
		
		System.arraycopy(position, 0, this.position, 0, position.length);
		edges[0] = 1;
		edges[1] = 1;
		edges[2] = 1;
		edges[3] = 1;
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
	
	public void setUpperEdge(int value)
	{
		if(edges[0] < value)
			edges[0] = value;
	}
	
	public void setBottomEdge(int value)
	{
		if(edges[1] < value)
			edges[1] = value;
	}

	public void setLeftEdge(int value)
	{
		if(edges[2] < value)
			edges[2] = value;
	}
	
	public void setRightEdge(int value)
	{
		if(edges[3] < value)
			edges[3] = value;
	}
	
	public void setType(int type)
	{
		this.type = type;
	}
	
	public String toString()
	{
		String x = Integer.toString(position[0]);
		String y = Integer.toString(position[1]);
		String up = Integer.toString(edges[0]);
		String down = Integer.toString(edges[1]);
		String left = Integer.toString(edges[2]);
		String right = Integer.toString(edges[3]);
		
		return "Node coordinates: [" + x + "," + y + "]  Type: " + type + "  Edges: " + up + " " + down + " " + left + " " + right;
	}
}