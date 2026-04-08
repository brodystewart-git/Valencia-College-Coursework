
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class StacksAndQueues {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Testing Stack: palindrome test.");
		System.out.print("Please input a word to check if a palindrome: ");
		String userInput;
		userInput = scanner.nextLine();
		System.out.println("This word is a palindrome: " + isPalindrome(userInput));
		
		System.out.println("Starting up Queue Manager.....");
		queueManager queueMan = new queueManager();
		queueMenu(scanner, queueMan);
		System.out.println("Exiting System.");
	}
	
	public static void queueMenu(Scanner s, queueManager queueMan) {
        System.out.println("\n\n\n\tSupport Ticket System");
        System.out.println("1. Add Ticket");
        System.out.println("2. Process Ticket");
        System.out.println("3. View Queue");
        System.out.println("4. Exit");
        System.out.print("Enter choice (number): ");
        
        String choice;
        choice = s.nextLine();
        
        switch(choice) {
        	case "1":
        		  System.out.print("Customer Name: ");
                  String name = s.nextLine();

                  System.out.print("Computer Type: ");
                  String computer = s.nextLine();

                  System.out.print("Issue Description: ");
                  String issue = s.nextLine();
                  queueMan.addTicket(name, computer, issue);
        		queueMenu(s, queueMan);
        		break;
			case "2":
			    queueMan.processTicket();
			    queueMenu(s, queueMan);
			    break;
			case "3":
				queueMan.viewQueue();
				queueMenu(s, queueMan);
				break;
			case "4":
				s.close();
				return;
			default:
        		System.out.println("Incorrect input. Try again.");
        		queueMenu(s, queueMan);
        		break;
        }
	}
	
	public static boolean isPalindrome(String input) {
		String reversedString = "";
		Stack<Character> stack = new Stack<>();
		input = input.toLowerCase();
		for (int i = 0; i < input.length(); i++) {
			stack.push(input.charAt(i));
		}
		
		while (!stack.isEmpty()) {
			reversedString += stack.pop();
		}
		System.out.println("Reversed: " + reversedString);
		return input.equals(reversedString);
	}
}


class Ticket{
	String customerName;
	String computerType;
	String issueDescription;
	
	public Ticket(String name, String type, String desc) {
		customerName = name;
		computerType = type;
		issueDescription = desc;
	}	
	
	@Override
	public String toString() {
		return String.format("%-25s %-20s %s", customerName, computerType, issueDescription);
	}
}

class queueManager{
	Queue<Ticket> tickets = new LinkedList<>();
	
	public void addTicket(String name, String type, String desc) {
		Ticket t = new Ticket(name, type, desc);	
		tickets.add(t);
		System.out.println("Successfully added ticket.");
	}
	
	public void processTicket() {
		if(tickets.isEmpty()) {
			System.out.println("No tickets in system.");
			return;
		}
		System.out.println("Processing ticket...");
		Ticket t = tickets.poll();
		System.out.printf("%-25s %-20s %s%n", "Customer Name", "Computer", "Issue");
		System.out.println(t);
		System.out.println("Ticket processed.");
	}
	
	public void viewQueue() {
		if(tickets.isEmpty()) {
			System.out.println("No tickets in system.");
			return;
		}
		System.out.printf("%-25s %-20s %s%n", "Customer Name", "Computer", "Issue");
		for (Ticket t: tickets) {
			System.out.println(t);
		}
	}
}

