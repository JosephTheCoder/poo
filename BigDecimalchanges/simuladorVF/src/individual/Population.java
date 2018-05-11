/*
 * File name: Population.java
 * Package: individual
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

package individual;

import java.util.*;
import java.math.*;

import events.*;


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
		this.individualsalive.remove(this.individualsalive.indexOf(ind));
		
		
		
	}
	
	public boolean checkEpidemics (int nbIndividualsMax) {
		
		return this.nbIndividuals>nbIndividualsMax;	
	}
	
	
	public LinkedList<Individual> epidemic(PriorityQueue<Event>EventList ) {
		
		Random decider = new Random();
		
		double dec = decider.nextDouble();
		
		BigDecimal deciderD = new BigDecimal(dec);
		
		LinkedList<Individual> indList = new LinkedList<Individual>();
		
		PriorityQueue<Event> newEventlist = new PriorityQueue<Event>( new EventComparator());
		
		PriorityQueue<Event> copyEventlist =  new PriorityQueue<Event>( new EventComparator());
		
		
		//best five remain 
		
		for(Event eve : EventList) {
			
			copyEventlist.add(eve);
		}
		
		Collections.sort(indList, new SortByConfort()); 
		
		for(int i=0 ; i<5;i++) {
			
			indList.add(this.individualsalive.get(i));
			
			while(!copyEventlist.isEmpty()) {
				
				
				Event aux = copyEventlist.poll();

				
				
				if(aux.getIndividual().getIdentifier()==i) {
					
					newEventlist.add(aux);
					
				}
			
			
		}
			
			for(Event eve : EventList) {
				
				copyEventlist.add(eve);
			}
	}
		
		
		
		for(Event eve : EventList) {
			
			copyEventlist.add(eve);
		}
		
		
		
		for(int i=5 ; i< this.individualsalive.size() ; i++) {
			
			if(this.individualsalive.get(i).getConfort().compareTo(deciderD)==1) {
				
				indList.add(this.individualsalive.get(i));
				while(!copyEventlist.isEmpty()) {
					
					
					Event aux =copyEventlist.poll();
					
					
					if(aux.getIndividual().getIdentifier()==i) {
						
						newEventlist.add(aux);
						
					}
				
				}
				
				for(Event eve : EventList) {
					
					copyEventlist.add(eve);
				}
			}
			
			else {
				this.addDeathIndividual(this.individualsalive.get(i));
			}
			
		}
		
		for(Event eve : newEventlist) {
			EventList.add(eve);
		}
		
		
		
		return indList;
	}
	

}

