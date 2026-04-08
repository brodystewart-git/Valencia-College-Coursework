package cop2805;

public class Homework6_BrodyStewart {  // TEST CLASS ( Want to keep the file clean by avoiding another unnecessary class and don't wanna change the name of the file.
	
	public static void main(String[] args) {
		Shape3D cube = new Cuboid(3.0, 6.7, 9.3);
		Shape3D cyl = new Cylinder(3.5, 11.3);
		System.out.println("Comparing the volume of a cuboid of with dimensions: [Width 3.0, Depth 6.7, Height 9.3]");
		System.out.println("To a Cylinder of Dimensions: [Radius 3.5, Height 11.3]\n");
		
		
		switch (cube.compareTo(cyl)){
			case -1:
				System.out.println("The Cylinder has a larger Volume.");
				break;
			case 0:
				System.out.println("They both have an equal volume.");
				break;
			case 1:
				System.out.println("The Cube has a larger Volume.");
				break;
		}
	}
	
}

// Shape3d Class
abstract class Shape3D implements Comparable<Shape3D>{
	public abstract double Volume();
	
	@Override 								// Override compareTo and set it to compare Volumes. Follows same return standards as standard compareTo.
	public int compareTo(Shape3D obj) {
		if (this.Volume() > obj.Volume()) {           // Greater than target = 1, less than target = -1 equal = 0
			return 1;
		}else if (this.Volume() < obj.Volume()) {
			return -1;
		}else
			return 0;
	}
}

// Cuboid Class
class Cuboid extends Shape3D{
	private double w;
	private double d;
	private double h;
	
	public Cuboid(double width, double depth, double height){
		w = width;
		d = depth;
		h =  height;
	}
	
	@Override
	public double Volume() {
		return w * d * h;		// width * depth * height
	}
}

// Cylinder Class
class Cylinder extends Shape3D{
	private double r;            // Radius
	private double h;            // Height
	
	public Cylinder(double radius, double height) {
		r = radius;
		h = height;
	}
	
	@Override
	public double Volume() {
		return Math.PI * r * r * h;   // Pi * radius^2 * height
	}
}
