package events;

import java.util.*;

import java.math.*;
import grid.*;
import individual.*;
import utilities.Utils;

public class Reproduction extends Event {
	
	
		
		public Reproduction (BigDecimal time ,Individual ind) {
			
		super(time,ind);
		
		}
		
		
		//rever might have problems
		public BigDecimal action(Grid map , Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest , int[]genericparams ) {
			
			Individual auxparent;
			Individual child;
			int childid = world.individualsalive.size();
			double parentspathlenght;
			double confortslenght;
			List<int[]> auxpath = new ArrayList<int[]>();
			
			BigDecimal thistime = this.getTime();
			
			// get parent
			auxparent=world.individualsalive.get(world.individualsalive.indexOf(ind));
			
			BigDecimal auxD = new BigDecimal(auxparent.getPathSize()/10);
			
			//get childs path
			parentspathlenght= Math.ceil((auxparent.getPathSize()*9)/10);
			confortslenght = Math.ceil(auxparent.getConfort().multiply(auxD).doubleValue());
			
			auxpath=auxparent.getPath();
			
			//create child
			child= new Individual(childid,auxparent.getCurrentPoint());
			
			for(int i=1;i<parentspathlenght;i++) {
				child.addPathPoint(auxpath.get(i));
				
			}
			
			for(int i=0;i<confortslenght;i++) {
				child.addPathPoint(auxpath.get(i+(int)parentspathlenght));
				
			}
			
			//calculate childs confort
			child.calcConfort(map, confort_sensitivity, dest);
			
			//add child to world
			
			world.individualsalive.add(child);
			
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
			
			return this.getTime();
			
		}

	

}
