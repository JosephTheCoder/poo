package individual;

import java.math.*;

import grid.*;
import java.util.*;




public class Individual  {
	
	//fields
	
	private int identifier;
	
	private int[] position;
	
	private BigDecimal  confort;
	
	List<int[]> path_list;
	
	//constructor

	
	public Individual(int identifier , int[] initial_position) {
		
		this.identifier=identifier;
		this.path_list=new ArrayList<int[]>();
		this.confort=new BigDecimal(0);
		this.position = new int[2];
		this.position = initial_position;
		
	}
	
	
	//methods 
	
	public int getIdentifier() {
		
		return this.identifier;
		
	}
	
	
	public BigDecimal getConfort() {
		
		return this.confort;
		
	}

//*utils class	
	public BigDecimal calcConfort(Grid worldmap, int confort_sensitivity,  int[] dest) {
		
		
		//** for test reasons**//
		double cmax = worldmap.getCmax();
		double cost_path = calcCostPath(worldmap);
		double lenght_path = getPathSize();
		double dist = calcDist(this.getCurrentPoint()[0],this.getCurrentPoint()[1], dest[0], dest[1]);
		double n = worldmap.getWidth();
		double m = worldmap.getHeight();
		BigDecimal calcA=new BigDecimal((Math.pow(1.0-((cost_path-lenght_path+2)/(((cmax-1)*lenght_path)+3)), confort_sensitivity)));
		BigDecimal calcB=new BigDecimal(Math.pow((1.0-((dist)/(n+m+1))), confort_sensitivity));
		

	
		
		
		
		BigDecimal auxconfort = calcA.multiply(calcB);
		
		
		this.confort=auxconfort;
		
		return this.confort;																																						
	}
	
	public int calcCostPath(Grid map) {
		int cost_path = 0;
		int[] actualPosition = new int[2];
		int[] nextPosition = new int[2];
		
		for(int i = 0; i < this.path_list.size() - 1; i++) {
			actualPosition = this.path_list.get(i);
			nextPosition = this.path_list.get(i+1);
			
			if(actualPosition[0] < nextPosition[0]) { //went right
				cost_path += map.getNode(actualPosition).getEdges()[3];
			}
			
			else if(actualPosition[0] > nextPosition[0]) { //went left
				cost_path += map.getNode(actualPosition).getEdges()[2];
			}
			
			else if(actualPosition[1] < nextPosition[1]) { //went up
				cost_path += map.getNode(actualPosition).getEdges()[0];
			}
			
			else if(actualPosition[1] > nextPosition[1]) { //went down
				cost_path += map.getNode(actualPosition).getEdges()[1];
			}
		}
		
		return cost_path;
	}
	
	
	public int calcDist(int xsource , int ysource , int destx , int desty) {
		int dist;
		
		dist= Math.abs(xsource-destx) + Math.abs(ysource-desty);
		
		return dist;
	}
	
	
	//falta os custos
	public void addPathPoint(int[] point) {
		
		this.position = point;
		this.path_list.add(point);
	}
	
	// has problems
	public int[] getCurrentPoint() {
		return this.position;
	}
	
	
	public List<int[]> getPath() {
		
		List<int[]>path= new ArrayList<int[]>();
		
		for(int i=0; i< this.path_list.size();i++) {
			
			path.add(this.path_list.get(i));
		}
		
		
		return path;
	}
	
	public int getPathSize() {
		
		return this.path_list.size();
	}
	
	
	
	
	
//// o map é o mapa atual///
	
	public int[] getNextPathPoint (Grid map,int [] position) {
		
		return map.getNextPosition(position);
		
	}
	
	public void printPathList() {
		
		List<int[]> auxiterator = path_list;
		
		for(int[]aux : auxiterator) {
			System.out.println(" x= "+aux[0]+" y= "+aux[1]);
			
		}
		
	}

	@Override
	public String toString() {
		return "Individual [identifier=" + identifier + ", confort=" + confort.toString() + "]";
	}

}

