/*
 * File Name : EventComparator.java
 * Package : events
 * 
 * Description: Class which takes care of implementing the Comparator interface.
 * 
 * Authors: José Correia
 * 			Pedro Soares
 * 			Tiago Santos
 * 
 * Date: 11 May 2018 
 */

package events;
import java.util.*;

//Interface implementation
public class EventComparator implements Comparator<Event> {
	
	// Compares a and b event time and returns -1 if a's time is smaller than b's time , 0 if they are equal and 1 if a's time
	// is bigger than b's time.
	public int compare ( Event a , Event b) {
		if(a.getTime().compareTo(b.getTime())==-1) return -1;
		else if (a.getTime().compareTo(b.getTime())==0) return 0;
		else return 1;
		
	}

}
