package simulator;

import java.util.ArrayList;
import java.util.List;

public abstract class Event {

	public double calcEvent(int var) {
		
		double resultEvent = 0;
		double auxConfort = get_confort();
		
		resultEvent = (1-Math.log(1-aux_Confort))*var;
		
		return resultEvent;
	}
	
	
	
	
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
}
