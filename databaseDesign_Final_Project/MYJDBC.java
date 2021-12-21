/*---------------------------------------------------------------------
 *  Class MYJDBC
 *  
 * Purpose: This class will open the database connection and contain
 * methods to insert, query, and close the DB connection. 
 * 
 *
 *  Inherits from: None
 *
 *  Interfaces: None
 * 
 *  Constants: 
 *      String NET_ID- String representing my net id: "blsmith86"
 *
 *      String DRIVER_NAME- Name of the oracle driver.
 *
 *      String ORACLE_URL- String representing the Oracle URL.
 *  
 *  Constructor: util()-- 
 *
 *  Class Methods: None
 * 
 *  Inst Methods:
 *     insertIntoDB(ArrayList<String> line)--
 *     queryDB()--
 *     openDBConnection(Connection dbConnection, 
 *             String userName, String password)
 *     closeDBConnection()--
 *     displayQuery(ResultSetMetaData metaData)--
 *
 *------------------------------------------------------------------*/

// IMPORTS
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;

public class MYJDBC{

    private static final String NET_ID      = "blsmith86";
    private static final String DRIVER_NAME = "oracle.jdbc.OracleDriver";
    private static final String ORACLE_URL  = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
    
    // Field for the DB connection
    private Connection dbConnection;
    private Statement stmt;
    private ResultSet result;
 
    /*----------------------------------------------------------------------------
    * Method: MYJDBC(String userName, String password)
    * 
    * Purpose: Creates and object that will be used to insert, query, and interact
    * with the database. 
    *
    * Pre-Condition: None
    *
    * Post-Condition: The DB connection will be made. 
    *
    * Parameters: String userName- User's name. (Command-line arguments from main())
    *             String password- User's password. (Command-line arguments from 
                  main()).
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public MYJDBC(String userName, String password){
      
        // Initialize the connection 
        this.dbConnection = null;
        this.stmt         = null;
        this.result       = null;

        // Load the JDBC driver
        loadDriver();

        // Open the connection
        openDBConnection(userName, password);
    } // End constructor

    

    
    /*----------------------------------------------------------------------------
    * Method: determineInsertMethod(ArrayList<String> list, String tableName)
    * 
    * Purpose:  Determines which table will be inserted into based on the loop
    * index (option) passed in. An ArrayList will be passed in that contains a
    * single row read in from the .csv file. The table name represents the 
    * table being inserted into. Switch statements will be used to determine
    * which table insertion function in this class will be called.
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters:  ArrayList<String> list - ArrayList with one row of data.
    *              String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void determineInsertMethod(ArrayList<String> list,  String tableName){
        switch(tableName){
            case "vehicle":
                insertVehicle(list, tableName);
                break;
            case "vehicle_registration":
                insertVehicleReg(list, tableName);
                break;
            case "license_plate":
                insertLicensePlate(list, tableName);
                break;
            case "customer":
                insertCustomer(list, tableName);
                break;
            case "state_id":
                insertStateID(list, tableName);
                break;
            case "license":
                insertLicense(list, tableName);
                break;
            case "employee":
                insertEmployee(list, tableName);
                break;
            case "department":
                insertDept(list, tableName);
                break;
            case "test":
                insertTest(list, tableName);
                break;
            case "transaction":
                insertXact(list, tableName);
                break;
            case "appointment":
                insertAppt(list, tableName);
                break;
        }
    } // End method

    /*----------------------------------------------------------------------------
    * Method: insertVehicle(ArrayList<String> list, String tableName)
    * 
    * Purpose:  Inserts a row into the Vehicle table. 
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertVehicle(ArrayList<String> line, String tableName){
        int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0) + "', "        // vinNo
                                        +       line.get(1) + ", "        // year
                                        + "'" + line.get(2) + "', "      // make
                                        + "'" + line.get(3) + "', "     // model
                                        + "'" + line.get(4) + "') " ); // color
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }

    }

    /*----------------------------------------------------------------------------
    * Method: insertVehicleReg(ArrayList<String> list)
    * 
    * Purpose:  Insert info into the vehicle registration table.
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertVehicleReg(ArrayList<String> line, String tableName){
       
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0) + "', "         // vinNo
                                        + "'" + line.get(1) + "', "        // customerId
                                        + "'" + line.get(2) + "', "        // plateNum
                                        + "'" + line.get(3) + "', "      // state
                                        + "TO_DATE('" + line.get(4) + "', 'MM/DD/YYYY'), "     // issueDate
                                        + "TO_DATE('" + line.get(5) + "', 'MM/DD/YYYY')) " ); // expDate
            
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    /*----------------------------------------------------------------------------
    * Method: insertVehicleReg(ArrayList<String> list)
    * 
    * Purpose:  Insert data read in from the into the Vehicle 
    * Registration table.
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertLicensePlate(ArrayList<String> line, String tableName){
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0) + "', "          // vinNo
                                        + "'" + line.get(1) + "', "         // plateNum
                                        + "'" + line.get(2) + "', "        // customerId
                                        + "'" + line.get(3) + "', "       // state
                                        + "TO_DATE('" + line.get(4) + "', 'MM/DD/YYYY'), "     // issueDate
                                        + "TO_DATE('" + line.get(5) + "', 'MM/DD/YYYY')) " ); // expDate
         
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    /*----------------------------------------------------------------------------
    * Method: insertCustomer(ArrayList<String> list, String tableName)
    * 
    * Purpose: Inserts info from the into the Customer table.
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertCustomer(ArrayList<String> line, String tableName){
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0)  + "', "    // customerId
                                        + "'" + line.get(1)  + "', "   // fName
                                        + "'" + line.get(2)  + "', "  // lName
                                        + "'" + line.get(3)  + "', " // mName
                                        +       line.get(4)  + ", "    // ssn
                                        + "'" + line.get(5)  + "', "  // address
                                        + "'" + line.get(6)  + "', " // state
                                        +       line.get(7)  + ", " // zip
                                        + "TO_DATE('" + line.get(8)  + "', 'MM/DD/YYYY'), " // dob
                                        + "'" + line.get(9)  + "', "       // sex
                                        + "'" + line.get(10) + "', "      // height
                                        + "'" + line.get(11) + "',"      // eyeColor
                                        + "'" + line.get(12) + "',"     // hairColor
                                        +       line.get(13) + ")"  ); // weight
            
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }


    /*-------------------------------
    *
    * Purpose: Gets the longest string in the ResultSet
    * Parameters: 
    *       answer - the ResultSet
    *       length - the column length of the resultSet
    --------------------------------*/
    public int getLongest(ResultSet answer, int length) throws SQLException {

        int longest = 0;

        while (answer.next()) {
            for (int i = 1; i < length; i++) {
                if (answer.getObject(i) != null) {
                    if (answer.getObject(i).toString().length() > longest) {
                        longest = answer.getObject(i).toString().length();
                    }
                }
            }
        }

        return longest;
    }
    

    /*----------------------------------------------------------------------------
    * Method: printTable(String tableName)
    *
    * Purpose:  Prints table with given tableName.
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: String tableName- Name of table being printed.
    *
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void printTable(String tableName){
        try{
            
            ResultSet temp = this.stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData answerMetaData = temp.getMetaData();

            if (temp != null) {
                int length = answerMetaData.getColumnCount();
                int longestItem = getLongest(temp, length);

                ResultSet answer = this.stmt.executeQuery("SELECT * FROM " + tableName);

                for (int i = 1; i < length + 1; i++) {
                    System.out.print(String.format("|%-" + longestItem + "s", answerMetaData.getColumnName(i)));
                }

                System.out.print("|");
                System.out.println();
                
                while (answer.next()) {
                    for (int i = 1; i < length + 1; i++) {
                        System.out.print(String.format("|%-" + longestItem + "s", answer.getObject(i)));
                    }
                    
                    System.out.print("|");
                    System.out.println();
                    
                }
            }
	

        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while printing values from: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }


    /*----------------------------------------------------------------------------
    * Method: insertStateID(ArrayList<String> list)
    * 
    * Purpose:  Insert a row of data into the State ID table.
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertStateID(ArrayList<String> line, String tableName){
        System.out.println(line);
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0) + "', "      // idNum
                                        + "'" + line.get(1) + "', "     // customerId
                                        + "TO_DATE('" + line.get(2) + "', 'MM/DD/YYYY'), "    // issueDate
                                        + "TO_DATE('" + line.get(3) + "', 'MM/DD/YYYY')) " ); // expDate
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    /*----------------------------------------------------------------------------
    * Method: insertStateID(ArrayList<String> list)
    * 
    * Purpose:  Insert data read in from the .csv file into the State ID table. 
    *
    * Pre-Condition: None
    *
    * Post-Condition:  None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertLicense(ArrayList<String> line, String tableName){
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0) + "', "   // driverId
                                        + "'" + line.get(1) + "', "  // customerId
                                        + "'" + line.get(2) + "', " // state
                                        + "TO_DATE('" + line.get(3) + "', 'MM/DD/YYYY'), "  // issueDate
                                        + "TO_DATE('" + line.get(4) + "', 'MM/DD/YYYY'), " // expDate
                                        + "'" + line.get(2)+"')" ); // idType
            
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    /*----------------------------------------------------------------------------
    * Method: insertEmployee(ArrayList<String> list)
    * 
    * Purpose:  Insert data read in from the .csv file into the Employee table.
    *
    * Pre-Condition: None
    *
    * Post-Condition:  None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertEmployee(ArrayList<String> line, String tableName){
        //System.out.println(line);
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0)  + "', "     // empId
                                        + "'" + line.get(1)  + "', "    // deptId
                                        + "'" + line.get(2)  + "', "   // fName
                                        + "'" + line.get(3)  + "', "  // lName
                                        + "'" + line.get(4)  + "', " // mName
                                        + "TO_DATE('" + line.get(5)  + "', 'MM/DD/YYYY'), "// dob
                                        + "'" + line.get(6)  + "', "        // address
                                        + "'" + line.get(7)  + "', "       // state
                                        + "'" + line.get(8)  + "', "      // sex
                                        +       line.get(9)  + ", "      // ssn 
                                        +       line.get(10) + ", "     // super_ssn 
                                        + "'" + line.get(11) + "')" ); // jobTitle
            
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    /*----------------------------------------------------------------------------
    * Method: insertDept(ArrayList<String> list)
    * 
    * Purpose: Insert data read in from the .csv file into the Department Table.
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns:  None
    *----------------------------------------------------------------------------*/
    public void insertDept(ArrayList<String> line, String tableName){
        //System.out.println(line);
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0) + "', "        // deptId
                                        + "'" + line.get(1) + "', "       // deptName
                                        +       line.get(2) + ", "       // mgr_ssn
                                        + "'" + line.get(3) + "', "     // state
                                        + "'" + line.get(4) + "') " ); // address
            
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    /*----------------------------------------------------------------------------
    * Method: insertTest(ArrayList<String> list)
    * 
    * Purpose:  Insert data read in from the .csv file into the Test table.
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertTest(ArrayList<String> line, String tableName){
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0) + "', "         // driverId
                                        + "'" + line.get(1) + "', "        // custermerId
                                        +       line.get(2) + ", "        // score
                                        + "TO_DATE('" + line.get(3) + "', 'MM/DD/YYYY'), " // dateTaken
                                        + "'" + line.get(4) + "', "     // testType
                                        + "'" + line.get(5) + "') " ); // status
            
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    /*----------------------------------------------------------------------------
    * Method: insertXact(ArrayList<String> list)
    * 
    * Purpose:  Insert data read in from the .csv file and insert into the 
    * transaction table. 
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertXact(ArrayList<String> line, String tableName){
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0) + "', "      // xactId
                                        + "TO_DATE('" + line.get(1) + "', 'MM/DD/YYYY'), " // xactDate
                                        + "'" + line.get(2) + "', "    // customerId
                                        +       line.get(3) + ") " ); //serviceFee
            
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }
    
    /*----------------------------------------------------------------------------
    * Method: insertAppt(ArrayList<String> list)
    * 
    * Purpose:  Insert data read in from the .csv file into the Appointment table.
    *
    * Pre-Condition: None
    *
    * Post-Condition: None
    *
    * Parameters: ArrayList<String> list - ArrayList with one row of data.
    *             String tableName- Name of table being inserted into.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void insertAppt(ArrayList<String> line, String tableName){
         int isValid = 0;
        try{
        isValid = this.stmt.executeUpdate("INSERT INTO "+ NET_ID + "." + tableName + " VALUES (" 
                                        + "'" + line.get(0) + "', "          // apptId
                                        + "'" + line.get(1) + "', "         // empId
                                        + "'" + line.get(2) + "', "        // customerId
                                        + "TO_DATE('" + line.get(3) + "', 'MM/DD/YYYY'), " // apptDate
                                        + "'" + line.get(4) + "', "      // startTime
                                        + "'" + line.get(5) + "', "     // type
                                        + "'" + line.get(6) + "') " ); // status
            
        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while inserting values into: "+ tableName);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }
    /*----------------------------------------------------------------------------
    * Method: queryOne(String query)
    * 
    * Purpose:  Answer the first query given by the project spec.
    *
    * Pre-Condition: Database connection is open.
    *
    * Post-Condition: None
    *
    * Parameters: String query - The SQL query to execute
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void queryOne(String query) {
        try {
            result = this.stmt.executeQuery(query);
            if(result != null) {
                ResultSetMetaData answermetadata = result.getMetaData();

                for (int i = 1; i <= answermetadata.getColumnCount(); i++) {
                    if(i==3)
                        System.out.print(answermetadata.getColumnName(i) + "\t\t\t\t"); // more tabs for larger columns
                    else if(i==4 || i==5)
                        System.out.print(answermetadata.getColumnName(i) + "\t\t");
                    else
                        System.out.print(answermetadata.getColumnName(i) + "\t");
                }
                System.out.println();
                while(result.next()) {
                    System.out.print(String.format("%-16s", result.getString(1)) + String.format("%-16s", result.getString(2)) + result.getString(3)
                            + "\t" + result.getString(4) + "\t" + result.getString(5) + "\t" + result.getString(6));

                }
                System.out.println();
            }
        } catch(SQLException e) {
            System.err.println("*** SQL Exception:  "
            + "Error while executing query.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    /*----------------------------------------------------------------------------
    * Purpose: To delete a row in the table
    * Parameters: 
    *       table - the table to delete from
    *       flag - a flag which indicates if the id is an integer or string
    *       id - the string ID value indicating which row to delete
    *       intId - the integer ID value indicating which row to delete
    *       string - the string name of the primary key attribute
    *----------------------------------------------------------------------------*/
    public void deleteRow(String table, String flag, String id, int intId, String string) {
        try{
            
            String query = "";

            if (flag.equals("string")) {
                int count = totalCount(table, string, id, -1, "NONE");
                if (count == 1) {
                    query = "DELETE FROM " + table + " WHERE " + string + "='" + id + "'";
                } else {
                    System.out.println("The element you chose does not exist in that table\n");
                    return;
                }
            } else {
                int count = totalCount(table, string, "none", intId, "NONE");
                if (count == 1) {
                    query = "DELETE FROM " + table + " WHERE " + string + "=" + intId;
                } else {
                    System.out.println("The element you chose does not exist in that table\n");
                    return;
                }
            }

            ResultSet answer = this.stmt.executeQuery(query);

            System.out.println("Row was successfully deleted\n");

        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while deleting values from: "+ table);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

    

    /*
    * Purpose: To get a total count of the number of elements with a specific PK value in a given table
    * Parameters:
    *       table - the table name
    *       string - the string name of the primary key attribute
    *       id - the string ID value indicating which row to delete
    *       intId - the integer ID value indicating which row to delete
    */
    public int totalCount(String table, String string, String id, int intId, String empId) {
        String query = "";
        int count = 0;

        if (intId == -1) {
            query = "SELECT count(*) FROM BLSMITH86." + table + " WHERE " + string + "='" + id + "'";
        } else if (! empId.equals("NONE")) {
            query = "SELECT count(*) FROM BLSMITH86." + table + " WHERE " + string + "='" + id + "' AND empId <> " + empId;
        } else {
            query = "SELECT count(*) FROM BLSMITH86." + table + " WHERE " + string + "='" + intId + "'";
        }
    
        try {	
            ResultSet answer = stmt.executeQuery(query);
            
            if (answer != null) {

                System.out.println();
				    if (answer.next()) {
                        count = answer.getInt("count(*)");
                    }
            }
			
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

    /*----------------------------------------------------------------------------
    * Method: updateTable(String table, ArrayList<String> list, String pk)
    * 
    * Purpose: update the given table with the given values
    *
    * Pre-Condition: list contains the values needed in the order they appear in the table
    *
    * Post-Condition: 
    *
    * Parameters: table: name of table to update
    *             list: list of values to be inserted
    *             pk: primary key of object to be updated
    * 
    * Returns: Void
    *----------------------------------------------------------------------------*/
    public void updateTable(String table, ArrayList<String> list, String pk){
        /*
            in case of debugging: every table except for license_plate has the primary key as the first column
                which means that list should contain values for every other column in order starting at column 2
                
                possible causes of errors: list not in right error, or another table doesn't have the primary key
                in the first 
        */

        try{
            System.out.println(list + "    " + table);
            String query1 = "Select * from "+ NET_ID + "." + table;
            
            ResultSet result = this.stmt.executeQuery(query1);
            ResultSetMetaData resultMetaData = result.getMetaData();
            
            String query = "UPDATE " + NET_ID + "." + table + " SET ";
            HashMap<String, Integer> nonPKCols = new HashMap<String, Integer> (); 
            nonPKCols.put("vehicle"             , 1);  // Tablename andquit Number of PKs and FKs
            nonPKCols.put("vehicle_registration", 3);
            nonPKCols.put("license_plate"       , 3);
            nonPKCols.put("customer"            , 1);
            nonPKCols.put("state_id"            , 2);
            nonPKCols.put("license"             , 2);
            nonPKCols.put("employee"            , 2);
            nonPKCols.put("department"          , 1);
            nonPKCols.put("test"                , 2);
            nonPKCols.put("transaction"         , 3);
            nonPKCols.put("appointment"         , 3);

            // Get the column number of the last PK or FK and add 1 to get the first non-PK/FK point
            int startingPoint = nonPKCols.get(table) + 1;
            int loopBound = resultMetaData.getColumnCount();
            
             
            // If the num PKs/Fks is even subtract 1 from the loop bound
            if(nonPKCols.get(table) % 2 == 0){
                loopBound--;
            }


            for(int i = startingPoint; i <= loopBound; i++){
               
                String inputStr = list.get(i - startingPoint);
                
                // Concatenate a new query string
                //query += resultMetaData.getColumnName(i) + " = '" + list.get(i - startingPoint)+ "'";

                if(inputStr.matches(".*\\d.*") && !inputStr.contains("/") && !inputStr.contains(":")){
                    query += resultMetaData.getColumnName(i) + " = " + inputStr;
                } else if(inputStr.contains("/")  ){
                    query += resultMetaData.getColumnName(i) + " = to_date('" + inputStr + "', 'MM/DD/YYYY')";
                } else {
                    query += resultMetaData.getColumnName(i) + " = '" + inputStr + "'";
                }


                // Insert a comma in between fields
                if(i != resultMetaData.getColumnCount()){
                    query += ", ";
                }   
                
            }
            query += " WHERE " + resultMetaData.getColumnName(1) + " = '" + pk + "'";
            
            this.stmt.executeUpdate(query);
            System.out.println(table + " updated");



        } catch (SQLException e){
            System.err.println("*** SQL Exception:  "
            + "Error while updating the table: "+ table);
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }

    } // End method

    /*----------------------------------------------------------------------------
    * Method: queryTwo(String totalAppointments, String passedAppointments, int month)
    * 
    * Purpose:  Answer the second query given by the project spec.
    *
    * Pre-Condition: Database connection is open.
    *
    * Post-Condition: None
    *
    * Parameters: String totalAppointments - A query to get the total number of appointments
                                            in a given month.
    *             String passedAppointments - A query to get the number of passed appointments
                                            in a given month.
    *             int month - The month number that the user provided.
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void queryTwo(String totalAppointments, String passedAppointments, int month) {
        // Key is the type of appointment. Value is [passed, total]
        HashMap<String, int[]> stats = new HashMap<>();
        // This covers all the possible types
        stats.put("Permit", new int[] {0,0});
        stats.put("License", new int[] {0,0});
        stats.put("Registration", new int[] {0,0});
        stats.put("ID", new int[] {0,0});
        try {
            result = this.stmt.executeQuery(totalAppointments);
            if(result != null) {
                while(result.next()) {
                    stats.get(result.getString("Type"))[1] = result.getInt("Total");
                }
            }
            result = this.stmt.executeQuery(passedAppointments);
            if(result != null) {
                while(result.next()) {
                    stats.get(result.getString("Type"))[0] = result.getInt("Passed");
                }
            }

            System.out.println("Appointment statuses for month: " + month);
            System.out.println("Type\t\tPassed\tTotal");
            for(String key : stats.keySet()) {
                int passed = stats.get(key)[0];
                int total = stats.get(key)[1];
                System.out.print(String.format("%-16s", key) + passed + "\t" + total + "\n");
            }
        } catch(SQLException e) {
            System.err.println("*** SQL Exception:  "
            + "Error while executing query.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    }

     /*----------------------------------------------------------------------------
    * Method: queryThree(String query, String month, String year)
    *
    * Purpose:  Answer the third query given by the project spec.
    *
    * Pre-Condition: Database connection is open.
    *
    * Post-Condition: None
    *
    * Parameters: String query - A query to get the total fee collected by each dept in a month
    *             Strign month - The month that the user provided.
    *             String year - The year that the user provided.
    *
    * Returns: None
    *----------------------------------------------------------------------------*/

	public void queryThree(String query, String month, String year){
		try{
                        ResultSet answer = this.stmt.executeQuery(query);
                       	while(answer.next()){
				System.out.println("Department: "+ answer.getString(1)+", Fees collected: " + String.valueOf(answer.getInt(2)));
			}
			System.out.println();
                } catch(SQLException e) {
                        System.err.println("*** SQL Exception:  "
                        + "Error while executing query.");
                        System.err.println("\tMessage:   " + e.getMessage());
                        System.err.println("\tSQLState:  " + e.getSQLState());
                        System.err.println("\tErrorCode: " + e.getErrorCode());
                        System.exit(-1);
                }

	}

 	 /*----------------------------------------------------------------------------
    * Method: queryThree(String query, String month, String year)
    *
    * Purpose:  Answer the third query given by the project spec.
    *
    * Pre-Condition: Database connection is open.
    *
    * Post-Condition: None
    *
    * Parameters: String vehicles - A query to get the details of vehicles owned by someone with given ssn
    *             Strign ssn - The social security number that the user provided.
    *
    * Returns: None
    *----------------------------------------------------------------------------*/

	public void queryFour(String vehicles, String ssn){
		try{
			ResultSet answer = this.stmt.executeQuery(vehicles);
			while(answer.next()){
				System.out.print(answer.getString("fName") +" "+ answer.getString("mName") +" "+ answer.getString("lName") + ": ");
				System.out.println(answer.getString("make") + " " + answer.getString("model"));
			}
		} catch(SQLException e) {
            		System.err.println("*** SQL Exception:  "
            		+ "Error while executing query.");
           		System.err.println("\tMessage:   " + e.getMessage());
            		System.err.println("\tSQLState:  " + e.getSQLState());
            		System.err.println("\tErrorCode: " + e.getErrorCode());
            		System.exit(-1);
        	}

	}


    /*----------------------------------------------------------------------------
    * Method: openDBConnection()
    * 
    * Purpose: Opens a connection with the Oracle DB. The username and password 
    * are provided via the command-line arguments in Prog3.java. The program will
    * exit if a connection cannot be established or the user provides an incorrect
    * username/password. 
    *
    * Pre-Condition: The user's name and password will be passed in as command-line
    * arguments in main. 
    *
    * Post-Condition: The DB connection will be open. 
    *
    * Parameters: String userName- User's name.
    *             String password- User's password. 
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    private void openDBConnection(String userName, String password){
        try {
            this.dbConnection = DriverManager.getConnection
                       (ORACLE_URL, userName, password);
            this.stmt = this.dbConnection.createStatement();
                
        } catch (SQLException e) {
            System.err.println("*** SQLException:  "
            + "Could not open JDBC connection.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    } // End method

    /*----------------------------------------------------------------------------
    * Method: closeDBConnection()
    * 
    * Purpose: Closes the DB connection when the program is about to finish. This
    * method will only be envoked from Prog3.java's main(). 
    *
    * Pre-Condition: The dbConnection will not be null. 
    *
    * Post-Condition: The connection will be closed. 
    *
    * Parameters: None
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    public void closeDBConnection(){
        try{
            this.dbConnection.close();
            this.stmt.close();
            System.out.println("\nDatabase connection closed. Program Ending normally...");
        } catch (SQLException e){
            System.err.println("*** SQLException:  "
            + "Could not close the JDBC connection.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }
    } // End method

    /*----------------------------------------------------------------------------
    * Method: loadDriver()
    * 
    * Purpose: Loads the driver prior to the database connection. 
    *
    * Pre-Condition: None
    *
    * Post-Condition: The driver will be loaded. 
    *
    * Parameters: None
    * 
    * Returns: None
    *----------------------------------------------------------------------------*/
    private void loadDriver(){
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("*** ClassNotFoundException:  "
            + "Error loading Oracle JDBC driver.  \n"
            + "\tPerhaps the driver is not on the Classpath?");
            System.exit(-1);
        }
    } // End method

    
} // End class
