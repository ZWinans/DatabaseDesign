/*
 * Authors: Zach Winnans, Brandon Smith, Parker Webb, Cal Dugger
 * Class: CSC460
 * Semester: Fall 21
 * Assignment: Prog4
 * Purpose: create a text based interface to interact with a DB simulating AZ MVD
 */

// import java.io.*;
// import java.sql.*;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;

public class Prog4
{
	public static MYJDBC myjdbc;

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		myjdbc = new MYJDBC("blsmith86", "a8709"); // NEED TO CHANGE TO COMMAND-LINE ARGUMENT

                if(args.length == 2) {
                        myjdbc = new MYJDBC(args[0], args[1]);
                } else {
                        System.out.println("Error: Invalid username or password entered."); 
                        System.exit(-1);                                                   
                }
            

        	printMainMenu();
		handleMainMenuSelection(sc);

		sc.close();
			
	}

	public static void printMainMenu()
	{
		System.out.println(" ===================================\n" +
				           "|       WELCOME TO THE PROGRAM      |\n" +
				           " ===================================\n");
		
		System.out.println("You have several options, would you like to:\n" +
				   "(a) Insert a new data record\n" +
				   "(b) Delete a data record\n" +
				   "(c) Update an existing data record\n" +
				   "(d) Print a specific table\n" +
				   "(e) Get query answers\n");
	}

	 /*
         * Purpose: convert user input into valid table name
         */
        public static String convertTableName(String table){
                table = String.join("_",table.split(" ")); // replaces spaces with underscores
                return(table.toLowerCase());
        }

	
	/*
	 * Purpose: check if valid table is selected
         * Parameters: table - name of table to delete from
	 */
	public static boolean checkValidTable(String table){
		String [] validTables = {"vehicle", "vehicle_registration", "license_plate",
			"customer", "state_id", "license", "employee", "department", "test",
			"transaction", "appointment"};
		return(Arrays.asList(validTables).contains(convertTableName(table)));
	}

        /*
        * Purpose: To handle deletion logic including getting the primary key to delete
        * Parameters: 
        *       sc - scanner used to collect input
        *       table - name of table to delete from
        */
        public static void deleteFromTable(Scanner sc, String table){
                table = convertTableName(table);
                
                if (table.equals("employee")) {
                        System.out.println("Please enter the employee ID of the row you would like to delete\n");
                        String empId = sc.nextLine();
                        myjdbc.deleteRow(table, "string", empId, 0, "empId");

                } else if (table.equals("transaction")) {
                        System.out.println("Please enter the transaction ID of the row you would like to delete\n");
                        String xactId = sc.nextLine();
                        myjdbc.deleteRow(table, "string", xactId, 0, "xactId");

                } else if (table.equals("appointment")) {
                        System.out.println("Please enter the appointment ID of the row you would like to delete\n");
                        String apptId = sc.nextLine();
                        myjdbc.deleteRow(table, "string", apptId, 0, "apptId");

                } else if (table.equals("license")) {
                        System.out.println("Please enter the driver ID of the row you would like to delete\n");
                        String driverId = sc.nextLine();
                        myjdbc.deleteRow(table, "string", driverId, 0, "driverId");

                } else if (table.equals("state_id")) {
                        System.out.println("Please enter the ID number of the row you would like to delete\n");
                        String idNum = sc.nextLine();
                        myjdbc.deleteRow(table, "string", idNum, 0, "idNum");

                } else if (table.equals("customer")) {
                        System.out.println("Please enter the customer ID of the row you would like to delete\n");
                        int custId = sc.nextInt();
                        myjdbc.deleteRow(table, "int", "none", custId, "customerId");

                } else if (table.equals("vehicle")) {
                        System.out.println("Please enter the vin number of the row you would like to delete\n");
                        String vinNo = sc.nextLine();
                        myjdbc.deleteRow(table, "string", vinNo, 0, "vinNo");

                } else if (table.equals("license_plate")) {
                        System.out.println("Please enter the plate number of the row you would like to delete\n");
                        String plateNo = sc.nextLine();
                        myjdbc.deleteRow(table, "string", plateNo, 0, "plateNum");

                } else {
                        System.out.println("ERROR: You are unable to delete from that table");
                }

        }

	/*
         * Purpose: print given table
         * Parameters: table is the name of table to be printed
         */
        public static void printTable(String table){
                myjdbc.printTable(convertTableName(table));
        }

        /*
        * Purpose: collects input and calls jdbc to insert into the appropriate table
        * Parameters: sc: scanner used to collect input
        *             table: name of table to insert into
        */
	public static void insertToTable(Scanner sc, String table){
		table = convertTableName(table);
		ArrayList<String> list = new ArrayList<String>();
		if(table.equals("vehicle_registration")){
			System.out.println("VIN: ");
			list.add(sc.nextLine());
			System.out.println("Customer Id: ");
                        list.add(sc.nextLine());
			System.out.println("Licence Plate: ");
                        list.add(sc.nextLine());
			System.out.println("State: ");
                        list.add(sc.nextLine());
			System.out.println("Issue Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
			System.out.println("Expiration Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
			myjdbc.insertVehicleReg(list, table);
		} else if(table.equals("customer")){
			System.out.println("Customer Id: ");
                        list.add(sc.nextLine());
                        System.out.println("First Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Last Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Middle Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Social Security Number: ");
                        list.add(sc.nextLine());
                        System.out.println("Address: ");
                        list.add(sc.nextLine());
			System.out.println("State: ");
                        list.add(sc.nextLine());
                        System.out.println("Zip Code: ");
                        list.add(sc.nextLine());
                        System.out.println("Date of Birth MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Sex: ");
                        list.add(sc.nextLine());
                        System.out.println("Height: ");
                        list.add(sc.nextLine());
                        System.out.println("Eye Color: ");
                        list.add(sc.nextLine());
			System.out.println("Hair Color: ");
                        list.add(sc.nextLine());
                        System.out.println("Weight: ");
                        list.add(sc.nextLine());
			myjdbc.insertCustomer(list, table);
		} else if(table.equals("employee")){
                        System.out.println("Employee ID: ");
                        list.add(sc.nextLine());
                        System.out.println("Department ID: ");
                        list.add(sc.nextLine());
                        System.out.println("First Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Last Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Middle Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Date of Birth MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Address: ");
                        list.add(sc.nextLine());
                        System.out.println("State: ");
                        list.add(sc.nextLine());
                        System.out.println("Sex: ");
                        list.add(sc.nextLine());
			System.out.println("Social Security Number: ");
                        list.add(sc.nextLine());
                        System.out.println("Supervisor Social Security Number: ");
                        list.add(sc.nextLine());
                        System.out.println("Job Title: ");
			myjdbc.insertEmployee(list, table);
		} else if(table.equals("appointment")){
                        System.out.println("Appointment ID: ");
                        list.add(sc.nextLine());
                        System.out.println("Employee ID: ");
                        String empId = sc.nextLine();
                        list.add(empId);
                        System.out.println("Customer ID: ");
                        list.add(sc.nextLine());
                        System.out.println("Appointment Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Start Time: ");
                        String time = sc.nextLine();
                        int count = myjdbc.totalCount(table, "startTime", time, -1, empId);
                        while (count != 0) {
                                System.out.println("There is already an appointment at that time, please try another time\n");
                                time = sc.nextLine();
                                count = myjdbc.totalCount(table, "startTime", time, -1, empId);
                        }
                        list.add(time);
                        System.out.println("Type of Appointment: ");
                        list.add(sc.nextLine());
                        System.out.println("Status: ");
                        list.add(sc.nextLine());
                        myjdbc.insertAppt(list, table);
                } else if(table.equals("license_plate")){
                        System.out.println("VIN: ");
                        list.add(sc.nextLine());
                        System.out.println("License Plate: ");
                        list.add(sc.nextLine());
                        System.out.println("Customer ID: ");
                        list.add(sc.nextLine());
                        System.out.println("State: ");
                        list.add(sc.nextLine());
                        System.out.println("Issue Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Expiration Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        myjdbc.insertLicensePlate(list, table);
                } else if(table.equals("state_id")){
                        System.out.println("ID Number: ");
                        list.add(sc.nextLine());
                        System.out.println("Customer ID: ");
                        list.add(sc.nextLine());
                        System.out.println("Issue Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Expiration Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        myjdbc.insertStateID(list, table);
                } else if(table.equals("license")){
                        System.out.println("Driver ID: ");
                        list.add(sc.nextLine());
                        System.out.println("Customer ID: ");
                        list.add(sc.nextLine());
                        System.out.println("State: ");
                        list.add(sc.nextLine());
                        System.out.println("Issue Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Expiration Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
			System.out.println("ID Type: ");
                        list.add(sc.nextLine());
                        myjdbc.insertLicense(list, table);
                } else if(table.equals("test")){
			System.out.println("Driver ID: ");
                        list.add(sc.nextLine());
                        System.out.println("Customer ID: ");
                        list.add(sc.nextLine());
                        System.out.println("Score: ");
                        list.add(sc.nextLine());
                        System.out.println("Date Taken MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Test Type: ");
                        list.add(sc.nextLine());
                        System.out.println("Status: ");
                        list.add(sc.nextLine());
			myjdbc.insertTest(list, table);
		} else {
			System.out.println("You don't have permission to add to "+table);
		}


	}

    /*
    * Purpose: Handles any of the four main menu actions if they are not to get queries
    * Parameters: sc: scanner used to collect input
    *             table: name of table to insert into
    */
    public static void handleDatabaseAction(Scanner sc, String userInput) 
    {
	String userTable;
        if (userInput.equals("a")) {
            System.out.println("What table would you like to enter into?\n");
	    userTable = sc.nextLine();
	    if(checkValidTable(userTable)){
		    insertToTable(sc, userTable);
	    } else {
		    System.out.println("Enter a valid table name");
		    handleDatabaseAction(sc, userInput);
	    }
        } else if (userInput.equals("b")) {
            System.out.println("Which table would you like to delete from?\n");
	    userTable = sc.nextLine();
            if(checkValidTable(userTable)){
                    deleteFromTable(sc, userTable);

            } else {
                    System.out.println("Enter a valid table name");
                    handleDatabaseAction(sc, userInput);
            }
        } else if (userInput.equals("c")) {
            System.out.println("Which table would you like to update?\n");
            userTable = sc.nextLine();
            if(checkValidTable(userTable)){
                    updateToTable(sc, userTable);
            } else {
                    System.out.println("Enter a valid table name");
                    handleDatabaseAction(sc, userInput);
            }
        } else {
            System.out.println("Which table would you like to print?\n");
            userTable = sc.nextLine();
            if(checkValidTable(userTable)){
                    printTable(userTable);
            } else {
                    System.out.println("Enter a valid table name");
                    handleDatabaseAction(sc, userInput);
            }

        }
    }

     /*
         * Method: updateToTable(Scanner sc, String table)
         * Parameters: sc: scanner used to collect input
        *             table: name of table to insert into
        */
        public static void updateToTable(Scanner sc, String table){
                table = convertTableName(table);
		ArrayList<String> list = new ArrayList<String>();
                int count = 0;
                
                if (table.equals("employee")) {
                        System.out.println("Please enter the employee ID of the row you would like to update\n");
                        String empId = sc.nextLine();
                        count = myjdbc.totalCount(table, "empId", empId, -1, "NONE");
                        if (count > 0) {
                                list = getUpdateNonPkValues(sc, table,  list);
                                myjdbc.updateTable(table, list, empId);
                        }

                } else if (table.equals("transaction")) {
                        System.out.println("Please enter the transaction ID of the row you would like to update\n");
                        String xactId = sc.nextLine();
                        count = myjdbc.totalCount(table, "xactId", xactId, -1, "NONE");
                        if (count > 0) {
                                list = getUpdateNonPkValues(sc, table,  list);
                                myjdbc.updateTable(table, list, xactId);
                        }
                        
                } else if (table.equals("appointment")) {
                        System.out.println("Please enter the appointment ID of the row you would like to update\n");
                        String apptId = sc.nextLine();
                        count = myjdbc.totalCount(table, "apptId", apptId, -1, "NONE");
                        if (count > 0) {
                                list = getUpdateNonPkValues(sc, table,  list);
                                myjdbc.updateTable(table, list, apptId);
                        }

                } else if (table.equals("license")) {
                        System.out.println("Please enter the driver ID of the row you would like to update\n");
                        String driverId = sc.nextLine();
                        count = myjdbc.totalCount(table, "driverId", driverId, -1, "NONE");
                        if (count > 0) {
                                list = getUpdateNonPkValues(sc, table,  list);
                                myjdbc.updateTable(table, list, driverId);
                        }
                        
                } else if (table.equals("state_id")) {
                        System.out.println("Please enter the ID number of the row you would like to update\n");
                        String idNum = sc.nextLine();
                        count = myjdbc.totalCount(table, "idNum", idNum, -1, "NONE");
                        if (count > 0) {
                                list = getUpdateNonPkValues(sc, table,  list);
                                myjdbc.updateTable(table, list, idNum); 
                        }
                        
                } else if (table.equals("customer")) {
                        System.out.println("Please enter the customer ID of the row you would like to update\n");
                        int custId = sc.nextInt();
                        count = myjdbc.totalCount(table, "customerId", "none", custId, "NONE");
                        if (count > 0) {
                                list = getUpdateNonPkValues(sc, table,  list);
                                myjdbc.updateTable(table, list, String.valueOf(custId)); ///////////////////////////////////////////// Customer ID is the wrong data type
                        }
                        
                } else if (table.equals("vehicle")) {
                        System.out.println("Please enter the vin number of the row you would like to update\n");
                        String vinNo = sc.nextLine();
                        count = myjdbc.totalCount(table, "vinNo", vinNo, -1, "NONE");
                        if (count > 0) {
                                list = getUpdateNonPkValues(sc, table,  list);
                                myjdbc.updateTable(table, list, vinNo); 
                        }
                        
                } else if (table.equals("license_plate")) {
                        System.out.println("Please enter the plate number of the row you would like to update\n");
                        String plateNo = sc.nextLine();
                        count = myjdbc.totalCount(table, "plateNum", plateNo, -1, "NONE");
                        if (count > 0) {
                                list = getUpdateNonPkValues(sc, table,  list);
                                myjdbc.updateTable(table, list, plateNo); 
                        }

                } else {
                        System.out.println("ERROR: You are unable to update from that table");
                }

        }

        /*----------------------------------------------------------------------------
        * Method: getUpdateNonPkValues(Scanner sc, String table, ArrayList<String> list)
        * 
        * Purpose:  Fills the array with the Non-primary key values for a given table.
        * It will do so based on the table name that is passed in as a parameter. 
        *
        * Pre-Condition: None
        *
        * Post-Condition: None
        *
        * Parameters:  ArrayList<String> list - ArrayList with one row of data.
        *              String tableName- Name of table being inserted into.
        *       
        *              Scanner sc- Scanner object used to get input.
        *
        *              String table- name of the table to gather info for.
        * Returns: None
        *----------------------------------------------------------------------------*/
        private static ArrayList<String> getUpdateNonPkValues(Scanner sc, String table, ArrayList<String> list){
                if(table.equals("vehicle_registration")){
			System.out.println("State: ");
                        list.add(sc.nextLine());
			System.out.println("Issue Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
			System.out.println("Expiration Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
			return list;
                } else if(table.equals("vehicle")){
                        System.out.println("Vehicle make: ");
                        list.add(sc.nextLine());
                        System.out.println("Model: ");
                        list.add(sc.nextLine());
                        System.out.println("Color: ");
                        list.add(sc.nextLine());
                        return list;
                } else if(table.equals("customer")){
			 System.out.println("First Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Last Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Middle Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Social Security Number: ");
                        list.add(sc.nextLine());
                        System.out.println("Address: ");
                        list.add(sc.nextLine());
			System.out.println("State: ");
                        list.add(sc.nextLine());
                        System.out.println("Zip Code: ");
                        list.add(sc.nextLine());
                        System.out.println("Date of Birth MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Sex: ");
                        list.add(sc.nextLine());
                        System.out.println("Height: ");
                        list.add(sc.nextLine());
                        System.out.println("Eye Color: ");
                        list.add(sc.nextLine());
			System.out.println("Hair Color: ");
                        list.add(sc.nextLine());
                        System.out.println("Weight: ");
                        list.add(sc.nextLine());
			return list;
		} else if(table.equals("employee")){
                        System.out.println("First Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Last Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Middle Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Date of Birth MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Address: ");
                        list.add(sc.nextLine());
                        System.out.println("State: ");
                        list.add(sc.nextLine());
                        System.out.println("Sex: ");
                        list.add(sc.nextLine());
			System.out.println("Social Security Number: ");
                        list.add(sc.nextLine());
                        System.out.println("Supervisor Social Security Number: ");
                        list.add(sc.nextLine());
                        System.out.println("Job Title: ");
			myjdbc.insertEmployee(list, table);
		} else if(table.equals("appointment")){
                        System.out.println("Employee ID: ");
                        String empId = sc.nextLine();
                        System.out.println("Appointment Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Start Time: ");
                        String time = sc.nextLine();
                        int count = myjdbc.totalCount(table, "startTime", time, -1, empId);
                        while (count != 0) {
                                System.out.println("There is already an appointment at that time, please try another time\n");
                                time = sc.nextLine();
                                count = myjdbc.totalCount(table, "startTime", time, -1, empId);
                        }
                        list.add(time);
                        System.out.println("Type of Appointment: ");
                        list.add(sc.nextLine());
                        System.out.println("Status: ");
                        list.add(sc.nextLine());
			return list;
                } else if(table.equals("license_plate")){
                        System.out.println("State: ");
                        list.add(sc.nextLine());
                        System.out.println("Issue Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Expiration Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
			return list;
                } else if(table.equals("state_id")){
                        System.out.println("Issue Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Expiration Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
			return list;
                } else if(table.equals("license")){
                        System.out.println("State: ");
                        list.add(sc.nextLine());
                        System.out.println("Issue Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
                        System.out.println("Expiration Date MM/DD/YYYY: ");
                        list.add(sc.nextLine());
			System.out.println("ID Type: ");
                        list.add(sc.nextLine());
			return list;
                } else if(table.equals("department")){     ////////////////////////////////////////////////////////// NOT SURE IF THIS NEEDS TO BE UPDATED
                        System.out.println("Department Name: ");
                        list.add(sc.nextLine());
                        System.out.println("Manager SSN:");
                        list.add(sc.nextLine());
                        System.out.println("State: ");
                        list.add(sc.nextLine());
                        System.out.println("City: ");
                        list.add(sc.nextLine());
                        System.out.println("Address:");
                        list.add(sc.nextLine());
                        return list;
                } else if(table.equals("test")) {
                        System.out.println("Score:");
                        list.add(sc.nextLine());
                        System.out.println("Date Taken");
                        list.add(sc.nextLine());
                        System.out.println("Test Type: ");
                        list.add(sc.nextLine());
                        System.out.println("Status");
                        list.add(sc.nextLine());
                        return list;
                } else if (table.equals("transaction")){
                        System.out.println("Transaction Date; ");
                        list.add(sc.nextLine());
                        System.out.println("Service Fee: ");
                        list.add(sc.nextLine());
                        return list;
                } else {
			System.out.println("You don't have permission to add to "+table);
                }

                return list;
	}
        
	
	/*
	 * Purpose: To print the selection of queries to choose from
	 *
	 */
	public static void printQueryMenu()
	{
		System.out.println("\nYou have four options to choose from: \n\n" +
				   "(a) Write a query that displays the user details whose Ids will expire given a date in format MM/DD/YYYY\n" +
				   "(given by the user). The result should display the user id, user name, id issued date, id expiry date and\n" +
				   "type of id.\n\n" +
				   "(b) To get a licence, the user has to clear some tests, and there are other conditions to other types of ids. So,\n" +
				   "not every appointment is successful. Your work is to write a query for the previous month count every\n" +
				   "type of appointment and check how many of them got successful their IDs. For example: in October,\n" +
				   "DMV got 50 appointments for driving licence and 30 appointments for vehicle registration but 40 got\n" +
				   "approved for the licence and 25 for registration. Assume all work is done in one day.\n\n" +
				   "(c) Write a query that displays the collected fee amount for every department for a given month in the format\n" +
				   "MM/YYYY (given by the user). The result should display the amount and department information and\n" +
				   "sort the result on amount in descending order.\n\n" +
				   "(d) What are the full names and makes and models of vehicles owned by a customer with a user given ssn?\n" +
                   "Please enter a, b, c, d or r to return to the previous menu\n");
	}

	/*
	 * Purpose: To handle the selection from the main menu
	 * Parameter(s):
	 * 	Scanner - sc - the scanner to obtain user input	
	 */
	public static void handleMainMenuSelection(Scanner sc)
	{
		int endLoop = 1;
		
		String userInput = "";

		do {
			userInput = sc.nextLine();

			if (userInput.equals("exit")) {
				System.out.println("Exiting...");
				return;
			}

            if (userInput.equals("a") ||
                userInput.equals("b") ||
                userInput.equals("c") ||
                userInput.equals("d")) {
                    handleDatabaseAction(sc, userInput);
                }

			else if (userInput.equals("e")) {
				printQueryMenu();
				handleQuerySelection(sc);
			}
			
			else { 
				System.out.println("Please enter a, b, c, d, or e.\n" +
						   "If you'd like to exit, please type \"exit\"\n");
			}

                System.out.println("If you'd like to select more options, please enter \"a\" to insert," +
                                " \"b\" to delete, \"c\" to update, \"d\" to print a table, or \"e\" to get query answers");

		} while (endLoop == 1);
	}
	
	/*
	 * Purpose: To handle the query portion of the main menu
	 * Parameter(s): sc- scanner to collect input
	 */
	public static void handleQuerySelection(Scanner sc)
	{
        int flag = 0;

        while (true) {
            
            String userSelection = sc.nextLine();

            if (userSelection.equals("a") ||
                userSelection.equals("b") ||
                userSelection.equals("c") ||
                userSelection.equals("d")) {
                    handleQuery(sc, userSelection);
            }

            else if (userSelection.equals("r")) {
				printMainMenu();
                break;

            } else {
                System.out.println("Please enter a valid character (a, b, c, d, or r)");
                flag = 1;
            }

            if (flag == 0) {
                int cont = goAgain(sc, 1);
			
				    if (cont == 0) {
					    printQueryMenu();
				    }
            }
        }

	}

        /*
	 * Purpose: To format and call query
	 * Parameter(s): sc- scanner to collect input
         *               userSelection - string representing user's selection
	 */
    public static void handleQuery(Scanner sc, String userSelection) 
    {

        if (userSelection.equals("a")) {
			System.out.print("Please enter a month (MM): ");
            int userEnteredMonth = sc.nextInt();
			if (userEnteredMonth <= 0 || userEnteredMonth > 12) {
				System.out.println("Invalid month");
				return;
			}

			System.out.print("Please enter a day (DD): ");
			int userEnteredDay = sc.nextInt();
			if (userEnteredDay <= 0 || userEnteredDay > 31) {
				System.out.println("Invalid day");
				return;
			}
			System.out.println("Please enter a year (YYYY): ");
			int userEnteredYear = sc.nextInt();
			if (userEnteredYear < 0 || userEnteredYear > 9999) { // any four digit year
				System.out.println("Invalid year");
				return;
			}

			String query = "SELECT license.customerId, license.driverId, customer.fName || ' ' || customer.mName || ' ' || customer.lName as NAME, license.issueDate, license.expDate, license.idType " +
			"FROM blsmith86.license, blsmith86.customer " +
			"WHERE customer.customerId = license.customerId " +
			"AND " + userEnteredDay + " = EXTRACT(DAY FROM license.expDate) " +
			"AND " + userEnteredMonth + " = EXTRACT(MONTH FROM license.expDate) " +
			"AND " + userEnteredYear + " = EXTRACT(YEAR FROM license.expDate) ";

			myjdbc.queryOne(query);
            return;

        } else if (userSelection.equals("b")) {
			System.out.print("Please enter a month in MM format: ");

			int userEnteredMonth = sc.nextInt();
			if(userEnteredMonth <= 0 || userEnteredMonth > 12) {
				System.out.println("Invalid month");
				return;
			}

			String query1 = "SELECT appointment.type Type, count(appointment.status) Total " +
			"FROM blsmith86.appointment " +
			"WHERE " + userEnteredMonth + " = EXTRACT(MONTH FROM appointment.apptDate) " +
			"GROUP BY appointment.type";
			
			String query2 = "SELECT appointment.type Type, count(appointment.status) Passed " +
			"FROM blsmith86.appointment " +
			"WHERE " + userEnteredMonth + " = EXTRACT(MONTH FROM appointment.apptDate) " +
			"AND appointment.status='Pass' " +
			"GROUP BY appointment.type";

			myjdbc.queryTwo(query1, query2, userEnteredMonth);

			return;
            

        } else if (userSelection.equals("c")) {
            System.out.println("Please enter a date in MM/YYYY format:\n");

            System.out.print("Please enter a month in MM format: ");

			int userEnteredMonth = sc.nextInt();
			if(userEnteredMonth <= 0 || userEnteredMonth > 12) {
				System.out.println("Invalid month");
				return;
			}

            System.out.print("Please enter a year in YYYY format: ");
            int userEnteredYear = sc.nextInt();
            
		String year = String.valueOf(userEnteredYear);
		String month = String.valueOf(userEnteredMonth);

            String query = "SELECT department.deptName, SUM(transaction.serviceFee) "+
		"FROM blsmith86.department, blsmith86.transaction, blsmith86.appointment, blsmith86.employee "+
		"WHERE transaction.apptId = appointment.apptId "+
		"AND appointment.empId = employee.empId "+
		"AND employee.deptId = department.deptId "+
		"AND " + userEnteredYear + " = EXTRACT(YEAR FROM transaction.xactdate) "+
		"AND " + userEnteredMonth +" = EXTRACT(MONTH FROM transaction.xactdate) "+
		"GROUP BY department.deptName";            

	    myjdbc.queryThree(query, month, year);
            
            return;

        } else if (userSelection.equals("d")) {
		System.out.println("Please enter a social security number with no dashes");
        	int userEnteredSSN = sc.nextInt();

		String query = "SELECT customer.fName, customer.mName, customer.lName, vehicle.make, vehicle.model " + 
		"FROM blsmith86.customer, blsmith86.vehicle_registration, blsmith86.vehicle "+
		"WHERE customer.ssn = "+ String.valueOf(userEnteredSSN)+
		" AND customer.customerId=vehicle_registration.customerId"+
		" AND vehicle.vinNo=vehicle_registration.vinNo";

		myjdbc.queryFour(query, String.valueOf(userEnteredSSN));

		return;
	}
    }

	/*
	 * Purpose: To prompt the user to enter more options
	 * Parameter(s): sc - scanner to collect input
         *               flag - int representing whether to continue
	 */	
	public static int goAgain(Scanner sc, int flag)
	{

        if (flag == 0) {
		    System.out.println("\nWould you like to enter more options?\n" +
				   "If yes, please enter \"yes\", if no, please enter \"no\"");
        } else {
            System.out.println("Would you like to get the answers to more queries?");
        }


		int timesAsked = 0;
		String result = "";

		do {
			if (timesAsked > 0) {
				System.out.println("Please enter \"yes\" or \"no\"");
			}

			result = sc.nextLine();

			timesAsked++;

		} while (!result.equals("yes") && !result.equals("no"));

		if (result.equals("no")) {
			System.out.println("Exiting...");
			myjdbc.closeDBConnection();
			System.exit(0);
		} 

		return 0;

	}
}
