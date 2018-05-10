package utilities;

import java.math.*;

public final class Utils {
	
	private Utils() {}  // contructor 
	
	//methods
	
		public static BigDecimal genericTimeCalculator(int genericParameter , BigDecimal confort) {
			
			double param = genericParameter;
			BigDecimal um = new BigDecimal(1);
			BigDecimal time = new BigDecimal((1.0-Math.log(um.subtract(confort).doubleValue()))*param);
			return  time;
		}

}
