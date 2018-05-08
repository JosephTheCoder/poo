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
		
		
		//XMLFileParser parser = new XMLFileParser("data1.xml");
		// cenas que supostamente veem do ficheiro
		int confortsense = 3;
		int deathparam = 10;
		int reproductionparam = 1;
		int moveparam = 1;
		int finalinst = 100;
		int gridheight = 4;
		int gridwidth = 3;
		int[] initialpoint = new int[2];
		initialpoint[0]=1;
		initialpoint[1]=1;
		int  initialpop = 10;
		int maxpop = 500;
		Population world = new Population();
		PriorityQueue<Event> Eventlist = new PriorityQueue<Event>( new EventComparator());
		
		
		// criação da população inicial
		for(int i=0;i<initialpop;i++) {
			
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
			
			Move aux = new Move(Utils.genericTimeCalculator(moveparam,ind.getConfort())+i,ind);
			Eventlist.add(aux);
			i++;
			
		}
		
		
		
		while(!Eventlist.isEmpty()) {
			
			System.out.println("Event time : " + Eventlist.poll().getTime());
		}
		
		//End test 2
		
		
		
	}

}
