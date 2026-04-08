package cop2805;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FinalProjectServer {

	public static void main(String[] args) {
		boolean shutdown = false;
		
		try {
			ServerSocket server = new ServerSocket(1294); //sets up server
			while(!shutdown) { // while it isnt shutdown (shutdown = false), run
				System.out.println("Waiting for clients.....");
				Socket client = server.accept(); // Waits for a connection from a client to be made. Returns the Socket which the client connects to.
				System.out.println("Client received.....");
				
				BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream())); //Gets the input stream from the client
				String input = br.readLine(); // When a NEW LINE character is ending a string, it will take the input. 
				System.out.println("Encrypted message: " + input);
				if(input.contains("shutdown")) {
					System.out.println("User sent shutdown code.");
					shutdown = true;
				}
				
				String response = decrypt(input);
				PrintWriter pr = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); 
				pr.println(response);
				pr.flush(); // ALWAYS flush. Its basically like flushing a toilet, sending out the data to the socket.
				client.close(); //When done with the client, close its socket (close its connection)
			}
			server.close(); //Has to close when shutdown is true
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String decrypt(String msg) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < msg.length(); i++) {
			char c = msg.charAt(i);
			int modC = (int) c + 10;
			result.append((char)modC);
		}
		return result.toString();
	}
	
}
