package cop2805;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Homework5_BrodyStewart extends JFrame {

	public JTextField firstNumberField;
	public JTextField secondNumberField;
	public JComboBox<String> dropdownBox;
	public JButton calculateButton;
	public JLabel resultLabel;
	
	public Homework5_BrodyStewart() {
		super();
		init();
	}
	
	private void init() {
		//Initialized frame window, setting grid and giving name.
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("Simple Calculator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(5,2));
		
		
		// Create contents of the frame, starting with the text fields.
		this.add(new JLabel("First Number:"));
		firstNumberField = new JTextField();
		this.add(firstNumberField);
		this.add(new JLabel("Second Number"));
		secondNumberField = new JTextField();
		this.add(secondNumberField);
		this.add(new JLabel("")); // Empty to make it look like the screenshots
		
		// Add the combo box operation field
	    String[] operations = {"Add", "Subtract", "Multiply", "Divide"};
	    dropdownBox = new JComboBox<String>(operations);
		this.add(dropdownBox);
		this.add(new JLabel("")); // Empty to make it look like the screenshots
		
		// Add the calculate button and its listener, along with the results label.
		calculateButton = new JButton("Calculate");
		this.add(calculateButton);
		calculateButton.addActionListener(new MyCalculateListener(this)); // Established listener link
		resultLabel = new JLabel("Result: ");
		this.add(resultLabel);
		
		// Set frame size and location. Chose what I thought looked best (middle of the screen)
		int frameWidth = 400;
		int frameHeight = 300;
		Dimension screenSize = 
				Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) screenSize.getWidth()/2, (int) screenSize.getHeight()/2 - frameHeight,
				frameWidth, frameHeight); 
		
		//Finished construction of window, set visible.
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Homework5_BrodyStewart newWindow = new Homework5_BrodyStewart(); //Got rid of construct gui, seemed pointless to have a function for a single line.
			}
		});
	}	
}

class MyCalculateListener implements ActionListener {
	Homework5_BrodyStewart myFrame;
	public MyCalculateListener(Homework5_BrodyStewart frame) {
		myFrame = frame;
	}
	
	public void actionPerformed(ActionEvent eventData) {
		// Get the fields
		double num1 = Double.parseDouble(myFrame.firstNumberField.getText());
		double num2 = Double.parseDouble(myFrame.secondNumberField.getText());
		double result = 0;
		
		// Check Operation then calculate. Used a switch because it felt faster to type (and probably to run). 
		switch ((String) myFrame.dropdownBox.getSelectedItem()) {
			case "Add":
				result = num1 + num2;
				break;
			case "Subtract":
				result = num1 - num2;
				break;
			case "Multiply":
				result = num1 * num2;
				break;
			case "Divide":
				result = num1 / num2;
				break;
		}
		
		JButton btn = (JButton) eventData.getSource();
		myFrame.resultLabel.setText("Result: " + result);
		
	}
}