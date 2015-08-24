package local.maxdevelopment.java;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.FileReader;


import java.sql.PreparedStatement;

import com.opencsv.CSVReader;


public class CSVLoaderToDbase {
	
	private Connection dbConnection;
	private char separator;		//Default separator is comma
	private char quoteCharacter;//Default quote character
	private int startLine;		//Start reading from line
	
	public CSVLoaderToDbase(Connection dbConnection, char separator, char quoteCharacter, int startLine) {
		this.dbConnection = dbConnection;
		this.separator = separator;
		this.quoteCharacter = quoteCharacter;
		this.startLine = startLine;
	}

	public void loadCSV(String csvFile, String tableName) throws Exception {
		
		try {
			CSVReader reader = new CSVReader(new FileReader(csvFile), separator, quoteCharacter, startLine);
			String query = "INSERT INTO Customer(name, surname, login, email, phone) VALUES (?, ?, ?, ?, ?)";
			try {
				PreparedStatement insertStatement = null;
				insertStatement = dbConnection.prepareStatement(query);
				
				String[] nextLine;
				while ((nextLine = reader.readNext()) != null) {
					for(int i = 0, j = 1 ; i < nextLine.length; i++) {
						insertStatement.setString(j++, nextLine[i]);
					}
					insertStatement.execute();
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
