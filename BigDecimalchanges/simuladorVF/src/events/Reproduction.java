/*
 * File Name : Reproduction.java
 * Package : events
 * 
 * Description: Class which takes care of creating and executing reproduction events .
 * 
 * Authors: José Correia
 * 			Pedro Soares
 * 			Tiago Santos
 * 
 * Date: 11 May 2018 
 */

package events;

import java.util.*;

import java.math.*;
import grid.*;
import individual.*;
import utilities.Utils;

/*
 *  Class that implements the Abstract class Event in order to process the multiple reproductions of all the individuals in question.
 */

public class Reproduction extends Event {
	
		
	
		// Constructor
	
		public Reproduction (BigDecimal time ,Individual ind) {
		
		// Call to Event constructor
		super(time,ind);
		
		}
		
		
		/*
		 * Function name : action
		 * Entries : map , world , Eventlist , confort_sensitivity , final point , all event parameters
		 * Return : It returns a BigDecimal number which represents the time wich this event is taking place
		 * Description : This function not only processes a reproduction but also takes care of creating the next reproduction event
		 * 				of the parent and all the first event of the child ( first move , first reproduction and death).
		 * 
		 */
		public BigDecimal action(Grid map , Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest , int[]genericparams ) {
			
			
			Individual auxparent;
			Individual child;
			int childid = world.getNbIndividuals();
			double parentspathlenght;
			double confortslenght;
			List<int[]> auxpath = new ArrayList<int[]>();
			
			BigDecimal thistime = this.getTime();
			
			// get parent from the world population if alive 
			if(world.individualsalive.indexOf(ind)==-1) {
				return this.getTime();
			}
			auxparent=world.individualsalive.get(world.individualsalive.indexOf(ind));
			
			BigDecimal auxD = new BigDecimal(auxparent.getPathSize()/10);
			
			//get childs path from parents path accordingly
			parentspathlenght= Math.ceil((auxparent.getPathSize()*9)/10);
			confortslenght = Math.ceil(auxparent.getConfort().multiply(auxD).doubleValue());
			
			auxpath=auxparent.getPath();
			
			//create child
			child= new Individual(childid,auxparent.getCurrentPoint()) ;
			
			for(int i=0;i<parentspathlenght;i++) {
				child.addPathPoint(auxpath.get(i));
				
			}
			
			for(int i=0;i<confortslenght;i++) {
				child.addPathPoint(auxpath.get(i+(int)parentspathlenght));
				
			}
			
			//calculate childs confort
			child.calcConfort(map, confort_sensitivity, dest);
			
			//add child to world
			
			world.addIndividualAlive(child);
			
			// calculates the next reproduction of the parent 
			
			Reproduction aux = new Reproduction(Utils.genericTimeCalculator(genericparams[2],ind.getConfort()).add(thistime),ind);
			
			// add next reproduction of parent to event list
			Eventlist.add(aux);
			
			// Calculate the first Move , Reproduction and death of the child 
			
			Reproduction auxrep = new Reproduction(Utils.genericTimeCalculator(genericparams[2],child.getConfort()).add(thistime),child);
			Move auxmove = new Move(Utils.genericTimeCalculator(genericparams[0],child.getConfort()).add(thistime),child);
			Death auxdeath = new Death(Utils.genericTimeCalculator(genericparams[1],child.getConfort()).add(thistime),child);
			
			//add all events from the child to the Eventlist
			Eventlist.add(auxrep);
			Eventlist.add(auxmove);
			Eventlist.add(auxdeath);
			
			//Return the current event time
			return this.getTime();
			
		}

	

}
