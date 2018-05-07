package events;

import java.util.LinkedList;

import org.w3c.dom.Document;

import individual.*;
import utilities.Utils;

public class Death extends Event{
	
	private double u;
	private LinkedList<Individual> individuals;
	private Individual indivaux = individuals.get();
	private double confort = Individual.getConfort();
	
	private double deathValue = Utils.genericTimeCalculator(u, confort);
	
	
	

}
