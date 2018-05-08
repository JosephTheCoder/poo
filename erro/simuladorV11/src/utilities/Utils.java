package utilities;

public final class Utils {
	
	private Utils() {}  // contructor 
	
	//methods
	
		public static double genericTimeCalculator(int genericParameter , double confort) {
			
			
			return  (1.0-Math.log(1.0-confort))*genericParameter;
		}

}
