package cop2805;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class Homework4_BrodyStewart {
	
	public static void main(String[] args) {
		List<Double> fileContents= ReadFile("data.txt");
		System.out.println("This file contains the doubles: " + fileContents);
		Collections.sort(fileContents);
		WriteFile(fileContents, "data-sorted.txt");
		fileContents = ReadFile("data-sorted.txt");
		System.out.println("Sorted the file in ascending order to: " + fileContents);
	}
	
	private static List<Double> ReadFile(String filePath) {  // Reads the doubles from a given file location in string format.
		Scanner scan; 
		List<Double> nums = new ArrayList<>();
		try {
			scan = new Scanner(new File(filePath)); // Open Reader
			while (scan.hasNext()) {
				String line = scan.nextLine().trim(); 		//Trims the whitespace just in case.
				try {										// if it's a double, add it. 
					nums.add(Double.parseDouble(line));
				}catch (NumberFormatException e){			// Catching the error of the next item not being a double, skipping the line and letting the user know.
					System.out.println("Format error in file, skipping non-double: " + line); // Just lets the user know that there was a non-double in the list and where it was.
				}
			}
			scan.close();	
		} catch(Exception e) {
			System.out.println("ERROR! Couldn't open the file. Please check the path.");
		}
		return nums;
	}
	
	private static void WriteFile(List<Double> data, String filePath) { // Takes the new sorted list and prints it to the file.
		Path path = Paths.get(filePath);
		try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(path, Charset.defaultCharset(), 
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))){ // Open writer on file, TRUNCATE_EXISTING to clear it in case the file has errors in it.
			for(int i = 0; i< data.size(); i++) {
				pw.print(data.get(i));				// Using a for loop instead of a for each loop to get rid of excess newlines. This won't add a new line if its the last one.
					pw.println();
			}
		}catch (IOException e) {
			System.out.println("ERROR! Couldn't open the file. Please check the path.");
		}
	}
}
