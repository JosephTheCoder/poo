package individual;

import java.util.*;

public class SortByConfort implements Comparator<Individual>{
	
	public int compare ( Individual a , Individual b) {
		if(a.getConfort().compareTo(b.getConfort())==-1) return 1;
		else if (a.getConfort() == b.getConfort()) return 0;
		else return -1;
		
	}

}
