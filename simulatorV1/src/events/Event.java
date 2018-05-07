package events;

import java.util.Collections;
import java.util.LinkedList;
import utilities.*;
import grid.*;
import individual.*;

public abstract class Event {

	public Individual indiv;
	
	public Population pop;
	
	public Node node;
		
	public int nbIndividuals;
	public LinkedList<Individual> individuals;
	
	public Event(int id, int confort) {
		indiv = new Individual(id, confort);
		pop = new Population(nbIndividuals, individuals);
		
	}


	public double calcEvent(int var) {
		
		double resultEvent = 0;
		double auxConfort = getConfort();
		
		resultEvent = (1-Math.log(1-aux_Confort))*var;
		
		return resultEvent;
	}
	
}
