package events;

import individual.*;

import java.util.PriorityQueue;

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
	
	public abstract void action(Grid map ,Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest,int[]genericparams );


	
	
																							
}
