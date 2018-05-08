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
	
	public void SimulatorSimulate() {
		
		
		XMLFileParser parser = new XMLFileParser("data1.xml");
		// cenas que supostamente veem do ficheiro
		int confortsense = parser.getComfortsens();
		int deathparam = parser.getDeath_param();
		int reproductionparam = parser.getReproduction_param();
		int moveparam = parser.getMove_param();
		int finalinst = parser.getFinalinst();
		int gridheight = parser.getGrid_height();
		int gridwidth = parser.getGrid_width();
		int[] initialpoint = parser.getInitialpoint();
		int  initialpop = parser.getInitpop();
		int maxpop = parser.getMaxpop();
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
		
		// verificação do mapa 
		
		worldmap.printGrid();
		
		//ordenar por conforto
		Collections.sort(world.individualsalive , new SortByConfort());
		
		
		//Test 1
		List<Individual> inds = world.getIndividualsInPopulation();
		
		for(Individual ind : inds) {
			
			System.out.println(ind.toString());
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		
		// End Test 1
		
		for(int i=0;i<initialpop;i++) {
			
			System.out.println("current point : "+world.individualsalive.get(i).getCurrentPoint());
		}
		
		
		//Test 2 : cálculo dos 10 primeiros movimentos
		int i =0;
		for(Individual ind : inds) {
			
			Move aux = new Move(Utils.genericTimeCalculator(moveparam,ind.getConfort())+i,ind);
			Eventlist.add(aux);
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
			
			Eventlist.poll().action(worldmap, world);
			
		}
		
		for(Individual ind : inds) {
			int[] position = new int[2];
			position[0] = 0;
			position[1] = 0;
			System.out.println(worldmap.getNode(position).toString());
			System.out.println(ind.toString());
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		
		// end Test 3
		
	}

}
