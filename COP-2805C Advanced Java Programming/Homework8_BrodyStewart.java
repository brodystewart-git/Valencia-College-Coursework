package cop2805;

public class Homework8_BrodyStewart {
	
	public static void main (String args[]) {
		int n = 40; // Main Number
		RecursiveFibonacci recursiveThread = new RecursiveFibonacci(n);
		DynamicFibonacci dyanmicthread = new DynamicFibonacci(n);
		
		recursiveThread.start(); //Let's begin
		dyanmicthread.start();
	}
}

class RecursiveFibonacci extends Thread { // Fibonacci 1
	public int n = 0;
	
	public RecursiveFibonacci(int num) {
		this.n = num; 
	}
	
	public void run() {
        long start = System.currentTimeMillis(); //Get Start Time
        int result = fibonacci(n); // Run Sequence
        long end = System.currentTimeMillis(); //Get End Time
        // Output the result and End time - Start time
        System.out.println("Recursion Thread found the answer: " + result + " in " + (end - start) + "ms"); 
	}
	
	public int fibonacci(int n) {
		if (n == 0) return 0;
		if (n == 1) return 1;
		
		return fibonacci(n - 1) + fibonacci(n - 2); 
	}
}

class DynamicFibonacci extends Thread {  //Fibonacci 2
	public int n = 0;
	
	public DynamicFibonacci(int num) {
		this.n = num;
	}
	
	public void run() {
        long start = System.currentTimeMillis(); //Get Start Time
        int result = fibonacci(n); // Run Sequence
        long end = System.currentTimeMillis(); //Get End Time
     // Output the result and End time - Start time
        System.out.println("Dynamic Thread found the answer: " + result + " in " + (end - start) + "ms");
	}

	public int fibonacci(int n) {
		if (n <= 1) return n;
		int v1 = 0;
		int v2 = 1;
		int v3 = 0;
		for (int i = 2; i <= n; i++) {
			v3 = v1 +v2;
			v1 = v2;
			v2 = v3;
		}
		return v3;
	}
}