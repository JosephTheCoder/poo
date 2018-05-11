/*
 * File Name : Death.java
 * Package : events
 * 
 * Description: Class which takes care of creating and executing death events .
 * 
 * Authors: José Correia
 * 			Pedro Soares
 * 			Tiago Santos
 * 
 * Date: 11 May 2018 
 */

package events;

import java.util.*;
import java.math.*;

import grid.*;
import individual.*;



public class Death extends Event {
	
	//Constructor
	public Death (BigDecimal time ,Individual ind) {
		
	// Call to Event's constructor	
	super(time,ind);
	
	}
	
	/*
	 * Function name : action
	 * Entries : map , world , Eventlist , confort_sensitivity , final point , all event parameters
	 * Return : It returns a BigDecimal number which represents the time wich this event is taking place
	 * Description : This function not only processes a death but also takes care of removing all of the events associated with the
	 * 				Individual that died.
	 * 
	 */
	public BigDecimal action(Grid map , Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest , int[]genericparams ) {
		
		
		
		int id;
		PriorityQueue<Event> newEventlist = new PriorityQueue<Event>( new EventComparator());
		
		id= world.individualsalive.get(world.individualsalive.indexOf(ind)).getIdentifier();
		//execute dead 
		world.addDeathIndividual(world.individualsalive.get(world.individualsalive.indexOf(ind)));

		
		//
		//remove all events from the individual that died from eventlist
		
		while(!Eventlist.isEmpty()) {
			
			
			Event aux = Eventlist.poll();
			
			
			if(aux.getIndividual().getIdentifier()!=id) {
				
				newEventlist.add(aux);
				
			}
			
			
		}
		
		Eventlist=newEventlist;
		
		return this.getTime();
	}

}
