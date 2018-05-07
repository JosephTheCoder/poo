package events;

import java.util.LinkedList;

import org.w3c.dom.Document;

import individual.*;
import utilities.Utils;

public class Death<T> extends Event<T>{

	private final Prior<T> prior;
	private Node<T> lastElemPrior;
	
	public Death(Prior<T> p){
		prior = p;
	}

	@Override
	public void add(T t) {
		if (!prior.isPrior(t))
			super.add(t);
		else {
			if (lastElemPrior==null){
				head = new Node<T>(t,head);
				lastElemPrior = head;
				if (tail==null)
					tail=head;
			} else {
				lastElemPrior.next = new Node<T>(t,lastElemPrior.next);
				if (tail==lastElemPrior) tail = lastElemPrior.next;
				lastElemPrior = lastElemPrior.next;
			}
			length++;
		}
	}

	@Override
	public void remove() {
		if (lastElemPrior==head)
			lastElemPrior=null;
		super.remove();
	}	
	
	
	
	
	private double u;
	private LinkedList<Individual> individuals;
	private Individual indivaux = individuals.get();
	private double confort = Individual.getConfort();
	
	private double deathValue = Utils.genericTimeCalculator(u, confort);
	
	


}
