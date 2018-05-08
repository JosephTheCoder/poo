package events;

import grid.*;
import individual.*;



public class Death extends Event {
	
	public Death (double time ,Individual ind) {
		
	super(time,ind);
	
	}
	
	public void action(Grid map , Population world) {
		
		world.addDeathIndividual(world.individualsalive.get(world.individualsalive.indexOf(ind)));
		
	}

}
