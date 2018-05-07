package individual;

import java.util.*;

// this is a composition
public class Population {
	
	private int nbIndividuals;
	private LinkedList<Individual> individuals;
	
	
	//constructor
	public Population(int nbIndividuals , LinkedList<Individual> individuals) {
		
		this.nbIndividuals=nbIndividuals;
		this.individuals=individuals;
		
	}
	
	//methods
	
	public int getNbIndividuals() {
		
		this.nbIndividuals = individuals.size();
		
		return nbIndividuals;
		
	}
	
	public LinkedList<Individual> getIndividualsInPopulation(){
		
		return individuals;
	}
	
	public void increaseNumberOfIndividuals() {
		
		this.nbIndividuals++;
	}
	
	public boolean checkEpidemics (int nbIndividualsMax) {
		
		return this.nbIndividuals>nbIndividualsMax;	
	}
	
	
	public LinkedList<Individual> epidemic() {
		
		LinkedList<Individual> indList = new LinkedList<Individual>();
		
		//best five remain 
		
		Collections.sort(this.individuals, new SortByConfort()); // ordena a lista por conforto
		for(int i=0 ; i<5;i++) {
			
			indList.add(this.individuals.get(i));
			
			
		}
		
		
		
		// rest of code for the others
		
		return this.individuals=indList;
	}
	
	
	
	
	// create do epidemics ( erase pop and make sure it erases all individuals since it is a composition

}
