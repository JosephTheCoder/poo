package events;

import java.math.*;

import java.util.PriorityQueue;

import grid.*;
import individual.*;
import utilities.*;

public class Observation extends Event {
	
	
	public Observe (BigDecimal time ,Individual ind ) {
		
	super(time,ind);
	
	}
	
	public BigDecimal action(Grid map,Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest , int[]genericparams) {
		
		
		// Execute a movement
		world.individualsalive.get(world.individualsalive.indexOf(ind)).addPathPoint(ind.getNextPathPoint(map, ind.getCurrentPoint()));
		
		// Calculate the new confort of the individual
		
		world.individualsalive.get(world.individualsalive.indexOf(ind)).calcConfort(map, confort_sensitivity, dest);
		
		//calculate time of next move
		Move aux = new Move(Utils.genericTimeCalculator(genericparams[0],ind.getConfort()).add(this.getTime()),ind );
		
		//add next move to eventlist
		Eventlist.add(aux);

		System.out.println("Present instant: " + present_instant);
		System.out.println("Number of realized events: " + nr_events);
		System.out.println("Population size: " + pop_size);

		if(final_point_hit == true)
			System.out.println("Final point has been hit: yes");

		else
			System.out.println("Final point has been hit: no");

		System.out.print("Path of the best fit individual: " + );

		System.out.println("Cost/comfort: " + cost + "/" + comfort);
		
		return this.getTime();
		
	}
///palestras workshops, interesse
	
}