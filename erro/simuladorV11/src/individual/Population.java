package individual;

import java.util.*;

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
		
		return individualsalive;
	}
	
	public List<Individual> getdeathindividuals(){
		
		return deathindividuals;
	}
	
	public void addIndividualAlive (Individual ind) {
		increaseNumberOfIndividuals();
		this.individualsalive.add(ind);
	}
	
	public void addDeathIndividual (Individual ind) {
		
		decreaseNumberOfIndividuals();
		this.individualsalive.remove(ind);
		this.deathindividuals.add(ind);
		
		
	}
	
	public boolean checkEpidemics (int nbIndividualsMax) {
		
		return this.nbIndividuals>nbIndividualsMax;	
	}
	
	
	public LinkedList<Individual> epidemic() {
		
		LinkedList<Individual> indList = new LinkedList<Individual>();
		
		//best five remain 
		
		Collections.sort(this.individualsalive, new SortByConfort()); // ordena a lista por conforto
		for(int i=0 ; i<5;i++) {
			
			indList.add(this.individualsalive.get(i));
			
			
		}
		
		
		
		// rest of code for the others
		
		return this.individualsalive=indList;
	}
	
	
	
	
	// create do epidemics ( erase pop and make sure it erases all individuals since it is a composition

}
