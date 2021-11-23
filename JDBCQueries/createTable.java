/*
 * Author: Zachary Winans
 * Class: CSC460
 * Assignments - Program 3
 * Purpose: The purpose of this program is to create the tables that we will be using
 * 	    to answer queries using JDBC. I did this by hard coding table name values
 * 	    which I know I could have done all four tables at once but instead I did one
 * 	    at a time. 
 */

import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.Normalizer;

public class createTable {

	public static void main(String[] args) throws IOException {
		
		final String oracleURL = // Magic lectura -> aloe access spell
			"jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

		String username = "zwinans", // Oracle username
		       password = "a3254"; // Oracle password


		// load the (Oracle) JDBC driver

		try {

			Class.forName("oracle.jdbc.OracleDriver");
			
		} catch (ClassNotFoundException e) {
			
			System.err.println("*** ClassNotFoundException:  "
					+ "Error loading Oracle JDBC driver. \n"
					+ "\tPerhaps the driver is not on the CLASSPATH?");
			System.exit(-1);

		}


		// make and return a database connection to the user's Oracle DB

		Connection dbconn = null;

		try {
			dbconn = DriverManager.getConnection(oracleURL,username,password);
		
		} catch (SQLException e) {

			System.err.println("*** SQLException:  "
					+ "Could not open JDBC connection.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);
		}
			
		// open csv here and make table
		String filename = "";		
		String fname = "";

		if (args.length != 1) {
			System.err.println("*** PLEASE ENTER A FILENAME ***");
			System.exit(-1);
		
		} else {
			fname = args[0];
			filename = "/home/zwinans/CSC460/Prog3/" + fname;
					
		}

		BufferedReader br = new BufferedReader(new FileReader(filename));
		if (br.readLine() == null) {
			System.err.println("The CSV file you entered is empty");
			System.exit(-1);
		}

		int maxDname = 0;
		int maxSname = 0;

		System.out.println(filename);

		ArrayList<String> dbList = new ArrayList<String>();

		try {
			buildDbList(dbList, filename);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		ArrayList<String[]> parsedDbList = parseDbList(dbList);

		String[][] array = createArray(parsedDbList);

		maxDname = getLongestString(array, 1);
		maxSname = getLongestString(array, 3);

		// at this point, array is good to add

		Statement stmt = null;
		Statement updt = null;
		ResultSet answer = null;

		try {
			stmt = dbconn.createStatement();

			String create = "CREATE TABLE " + "SPRING21(" + 
				"year INTEGER, " +
				"dno INTEGER, " +
				"dname VARCHAR(" + maxDname + "), " +
				"snum INTEGER, " +
				"sname VARCHAR(" + maxSname + "), " +
				"numStudents INTEGER, " +
				"meanScore INTEGER, " +
				"pctAbv3 INTEGER, " +
				"l1 INTEGER, " +
				"l2 INTEGER, " +
				"l3 INTEGER, " +
				"l4 INTEGER, " +
				"l5 INTEGER )";
			answer = stmt.executeQuery(create);
			
			for (int i = 0; i < array.length; i++) {
				updt = dbconn.createStatement();
				String command = "insert into spring21 values ( " + 2021 + ", " +
					array[i][0] + ", '" + array[i][1] + "' , " + array[i][2] + ", '" +
					array[i][3] + "' , " + array[i][4] + ", " + array[i][5] + ", " +
					array[i][6] + ", " + array[i][7] + ", " + array[i][8] + ", " +
					array[i][9] + ", " + array[i][10] + ", " + array[i][11] + ")";
				updt.executeUpdate(command);
				updt.close();

			}
			
			stmt.close();
			dbconn.close();
		       		       
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("CREATED");



		
	
	}


	/*
	 * Purpose: to create a string[][] array holding all of the csv values
	 * Parameters:
	 *	parsedDbList - a cleaned db list to take elements from and move into the string[][] array
	 */
	private static String[][] createArray(ArrayList<String[]> parsedDbList) {
		String[][] array = new String[parsedDbList.size()][];

		for (int i = 0; i < parsedDbList.size(); i++) {
			array[i] = parsedDbList.get(i);

		}

		return array;

	}


	/*
	 * Purpose: To get the longest string in the given field
	 * Parameters:
	 *	array - a string[][] array to iterate through
	 *	element - an integer representing the field to get
	 */
	private static int getLongestString(String[][] array, int element) {
		int max = 0;

		for (int i = 0; i < array.length; i++) {
			if (array[i][element].length() > max) {
				max = array[i][element].length();
			}
		}
		return max;

	}


	/*
	 * Purpose: To iterate through the arraylist and replace elements with asterisks with null
	 * Parameters:
	 *	dbList - the arraylist to iterate through
	 */
	private static ArrayList<String[]> parseDbList(ArrayList<String> dbList) {
	
		ArrayList<String[]> parsed = new ArrayList<String[]>();
		
		for (int j = 0; j < dbList.size(); j++) {
			
			String[] split = dbList.get(j).split(",");

			for (int i = 0; i < split.length; i++) {
			
				if (split[i].equals("*")) {
					split[i] = null;
				}


			}

			if (split.length == 12) {
				parsed.add(split);
			}


		}
		System.out.println(parsed.size() + " parsed");
		return parsed;
	}


	/*
	 * Purpose: To build an arraylist from the CSV file  
	 * Parameters:
	 *	dbList - an arraylist to populate with entries
	 *	filename - the filename to read from
	 */
	private static void buildDbList(ArrayList<String> dbList, String filename) throws FileNotFoundException {
		
		try (Scanner scanner = new Scanner(new File(filename));) {
			while (scanner.hasNextLine()) {
				dbList.add(scanner.nextLine());
			}

			scanner.close();

		}


	}

}
