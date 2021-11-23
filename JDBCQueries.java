/*
 * Author: Zachary Winans
 * Date: 11/1/2021
 * Assignment: Prog3
 * Purpose: The purpose of this program is to embed SQL within java by means of the JDBC API. We
 * 	    will demonstrate this by connecting with four tables created in oracle which represent
 * 	    school data in the Florida school system. We will use this data to answer three
 * 	    queries along with a query created by ourselves.
 *
 */

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Prog3 {

	static String oracleURL;
	static String username = null;
	static String password = null;

	public static void main(String[] args) {
		
		oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
		final String query1 = "SELECT count(*) from zwinans.spring17, zwinans.spring21 " +
				      "WHERE zwinans.spring17.snum=zwinans.spring21.snum " +
				      "AND zwinans.spring17.dno=zwinans.spring21.dno " +
				      "AND zwinans.spring21.sname != zwinans.spring17.sname";
		
		final String query2 = "SELECT zwinans.spring21.dname, zwinans.spring21.sname " +
				      "FROM zwinans.spring19, zwinans.spring21 " +
				      "WHERE zwinans.spring19.l1 is not null " +
				      "AND zwinans.spring21.l1 is not null " +
				      "AND zwinans.spring19.l2 is not null " +
				      "AND zwinans.spring21.l2 is not null " +
				      "AND zwinans.spring19.l3 is not null " +
				      "AND zwinans.spring21.l3 is not null " +
				      "AND zwinans.spring19.l4 is not null " +
				      "AND zwinans.spring21.l4 is not null " +
				      "AND zwinans.spring19.l5 is not null " +
				      "AND zwinans.spring21.l5 is not null " +
				      "AND zwinans.spring19.dno = zwinans.spring21.dno " +
				      "AND zwinans.spring19.snum = zwinans.spring21.snum " +
				      "AND zwinans.spring19.l1 < zwinans.spring21.l1 " +
				      "AND zwinans.spring19.l2 < zwinans.spring21.l2 " +
				      "AND zwinans.spring19.l4 > zwinans.spring21.l4 " +
				      "AND zwinans.spring19.l5 > zwinans.spring21.l5";


		if (args.length == 2) {
			username = args[0];
			password = args[1];
		} else {
			System.out.println("\nUsage: java Prog3 <username> <password>\n"
					+ "     where <username> is your Oracle DBMS"
					+ "  username,\n    and <password> is your Oracle"
					+ "  password.\n");
			System.exit(-1);
		}

		// load the JDBC driver
		
		try {

			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			
			System.err.println("*** ClassNotFoundException: "
					+ "Error loading the JDBC driver.  \n");
			System.exit(-1);

		}

		
		
		Connection login = null;

		try {
			login = DriverManager.getConnection
				(oracleURL,username,password);
			login.close();
		} catch (SQLException e) {
			System.err.println("*** SQLException: "
					+ "Could not open JDBC connection.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);

		}

		printMenu();	
		
		boolean flag = true;
		int count = 0;
		Scanner sc = new Scanner(System.in);	

		while (flag == true) {
			
			String query = queryUser(sc,count);
			
			if (query.equals("exit")) {
				sc.close();
				System.out.println("Exiting...");
				System.exit(0);

			}

			if (query.equals("a")) {
				System.out.println("***** QUERY: (a) *****");
				executeQueries(query1, 1);
				System.out.println("***** END QUERY: (a) *****\n");


			}
			
			if (query.equals("b")) {
				System.out.println("***** QUERY: (b) *****");
				executeQueries(query2, 2);
				System.out.println("***** END QUERY: (b) *****\n");
			}

			if (query.equals("c")) {

				System.out.println("***** QUERY: (c) *****");
    				System.out.println("Please enter a year: ");
    				String temp = sc.nextLine();
				while (!temp.equals("2017") && !temp.equals("2018") && !temp.equals("2019") && !temp.equals("2021")) {
					System.out.println("Please enter a valid year, the options are 2017, 2018, 2019, 2021: ");
					temp = sc.nextLine();	

				}

				int year = Integer.parseInt(temp);
				

				System.out.println("Please enter a district: ");
				String district = sc.nextLine();
				
				int queryCount = getQueryCount(year, district.toUpperCase(), 3);

				if (queryCount < 10) {
					System.out.println("I'm sorry, but " + district + " has just " + queryCount + " schools.\n");

				} else {
					String query3 = "SELECT snum, sname, l4, l5, sum(l4 + l5) as total" +
						" from ZWINANS.SPRING" + year%100 + " where dname='" + district.toUpperCase() + "'" +
						" AND rownum <= 5 and l4 is not null and l5 is not null" +
					        " group by snum, sname, l4, l5 order by sum(l4+l5) DESC";
					executeQueries(query3, 3);
						
					
				}
				System.out.println("***** END QUERY: (c) *****\n");

			}
				
			if (query.equals("d")) {
				System.out.println("***** QUERY: (d) *****");
				System.out.println("Please enter a district: ");				
				String district = sc.nextLine();
				
				int count19 = getQueryCount(2019, district.toUpperCase(), 4);
				int count21 = getQueryCount(2021, district.toUpperCase(), 4);
				
				if (count19 < 3 || count21 < 3) {
					
					System.out.println("I'm sorry, in order to get the top three schools\n" +
							"The school must have available their last two years\n" +
							"of data pertaining to the percentage of students in\n" +
							"level 3 or above and also have had a minimum\n" +
							"of three schools in the district both years\n" +
							"Please try another district with more schools\n");
				} else {
					
					String query4 =	"select zwinans.spring21.dname, zwinans.spring21.sname, (avg(zwinans.spring21.pctabv3) +" +
					        " avg(zwinans.spring19.pctabv3))/2 as average" +
 						" from zwinans.spring19, zwinans.spring21" +
 						" WHERE zwinans.spring21.dname=zwinans.spring19.dname" +
 						" AND zwinans.spring21.snum=zwinans.spring19.snum" +
 						" AND zwinans.spring21.dname='" + district.toUpperCase() + "'" +
						" AND zwinans.spring19.pctabv3 IS NOT NULL" +
						" AND zwinans.spring21.pctabv3 IS NOT NULL" +
 						" AND rownum <= 3 GROUP BY zwinans.spring21.dname," +
  						" zwinans.spring21.sname ORDER BY" +
						" (avg(zwinans.spring21.pctabv3) + avg(zwinans.spring19.pctabv3))/2 DESC";
					executeQueries(query4, 4);

				}

				System.out.println("***** END QUERY: (d) *****\n");	 

			}

			count++;

		}

	}


	/*
	 * PURPOSE: To get the count for the third query, which inquires about the number
	 * of schools in a district
	 * PARAMETERS:
	 * 	(integer) year - the year to get the count of
	 * 	(String)  district - the district to get the number of schools in
	 * 	(integer) queryNum - a number indicating which query to get a count for
	 */
	public static Integer getQueryCount(int year, String district, int querynum) {
		
		String yearNum = String.valueOf(year % 100);
		
		String query = "";
		
		if (querynum == 3) {
			query = "SELECT count(*) FROM ZWINANS.SPRING" + yearNum +
			" WHERE ZWINANS.SPRING" + yearNum + ".dname='" + district + "'";
		} 

		if (querynum == 4) {
			query = "SELECT count(*) FROM ZWINANS.SPRING" + yearNum +
			" WHERE ZWINANS.SPRING" + yearNum + ".dname='" + district + "'" +
			" AND PCTABV3 IS NOT NULL";
		}
				
		Statement stmt = null;
                ResultSet answer = null;


                Connection dbconn = null;

                try {
                        dbconn = DriverManager.getConnection
                                        (oracleURL,username,password);
                } catch (SQLException e) {

                        System.err.println("*** SQLException: "
                                        + "Could not open JDBC connection.");
                        System.err.println("\tMessage:   " + e.getMessage());
                        System.err.println("\tSQLState:  " + e.getSQLState());
                        System.err.println("\tErrorCode: " + e.getErrorCode());
                        System.exit(-1);

                }

               
		int count = 0;
	        try {	
                        stmt = dbconn.createStatement();
                        answer = stmt.executeQuery(query);

                        if (answer != null) {

                                   System.out.println();
				   if (answer.next()) {
                                         count = answer.getInt("count(*)");

                                   }
                        }

			dbconn.close();
			stmt.close();
	      	} catch (SQLException e) {

                        System.err.println("*** SQLException: "
                                        + "Could not fetch query results.");
                        System.err.println("\tMessage:   " + e.getMessage());
                        System.err.println("\tSQLState:  " + e.getSQLState());
                        System.err.println("\tErrorCode: " + e.getErrorCode());
                        System.exit(-1);

                }
		
		return count;

	}
		
	/*
	 * PURPOSE: To print the menu which represents each four of the query options
	 *
	 *
	 */
	public static void printMenu() {

		System.out.println("===================================\n");
                System.out.println("       WELCOME TO THE PROGRAM      \n");
                System.out.println("===================================\n");
                System.out.println("You have four options which represent\n" +
                                "four different queries, those queries are:\n\n" +
                                "(a) How many schools are listed with different school names in 2021 \n" +
                                "than there were listed in 2017? To be counted, a school must appear \n" +
                                "in both years.\n");
                System.out.println("(b) Considering only the years 2019 and 2021, and only the\n" +
                                "schools for which level percentage data is available for both\n" +
                                "tears, what are the district names and school names of the\n" +
                                "schools that reported both decreases in percentages in both\n" +
                                "Levels 4 & 5 and increases in percentages in both Levels 1 &\n" +
                                "2 in 2021 as compared to 2019?\n");
                System.out.println("(c) For a year and a case-insensitive district name given\n" +
                                "by the user, if the district has at least 10 schools in the\n" +
                                "schools in the report, list in descending order the five\n" +
                                "schools with the highest combined level 4 and 5 percentages\n");
                System.out.println("(d) For a case-insensitive district name given by the user\n" +
				"Find the top 3 schools over the last two years (2019 and 2021)\n" +
				"according to the averages of the number of students in the\n" + 
				"percentage above level 3. If one of the districts, in either year,\n" +  
				"did not have at least three schools in it, OR the district did not\n" +
				"have enough available data, do not count it.\n");

	}

	
	/*
	 * PURPOSE: To query the user and return either a, b, c, d, or exit
	 * PARAMETERS: 
	 * 	(scanner) sc - the scanner used to obtain user input
	 *	(int)  count - the integer representing the number of times the user
	 *		       has selected options
	 */
	public static String queryUser(Scanner sc, int count) {


		if (count == 0) {
			System.out.println("Please enter \"exit\" to close the program, otherwise, please enter your selection: ");
		} else {
			System.out.println("If you'd like to close the program, enter \"exit\"." + 
					"\nTo make another selection, please enter a, b, c, or d: ");

		}
		String query = sc.nextLine();

		while (query != null) {
			count++;
			if (query.toLowerCase().equals("exit") ||
					query.toLowerCase().equals("a") ||
					query.toLowerCase().equals("b") ||
					query.toLowerCase().equals("c") ||
					query.toLowerCase().equals("d")) {
				
				return query.toLowerCase();	
			} else {
				System.out.println("Please enter a, b, c, d, or exit: ");
                                query = sc.nextLine();
			}

		}

		return "exit";	
			
	}

	/*
	 * PURPOSE: To execute each of the four queries by using a database connection
	 * 	    that connects to the oracle databases
	 * PARAMETERS:
	 * 	(String) query - the query to send to oracle
	 * 	(int) querynum - numbers representing each query option
	 *
	 */
	public static void executeQueries(String query, int queryNum) {
		
		Statement stmt = null;
		ResultSet answer = null;


		Connection dbconn = null;

		try {
			dbconn = DriverManager.getConnection
					(oracleURL,username,password);
		} catch (SQLException e) {
			
			System.err.println("*** SQLException: "
					+ "Could not open JDBC connection.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);

		}

		try {
			stmt = dbconn.createStatement();
			answer = stmt.executeQuery(query);
		
			if (answer != null) {
				if (queryNum == 1) {
					System.out.println("\nThe results of query (a) are: \n\n");
			
					ResultSetMetaData answermetadata = answer.getMetaData();
					
					for (int i = 1; i <= answermetadata.getColumnCount(); i++) {
						System.out.print("        Count" + "\t");

					}

					System.out.println("\n____________________");
					while (answer.next()) {
						System.out.println("         " + answer.getInt("count(*)"));

					}
					System.out.println();
					System.out.println("____________________\n");

				} else if (queryNum == 2) {
					
					System.out.println("\nThe results of query (b) are: \n\n");					
					ResultSetMetaData answermetadata = answer.getMetaData();

					for (int i = 1; i <= answermetadata.getColumnCount(); i++) {
						System.out.print("     " + answermetadata.getColumnName(i) + 
							"               " + "\t");		

					}
					System.out.println("\n _____________________________________________________________");

					while (answer.next()) {
						System.out.println(String.format("|%-15s|" , answer.getString("dname")) +
						String.format("%-45s|", answer.getString("sname")));
					}
					System.out.println(" _____________________________________________________________\n");
					System.out.println();
				
				} else if (queryNum == 3) {
					
					System.out.println("\nThe results of query (c) are: \n\n");

					ResultSetMetaData answermetadata = answer.getMetaData();


					System.out.print("    " + answermetadata.getColumnName(1) +
							"                        " + answermetadata.getColumnName(2) + 
							"                       " +
							answermetadata.getColumnName(3) + 
							"     " + answermetadata.getColumnName(4) + 
							"    " +
							"Sum4and5");


                                        System.out.println("\n _________________________________________________________________________________");

                                        while (answer.next()) {
                                                System.out.println(String.format("|%-10s|" , answer.getString("snum")) +
                                                String.format("%-45s|", answer.getString("sname")) +
						String.format("%-6d|", answer.getInt("l4")) +
						String.format("%-6d|", answer.getInt("l5")) +
						String.format("%-10d|", answer.getInt("total")));
                                        }
                                        System.out.println(" _________________________________________________________________________________\n");
					

				 } else if (queryNum == 4) {
					System.out.println("\nThe results of query (d) are: \n\n");
					
					ResultSetMetaData answermetadata = answer.getMetaData();

					System.out.print("           " + answermetadata.getColumnName(1) +
							"                             " + answermetadata.getColumnName(2) +
							"                             " + answermetadata.getColumnName(3));

					System.out.println("\n ____________________________________________________________________________________________");

					while (answer.next()) {
						
						String avg = String.valueOf(answer.getDouble("average"));

						System.out.println(String.format("|%-25s|", answer.getString("dname")) + 
								String.format("%-45s|", answer.getString("sname")) +
								String.format("%-20s|", avg));

					}

					System.out.println(" ____________________________________________________________________________________________\n");

				 }	 
			
			stmt.close();
			dbconn.close();
			}
		} catch (SQLException e) {
			
			System.err.println("*** SQLException: "
					+ "Could not fetch query results.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);

		}
	}

}

