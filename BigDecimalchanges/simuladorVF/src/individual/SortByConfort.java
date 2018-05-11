/*
 * File name: SortByConfort.java
 * Package: individual
 * 
 * Description: This file class implements the class Comparator (package java.util.*)
 * 				Its compares the comfort of two individuals and returns -1, 0 or 1
 * 				according to the result. This will be used while sorting individuals
 * 				by comfort.
 * 
 * Authors: José Correia
 * 			Pedro Soares
 * 			Tiago Santos
 * 
 * Date: 11th may 2018
 */

package individual;

import java.util.*;

public class SortByConfort implements Comparator<Individual>{
	
	public int compare ( Individual a , Individual b) {
		if(a.getConfort().compareTo(b.getConfort())==-1) return 1;
		else if (a.getConfort() .compareTo(b.getConfort())==0) return 0;
		else return -1;
	}

}
