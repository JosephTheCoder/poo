package events;

import java.util.PriorityQueue;

import grid.*;
import individual.*;
import utilities.*;

public class Move extends Event {
	
	
	public Move (double time ,Individual ind ) {
		
	super(time,ind);
	
	}
	

	
	public void action(Grid map,Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest , int[]genericparams) {
		
		// Execute a movement
		world.individualsalive.get(world.individualsalive.indexOf(ind)).addPathPoint(ind.getNextPathPoint(map, ind.getCurrentPoint()));
		
		// Calculate the new confort of the individual
		
		world.individualsalive.get(world.individualsalive.indexOf(ind)).calcConfort(map, confort_sensitivity, dest);
		
		//calculate time of next move
		Move aux = new Move(Utils.genericTimeCalculator(genericparams[0],ind.getConfort())+this.getTime(),ind );
		
		//add next move to eventlist
		Eventlist.add(aux);
		
	}

}