/*
 * File name: Grid.java
 * Package: grid
 * 
 * Description: This file class implements the grid where the individuals move.
 * 				This includes the creation of the matrix of Node objects, the setting
 * 				of the obstacles and the special zones including edges with bigger weight.
 *
 * 
 * Authors: José Correia
 * 			Pedro Soares
 * 			Tiago Santos
 * 
 * Date: 11th may 2018
 */

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
	
	/*
	 * Constructor which creates a new map and sets his obstacles and special zones.
	 */
	public Grid(int width, int height, int[][] obstacles, int[][] specialZones)
	{
		int[] position = new int[2];
		
		this.width = width;
		this.height = height;
		this.cmax = 1;  //default maximum weight is 1
		
		map = new Node[height][width];
		
		//iteration through the matrix
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				//position[] is interpreted as [x,y] but map = int[y][x];
				position[0] = j;
				position[1] = i;
				
				addNode(position, empty); //ads an available node to the position
				
				//if the current position is in the bottom line
				if(i == 0) {
					getNode(position).setBottomEdge(outOfBounds);
				}
				
				//if the current position is in the top line
				else if(i == height - 1) {
					getNode(position).setUpperEdge(outOfBounds);
				}
				
				//if the current position is in the left side
				if(j == 0) {
					getNode(position).setLeftEdge(outOfBounds);
				}
				
				//if the current position is in the right side
				else if(j == width - 1) {
					getNode(position).setRightEdge(outOfBounds);
				}
			}
		}
		
		if(obstacles != null)
			setObstacles(obstacles); //setting the obstacles according to the xml
		
		if(specialZones != null)
			setSpecialZones(specialZones); //setting the special znes according to the xml
	}
	
	
	/*
	 * Adds a node to the matrix
	 */
	public void addNode(int[] position, int type)
	{
		map[position[1]][position[0]] = new Node(position, type);
	}
	
	
	/*
	 * Getter for acquiring a node in a given position
	 */
	public Node getNode(int[] position)
	{
		return map[position[1]][position[0]];
	}
	
	
	/*
	 * Iterates through the obstacles matrix and sets the type of the 
	 * 			node in each position to 1
	 */
	private void setObstacles(int[][] obstacles)
	{
		for(int i = 0; i < obstacles.length; i++) {
			getNode(obstacles[i]).setType(obstacle);
		}
	}
	
	
	/*
	 * Iterates through the entire special zones matrix and
	 * sets the weight of the edges inside that zone to the int given
	 * in specialZones[i][4]
	 */
	private void setSpecialZones(int[][] specialZones)
	{
		int xstart, ystart, xend, yend, weight, x, y;
		int[] position = new int[2];
		this.cmax = 0;
		
		//iterates through each line of specialZones[][]
		for(int i = 0; i < specialZones.length; i++) {
			xstart = specialZones[i][0];
			ystart = specialZones[i][1];
			xend = specialZones[i][2];
			yend = specialZones[i][3];
			weight = specialZones[i][4];
			
			//case bigger weight than cmax is found, updates cmax
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
	 * method to return true if a position is not an obstacle, and
	 * return false otherwise
	 */
	public boolean isEmptyPosition(int[] position)
	{
		if(map[position[1]][position[0]].getType() == empty)
			return true;
		else
			return false;
	}
	
	
	/*
	 * method t calculate the next position of an individual, according
	 * to his current position
	 */
	public int[] getNextPosition(int[] actualPosition)
	{
		int[] nextPosition = new int[2];
		int x = actualPosition[0];
		int y = actualPosition[1];
		int found = 0; //has the next position been found?
		
		//creation of a new Random object
		Random rand = new Random();
		int randomInt;
		
		//while a next position hasn't been found:
		do {
			randomInt = rand.nextInt(4); //calculate a random int, range from 0 to 3
			found = 0;
			
			//if the edge according to the random integer does not lead
			//to the outside of the map
			if(getNode(actualPosition).getEdges()[randomInt] != outOfBounds)
			{
				//if the chosen integer corresponds the top edge
				if(randomInt == 0)
				{
					nextPosition[0] = x;
					nextPosition[1] = y + 1;
				}
				
				//if the chosen integer corresponds the bottom edge
				else if(randomInt == 1)
				{
					nextPosition[0] = x;
					nextPosition[1] = y - 1;
				}
				
				//if the chosen integer corresponds the left edge
				else if(randomInt == 2)
				{
					nextPosition[0] = x - 1;
					nextPosition[1] = y;
				}
				
				//if the chosen integer corresponds the right edge
				else if(randomInt == 3)
				{
					nextPosition[0] = x + 1;
					nextPosition[1] = y;
				}
				
				//if the next position calculated is empty, then we found it!
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
}
