package individual;

import grid.*;
import java.util.*;




public class Individual  {
	
	//fields
	
	private int identifier;
	
	private double confort;
	
	List<Node> path_list;
	
	//constructor
	
	public  Individual(int identifier , double initial_confort) {
		 
		this.identifier=identifier;
		this.confort=initial_confort;
		this.path_list= new ArrayList<Node>();
		
		
	}
	
	public Individual(int identifier) {
		
		this.identifier=identifier;
		this.path_list=new ArrayList<Node>();
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
	
	
	//falta os custos
	public void addPathPoint(int[] point) {
		
		
		int aux_nodetype =1;
		
		
		Node aux = new Node(point,aux_nodetype);
		
		this.path_list.add(aux);
		
	}
	
	
	public Node getCurrentPoint() {
		int test;
		test=this.path_list.size();
		test=test-1;
		return this.path_list.get(test);
	}
	
	
	public Node[] getPath() {
		
		Node path[]= new Node[this.path_list.size()];
		
		for(int i=0; i< this.path_list.size();i++) {
			
			path[i]=this.path_list.get(i);
		}
		
		
		return path;
	}
	
	public int getPathSize() {
		
		return this.path_list.size();
	}
	
	
	
	
	
//// o map é o mapa atual///
	
	public int[] getNextPathPoint (Grid map,int [] position) {
		
		System.out.println("entrou getNextPathPoint");
		System.out.println(position[0]);
		System.out.println(+ position[1]);
		return map.getNextPosition(position);
		
	}


	@Override
	public String toString() {
		return "Individual [identifier=" + identifier + ", confort=" + confort + ", path_list=" + path_list + "]";
	}
	
	
	
	
// DO pop.	
	
	
}
