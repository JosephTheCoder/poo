package utilities;

public class Time {
	int finalinst;
	public int Timer (int finalinst) {
		int time = 0;
		
		while(time < finalinst) {
			time = time + (finalinst/20);
		}
		
		return time;
	}
}
