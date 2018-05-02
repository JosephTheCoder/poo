package simulator;

import PokerProject.Card;

public abstract class Event {
	
<<<<<<< HEAD:simulator/Event.java
	package int id1 = 0;
	package float auxEvent;
	
	public Event(){
		id = ;
		auxEvent = id1;
	}	
	
	public getNewId() {
		id = id+1
	}
=======
	
	float auxEvent;
	
	int index = 0;
>>>>>>> 8dc4931de2cd4e26deb0a99e147c60a9b89889b3:src/simulator/Event.java
	
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
