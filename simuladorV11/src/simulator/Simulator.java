package simulator;

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

		Population world = new Population();
		PriorityQueue<Event> Eventlist = new PriorityQueue<Event>( new EventComparator());
		
		
		// criação da população inicial
		for(int i=0;i<parser.getInitpop();i++) {
			
			Individual ind = new Individual(i);
			world.addIndividualAlive(ind);
		}
		
		//ordenar por conforto
		Collections.sort(world.individualsalive , new SortByConfort());
		
		
		//Test 1
		List<Individual> inds = world.getIndividualsInPopulation();
		
		for(Individual ind : inds) {
			
			System.out.println(ind.toString());
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		
		// End Test 1
		
		//Test 2 : cálculo dos 10 primeiros movimentos
		int i =0;
		for(Individual ind : inds) {
			
			Move aux = new Move(Utils.genericTimeCalculator(parser.getMoveParam(),ind.getConfort())+i,ind);
			Eventlist.add(aux);
			i++;
			
		}
		
		
		
		while(!Eventlist.isEmpty()) {
			
			System.out.println("Event time : " + Eventlist.poll().getTime());
		}
		
		//End test 2
		
		
		
	}

}
