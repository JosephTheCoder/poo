/*
 * File Name : Simulator.java
 * Package : simulator
 * 
 * Description: Class which takes care of creating and executing the simulation.
 * 
 * Authors: José Correia
 * 			Pedro Soares
 * 			Tiago Santos
 * 
 * Date: 11 May 2018 
 */

package simulator;

import grid.*;
import java.util.Collections;
import java.util.List;

import java.math.*;


import java.util.*;

import individual.*;
import events.*;
import utilities.*;

public class Simulator {
	
	//Empty Constructor 
	public Simulator() {
		
	}
	
	/*
	 * Function name : SimulatorSimulate
	 * Entries : filename
	 * Return : 
	 * Description : This function is the "brain" of the all program it basically processes everything in order to effectuate a
	 * 				simulation given a file.
	 * 
	 */
	public void SimulatorSimulate(String filename) {
		
		// Variables extract from the file in question
		XMLFileParser parser = new XMLFileParser(filename);
		int confortsense =parser.getComfortsens();
		int deathparam = parser.getDeathParam();
		int reproductionparam = parser.getReproductionParam();
		int moveparam = parser.getMoveParam();
		BigDecimal finalinst = new BigDecimal(parser.getFinalinst());
		int gridheight = parser.getGridHeight();
		int gridwidth = parser.getGridWidth();
		int[] initialpoint = parser.getInitialpoint();
		int[] finalpoint = parser.getFinalpoint();
		int  initialpop =parser.getInitpop();
		int maxpop = parser.getMaxpop();
		
		// Auxiliar Variable to simplify somo function entries and loops
		int[] genericparams = {moveparam,deathparam,reproductionparam};
		BigDecimal currenttime = new BigDecimal(0);
		BigDecimal printtime = new BigDecimal(0);
		BigDecimal twenty = new BigDecimal(20);
		BigDecimal printtiming = finalinst.divide(twenty);
		
		// Structure wich will hold all death and live individuals troughout the simulation.
		Population world = new Population();
		// Event queue wich will hold all the events troughout the simulation.
		PriorityQueue<Event> Eventlist = new PriorityQueue<Event>( new EventComparator());
		// The grid map
		Grid worldmap = new Grid(gridwidth,gridheight,parser.getObstacles(),parser.getSpecialZones());
	
		
		
		// Birth of the initial population
		for(int i=0;i<initialpop;i++) {
			
			Individual ind = new Individual(i,initialpoint);
			ind.addPathPoint(initialpoint);
			ind.calcConfort(worldmap, confortsense, finalpoint);
			world.addIndividualAlive(ind);
			
			
		}
		
		// Ordination of the initial population in regard to confort ( From higher comfort to lowest comfort)
		Collections.sort(world.individualsalive , new SortByConfort());
		
		
		// Events of the initial population
		
		List<Individual> inds = world.getIndividualsInPopulation();
		
		
		for(Individual ind : inds) {
			
			Move aux = new Move(Utils.genericTimeCalculator(moveparam,ind.getConfort()),ind);
			Reproduction auxr = new Reproduction(Utils.genericTimeCalculator(reproductionparam,ind.getConfort()),ind);
			Death auxdeath = new Death(Utils.genericTimeCalculator(deathparam,ind.getConfort()), ind);
			Eventlist.add(aux);
			Eventlist.add(auxr);
			Eventlist.add(auxdeath);
			
		}
		
			
	
		// Start of the real simulation
		while(!Eventlist.isEmpty() && currenttime.compareTo(finalinst)!=1 && !world.individualsalive.isEmpty()) {
			
			currenttime=Eventlist.poll().action(worldmap, world, Eventlist, confortsense, finalpoint, genericparams);
			
			
			if(world.individualsalive.size()> maxpop) {
				world.individualsalive= world.epidemic(Eventlist);
			}
			
			if(currenttime.compareTo(printtime)==1) {
				
				System.out.println("Printing Best individual at current time : " + currenttime.setScale(0, RoundingMode.HALF_UP));
				Collections.sort(world.individualsalive , new SortByConfort());
				Individual best = world.individualsalive.get(0);
				System.out.println(best.toString());	
				best.printPathList();
				System.out.println("individual path size : " +best.getPathSize());
				System.out.println("individual current point : " +best.getCurrentPoint()[0] + best.getCurrentPoint()[1] );
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
				printtime = printtime.add(printtiming);
				
			}
			
		}
		
		
	}
		
		

}