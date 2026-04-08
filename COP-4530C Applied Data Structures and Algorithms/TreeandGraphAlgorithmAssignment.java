import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TreeandGraphAlgorithmAssignment {
	
	public static void main(String[] args) {
		Room entrance = new Room("Entrance");
		Room defiledChapel = new Room("Defiled Chapel");
		Room treasureRoom = new Room("Treasure Room");
		Room mausoleum = new Room("Mausoleum");
		Room hallway = new Room("Hallway");
		Room silentLibrary = new Room("Silent Library");
		Room armory = new Room("Armory");
		Room abandonedApothecary = new Room("Abandoned Apothecary");
		Room guardPost = new Room("Guard Post");
		Room tortureChamber = new Room("Torture Chamber");
		Room beastPen = new Room("Beast Pen");
		Room hallOfStatues = new Room("Hall of Statues");
		Room bossRoom = new Room("Boss Room");
		
		entrance.addRoom(defiledChapel);
		defiledChapel.addRoom(treasureRoom);
		defiledChapel.addRoom(mausoleum);
		entrance.addRoom(hallway);
		hallway.addRoom(silentLibrary);
		silentLibrary.addRoom(armory);
		hallway.addRoom(abandonedApothecary);
		abandonedApothecary.addRoom(guardPost);
		abandonedApothecary.addRoom(tortureChamber);
		tortureChamber.addRoom(beastPen);
		tortureChamber.addRoom(hallOfStatues);
		hallOfStatues.addRoom(bossRoom);
		
		ArrayList<Room> visited = new ArrayList<>();
		System.out.println("Depth-First Search:");
		DFS(entrance, visited, 0);
		
		addPortal(treasureRoom, hallOfStatues);
		addPortal(armory, beastPen);
		System.out.println("\nBreadth-First Search: (entrance -> boss room)");
		findShortestExit(entrance, bossRoom);
	}

	
	public static void DFS(Room current, ArrayList<Room> visited, int level) {
		if(current == null || visited.contains(current))
			return;
		
		current.print(level);
		visited.add(current);
		
		for(Room next : current.connections) {
			DFS(next, visited, level + 1);
		}
	}
	
	// Part 2 Ahead
	
	public static void addPortal(Room a, Room b) {
		a.addRoom(b);
		b.addRoom(a);
	}
	
	public static void findShortestExit(Room currentRoom, Room exitRoom) {
		Queue<Room> queue = new LinkedList<>();
	    HashSet<Room> visited = new HashSet<>();
	    Map<Room, Room> path = new HashMap<>();
	    
	    queue.add(currentRoom);
	    visited.add(currentRoom);
	    path.put(currentRoom, null);
	    int distance = 0;
	    
	    while(!queue.isEmpty()){
	    	int size = queue.size();
	    	
	        for (int i = 0; i < size; i++) {
	            Room room = queue.poll();
		    	if(room == exitRoom) {
		    		System.out.println("Shortest Distance: " + distance);
		    		printPath(path, exitRoom);
		    		return;
		    	}
		    	
		    	for(Room next : room.connections) {
		    		if(!visited.contains(next)) {
		    			queue.add(next);
		    			visited.add(next);
		    			path.put(next,  room);
		    		}
		    	}
	        }
	        distance++;
	    }
	}
	
	public static void printPath(Map<Room, Room> map, Room goal) {
		ArrayList<Room> path = new ArrayList<>();
		
		Room room = goal;
		
		while(room != null) {
			path.add(room);
			room = map.get(room);
		}
		Collections.reverse(path);
		for(int i = 0;  i < path.size(); i++) {
			path.get(i).print(i);
		}
	}
}

class Room{
	String name;
	ArrayList<Room> connections;
	
	public Room(String name) {
		this.name = name;
		this.connections = new ArrayList<>(); // can only be two in final version?
	}
	
	public void addRoom(Room room) {
		this.connections.add(room);
	}
	public void print(int level) {
		String indent = " ".repeat(level * 2);
		System.out.println(indent + "└── " + name + "(Level " + level + ")");
	}
}




