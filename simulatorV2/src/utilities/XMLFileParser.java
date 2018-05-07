package simulator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;

public class XMLFileParser {
	private int finalinst,
				initpop,
				maxpop,
				comfortsens,
				grid_width,
				grid_height,
				death_param,
				reproduction_param,
				move_param;
	private int[][] specialZones;  //[xinitial,yinitial,xfinal,yfinal,cost]
	private int[][] obstacles;		//[xpos,ypos]
	private int[] initialpoint = new int[2]; //[x,y]
	private int[] finalpoint = new int[2];
	
	public XMLFileParser(String filename)
	{
		try {
			
			File xmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(true);
			dbFactory.setNamespaceAware(true);
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			ErrorHandler errors = new ErrorHandler() {
				
				@Override
				public void warning(SAXParseException e) throws SAXException {
					show("Warning", e);
					throw(e);
				}
				
				@Override
				public void fatalError(SAXParseException e) throws SAXException {
					show("Fatal Error", e);
					throw(e);				
				}
				
				@Override
				public void error(SAXParseException e) throws SAXException {
					show("Error", e);
					throw(e);					
				}
			};
			
			dBuilder.setErrorHandler(errors);
			
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			//Simulation Element
			Element element = (Element) doc.getElementsByTagName("simulation").item(0);
				
			//Reading attributes
			finalinst = Integer.parseInt(element.getAttribute("finalinst"));
			initpop = Integer.parseInt(element.getAttribute("initpop"));
			maxpop = Integer.parseInt(element.getAttribute("maxpop"));
			comfortsens = Integer.parseInt(element.getAttribute("comfortsens"));
			
			//Grid Element
			element = (Element) doc.getElementsByTagName("grid").item(0);
			grid_width = Integer.parseInt(element.getAttribute("colsnb"));
			grid_height = Integer.parseInt(element.getAttribute("rowsnb"));
			
			//initialpoint
			element = (Element) doc.getElementsByTagName("initialpoint").item(0);
			initialpoint[0] = Integer.parseInt(element.getAttribute("xinitial")) - 1;
			initialpoint[1] = Integer.parseInt(element.getAttribute("yinitial")) - 1;
			
			//finalpoint
			element = (Element) doc.getElementsByTagName("finalpoint").item(0);
			finalpoint[0] = Integer.parseInt(element.getAttribute("xfinal")) - 1;
			finalpoint[1] = Integer.parseInt(element.getAttribute("yfinal")) - 1;
			
			//specialcostzones
			element = (Element) doc.getElementsByTagName("specialcostzones").item(0);
			int num = Integer.parseInt(element.getAttribute("num")); //gets the number of zones
			
			NodeList nList = doc.getElementsByTagName("zone"); //gets zones nodes List
			
			if(num > 0) {
				specialZones = new int[num][5];
				
				for(int i = 0; i < num; i++)
				{
					Element aux = (Element) nList.item(i);
					specialZones[i][0] = Integer.parseInt(aux.getAttribute("xinitial")) - 1;
					specialZones[i][1] = Integer.parseInt(aux.getAttribute("yinitial")) - 1;
					specialZones[i][2] = Integer.parseInt(aux.getAttribute("xfinal")) - 1;
					specialZones[i][3] = Integer.parseInt(aux.getAttribute("yfinal")) - 1;
					specialZones[i][4] = Integer.parseInt(aux.getTextContent());
				}
			}
			
			//obstacles
			element = (Element) doc.getElementsByTagName("obstacles").item(0);
			num = Integer.parseInt(element.getAttribute("num")); //gets the number of zones
			
			nList = doc.getElementsByTagName("obstacle"); //gets zones nodes List
			
			if(num > 0) {
				obstacles = new int[num][2];
				
				for(int i = 0; i < num; i++)
				{
					Element aux = (Element) nList.item(i);
					obstacles[i][0] = Integer.parseInt(aux.getAttribute("xpos")) - 1;
					obstacles[i][1] = Integer.parseInt(aux.getAttribute("ypos")) - 1;
				}
			}
			
			//events
			element = (Element) doc.getElementsByTagName("death").item(0);
			death_param = Integer.parseInt(element.getAttribute("param"));
			
			element = (Element) doc.getElementsByTagName("reproduction").item(0);
			reproduction_param = Integer.parseInt(element.getAttribute("param"));
			
			element = (Element) doc.getElementsByTagName("move").item(0);
			move_param = Integer.parseInt(element.getAttribute("param"));
			
			
			System.out.println(toString());
		} catch (Exception e) {
			System.out.println("Fodeu");
			e.printStackTrace();
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Final instant: " 
				+ finalinst 
				+ "\nInitial Population: "
				+ initpop
				+ "\nMax Population: "
				+ maxpop
				+ "\nComfort Sensitivity: "
				+ comfortsens;
	}
	
	public int getFinalinst() {
		return finalinst;
	}

	public int getInitpop() {
		return initpop;
	}

	public int getMaxpop() {
		return maxpop;
	}

	public int getComfortsens() {
		return comfortsens;
	}

	public int getGrid_width() {
		return grid_width;
	}

	public int getGrid_height() {
		return grid_height;
	}
	
	public int[] getInitialpoint() {
		return initialpoint;
	}

	public int[] getFinalpoint() {
		return finalpoint;
	}

	public int getDeath_param() {
		return death_param;
	}

	public int getReproduction_param() {
		return reproduction_param;
	}

	public int getMove_param() {
		return move_param;
	}

	public int[][] getSpecialZones() {
		return specialZones;
	}

	public int[][] getObstacles() {
		return obstacles;
	}
	
	private void show(String type, SAXParseException e) {
	    System.out.println(type + ": " + e.getMessage());
	    System.out.println("Line " + e.getLineNumber() + " Column "
	        + e.getColumnNumber());
	    System.out.println("System ID: " + e.getSystemId());
	  }
}