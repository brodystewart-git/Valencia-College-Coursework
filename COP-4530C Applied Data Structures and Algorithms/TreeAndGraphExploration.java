import java.util.*;

public class TreesAndGraphs {
	
	public static void main(String args[]) {
		//Children
		Person child = new Person("Mario Mario");
		
		//Parent
		Person parent = new Person("Mama Mia Mario");
		parent.addChild(child);
		
		// Grandparent
		Person grandparent = new Person("Grandpapa Pio Mario");
		grandparent.addChild(parent);
		
		// Friend
		Person friend = new Person("Yoshi");
		makeFriends(friend, child);
		
		// Ancestor Test
		System.out.println("Ancestor test: Yoshi to Grandpapa Pio Mario");
		System.out.println(isAncestor(friend, grandparent));
		System.out.println("Ancestor test: Mario to Grandpapa Pio Mario");
		System.out.println(isAncestor(child, grandparent));
		
		// Can Reach Test
		System.out.println("Can Reach test: Yoshi to Grandpapa Pio Mario");
		System.out.println(can_reach(friend, grandparent));
		System.out.println("Can Reach test: Mama Mia Mario to Yoshi");
		System.out.println(can_reach(parent, friend));
	}
	
	public static boolean isAncestor(Person person, Person ancestor) {
		// Make sure they're not the same person.
		if(person.getName().equals(ancestor.getName()))
			return false;
		
		// Check if the ancestor is the parent
		for(Person kid: ancestor.children) {
			if (kid.getName().equals(person.getName())) {
				return true;
			}
			// If not the parent, check their kid's children.
			if(isAncestor(person, kid)) {
				return true;
			}
		}
		return false;
	}
	
	public static void makeFriends(Person a, Person b) {
		a.addFriend(b);
		b.addFriend(a);
	}
	
	public static boolean can_reach(Person start, Person target) {
		// Make sure they're not the same person.
		if(start.getName().equals(target.getName())) return true; 
		//Make sure it's not an ancestor
		if(isAncestor(start, target)) return true;
		
		Queue<Person> queue = new LinkedList<>();
		Set<Person> checked = new HashSet<>();
		queue.add(start);
		checked.add(start);
		
		while(!queue.isEmpty()) {
			Person t = queue.poll();
			ArrayList<Person> people = new ArrayList<>();
			
			people.addAll(t.children);
			people.addAll(t.friends);

	        for (Person p : people) {
	            if (p == target || isAncestor(p, target)) {
	                return true;
	            }
	            if (!checked.contains(p)) {
	            	checked.add(p);
	                queue.add(p);
	            }
	        }
	    }

	    return false;
	}
}

class Person{
	String name;
	ArrayList<Person> children;
	ArrayList<Person> friends;
	
	public Person(String personName) {
		name = personName;
		this.children = new ArrayList<>();
		this.friends = new ArrayList<>();
	}
	
	public void addChild(Person kid) {
		children.add(kid);
	}
	
	public void addFriend(Person friend) {
		friends.add(friend);
	}
	
	public String getName() {
		return name;
	}
}
