package simulator;

import grid.*;
import java.util.Collections;
import java.util.List;


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
		int finalinst = parser.getFinalinst();
		int gridheight = parser.getGridHeight();
		int gridwidth = parser.getGridWidth();
		int[] initialpoint = parser.getInitialpoint();
		int[] finalpoint = parser.getFinalpoint();
		int  initialpop = parser.getInitpop();
		int maxpop = parser.getMaxpop();
		int[] genericparams = {moveparam,deathparam,reproductionparam};
		Population world = new Population();
		PriorityQueue<Event> Eventlist = new PriorityQueue<Event>( new EventComparator());
		Grid worldmap = new Grid(gridwidth,gridheight,parser.getObstacles(),parser.getSpecialZones());
		
		
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		// criação da população inicial
		for(int i=0;i<initialpop;i++) {
			
			Individual ind = new Individual(i);
			ind.addPathPoint(initialpoint);
			world.addIndividualAlive(ind);
			
		}
		
		//ordenar por conforto
		Collections.sort(world.individualsalive , new SortByConfort());
		
		
		//Test 1 : Individuos ordenados por conforto
		List<Individual> inds = world.getIndividualsInPopulation();
		
		
		for(Individual ind : inds) {
			
			System.out.println(ind.toString());
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		
		// End Test 1
			
		//Test 2 : cálculo dos 10 primeiros movimentos
		int i =0;
		for(Individual ind : inds) {
			
			Move aux = new Move(Utils.genericTimeCalculator(moveparam,ind.getConfort())+i,ind);
			Reproduction auxr = new Reproduction(Utils.genericTimeCalculator(reproductionparam,ind.getConfort())+i,ind);
			Eventlist.add(aux);
			Eventlist.add(auxr);
			i++;
			
		}
		
		//para testar a inserção com ordem a fundo 
		/*
		Individual ind = new Individual(10);
		Move aux = new Move(Utils.genericTimeCalculator(moveparam,0.5),ind);
		Eventlist.add(aux);
		*/
		// end mini test
		/*
		while(!Eventlist.isEmpty()) {
			
			System.out.println("Event info : " + Eventlist.poll().toString());
		}
		*/
		//End test 2
		
		// Test 3 : see if the Move event actually works
		while(!Eventlist.isEmpty()) {
			
			Eventlist.poll().action(worldmap, world , Eventlist ,confortsense , finalpoint , genericparams);
			
		}
		
		

		System.out.println("individuals alive");
		
		inds=world.getIndividualsInPopulation();
		
		for(Individual ind : inds) {
			
			System.out.println(ind.toString());
			ind.printPathList();
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		// end Test 3
		
		
	}

}