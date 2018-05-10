package events;

import java.util.*;
import grid.*;
import individual.*;
import utilities.Utils;

public class Reproduction extends Event {
	
	
		
		public Reproduction (double time ,Individual ind) {
			
		super(time,ind);
		
		}
		
		
		//rever might have problems
		public void action(Grid map , Population world , PriorityQueue<Event> Eventlist, int confort_sensitivity,  int[] dest , int[]genericparams ) {
			
			Individual auxparent;
			Individual child;
			int childid = world.individualsalive.size();
			double parentspathlenght;
			double confortslenght;
			List<int[]> auxpath = new ArrayList<int[]>();
			
			// get parent
			auxparent=world.individualsalive.get(world.individualsalive.indexOf(ind));
			
			//get childs path
			parentspathlenght= Math.ceil((auxparent.getPathSize()*9)/10);
			confortslenght = Math.ceil((auxparent.getPathSize()/10)*auxparent.getConfort());
			
			auxpath=auxparent.getPath();
			
			//create child
			child= new Individual(childid);
			
			System.out.println(parentspathlenght);
			System.out.println(confortslenght);
			
			for(int i=0;i<parentspathlenght;i++) {
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
			
			Reproduction aux = new Reproduction(Utils.genericTimeCalculator(genericparams[2],ind.getConfort())+this.getTime(),ind);
			
			// add next reproduction of parent to event list
			Eventlist.add(aux);
			
			// Calculate the first Move , Reproduction and death of the child 
			
			Reproduction auxrep = new Reproduction(Utils.genericTimeCalculator(genericparams[2],child.getConfort())+this.getTime(),child);
			Move auxmove = new Move(Utils.genericTimeCalculator(genericparams[0],child.getConfort())+this.getTime(),child);
			Death auxdeath = new Death(Utils.genericTimeCalculator(genericparams[1],child.getConfort())+this.getTime(),child);
			
			//add all events from the child to the Eventlist
			Eventlist.add(auxrep);
			Eventlist.add(auxmove);
			Eventlist.add(auxdeath);
			
		}

	

}
