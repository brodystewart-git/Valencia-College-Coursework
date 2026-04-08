package cop2805;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FinalProjectClient extends JFrame{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FinalProjectClient newWindow = new FinalProjectClient(); //Got rid of construct gui, seemed pointless to have a function for a single line.
			}
		});
	}
	
	public FinalProjectClient() {
		super();
		init();
	}
	
	
	public void init() {
		JFrame frame = new JFrame("Client To Server Encryption Service");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //JFrame created

        JButton selectFileButton = new JButton("Select File");
        JButton decipherButton = new JButton("Encrypt");
        JTextArea encryptedText = new JTextArea(10, 40);
        JTextArea decryptedText = new JTextArea(10, 40);
        decryptedText.setEditable(false);
        JScrollPane scrollEncrypted = new JScrollPane(encryptedText);
        JScrollPane scrollDecrypted = new JScrollPane(decryptedText);
        // All Objects Created
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); 
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JPanel row1 = new JPanel(new BorderLayout(10, 0));
        row1.add(selectFileButton, BorderLayout.WEST);
        row1.add(scrollEncrypted, BorderLayout.CENTER);
        panel.add(row1);
        panel.add(new JSeparator(SwingConstants.HORIZONTAL)); 
        JPanel row3 = new JPanel(new BorderLayout(10, 0));
        row3.add(decipherButton, BorderLayout.WEST);
        row3.add(scrollDecrypted, BorderLayout.CENTER);
        panel.add(row3);
        panel.add(new JSeparator(SwingConstants.HORIZONTAL));
        frame.add(panel);
        // Added and set layout of items
        
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);

        selectFileButton.addActionListener(e -> {            // File Button Access Listener
        	JFileChooser fileChooser = new JFileChooser();	
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt"); // Ensure its a text file.
            
            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                
                try {
                    String content = Files.readString(selectedFile.toPath(), StandardCharsets.UTF_8);
                    encryptedText.setText(content);
                    decryptedText.setText("");
                    
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, 
                        "Error reading file: " + ex.getMessage(), 
                        "File Read Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        decipherButton.addActionListener(e -> { // Listens for deciper button being pressed
            String cipherText = encryptedText.getText();
            String plainText = connectionHandler((cipherText)); //Sends to server and returns deciphered text
            decryptedText.setText(plainText); //Sets the answer
        });
        //Action Listeners Complete
        
        decryptedText.setText("Please enter in the unencrypted text area or use the button on the left to select a file.");
	}
	
	public static String connectionHandler(String msg) {
		String response = "Something went wrong.";
		if (msg.isEmpty())
			return response; // End the code if there's an empty string
		try {
			Socket socket = new Socket("127.0.0.1", 1294); //127.0.0.1 is default for local machine IP, using socket 1236 to match sever socket.
			
			PrintWriter pr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			pr.println(msg);
			pr.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			response = br.readLine();
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return response;
		}
		return response;
	}
}
