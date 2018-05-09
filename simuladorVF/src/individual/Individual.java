package individual;

import grid.*;
import java.util.*;




public class Individual  {
	
	//fields
	
	private int identifier;
	
	private double confort;
	
	List<int[]> path_list;
	
	//constructor
	
	public  Individual(int identifier , double initial_confort) {
		 
		this.identifier=identifier;
		this.confort=initial_confort;
		this.path_list= new ArrayList<int[]>();
		
		
	}
	
	public Individual(int identifier) {
		
		this.identifier=identifier;
		this.path_list=new ArrayList<int[]>();
		this.confort=0;
		
	}
	
	
	//methods 
	
	public int getIdentifier() {
		
		return this.identifier;
		
	}
	
	
	public double getConfort() {
		
		return this.confort;
		
	}

//*utils class	
	public double calcConfort(int confort_sensitivity) {
		
		double aux_confort=0;
		
		//** for test reasons**//
		int cmax=1;
		int cost_path=5;
		int lenght_path=5;
		int dist=5;
		int n=10;
		int m=10;
		//** for test reasons finish
		
		aux_confort=(1-((cost_path-lenght_path+2)/((cmax-1)*lenght_path+3))^confort_sensitivity)*((1-(dist/(n+m+1)))^confort_sensitivity);
				
		this.confort=aux_confort;
		
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
		
		this.path_list.add(point);
	}
	
	
	public int[] getCurrentPoint() {
		int test;
		test=this.path_list.size();
		test=test-1;
		return this.path_list.get(test);
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
		return "Individual [identifier=" + identifier + ", confort=" + confort + "]";
	}

}

