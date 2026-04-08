package cop2805;
import java.util.ArrayList;
import java.util.Collections;

public class Homework2_BrodyStewart {
	
	public static void main(String[] args) {
		ArrayList<Double> nums = new ArrayList<Double>();
		nums.add(1.5);
		nums.add(2.35);
		nums.add(-4.7);
		nums.add(0.01); //Initializes and prints list
		System.out.print("Base Array: ");
		printList(nums);
		
		
		Collections.sort(nums); // Sorts and prints list
		System.out.print("Sorted Array: ");
		printList(nums);
		
		BinarySearchDouble(nums, 1.5); //Binary search for 1.5 in list
		
		Collections.fill(nums, 0.0); // Zeroes out the list and prints
		System.out.print("Zeroed Array: ");
		printList(nums);
	}
	
	private static void printList(ArrayList<Double> arr) {
		for (Double n : arr) {
			System.out.print(n + " ");
		}
		System.out.println();
	}

	private static void BinarySearchDouble(ArrayList<Double> searchArr, Double num) {
		System.out.print("Binary Search for "+ num + ": ");
		int i = Collections.binarySearch(searchArr, 1.5);
		if (i<0)
			System.out.print("number is not found.\n");
		else
			System.out.print("is found at index " + i + ".\n");
	}
}
