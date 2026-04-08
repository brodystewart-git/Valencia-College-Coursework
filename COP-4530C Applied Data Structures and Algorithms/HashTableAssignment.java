import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HashTableAssignment {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		File theFile = getFile(scanner);
		if (theFile == null) {
			System.out.println("Exiting program...");
			scanner.close();
			return;
		}
		HashMap<String, Integer> table = createTable(theFile);
		printTopTen(table);
		
		lookupTime(table, "middleton");
		lookupTime(table, "invitation");
		lookupTime(table, "do");
		lookupTime(table, "Apple");
		scanner.close();
	}
	
	public static File getFile(Scanner scanner) {
		while(true) {
			System.out.print("Please enter the text file's path or type exit: "		);
			String path = scanner.nextLine();
			path = path.replace("\"", ""); 
			if (path.equalsIgnoreCase("exit")) {
				return null;
			}
			File checkFile = new File(path);
			if (checkFile.exists() && checkFile.isFile() && path.toLowerCase().endsWith(".txt")) {
				System.out.println("Text file found.");
				return checkFile;
			}else {
				System.out.println("File does not exist or isn't a .txt file.");
			}
		}
	}
	
	public static HashMap<String, Integer> createTable(File file){
		HashMap<String, Integer> table = new HashMap<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			String input;
			while ((input = reader.readLine()) != null){
				input = input.toLowerCase().replaceAll("[^a-z ]", "");
				String[] words = input.split(" ");
				for(String word: words) {
					if(word.isEmpty()) continue;
					if(word.equals("a") || word.equals("is") || word.equals("at") || 
							word.equals("which") || word.equals("on") || word.equals("the")) continue;
					if(table.containsKey(word)) 
						table.put(word,  table.get(word) +1);
					else table.put(word, 1);
				}
			}
		}catch (IOException e) {
			System.out.println("Unable to read the file: " + e.getMessage());
		}
		return table;
	}
	
	public static void printTopTen(HashMap<String, Integer> table) {
		System.out.println("Total number of entries in the table: " + table.size());
		
		List<Map.Entry<String, Integer>> list = new ArrayList<>(table.entrySet());
		list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
		System.out.println("\tTop Ten Most Used Words");
		System.out.println("-----------------------------------------");
		
		for (int i = 0; i < 10 && i < list.size(); i++) {
			Map.Entry<String, Integer> item = list.get(i);
			String word = item.getKey();
			Integer count = item.getValue();
			System.out.printf("%-2d) %-15s : ", (i + 1), word);
			for(int j = 0; j < count; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
	
	public static void lookupTime(HashMap<String, Integer> table, String word) {
		long startTime = System.nanoTime();
		Integer count = table.get(word);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("Lookup time for " + word + " is " + duration + " ns.");
	}
}
