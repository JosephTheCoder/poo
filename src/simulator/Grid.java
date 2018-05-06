package simulator;

/**
 * Class for the Map main representation
 * 
 * The map is a matrix of Node objects
 * 
 * @author menes
 *
 */

import java.util.Random;

public class Grid {
	private int width;
	private int height;
	private Node map[][];
	
	private static final int empty = 0;
	private static final int obstacle = 1;
	private static final int outOfBounds = -1;
	
	public Grid(int width, int height, int[][] obstacles, int[][] specialZones)
	{
		int[] position = new int[2];
		
		this.width = width;
		this.height = height;
		
		map = new Node[height][width];
		
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
		
		setObstacles(obstacles);
		setSpecialZones(specialZones);
	}
	
	
	/*
	 * 
	 */
	public void addNode(int[] position, int type)
	{
		Node newNode = new Node(position, type);
	
		map[position[1]][position[0]] = newNode;
		
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
			System.out.println(obstacles[i][0] + " " + obstacles[i][1]);
			System.out.println(getNode(obstacles[i]).toString());
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
		
		for(int i = 0; i < specialZones.length; i++) {
			xstart = specialZones[i][0];
			ystart = specialZones[i][1];
			xend = specialZones[i][2];
			yend = specialZones[i][3];
			weight = specialZones[i][4];
			
			/******Meter as edges do esxterior a -1****/
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
						getNode(position).setUpperEdge(weight);
					
					if(y < yend)
						getNode(position).setBottomEdge(weight);
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
				found = 1;
			}
			
		} while(getNode(nextPosition).getType() == obstacle || found == 0);
		
		return nextPosition;
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