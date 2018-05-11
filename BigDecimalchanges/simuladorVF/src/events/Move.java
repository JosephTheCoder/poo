/*
 * File Name : Move.java
 * Package : events
 * 
 * Description: Class which takes care of creating and executing movement events .
 * 
 * Authors: José Correia
 * 			Pedro Soares
 * 			Tiago Santos
 * 
 * Date: 11 May 2018 
 */

package events;

import java.math.*;

import java.util.PriorityQueue;

import grid.*;
import individual.*;
import utilities.*;

public class Move extends Event {
	
	//Constructor
	public Move (BigDecimal time ,Individual ind ) {
	
	// Call to Event's constructor
	super(time,ind);
	
	}
	

	/*
	 * Function name : action
	 * Entries : map , world , Eventlist , confort_sensitivity , final point , all event parameters
	 * Return : It returns a BigDecimal number which represents the time wich this event is taking place
	 * Description : This function not only processes a movements but also takes care of creating the next movement event
	 * 				of the individual in question . 
	 * 
	 */
	public BigDecimal action(Grid map,Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest , int[]genericparams) {
		
		
		// Execute a movement if alive
		if(world.individualsalive.indexOf(ind)==-1) {
			return this.getTime();
		}
		world.individualsalive.get(world.individualsalive.indexOf(ind)).addPathPoint(ind.getNextPathPoint(map, ind.getCurrentPoint()));
		
		// Calculate the new confort of the individual
		
		world.individualsalive.get(world.individualsalive.indexOf(ind)).calcConfort(map, confort_sensitivity, dest);
		
		//calculate time of next move
		Move aux = new Move(Utils.genericTimeCalculator(genericparams[0],ind.getConfort()).add(this.getTime()),ind );
		
		//add next move to eventlist
		Eventlist.add(aux);
		
		// Return current event time
		return this.getTime();
		
	}

}