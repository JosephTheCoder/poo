package simulator;

import java.util.ArrayList;
import java.util.List;

public abstract class Event {

	int id;
	float time;
	
	List<Event> eventList;
	
	public Event(int[] id, float[] time) {
		this.id=id;
		this.time=time
		this.eventList= new eventList<Event>();		
	}
	
	public int[] getEventId()
	{
		return this.id;
	}
	
	public float[] getEventTime()
	{
		return this.time;
	}
				
	public Timer(int finalinst) {
		int time = 0;
		
		while(time < finalinst) {
			time = time + (finalinst/20);
		}
	}
	
	abstract void death();
	abstract void reproduction();
	abstract void move();

}
