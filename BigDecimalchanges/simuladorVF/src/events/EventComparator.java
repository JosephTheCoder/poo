package events;
import java.util.*;


public class EventComparator implements Comparator<Event> {
	
	public int compare ( Event a , Event b) {
		if(a.getTime().compareTo(b.getTime())==-1) return -1;
		else if (a.getTime().compareTo(b.getTime())==0) return 0;
		else return 1;
		
	}

}
