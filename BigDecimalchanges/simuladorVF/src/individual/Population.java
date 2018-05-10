package individual;

import java.util.*;

import java.math.*;


// this is a composition
public class Population {
	
	private int nbIndividuals;
	public LinkedList<Individual> individualsalive;
	private List<Individual> deathindividuals;
	
	
	//constructor
	public Population(int nbIndividuals , LinkedList<Individual> individuals) {
		
		this.nbIndividuals=nbIndividuals;
		this.individualsalive=individuals;
		
	}
	
	public Population () {
		
		this.nbIndividuals=0;
		this.individualsalive = new LinkedList<Individual>();
		this.deathindividuals = new ArrayList<Individual>();
	}
	
	//methods
	
	public int getNbIndividuals() {
		
		this.nbIndividuals = individualsalive.size();
		
		return nbIndividuals;
		
	}
	
	public void increaseNumberOfIndividuals() {
		
		this.nbIndividuals++;
	}
	
	public void decreaseNumberOfIndividuals() {
		this.nbIndividuals--;
	}
	
	public LinkedList<Individual> getIndividualsInPopulation(){
		
		return this.individualsalive;
	}
	
	public List<Individual> getdeathindividuals(){
		
		return this.deathindividuals;
	}
	
	public void addIndividualAlive (Individual ind) {
		increaseNumberOfIndividuals();
		this.individualsalive.add(ind);
	}
	
	public void addDeathIndividual (Individual ind) {
		
		decreaseNumberOfIndividuals();
		this.deathindividuals.add(ind);
		this.individualsalive.remove(ind);
		
		
		
	}
	
	public boolean checkEpidemics (int nbIndividualsMax) {
		
		return this.nbIndividuals>nbIndividualsMax;	
	}
	
	
	public LinkedList<Individual> epidemic() {
		
		Random decider = new Random();
		
		double dec = decider.nextDouble();
		
		BigDecimal deciderD = new BigDecimal(dec);
		
		LinkedList<Individual> indList = new LinkedList<Individual>();
		
		//best five remain 
		
		Collections.sort(this.individualsalive, new SortByConfort()); 
		
		for(int i=0 ; i<5;i++) {
			
			indList.add(this.individualsalive.get(i));
			
			
		}
		
		for(int i=5 ; i< this.individualsalive.size() ; i++) {
			
			if(this.individualsalive.get(i).getConfort().compareTo(deciderD)==1) {
			
				indList.add(this.individualsalive.get(i));
				
			}
			
			else {
				this.deathindividuals.add(this.individualsalive.get(i));
			}
			
		}
		
		
		
		// rest of code for the others
		
		return this.individualsalive=indList;
	}
	
	
	
	
	// create do epidemics ( erase pop and make sure it erases all individuals since it is a composition

}

