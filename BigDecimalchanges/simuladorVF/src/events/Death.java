package events;

import java.util.*;
import java.math.*;

import grid.*;
import individual.*;



public class Death extends Event {
	
	
	public Death (BigDecimal time ,Individual ind) {
		
		
	super(time,ind);
	
	}
	
	public BigDecimal action(Grid map , Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest , int[]genericparams ) {
		
		System.out.println("Entrei na morte neste tempo : " + this.getTime().toString());
		
		int id;
		PriorityQueue<Event> newEventlist = new PriorityQueue<Event>( new EventComparator());
		
		id= world.individualsalive.get(world.individualsalive.indexOf(ind)).getIdentifier();
		//execute dead 
		world.addDeathIndividual(world.individualsalive.get(world.individualsalive.indexOf(ind)));
		
		//remove all events from the individual that died from eventlist
		
		while(!Eventlist.isEmpty()) {
			
			Event aux = Eventlist.poll();
			
			if(aux.getIndividual().getIdentifier()!=id) {
				
				newEventlist.add(aux);
				
			}
			
			Eventlist=newEventlist;
			
		}
		
		return this.getTime();
	}

}
