/*
 * File name: Individual.java
 * Package: individual
 * 
 * Description: This file class implements the individual.
 * 				Each individual has information on his position, the id, the comfort value
 * 				and the list with the path he has taken.
 *
 * Authors: José Correia
 * 			Pedro Soares
 * 			Tiago Santos
 * 
 * Date: 11th may 2018
 */

package individual;

import java.math.*;

import grid.*;
import java.util.*;


public class Individual  {
	
	//id of the individual
	private int identifier;
	//position of the individual
	private int[] position;
	//comfort
	private BigDecimal comfort;
	//List of integer vectors for the path of the individual
	List<int[]> path_list;
	
	/*
	 * Constructor that creates a new individual and sets the variables according
	 * to the parameters
	 */
	public Individual(int identifier , int[] initial_position) {
		
		this.identifier=identifier;
		this.path_list=new ArrayList<int[]>();
		this.comfort=new BigDecimal(0);
		this.position = new int[2];
		this.position = initial_position;
		
	}
	
	/*
	 * Getter for the individual's id
	 */
	public int getIdentifier() {
		
		return this.identifier;
		
	}
	
	/*
	 * Getter for the individual's comfort value
	 */
	public BigDecimal getConfort() {
		
		return this.comfort;
		
	}

	/*
	 * Method to calculate the comfort value according to the model in the
	 * project's paper	
	 */
	public BigDecimal calcConfort(Grid worldmap, int confort_sensitivity,  int[] dest) {
		

		double cmax = worldmap.getCmax();
		double cost_path = calcCostPath(worldmap);
		double lenght_path = getPathSize();
		double dist = calcDist(this.getCurrentPoint()[0],this.getCurrentPoint()[1], dest[0], dest[1]);
		double n = worldmap.getWidth();
		double m = worldmap.getHeight();
		BigDecimal calcA=new BigDecimal((Math.pow(1.0-((cost_path-lenght_path+2)/(((cmax-1)*lenght_path)+3)), confort_sensitivity)));
		BigDecimal calcB=new BigDecimal(Math.pow((1.0-((dist)/(n+m+1))), confort_sensitivity));
		
		BigDecimal auxconfort = calcA.multiply(calcB);
		
		this.comfort=auxconfort;
		
		return this.comfort;																																						
	}
	
	/*
	 * Calculation of the total cost of the path taken by the individual
	 */
	public int calcCostPath(Grid map) {
		int cost_path = 0;
		int[] actualPosition = new int[2];
		int[] nextPosition = new int[2];
		
		//iterates through the path list
		for(int i = 0; i < this.path_list.size() - 1; i++) {
			actualPosition = this.path_list.get(i);
			nextPosition = this.path_list.get(i+1);

			//went right
			if(actualPosition[0] < nextPosition[0]) { 
				cost_path += map.getNode(actualPosition).getEdges()[3];
			}
			
			//went left
			else if(actualPosition[0] > nextPosition[0]) { 
				cost_path += map.getNode(actualPosition).getEdges()[2];
			}
			
			//went up
			else if(actualPosition[1] < nextPosition[1]) { 
				cost_path += map.getNode(actualPosition).getEdges()[0];
			}
			
			//went down
			else if(actualPosition[1] > nextPosition[1]) { 
				cost_path += map.getNode(actualPosition).getEdges()[1];
			}
		}
		
		return cost_path;
	}
	
	
	/*
	 * Calculates the distance between two positions
	 */
	public int calcDist(int xsource , int ysource , int destx , int desty) {
		int dist;
		
		dist= Math.abs(xsource-destx) + Math.abs(ysource-desty);
		
		return dist;
	}
	
	
	/*
	 * Adds a position to the path list
	 */
	public void addPathPoint(int[] point) {
		
		this.position = point;
		this.path_list.add(point);
	}
	
	/*
	 * Getter for the current position
	 */
	public int[] getCurrentPoint() {
		return this.position;
	}
	
	/*
	 * Getter for the entire path list
	 */
	public List<int[]> getPath() {
		
		List<int[]>path= new ArrayList<int[]>();
		
		for(int i=0; i< this.path_list.size();i++) {
			
			path.add(this.path_list.get(i));
		}
		
		
		return path;
	}
	
	
	/*
	 * Getter for the path list size
	 */
	public int getPathSize() {
		
		return this.path_list.size();
	}
	
	
	/*
	 * Getter of the next position where the individual moves to
	 */
	public int[] getNextPathPoint (Grid map,int [] position) {
		
		return map.getNextPosition(position);
		
	}
	
	/*
	 * Prints the path list
	 */
	public void printPathList() {
		
		List<int[]> auxiterator = path_list;
		
		for(int[]aux : auxiterator) {
			System.out.println(" x= "+aux[0]+" y= "+aux[1]);
		}
	}
	
	

	@Override
	public String toString() {
		return "Individual [identifier=" + identifier + ", confort=" + comfort.toString() + "]";
	}

}

