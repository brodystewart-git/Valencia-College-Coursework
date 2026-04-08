package cop2805;
import java.sql.*;

public class Homework7_BrodyStewart {
	public static void main(String[] args) {
		Connection connection;
		try {
		     try {
		          Class.forName("com.mysql.cj.jdbc.Driver");
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }
		    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cop2805","root","Brodystew99!");
		    Statement statement = connection.createStatement();
		    ResultSet results = statement.executeQuery("SELECT * FROM users");
		    int count = 1;
		    while (results.next()){
		    	String customerFirst = results.getString("name");
		    	String customerLast = results.getString("last_name");
		    	System.out.println(count + ": " + customerLast + "," + customerFirst);
		    	count++;
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}

}

