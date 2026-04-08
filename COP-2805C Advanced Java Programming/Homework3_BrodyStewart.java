package cop2805;

public class Homework3_BrodyStewart {
	
	public static void main(String[] args) {
		String[] colors = {"Red", "Green", "Blue"};
		Integer[] numbers = {1,2,3};
		Double[] circleRadius = {3.0,5.9,2.9}; // Established arrays
		
		System.out.println("Smallest Object in Colors: " + min(colors)); 
		System.out.println("Smallest Object in Numbers: " +min(numbers));
		System.out.println("Smallest Object in CircleRadius: " +min(circleRadius)); //Called and printed results. All done!
	}
	
	public static <E extends Comparable<E>> E min (E [] list) {
		int length = list.length;
		int lowest = 0;	// Index of lowest (minimum) element
		
		for (int i = 1; i< length; i++) {				// Start at one because lowest is already 0.
			if (list[lowest].compareTo(list[i]) > 0)   // Compare the current lowest to the current index, set if the current lowest is smaller.
				lowest = i;	
		}
		
		return list[lowest];
	}
	
	
}
