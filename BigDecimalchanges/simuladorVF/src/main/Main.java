package main;

import simulator.*;

public class Main {

	public static void main(String[] args) {
		
		
		//String xmlFile = args[0];
		
		String xmlFile = "simuladorVF/data1.xml";
		
		Simulator simulation = new Simulator();
		
		simulation.SimulatorSimulate(xmlFile);
		
	}

}
