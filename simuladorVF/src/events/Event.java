package events;

import individual.*;
import grid.*;

public abstract class Event {

	private double time;
	protected Individual ind;
	
	public Event(double time,Individual ind) {
		this.time=time;
		this.ind=ind;
	}
	
	public double getTime() {
		
		return this.time;
	}
	
	public Individual getIndividual() {
		return this.ind;
	}
	
	public abstract void action(Grid map ,Population world );


}
