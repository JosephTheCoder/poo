package events;
import java.util.*;


public class EventComparator implements Comparator<Event> {
	
	public int compare ( Event a , Event b) {
		if(a.getTime() < b.getTime()) return -1;
		else if (a.getTime() == b.getTime()) return 0;
		else return 1;
		
	}

}
	
	
