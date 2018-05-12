package main;

import simulator.*;

public class Main {

	public static void main(String[] args) {
		
		String xmlFile = String.join("/", "TESTS", args[0]);
		
		Simulator simulation = new Simulator();
		
		simulation.SimulatorSimulate(xmlFile);
		
	}

}
