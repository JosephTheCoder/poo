package events;

import grid.*;
import individual.*;

public class Move extends Event {
	
	public Move (double time ,Individual ind) {
		
	super(time,ind);
	
	}
	
	public void action(Grid map,Population world) {
		
		world.individualsalive.get(world.individualsalive.indexOf(ind)).addPathPoint(ind.getNextPathPoint(map, ind.getCurrentPoint().getPosition()));
		
	}

}
