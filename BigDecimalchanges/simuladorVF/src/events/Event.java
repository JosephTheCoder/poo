package events;

import java.math.*;

import individual.*;

import java.util.PriorityQueue;

import grid.*;

public abstract class Event {


	private BigDecimal time;
	protected Individual ind;
	
	public Event(BigDecimal time,Individual ind) {
		this.time=time;
		this.ind=ind;
	}
	
	public BigDecimal getTime() {
		
		return this.time;
	}
	
	public Individual getIndividual() {
		return this.ind;
	}
	
	public abstract BigDecimal action(Grid map ,Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest,int[]genericparams );

	@Override
	public String toString() {
		return "Event [time=" + time.toString() + ", ind=" + ind + "]";
	}
	
	
																							
}
