package individual;

import java.util.Collections;
import java.util.LinkedList;
import utilities.*;
import grid.*;

public abstract class eventt {

	public Individual indiv;
	
	public Population pop;
	
	public Node node;
		
	public int nbIndividuals;
	public LinkedList<Individual> individuals;
	
	public eventt(int id, int confort) {
		indiv = new Individual(id, confort);
		pop = new Population(nbIndividuals, individuals);
		
	}
}
