package local.maxdevelopment.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainJava {
	
	private static Connection getDBConnection() {
		
		String user = "root";			//DB user login
	    String password = "blackjack";	//DB user password
	    String url = "jdbc:mysql://localhost:3306/CSVBase";	//DB URL
	    String driver = "com.mysql.jdbc.Driver";			//DB driver
	    
	    Connection dbConnection = null;
	    
	    try {
	        Class.forName(driver);
	    } catch (ClassNotFoundException e) {
	        System.out.println(e.getMessage());
	    }
	    try {
	        dbConnection = DriverManager.getConnection(url, user, password);
	        return dbConnection;
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return dbConnection;
	}
	
	private static void createDbUserTable() throws SQLException {
		Connection dbConnection = null;
	    Statement statement = null;
	    
	    String createTableSQL = "CREATE TABLE Customer("
	            + "name VARCHAR(30) NOT NULL, "
	            + "surname VARCHAR(30) NOT NULL, "
	            + "login VARCHAR(30) NOT NULL, "
	            + "email VARCHAR(30) NOT NULL, "
	            + "phone VARCHAR(30) NOT NULL "
	            + ")";
	    
	    try {
	    	dbConnection = getDBConnection();
	        statement = dbConnection.createStatement();
	        //make SQL query
	        statement.execute(createTableSQL);
	        System.out.println("Table created");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
	            statement.close();
	        }
	        if (dbConnection != null) {
	            dbConnection.close();
	        }
		}
	}

	private static void deleteDbUserTable() throws SQLException {
		Connection dbConnection = null;
	    Statement statement = null;
	    
	    String deleteTableSQL = "DROP TABLE Customer";
	    
	    try {
	    	dbConnection = getDBConnection();
	        statement = dbConnection.createStatement();
	        //delete table
	        statement.execute(deleteTableSQL);
	        System.out.println("Table deleted from DB");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
	            statement.close();
	        }
	        if (dbConnection != null) {
	            dbConnection.close();
	        }
		}
	}
	
	public static void main(String[] args) {
		try {
	        createDbUserTable();
	        deleteDbUserTable();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}

}
