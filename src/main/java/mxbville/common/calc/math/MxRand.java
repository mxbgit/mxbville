package mxbville.common.calc.math;

import java.util.Random;

public class MxRand {
	
	private static Random random;
		
		public static Random get()
		{
			if(random == null)
				random = new Random();
			return random;
		}
}
