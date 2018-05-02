package simulator;

import java.util.*;




public class Individual {
	
	//fields
	
	int identifier;
	
	double confort;
	
	List<Node> path_list;
	
	//constructor
	
	public  Individual(int identifier , double initial_confort) {
		 
		this.identifier=identifier;
		this.confort=initial_confort;
		this.path_list= new ArrayList<Node>();
		
		
	}
	
	
	//methods 
	
	public int get_identifier() {
		
		return this.identifier;
		
	}
	
	
	public double get_confort() {
		
		return this.confort;
		
	}

	
	public double calc_confort(int confort_sensitivity) {
		
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
	
	public double calc_death(int mew) {
		
		double death_time = 0;
		double aux_confort = get_confort();
		
		death_time=(1-Math.log(1-aux_confort))*mew;
		
		
		
		return death_time;
		
	}
	
	
	
	
	public double calc_rep(int raw) {
		
		double rep_time=0;
		double aux_confort = get_confort();
		
		rep_time=(1-Math.log(1-aux_confort))*raw;
		
		
		
		return rep_time;
	}
	
	
	public double calc_move(int delta) {
		
		double move_time=0;
		double aux_confort = get_confort();
		
		move_time=(1-Math.log(1-aux_confort))*delta;
		
		return move_time;
	}
	
	public void add_path_point(int x , int y) {
		
		
		int aux_vec[] = new int[2];
		int aux_edges[] = new int[4];
		int aux_nodetype =1;
		
		//test reasons maybe*************************************************************
		aux_vec[0]=x;
		aux_vec[1]=y;
		aux_edges[0]=1;
		aux_edges[1]=1;
		aux_edges[2]=1;
		aux_edges[3]=1;
		// test reasons end***************************************************************
		
		Node aux = new Node(aux_vec,aux_edges,aux_nodetype);
		
		this.path_list.add(aux);
		
	}
	
	
	public Node get_current_point() {
		
		return this.path_list.get(this.path_list.size());
	}
	
	
	public Node[] get_path() {
		
		Node path[]= new Node[this.path_list.size()];
		
		for(int i=0; i< this.path_list.size();i++) {
			
			path[i]=this.path_list.get(i);
		}
		
		
		return path;
	}
	
	
	