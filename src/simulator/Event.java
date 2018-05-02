package simulator;

public abstract class Event {
	
	
	float auxEvent;
	
	int index = 0;
	
	public Timer(int finalinst) {
		int time = 0;
		index = index+1
		while(time < finalinst) {
			time = time + (finalinst/20);
		}
	}
	
	auxEvent = (1 - Math.log(comfort)));

	abstract void death();
	abstract void reproduction();
	abstract void move();

}
