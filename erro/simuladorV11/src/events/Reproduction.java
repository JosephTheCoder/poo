package events;

import grid.*;
import individual.*;

public class Reproduction extends Event {
	
		
		public Reproduction (double time ,Individual ind) {
			
		super(time,ind);
		
		}
		
		public void action(Grid map , Population world) {
			
			Individual auxparent;
			Individual child;
			int childid = world.individualsalive.size();
			double parentspathlenght;
			double confortslenght;
			Node[] auxpath;
			
			// get parent
			auxparent=world.individualsalive.get(world.individualsalive.indexOf(ind));
			
			parentspathlenght= Math.ceil((auxparent.getPathSize()*9)/10);
			confortslenght = Math.ceil((auxparent.getPathSize()/10)*auxparent.getConfort());
			
			auxpath=auxparent.getPath();
			
			child= new Individual(childid);
			world.individualsalive.add(child);
			
			for(int i=0;i<parentspathlenght;i++) {
				child.addPathPoint(auxpath[i].getPosition());
				
			}
			
			for(int i=0;i<confortslenght;i++) {
				child.addPathPoint(auxpath[i+(int)parentspathlenght].getPosition());
				
			}
			
			child.calcConfort(1/*generic number to replace once calc_confort is done*/);
			
		}

	

}
