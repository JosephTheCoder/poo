package simulator;

import PokerProject.Card;

public abstract class Event {
	
	package int id1 = 0;
	package float auxEvent;
	
	public Event(){
		id = ;
		auxEvent = id1;
	}	
	
	public getNewId() {
		id = id+1
	}
	
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
