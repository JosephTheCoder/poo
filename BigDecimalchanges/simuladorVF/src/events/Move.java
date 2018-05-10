package events;

import java.math.*;

import java.util.PriorityQueue;

import grid.*;
import individual.*;
import utilities.*;

public class Move extends Event {
	
	
	public Move (BigDecimal time ,Individual ind ) {
		
	super(time,ind);
	
	}
	

	
	public BigDecimal action(Grid map,Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest , int[]genericparams) {
		
		
		// Execute a movement
		world.individualsalive.get(world.individualsalive.indexOf(ind)).addPathPoint(ind.getNextPathPoint(map, ind.getCurrentPoint()));
		world.individualsalive.get(world.individualsalive.indexOf(ind)).setCurrentPoint(ind.getNextPathPoint(map, ind.getCurrentPoint()));
		
		// Calculate the new confort of the individual
		
		world.individualsalive.get(world.individualsalive.indexOf(ind)).calcConfort(map, confort_sensitivity, dest);
		
		//calculate time of next move
		Move aux = new Move(Utils.genericTimeCalculator(genericparams[0],ind.getConfort()).add(this.getTime()),ind );
		
		//add next move to eventlist
		Eventlist.add(aux);
		
		return this.getTime();
		
	}

}