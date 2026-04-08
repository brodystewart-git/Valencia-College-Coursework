import java.util.Scanner;
import java.io.*;

public class LinkedListAssignment {
	static Scanner scanner = new Scanner(System.in);
	static TaskQueue queue = new TaskQueue();
	static boolean running = true;
	
	public static void main(String[] args) {
		while (running) {
			mainMenu();
		}
		scanner.close();
	}
	
	public static void mainMenu() {
		System.out.println(
				"\n\n-----Main Menu-----\n" +
				"1. Add a task\n" +
				"2. Mark oldest task as complete\n" +
				"3. View entire list\n" +
				"4. Exit"
				);
		System.out.print("Please enter a number choice: ");
		String choice = scanner.nextLine();
		switch (choice) {
        case "1":
        	System.out.println("Adding a task.");
            System.out.print("Enter description: ");
            String desc = scanner.nextLine();
            System.out.print("Enter priority (integer): ");
            int priority = Integer.parseInt(scanner.nextLine());
            queue.enqueue(desc, priority);
            break;
        case "2":
        	System.out.println("Completing a task.");
            queue.dequeue();
            break;
        case "3":
        	System.out.println("Displaying tasks.");
            queue.display();
            break;
        case "4":
            queue.saveTasks(); // Save before exiting
            running = false;
            System.out.println("Exiting program.");
            break;
        default:
            System.out.println("Invalid option.");
    }
	}
}


class TaskQueue{
	Task top;
	Task bottom;
	private String fileName = "Queue.txt";
	
	public TaskQueue() { 
		top = null;
		bottom = null;
		loadTasks();
	}
	
	public void enqueue(String d, int p) {
		Task t = new Task(d,p);
		if (bottom == null) {
			bottom = t;
			top = bottom;
		}else {
			bottom.next = t;
			bottom = t;
		}
		System.out.println("Tasks added.");
	}
	
	public void dequeue() {
		if (top == null) { // Check if empty
			System.out.println("The queue is empty, ending process.");
			return;
		}
		System.out.println("Dequeued: " + top.description);
		top = top.next;
		if(top == null) { // Ensures its empty
			bottom = null;
		}
	}
	
	public void display() {
		if (top == null) { // Check if empty
			System.out.println("The queue is empty, ending process.");
			return;
		}
		Task t = top;
		System.out.println("\tTo Do");
		while(t != null) {
			System.out.println(t.priority + "): " + t.description );
			t = t.next;
		}
	}
	
	public void saveTasks() {
		try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
			Task t = top;
			while (t!= null) {
				writer.println(t.toString());
				t = t.next;
			}
		} catch (IOException e) {
			System.out.println("File unreadable. Skipping.");
		}
	}
	
	public void loadTasks() {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("Read file doesn't exist. Skipping data import.");
			return;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String l = reader.readLine();
			while (l != null){
				String[] words = l.split("-");
				if(words.length == 2) {
					enqueue(words[1], Integer.parseInt(words[0]));
				}
				l = reader.readLine();
			}
		} catch (IOException e) {
			System.out.println("File unwritable. Skipping.");
		}
	}
	
}

class Task {
	String description;
	int priority;
	Task next;
	
	public Task(String d, int p) {
		description = d;
		priority = p;
		next = null;
	}
	
	public void setNext(Task t) {
		next = t;
	}
	
	public String toString() {
		String s;
		s = priority + "-" + description;
		return s;
	}
}
