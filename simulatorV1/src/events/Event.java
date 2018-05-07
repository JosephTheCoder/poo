package events;

import java.util.Collections;

import java.util.LinkedList;
import utilities.*;
import grid.*;
import individual.*;

public class Event<T> {

		static class Node<T>{
			T elem;
			Node<T> next;
			Node(T t, Node<T> n){
				elem=t;
				next=n;
			}
			@Override
			public String toString() {
				return "" + elem + "\n" + (next==null ? "" : next);
			}
		}

		Node<T> head;
		Node<T> tail;
		int length;

		@Override
		public void add(T t) {
			if (head==null){
				head = new Node<T>(t,null);
				tail = head;
			} else {
				tail.next = new Node<T>(t,null);
				tail = tail.next;
			}
			length++;
		}

		@Override
		public T first() {
			return head==null ? null : head.elem;
		}

		@Override
		public void remove() {
			if (head==null) 
				return;
			head = head.next;
			if (head==null)
				tail=null;
			length--;
		}

		@Override
		public boolean isEmpty() {
			return head==null ? true : false;
		}

		@Override
		public int length() {
			return length;
		}

		@Override
		public String toString() {
			return "UnlimitedQueue [head=" + head + "]";
		}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	
	
	private int confort;
	private double param;
	
	public Individual indiv;
	
	public Population pop;
	
	public Node node;
		
	public int nbIndividuals;
	public LinkedList<Individual> individuals;
	
	public Event(int confort, double param) {
		int[] position = new int[2];
		
		this.param = param;
		this.confort = confort;
		
		genericTimeCalculator(param, confort);
	}
	
	public Event(int id, int confort) {
		indiv = new Individual(id, confort);
		pop = new Population(nbIndividuals, individuals);
		
	}


	public double calcEvent(int var) {
		
		double resultEvent = 0;
		double auxConfort = getConfort();
		
		resultEvent = (1-Math.log(1-aux_Confort))*var;
		
		return resultEvent;
	}*/

