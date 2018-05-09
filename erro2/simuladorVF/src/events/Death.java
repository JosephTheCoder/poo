package events;

import java.util.PriorityQueue;

import grid.*;
import individual.*;



public class Death extends Event {
	
	public Death (double time ,Individual ind) {
		
	super(time,ind);
	
	}
	
	public void action(Grid map , Population world , PriorityQueue<Event> Eventlist) {
		
		world.addDeathIndividual(world.individualsalive.get(world.individualsalive.indexOf(ind)));
		
	}

}
