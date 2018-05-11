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
	
	public Simulator() {
		
	}
	
	public void SimulatorSimulate(String filename) {
		
		
		XMLFileParser parser = new XMLFileParser(filename);
		int confortsense = parser.getComfortsens();
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
		int[] genericparams = {moveparam,deathparam,reproductionparam};
		Population world = new Population();
		PriorityQueue<Event> Eventlist = new PriorityQueue<Event>( new EventComparator());
		Grid worldmap = new Grid(gridwidth,gridheight,parser.getObstacles(),parser.getSpecialZones());
		BigDecimal currenttime = new BigDecimal(0);
		BigDecimal printtime = new BigDecimal(0);
		BigDecimal twenty = new BigDecimal(20);
		BigDecimal printtiming = finalinst.divide(twenty);
		
		
		
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		// criação da população inicial
		for(int i=0;i<initialpop;i++) {
			
			Individual ind = new Individual(i,initialpoint);
			ind.addPathPoint(initialpoint);
			ind.calcConfort(worldmap, confortsense, finalpoint);
			world.addIndividualAlive(ind);
			
			
		}
		
		//ordenar por conforto
		Collections.sort(world.individualsalive , new SortByConfort());
		
		
		//Test 1 : Individuos ordenados por conforto
		List<Individual> inds = world.getIndividualsInPopulation();
		
		// Handling the initial population and it´s events
		
		/*
		for(Individual ind : inds) {
			
			System.out.println(ind.toString());
			ind.printPathList();
			System.out.println("individual path size : " +ind.getPathSize());
			System.out.println("individual current point : " +ind.getCurrentPoint()[0] + ind.getCurrentPoint()[1] );
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		*/
		
		for(Individual ind : inds) {
			
			Move aux = new Move(Utils.genericTimeCalculator(moveparam,ind.getConfort()),ind);
			Reproduction auxr = new Reproduction(Utils.genericTimeCalculator(reproductionparam,ind.getConfort()),ind);
			Death auxdeath = new Death(Utils.genericTimeCalculator(deathparam,ind.getConfort()), ind);
			Eventlist.add(aux);
			Eventlist.add(auxr);
			Eventlist.add(auxdeath);
			
		}
		
		
	
		//final test******************************
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
			//System.out.println(currenttime.toString());
		}
		
		// result testing
		
		/*
		Collections.sort(world.individualsalive, new SortByConfort()); 
		System.out.println("++++++++++++++++++++results++++++++++++++++++++++");
		
		List<Individual> indsd = world.getdeathindividuals();
		
		Collections.sort(indsd, new SortByConfort()); 
		
		for(Individual ind : indsd) {
			
			System.out.println(ind.toString());	
			ind.printPathList();
			System.out.println("individual path size : " +ind.getPathSize());
			System.out.println("individual current point : " +ind.getCurrentPoint()[0] + ind.getCurrentPoint()[1] );
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
			
		}
		
	List<Individual> indslast = world.getIndividualsInPopulation();
		
		
		for(Individual ind : indslast) {
			
			System.out.println(ind.toString());
			ind.printPathList();
			System.out.println("individual path size : " +ind.getPathSize());
			System.out.println("individual current point : " +ind.getCurrentPoint()[0] + ind.getCurrentPoint()[1] );
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		*/
		
		System.out.println("End of Program");
	}
		
		

}