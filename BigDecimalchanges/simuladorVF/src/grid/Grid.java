package grid;


import java.util.Random;

public class Grid {
	//variables for the dimensions and definition of the node matrix
	private int width;
	private int height;
	private int cmax;
	private Node map[][];
	
	//static variables for each node type
	private static final int empty = 0;
	private static final int obstacle = 1;
	
	//static variable for unavailable edges
	private static final int outOfBounds = -1;
	
	public Grid(int width, int height, int[][] obstacles, int[][] specialZones)
	{
		//setting the variables
		int[] position = new int[2];
		
		this.width = width;
		this.height = height;
		this.cmax = 1;
		
		map = new Node[height][width];
		
		//Creation of the nodes and setting the edges
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				position[0] = j;
				position[1] = i;
				
				addNode(position, empty);
				
				if(i == 0) {
					getNode(position).setBottomEdge(outOfBounds);
				}
				
				else if(i == height - 1) {
					getNode(position).setUpperEdge(outOfBounds);
				}
				
				if(j == 0) {
					getNode(position).setLeftEdge(outOfBounds);
				}
				
				else if(j == width - 1) {
					getNode(position).setRightEdge(outOfBounds);
				}
			}
		}
		
		//setting the obstacles
		setObstacles(obstacles);
		
		
		setSpecialZones(specialZones);
	}
	
	
	/*
	 * 
	 */
	public void addNode(int[] position, int type)
	{
		map[position[1]][position[0]] = new Node(position, type);
	}
	
	
	/*
	 * 
	 */
	public Node getNode(int[] position)
	{
		return map[position[1]][position[0]];
	}
	
	
	/*
	 * int[][] -> [xpos,ypos]
	 */
	private void setObstacles(int[][] obstacles)
	{
		for(int i = 0; i < obstacles.length; i++) {
			getNode(obstacles[i]).setType(obstacle);
		}
	}
	
	
	/*
	 * 
	 */
	private void setSpecialZones(int[][] specialZones)
	{
		int xstart, ystart, xend, yend, weight, x, y;
		int[] position = new int[2];
		this.cmax = 0;
		
		for(int i = 0; i < specialZones.length; i++) {
			xstart = specialZones[i][0];
			ystart = specialZones[i][1];
			xend = specialZones[i][2];
			yend = specialZones[i][3];
			weight = specialZones[i][4];
			
			if(weight > this.cmax)
				this.cmax = weight;
			
			for(y = ystart; y <= yend; y++)
			{
				for(x = xstart; x <= xend; x++)
				{
					position[0] = x;
					position[1] = y;
					
					
					if(x < xend)
						getNode(position).setRightEdge(weight);
					
					if(x > xstart)
						getNode(position).setLeftEdge(weight);
					
					if(y > ystart)
						getNode(position).setBottomEdge(weight);
					
					if(y < yend)
						getNode(position).setUpperEdge(weight);
				}	
			}
		}
	}
	
	
	/*
	 * 
	 */
	
	public boolean isEmptyPosition(int[] position)
	{
		if(map[position[1]][position[0]].getType() == empty)
			return true;
		else
			return false;
	}
	
	
	/*
	 * 
	 */
	public int[] getNextPosition(int[] actualPosition)
	{
		int[] nextPosition = new int[2];
		int x = actualPosition[0];
		int y = actualPosition[1];
		int found = 0;
		
		Random rand = new Random();
		int randomInt;
		
		/******************************** TODO *******/
		
		do {
			randomInt = rand.nextInt(3);
			found = 0;
			
			if(getNode(actualPosition).getEdges()[randomInt] != outOfBounds)
			{
				if(randomInt == 0)
				{
					nextPosition[0] = x;
					nextPosition[1] = y + 1;
				}
				
				else if(randomInt == 1)
				{
					nextPosition[0] = x;
					nextPosition[1] = y - 1;
				}
				
				else if(randomInt == 2)
				{
					nextPosition[0] = x - 1;
					nextPosition[1] = y;
				}
				
				else if(randomInt == 3)
				{
					nextPosition[0] = x + 1;
					nextPosition[1] = y;
				}
				
				
				if(getNode(nextPosition).getType() == empty)
					found = 1;
			}
			
		} while(found == 0);
		
		return nextPosition;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getCmax()
	{
		return this.cmax;
	}
	
	/*
	 * Returns a string with the given line
	 */
	public String toString(int line)
	{
		String str = new String();
		
		for(int i = 0; i < width; i++)
			str += Integer.toString(map[line][i].getType()) + " ";
		
		return str;
	}
	
	/*
	 * Prints the map
	 */
	public void printGrid()
	{
		System.out.println("\nMap:");
		for(int i = height - 1; i >= 0; i--)
			System.out.println(toString(i));
	}
}
