package local.maxdevelopment.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class MainJava {
	private static Connection dbConnection = null;
	private static Statement statement = null;
	
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
	    
	    String createTableSQL = "CREATE TABLE IF NOT EXISTS Customer("
	            + "name VARCHAR(50) NOT NULL, "
	            + "surname VARCHAR(50) NOT NULL, "
	            + "login VARCHAR(50) NOT NULL, "
	            + "email VARCHAR(50) NOT NULL, "
	            + "phone VARCHAR(50) NOT NULL "
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
			closeDbConnection();
		}
	}

	private static void deleteDbUserTable() throws SQLException {
	    
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
			closeDbConnection();
		}
	}
	
	private static void closeDbConnection() throws SQLException {
		try {
			if (statement != null) {
	            statement.close();
	        }
	        if (dbConnection != null) {
	            dbConnection.close();
	        }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	} 
	
	public static void main(String[] args) throws Exception {
		try {
	        createDbUserTable();
	        //deleteDbUserTable();
	        
	        CSVLoaderToDbase csvLoaderToDbase = new CSVLoaderToDbase(getDBConnection(), ',', '"', 1);
	        csvLoaderToDbase.loadCSV("users.csv", "CSVBase");
	        closeDbConnection();
	        
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}

}
