package databasePart1;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;
import java.text.SimpleDateFormat; 
import java.util.Date; 

import databasePart1.Answer;
import databasePart1.AnswersList;
import databasePart1.Question;
import databasePart1.QuestionsList;
import databasePart1.ReviewerRating;
import databasePart1.User;
import databasePart1.PrivateMessage;


/**
 * The DatabaseHelper class is responsible for managing the connection to the database,
 * performing operations such as user registration, login validation, and handling invitation codes, 
 * stores/updating/deleting Questions/Answers/Reviews, Storing Global reviewer Rankings, Private Messaging, Searching algorithms
 */
/**
 * <h1>DatabaseHelper</h1>
 * 
 * The DatabaseHelper class is responsible for managing the connection to the database,
 * performing operations such as user registration, login validation, and handling invitation codes, 
 * stores/updating/deleting Questions/Answers/Reviews, Storing Global reviewer Rankings, Private Messaging, Searching algorithms
 * 
 * @author CSE360 Monday 11, and Professor Carter
 * @version 1.0
 * @since 2025-04-02
 */
public class DatabaseHelper {

	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/FoundationDatabase";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	private Connection connection = null;
	private Statement statement = null; 
	//	PreparedStatement pstmtd
	
	
	/**
	 * 
	 * @throws SQLException
	 * Connects to system to the data base so we can use it. Came with the original file. 
	 */
	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement(); 
			// You can use this command to clear the database and restart from fresh.
			//statement.execute("DROP ALL OBJECTS");

			createTables();  // Create the necessary tables if they don't exist
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}

	/**
	 * 
	 * 
	 * @throws SQLException
	 * This amazing function allows us to create all the tables we need for storing information ranging from every user, every question/answer and every conversation going on. 
	 */
	private void createTables() throws SQLException {
		String userTable = "CREATE TABLE IF NOT EXISTS cse360users ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "userName VARCHAR(255) UNIQUE, "
				+ "password VARCHAR(255), "
				+ "role VARCHAR(50), "
				+ "isAdmin BOOLEAN, "
				+ "isUser BOOLEAN, "
				+ "isStaff BOOLEAN, "
				+ "isInstructor BOOLEAN, "
				+ "isReviewer BOOLEAN, "
				+ "isStudent BOOLEAN, "
                + "name VARCHAR(20), "
                + "email VARCHAR(50))";
		statement.execute(userTable);
		
		// Create the invitation codes table, Added columns to include: Deadline, and all of the is*Role* BOOLEANS
	    String invitationCodesTable = "CREATE TABLE IF NOT EXISTS InvitationCodes ("
	            + "code VARCHAR(10) PRIMARY KEY, "
	            + "isUsed BOOLEAN DEFAULT FALSE, "
                + "deadline DOUBLE, " 
                + "isAdmin BOOLEAN, "
				+ "isUser BOOLEAN, "
				+ "isStaff BOOLEAN, "
				+ "isInstructor BOOLEAN, "
				+ "isReviewer BOOLEAN, "
				+ "isStudent BOOLEAN)";
	    statement.execute(invitationCodesTable); 

		// Create the One Time Passwords Table
	    String OneTimePasswordsTable = "CREATE TABLE IF NOT EXISTS OneTimePasswords ("
	            + "password VARCHAR(20) PRIMARY KEY, "
	            + "isUsed BOOLEAN DEFAULT FALSE)";
	    statement.execute(OneTimePasswordsTable); 
	    
	    //================================================================================
	    //Storing the Current Unique ID Table.
	    String CurrentIdTable = "CREATE TABLE IF NOT EXISTS CurrentIdTable ("
	    		+ "id INT PRIMARY KEY, "
	    		+ "isUsed BOOLEAN DEFAULT FALSE)";
	    statement.execute(CurrentIdTable); 
	    
	    //Create the QUESTIONS and Answers Table
	    String QuestionsTable = "CREATE TABLE IF NOT EXISTS Questions ("
	            + "id INT PRIMARY KEY, "
	            + "title VARCHAR(50), "
	            + "questionText VARCHAR(2000),"
	            + "userName VARCHAR(17), "
	            + "resolved BOOLEAN, "
	            + "isSubQuestion BOOLEAN, "
	            + "parentQuestionLinkId INT, "
	            + "hideUserName BOOLEAN, "
	            + "preferredAnswerId INT, "
				+ "homeworkTag BOOLEAN DEFAULT FALSE, "					// additions
				+ "conceptTag BOOLEAN DEFAULT FALSE, "
				+ "examTag BOOLEAN DEFAULT FALSE, "
				+ "generalTag BOOLEAN DEFAULT FALSE, "
				+ "socialTag BOOLEAN DEFAULT FALSE, "
				+ "tag VARCHAR(15))";
	    statement.execute(QuestionsTable);
	    
	  //Create the ANSWERS and Answers Table
	    String AnswersTable = "CREATE TABLE IF NOT EXISTS Answers ("
	            + "id INT PRIMARY KEY, "
	            + "userName VARCHAR(17), "
	            + "answerText VARCHAR(3000), "
	            + "questionLinkID INT, "
	            + "hideUserName BOOLEAN, "
	            + "answerLinkID INT, "
	            + "level INT, "
				+ "highestRole VARCHAR(12), "
				+ "isReview BOOLEAN DEFAULT FALSE)";
	    statement.execute(AnswersTable);

		// Create Bazz's special database!!!
		String ReviewerRequestsTable = "CREATE TABLE IF NOT EXISTS ReviewerRequests ("
				+ "userName VARCHAR(255) PRIMARY KEY, "
				+ "approved BOOLEAN, "
				+ "pending BOOLEAN)";
		statement.execute(ReviewerRequestsTable);

		String ReviewersListTable = "CREATE TABLE IF NOT EXISTS ReviewerTable ("
			+ "userName VARCHAR(17))";
		statement.execute(ReviewersListTable);

		//Store all rankings of Reviewers
		String ReviewerRatingsTable = "CREATE TABLE IF NOT EXISTS ReviewerRating ("
				+ "userName VARCHAR(17), "
				+ "reviewerName VARCHAR(17), "
				+ "score INT, "
				+ "isTrusted BOOLEAN DEFAULT FALSE)";
		statement.execute(ReviewerRatingsTable);
		
		// Create the Conversations Table 
		String ConversationsTable  = "CREATE TABLE IF NOT EXISTS Conversations ("
				+ "connectionID INT AUTO_INCREMENT PRIMARY KEY, "
				+ "stuUsername VARCHAR(17), "
				+ "revUsername VARCHAR(17), "
				+ "UNIQUE(stuUsername, revUsername))";
		statement.execute(ConversationsTable);
		
		// Create the PMs Table for Private Messages 
		String PMsTable  = "CREATE TABLE IF NOT EXISTS PMs ("
				+ "stuUsername VARCHAR(17), "
				+ "revUsername VARCHAR(17), "
				+ "connectionID INT, "  
				+ "message VARCHAR(3000), "
				+ "time INT, "
				+ "sender VARCHAR(17), "
				+ "FOREIGN KEY (connectionID) REFERENCES Conversations (connectionID))";
		statement.execute(PMsTable);

	    ///=======================================================================
	}

	
	/**
	 * 
	 * @return Boolean; returns true or false based on database's emptiness. 
	 * @throws SQLException
	 */
	// Check if the database is empty
	public boolean isDatabaseEmpty() throws SQLException {
		String query = "SELECT COUNT(*) AS count FROM cse360users";
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			return resultSet.getInt("count") == 0;
		}
		return true;
	}

	
	/**
	 * 
	 * @param user ; given a User, we can register them into the system by inserting them into the cse360users DB. 
	 * @throws SQLException
	 */
	// Registers a new user in the database.
	public void register(User user) throws SQLException {
		String insertUser = "INSERT INTO cse360users (userName, password, role, isAdmin, isUser, isStaff, isInstructor, isReviewer, isStudent, name, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3,  user.getRole());
			pstmt.setBoolean(4, user.getIsAdmin());
			pstmt.setBoolean(5, user.getIsUser());
			pstmt.setBoolean(6, user.getIsStaff());
			pstmt.setBoolean(7, user.getIsInstructor());
			pstmt.setBoolean(8, user.getIsReviewer());
			pstmt.setBoolean(9, user.getIsStudent());
            pstmt.setString(10, user.getName());
            pstmt.setString(11, user.getEmail());
			
			pstmt.executeUpdate();
		}
	}

	
	/**
	 * 
	 * @param userName ; Based on this username, we can query the DB to delete any row with that username. 
	 * @throws SQLException
	 */
    // Deletes a user from the database
    public void deleteUser(String userName) throws SQLException {
        String delete = "DELETE FROM cse360users WHERE userName = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(delete)){
            pstmt.setString(1, userName); //gets the username from the user object.

            pstmt.executeUpdate();
        } catch (SQLException e) {
	        e.printStackTrace();
	    }
    }

    /**
     * This method checks if a given login attempt is valid. 
     * @param user ; Based on this username, password and role,  we can search for a row where this username exists. 
     * @return Boolean; returns a boolean if the login information is valid and there exists a user in the database with the information provided. 
     * @throws SQLException
     */
	// Validates a user's login credentials.
	public boolean login(User user) throws SQLException {
		String query = "SELECT * FROM cse360users WHERE userName = ? AND password = ? AND role = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getRole());
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	
	/**
	 * 
	 * This method checks if a user exists based on a Username. 
	 * @param userName ; Similar to the login, we query the DB for a row in the cse360table for any user with the given username. 
	 * @return Boolean; returns true if the user exists, and false if the query doesn't work. 
	 * @throws SQLException
	 */
	// Validates a user's existance
	public boolean userExists(String userName) throws SQLException {
			String query = "SELECT * FROM cse360users WHERE userName = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, userName);
				
				try (ResultSet rs = pstmt.executeQuery()) {
					boolean result = rs.next();
					System.out.print(result);
					return result; //this would be true if it made it this far
					
				} catch (SQLException e) {
			        e.printStackTrace();
			    }
			} catch (SQLException e) {
		        e.printStackTrace();
		    }
			return false;
		}
	
	
	/**
	 * This is a method that checks for valid one time passwords for user account reset. 
	 * @param password ; given a one time password, we check if its used in the DB
	 * @return returns false if it is used, true if it's unused. 
	 * @throws SQLException
	 */
	// Validates a user's login credentials.
	public boolean oneTimeLogin(String password) throws SQLException {
		String query = "SELECT * FROM OneTimePasswords WHERE password = ? AND isUsed = FALSE";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, password);
			try (ResultSet rs = pstmt.executeQuery()) {
				markOneTimePasswordAsUsed(password);
				return rs.next();
			}
		}
	}
	
	
	/**
	 * This method simply marks a one time password as used, voiding it in future endeavors. 
	 * @param password ; given a password, we search the database for it and update the information so it's voided. 
	 */
	// Marks the invitation code as used in the database.
	private void markOneTimePasswordAsUsed(String password) {
	    String query = "UPDATE OneTimePasswords SET isUsed = TRUE WHERE password = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, password);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Updates a password given an username and a new password. Used for resetting passwords. 
	 * @param userName ; get a username, we can search the DB for a row with that username
	 * @param password ; once a row is found, we update the information to this parameter. 
	 * @throws SQLException
	 */
	// Updates Specific Password of a User
    public void setPassword(String userName, String password) throws SQLException {
		String query = "UPDATE cse360users SET password = ? WHERE username = ?"; //Sets all necessary columns to the passed values @ specified username
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, password);
            pstmt.setString(2, userName);

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    }

    /**
     * Creates a one time password
     * @param password ; provided by the admin, a new password is generated into the DB based on the admin's provided password. 
     */
	//this little guy will insert a new password that the admin gives.
	public void createOneTimePassword(String password) {
	    String query = "INSERT INTO OneTimePasswords (password) VALUES ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, password);
            
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	/**
	 * This is another method that allows you to check if a user exists based on their username. For more info, check the previous method. 
	 * @param userName ; check the previous method. This is a duplicate method. 
	 * @return ; check the previous method. This is a duplicate method. 
	 */
	// Checks if a user already exists in the database based on their userName.
	public boolean doesUserExist(String userName) {
	    String query = "SELECT COUNT(*) FROM cse360users WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // If the count is greater than 0, the user exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume user doesn't exist
	}

	/**
	 * Given a username and 6 Booleans, we can update a user's role in the database. 
	 * @param userName  ; Serves as the search key for a user in the DB. 
	 * @param isAdmin ; Updates the admin role
	 * @param isUser ; updates the user role
	 * @param isStaff ; updates the staff role 
	 * @param isInstructor ; updates the instructor role 
	 * @param isReviewer ; updates the reviewer role 
	 * @param isStudent ; updates the student role
	 * @throws SQLException
	 */
    // Updates the specified Usernames Roles on the database, with these Booleans
    public void updateUserRoles(String userName, Boolean isAdmin, Boolean isUser, Boolean isStaff, Boolean isInstructor, Boolean isReviewer, Boolean isStudent) throws SQLException {
		String query = "UPDATE cse360users SET isAdmin = ?, isUser = ?, isStaff = ?, isInstructor = ?, isReviewer = ?, isStudent = ? WHERE username = ?"; //Sets all necessary columns to the passed values @ specified username
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setBoolean(1, isAdmin);
			pstmt.setBoolean(2, isUser);
			pstmt.setBoolean(3, isStaff);
			pstmt.setBoolean(4, isInstructor);
			pstmt.setBoolean(5, isReviewer);
			pstmt.setBoolean(6, isStudent);
            pstmt.setString(7, userName);

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    }

    /**
     * Allows you to update the roles based on the following parameters: 
     * @param userName ; Serves as the search key for a user in the DB. 
     * @param role ; the name of the role you want to alter 
     * @param isRole ; the boolean that switches the role of the searched user 
     * @throws SQLException
     */
	// Updates Specific Usernames Roles on the database, with fed role name and boolean
    public void setUserRoles(String userName, String role, Boolean isRole) throws SQLException {
		String query = "UPDATE cse360users SET " + role + " = ? WHERE username = ?"; //Sets all necessary columns to the passed values @ specified username
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setBoolean(1, isRole);
            pstmt.setString(2, userName);

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    }
	
    /**
     * Allows you get a user's role.
     * @param userName ; Serves as the search key for a user in the DB 
     * @return String  ; The Role of a User (Typically the highest ranking role if they have multiple)
     */
	// Retrieves the role of a user from the database using their UserName.
	public String getUserRole(String userName) {
	    String query = "SELECT role FROM cse360users WHERE userName = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getString("role"); // Return the role if user exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	
	/**
	 * Retrieves the name (different from username) of a user based on their Username. 
	 * @param userName ; Serves as the search key for a user in the DB. 
	 * @return String ; The name of the found user. 
	 */
    // Retrives NAME of User using their username
	public String getName(String userName) {
		String query = "SELECT name FROM cse360users WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getString("name"); // Return NAME if user exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	/**
	 * Returns the email of a user given their username. 
	 * @param userName ; Serves as the search key for a user in the DB. 
	 * @return String ; The Email of the found user. 
	 */

    // Retrives EMAIL of user using their username
	public String getEmail(String userName) {
		String query = "SELECT email FROM cse360users WHERE userName = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, userName);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getString("email"); // Return EMAIL if user exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no user exists or an error occurs
	}
	
	
	/**
	 * Retrieves the isAdmin Boolean from a particular DB using their username. 
	 * @param tableName ; the DB you want to search. 
	 * @param userName ; Serves as the search key for a user in the DB 
	 * @param code ; It's code :)) 
	 * @return Boolean ; True if they're admin, false otherwise. 
	 */
	// Retrives isADMIN var from a particular database using their username
	public Boolean getIsAdmin(String tableName, String userName, String code) {
		
		if(userName == "") {
			String query = "SELECT isAdmin FROM " + tableName + " WHERE code = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, code);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getBoolean("isAdmin"); // Return the isAdmin if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		else {
			String query = "SELECT isAdmin FROM " + tableName + " WHERE userName = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, userName);
				ResultSet rs = pstmt.executeQuery();
	        
				if (rs.next()) {
					return rs.getBoolean("isAdmin"); // Return the isAdmin if user exists
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null; // If no user exists or an error occurs
		}
	}
	
	/**
	 * Retrieves the isUser Boolean from a particular DB using their username. 
	 * @param tableName ; the DB you want to search. 
	 * @param userName ; Serves as the search key for a user in the DB 
	 * @param code ; It's code :)) 
	 * @return Boolean ; True if they're a user, false otherwise. 
	 */
	// Retrives is USER var from a particular database using their username
	public Boolean getIsUser(String tableName, String userName, String code) {
		
		if(userName == "") {
			String query = "SELECT isUser FROM " + tableName + " WHERE code = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, code);
				ResultSet rs = pstmt.executeQuery();
	        
				if (rs.next()) {
					return rs.getBoolean("isUser"); // Return the isUser if user exists
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null; // If no user exists or an error occurs
		}
			
			else {
				String query = "SELECT isUser FROM " + tableName + " WHERE userName = ?";
				try (PreparedStatement pstmt = connection.prepareStatement(query)) {
					pstmt.setString(1, userName);
					ResultSet rs = pstmt.executeQuery();
		        
					if (rs.next()) {
						return rs.getBoolean("isUser"); // Return the isUser if user exists
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null; // If no user exists or an error occurs
				
			}
	}
	
	/**
	 * Retrieves the isStaff Boolean from a particular DB using their username. 
	 * @param tableName ; the DB you want to search. 
	 * @param userName ; Serves as the search key for a user in the DB 
	 * @param code ; It's code :)) 
	 * @return Boolean ; True if they're staff, false otherwise. 
	 */
	
	// Retrives isSTAFF from var from a particular database using their username
	public Boolean getIsStaff(String tableName, String userName, String code) {
		
		if(userName == "") {
			String query = "SELECT isStaff FROM " + tableName + " WHERE code = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, code);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getBoolean("isStaff"); // Return the isStaff if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		else {
			String query = "SELECT isStaff FROM " + tableName + " WHERE userName = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, userName);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getBoolean("isStaff"); // Return the isStaff if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
	}
	
	/**
	 * Retrieves the isInstructor Boolean from a particular DB using their username. 
	 * @param tableName ; the DB you want to search. 
	 * @param userName ; Serves as the search key for a user in the DB 
	 * @param code ; It's code :)) 
	 * @return Boolean ; True if they're instructor, false otherwise. 
	 */
	
	
	// Retrives isINSTRUCTOR var from a particular database using their username
	public Boolean getIsInstructor(String tableName, String userName, String code) {
		
		if(userName =="") {
			String query = "SELECT isInstructor FROM " + tableName + " WHERE code = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, code);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getBoolean("isInstructor"); // Return the isInstructor if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		else {
			String query = "SELECT isInstructor FROM " + tableName + " WHERE userName = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, userName);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getBoolean("isInstructor"); // Return the isInstructor if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		
	}
	
	/**
	 * Retrieves the isReviewer Boolean from a particular DB using their username. 
	 * @param tableName ; the DB you want to search. 
	 * @param userName ; Serves as the search key for a user in the DB 
	 * @param code ; It's code :)) 
	 * @return Boolean ; True if they're reviewer, false otherwise. 
	 */
	
	// Retrives isREVIEWER var from a particular database using their username
	public Boolean getIsReviewer(String tableName, String userName, String code) {
		if(userName == "") {
			String query = "SELECT isReviewer FROM " + tableName + " WHERE code = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, code);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getBoolean("isReviewer"); // Return the isReviewer if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		else {
			String query = "SELECT isReviewer FROM " + tableName + " WHERE userName = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, userName);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getBoolean("isReviewer"); // Return the isReviewer if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		
	}
	
	
	/**
	 * Retrieves the isStudent Boolean from a particular DB using their username. 
	 * @param tableName ; the DB you want to search. 
	 * @param userName ; Serves as the search key for a user in the DB 
	 * @param code ; It's code :)) 
	 * @return Boolean ; True if they're student, false otherwise. 
	 */
	
	// Retrives isSTUDENT var from a particular database using their username
	public Boolean getIsStudent(String tableName, String userName, String code) {
		if(userName =="") {
			String query = "SELECT isStudent FROM " + tableName + " WHERE code = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, code);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getBoolean("isStudent"); // Return the isStudent if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		else {
			String query = "SELECT isStudent FROM " + tableName + " WHERE userName = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        pstmt.setString(1, userName);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            return rs.getBoolean("isStudent"); // Return the isStudent if user exists
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; // If no user exists or an error occurs
		}
		
	}
	/**
	 * Generates a new invitation code and inserts it into the database.
	 * @param deadline ; A time frame that you must use the Invitation Code by 
	 * @param isAdmin ; isAdmin Boolean 
	 * @param isUser ; isUser Boolean
	 * @param isStaff ; isStaff Boolean 
	 * @param isInstructor ; isInstructor Boolean
	 * @param isReviewer ; isReviewer Boolean 
	 * @param isStudent ; isStudent Boolean
	 * @return String ; The code that has all the role information to create a new user. 
	 */
	
	// Generates a new invitation code and inserts it into the database.
    /*Now Takes in arguments to specify what kind of Roles this invitation code will give a 
    user upon creating an account using the Invitation code before the deadline */
	public String generateInvitationCode(double deadline, Boolean isAdmin, Boolean isUser, Boolean isStaff, Boolean isInstructor, Boolean isReviewer, Boolean isStudent) { //Added Arguments to passthrough, 
	    String code = UUID.randomUUID().toString().substring(0, 4); // Generate a random 4-character code
	    String query = "INSERT INTO InvitationCodes (code, deadline, isAdmin, isUser, isStaff, isInstructor, isReviewer, isStudent) VALUES (?,?,?,?,?,?,?,?)";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
            pstmt.setDouble(2, deadline);
            pstmt.setBoolean(3, isAdmin);
            pstmt.setBoolean(4, isUser);
            pstmt.setBoolean(5, isStaff);
            pstmt.setBoolean(6, isInstructor);
            pstmt.setBoolean(7, isReviewer);
            pstmt.setBoolean(8, isStudent);
            
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return code;
	}
	
	
	/**
	 * Validates an invitation code to check if it is unused.
	 * @param code ; the code to valid 
	 * @param timeStamp ; the current time used to check if the code was used within the given time frame
	 * @return true if the Invitation code is valid, false otherwise. 
	 */
	// 
	public boolean validateInvitationCode(String code, double timeStamp) {
	    String query = "SELECT deadline FROM InvitationCodes WHERE code = ? AND isUsed = FALSE";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            // Mark the code as used
	        	if(rs.getDouble("deadline") >= timeStamp){
					markInvitationCodeAsUsed(code);
					return true;
				}
	            return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	/**
	 * Marks the invitation code as used in the database.
	 * @param code ; searches the DB for the code, and marks it as used. 
	 */
	// 
	private void markInvitationCodeAsUsed(String code) {
	    String query = "UPDATE InvitationCodes SET isUsed = TRUE WHERE code = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, code);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	/**
	 * Return All Users in the system
	 * @return LinkedList<User> ; A linked List of all users in the DB. 
	 */
	//
		public LinkedList<User> getAllUsers(){
			String query = "SELECT name, userName, email, isAdmin, isUser, isInstructor, isStaff, isReviewer, isStudent FROM cse360users";
			//Create a 'blank' string to store everything
			LinkedList<User> usersList = new LinkedList<User>();
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					User user = new User(rs.getString("name"), rs.getString("userName"),rs.getString("email"),rs.getBoolean("isAdmin"),rs.getBoolean("isUser"),rs.getBoolean("isInstructor"),rs.getBoolean("isStaff"),rs.getBoolean("isReviewer"),rs.getBoolean("isStudent"));
					usersList.add(user);
		        }
				
			} catch (SQLException e){
				e.printStackTrace();
			}
			return usersList;
		}

		
		/**
		 * Closes the database connection and statement.
		 */
	// 
	public void closeConnection() {
		try{ 
			if(statement!=null) statement.close(); 
		} catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		try { 
			if(connection!=null) connection.close(); 
		} catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}

	
	/**
	 * Stores a New Question on the Database
	 * @param newQuestion ; The new Question object to be added into the data base. 
	 * @return Boolean ; Returns true if the insertion worked. False otherwise. 
	 */
	//Stores a New Question on the Database
	public boolean addNewQuestion(Question newQuestion) {
		String insertQuestion = "INSERT INTO Questions (id, title, questionText, userName, resolved, isSubQuestion, parentQuestionLinkId, hideUserName, preferredAnswerId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pstmt = connection.prepareStatement(insertQuestion)) {
				pstmt.setInt(1, getNewId());
				pstmt.setString(2, newQuestion.getTitle());
				pstmt.setString(3,  newQuestion.getQuestionText());
				pstmt.setString(4,  newQuestion.getUserName());
				pstmt.setBoolean(5, newQuestion.getResolved());
				pstmt.setBoolean(6, newQuestion.getIsSubQuestion());
				pstmt.setInt(7, newQuestion.getParentQuestionLinkId());
				pstmt.setBoolean(8, newQuestion.getHideUserName());
				pstmt.setInt(9, newQuestion.getPreferredAnswerId());
				
				pstmt.executeUpdate();
				
				System.out.println("A new Question was Added");
				return true;
			
			} catch (SQLException e1) {
				System.out.println("The Add Question Did not work");

				e1.printStackTrace();
				return false;
			}
	}
	
	
		
	/**
	 * Counts the amount of questions in the DB. 
	 * @return int ; the amount of questions in the DB 
	 */
	public int countOfQuestions() {
		int count = 0;
		String query = "SELECT COUNT(id) FROM Questions";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				count++;
			}
			
			
		} catch (SQLException e){
			System.out.println("Count Questions Did not work");
			e.printStackTrace();
		}
		if(count <= 0) {
			return 0;
		}
		return count;

	}
	
	
	/**
	 * Returns all Questions in the form of the following return type: 
	 * @return QuestionsList ; A new variable type: A LinkedList of Questions. 
	 */
	public QuestionsList loadQuestionsList() {
		QuestionsList questionsList = new QuestionsList();
		String query = "SELECT * FROM Questions";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				//Create New Question Object
				Question question = new Question(rs.getInt("id"), rs.getString("title"), rs.getString("questionText"), rs.getString("userName"), rs.getBoolean("resolved"), rs.getBoolean("isSubQuestion"), rs.getInt("parentQuestionLinkId"), rs.getBoolean("hideUserName"), rs.getInt("preferredAnswerId"));
				//add QUestion Object to the QuestionsList to return
				questionsList.loadQuestion(question);
				System.out.println("Getting Question with id: " + rs.getInt("id"));
	        }
			
		} catch (SQLException e){
			System.out.println("The Load Quesitons Did not work");

			e.printStackTrace();
		}
		return questionsList;
	}
	
	/**
	 * New ID number generator for Questions and Answers
	 * @return int ; the new ID based on current time. 
	 */
	//
	public int getNewId() {
		return (int) (System.currentTimeMillis() % 1000000000);
	}
	
	
	/**
	 * Stores a New ANSWER on the Database
	 * @param answer ; Given an answer, we can store it in the data base with a query. 
	 * @return Boolean ; true if it worked, false otherwise. 
	 */ 
	//
	public boolean addNewAnswer(Answer answer) {
		String insertQuestion = "INSERT INTO Answers (id, userName, answerText, questionLinkID, hideUserName, answerLinkID, level, highestRole, isReview) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pstmt = connection.prepareStatement(insertQuestion)) {
				pstmt.setInt(1, getNewId());
				pstmt.setString(2, answer.getUserName());
				pstmt.setString(3,  answer.getAnswerText());
				pstmt.setInt(4, answer.getQuestionLinkID());
				pstmt.setBoolean(5,  answer.getHideUserName());
				pstmt.setInt(6, answer.getAnswerLinkID());
				pstmt.setInt(7, answer.getLevel());
				pstmt.setString(8,  answer.getHighestRole());
				pstmt.setBoolean(9,  answer.getIsReview());
 
 				pstmt.executeUpdate();
 					String bool;
 				if(answer.getIsReview()){
 					bool = "true";
 				} else{
 					bool = "false";
 				}
 				System.out.println("This Review: \""+answer.getAnswerText() + "\" has been added to the database as "+bool );
				System.out.println("Answer Added");
				return true;
				
			} catch (SQLException e1) {
				System.out.println("The Add Answer Did not work");
				e1.printStackTrace();
				return false;
			}
		}
	
	
	/**
	 * Returns an AnswersList object will all answers from the database
	 * @return AnswersList ; New data type: LinkedList of Answers. 
	 */
	//
	public AnswersList loadAnswersList() {
		AnswersList answersList = new AnswersList();
		String query = "SELECT * FROM Answers";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				//Create New Question Object
				Answer answer = new Answer(rs.getInt("id"), rs.getString("userName"), rs.getString("answerText"), rs.getInt("questionLinkID"), rs.getBoolean("hideUserName"), rs.getInt("answerLinkID"), rs.getInt("level"), rs.getString("highestRole") );
 				if(rs.getBoolean("isReview")){
 					answer.setIsReview();
 				}
				answersList.loadAnswer(answer);
				System.out.println("Getting Answer with id: " + rs.getInt("id"));
	        }
			
		} catch (SQLException e){
			System.out.println("The Load Answers Did not work");

			e.printStackTrace();
		}
		return answersList;
	}
	
	
	/**
	 *  Deletes a Question from the DB
	 * @param id ; searches DB for a question with that ID
	 * @return Boolean ; true if deleted, false otherwise
	 * @throws SQLException
	 */
	// Deletes a Question from the database
    public boolean deleteQuesiton(int id) throws SQLException {
        String delete = "DELETE FROM Questions WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(delete)){
            pstmt.setInt(1, id); 
            
            deleteAnswersLinked(id);// Delete any AnswersLinked before deleting the question
            
            pstmt.executeUpdate();//deletes Question from DB
            System.out.println("Question " + id +" has been Deleted");
            return true;
        } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Question " + id +" has not been Deleted");
	        return false;
	    }
    }
    
    /**
	 *  Deletes an Answer from the DB
	 * @param id ; searches DB for an answer with that ID
	 * @return Boolean ; true if deleted, false otherwise
	 * @throws SQLException
	 */
 // Deletes a Answer from the database
    public boolean deleteAnswer(int id) throws SQLException {
    	//delete all replies as well
    	deleteRepliesLinked(id);
    	
    	
        String delete = "DELETE FROM Answers WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(delete)){
            pstmt.setInt(1, id); //deletes Answer from DB
            pstmt.executeUpdate();
            System.out.println("Deleting Answer " + id);
            return true;
        } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Answer " + id +" has not been Deleted");
	        return false;
	    }
    }
    
    /**
     * Deletes Answers linked to a specific Question ID from the database
     * @param questionId ; based on an ID, we can delete the answers linked to this question 
     * @throws SQLException
     */
    // Deletes Answers linked to a specific Question ID from the database
    public void deleteAnswersLinked(int questionId) throws SQLException {
        String delete = "DELETE FROM Answers WHERE questionLinkID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(delete)){
            pstmt.setInt(1, questionId); //deletes Question from DB

            pstmt.executeUpdate();
            System.out.println("Deleting Answer linked to Question " + questionId);
        } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error Deleting Answer linked to Question " + questionId);
	    }
    }
    
    /**
     * Deletes Replies linked to a specific Answer ID from the database
     * @param answerId ; based on an ID, we can delete the replies linked to this answer
     * @throws SQLException
     */
 // 
    public void deleteRepliesLinked(int answerId) throws SQLException {
    	
    	AnswersList repliesList = loadRepliesList(answerId);
    	if(repliesList.getSize() > 0) {
    		for(int i=0; i < repliesList.getSize(); i++) {
    			deleteRepliesLinked(repliesList.getAnswerFromIndex(i).getId());
    		}
    	}
        
        String delete = "DELETE FROM Answers WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(delete)){
            pstmt.setInt(1, answerId); //deletes Reply from DB

            pstmt.executeUpdate();
            System.out.println("Deleted Reply Answer Id: " + answerId);
        } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Reply Answer " + answerId +" has not been Deleted");
	    }
        
        
        
    }
    
    /**
     * Returns an AnswersList object will all Replies from a given answerID from the database
     * @param answerId ; the id of an answer who's replies we want to list
     * @return AnswersList ; a list of answers under the provided answer 
     */
  //
  	public AnswersList loadRepliesList(int answerId) {
  		AnswersList answersList = new AnswersList();
  		String query = "SELECT * FROM Answers WHERE answerLinkID = ?";
  		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
  			pstmt.setInt(1, answerId);
  			
  			ResultSet rs = pstmt.executeQuery();
  			while (rs.next()) {
  				//Create New Question Object
  				Answer answer = new Answer(rs.getInt("id"), rs.getString("userName"), rs.getString("answerText"), rs.getInt("questionLinkID"), rs.getBoolean("hideUserName"), rs.getInt("answerLinkID"), rs.getInt("level"), rs.getString("highestRole"));
  				//add QUestion Object to the QuestionsList to return
  				answersList.loadAnswer(answer);
  				System.out.println("Getting Reply with id: " + rs.getInt("id"));
  	        }
  			
  		} catch (SQLException e){
  			System.out.println("The Load Replies Did not work");

  			e.printStackTrace();
  		}
  		return answersList;
  	}
    
  	 /**
  	  * Updates a Specific Quesiton in the database
  	  * @param question ; given a question, we update its attributes in the DB 
  	  * @return boolean ; true if it worked, false otherwise. 
  	  * @throws SQLException
  	  */
    // 
    public boolean updateQuestion(Question question) throws SQLException {
		String query = "UPDATE Questions SET title = ?, questionText = ?, resolved = ?, hideUserName = ?, preferredAnswerId = ? WHERE id = ?"; 
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	    
			pstmt.setString(1, question.getTitle());
			pstmt.setString(2,  question.getQuestionText());
			pstmt.setBoolean(3, question.getResolved());
			pstmt.setBoolean(4, question.getHideUserName());
			pstmt.setInt(5, question.getPreferredAnswerId());
			pstmt.setInt(6, question.getId());


	        pstmt.executeUpdate();
	        System.out.println("Question " + question.getId() +" has been updated");
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Question " + question.getId() +" has not been updated");
	        return false;
	    }
    }
    
    /**
     * Updates  a Specific Answer
     * @param answer ; given an answer, we update its attributes in the DB 
     * @return boolean ; true if it worked, false otherwise 
     * @throws SQLException
     */
    // 
    public boolean updateAnswer(Answer answer) throws SQLException {
		String query = "UPDATE Answers SET userName = ?, answerText = ?, hideUserName = ? WHERE id = ?"; 
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	    	pstmt.setString(1, answer.getUserName());
			pstmt.setString(2,  answer.getAnswerText());
			pstmt.setBoolean(3,  answer.getHideUserName());
	        pstmt.setInt(4, answer.getId());

	        pstmt.executeUpdate();
	        System.out.println("Answer " + answer.getId() +" has been updated");
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Answer " + answer.getId() +" has not been updated");
	        return false;
	    }
    }
    
    
    /**
     * gets an answer based on an ID
     * @param id ; the key to fetch the answer.
     * @return Answer ; returns an answer once it's found in the DB 
     * @throws SQLException
     */
 // Updates  a Specific Answer
    public Answer getAnswerById(int id) throws SQLException {
		String query = "SELECT * FROM Answers WHERE id = ?"; 
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	    	pstmt.setInt(1, id);
	    	ResultSet rs = pstmt.executeQuery();
	    	Answer answer;
	    	while (rs.next()) {
	    		answer = new Answer(rs.getInt("id"), rs.getString("userName"), rs.getString("answerText"), rs.getInt("questionLinkID"), rs.getBoolean("hideUserName"), rs.getInt("answerLinkID"), rs.getInt("level"), rs.getString("highestRole"));
	    		return answer;
	    	}
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
		return null;
    }
    
    /**
     * Updates the specified searchable tags in the QuestionsTable database with these Booleans
     * @param id ; the key to fetch the question 
     * @param homeworkTag ; the hw tag
     * @param conceptTag ; the concept tag 
     * @param examTag ; the exam tag
     * @param generalTag ; the general tag
     * @param socialTag ; the social tag 
     * @throws SQLException
     */
	// 
    public void updateTags(int id, Boolean homeworkTag, Boolean conceptTag, Boolean examTag, Boolean generalTag, Boolean socialTag) throws SQLException {
		String query = "UPDATE Questions SET homeworkTag = ?, conceptTag = ?, examTag = ?, generalTag = ?, socialTag = ?, tag = ? WHERE id = ?"; //Sets all necessary columns to the passed values @ specified username
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setBoolean(1, homeworkTag);
			pstmt.setBoolean(2, conceptTag);
			pstmt.setBoolean(3, examTag);
			pstmt.setBoolean(4, generalTag);
			pstmt.setBoolean(5, socialTag);
			
			System.out.println("Creating new question in database: ");
            
            if(homeworkTag) {
            	pstmt.setString(6, "Homework");
            	System.out.println("Set tag: homework");
            }
            else if(conceptTag) {
            	pstmt.setString(6, "Concept");
            	System.out.println("Set tag: concept");
            }
            else if(examTag) { 
            	pstmt.setString(6, "Exam");
            	System.out.println("Set tag: exam");
            }
            else if(generalTag) {
            	pstmt.setString(6, "General");
            	System.out.println("Set tag: general");
            }
            else {
            	pstmt.setString(6, "Social");
            	System.out.println("Set tag: social");
            }
            pstmt.setInt(7, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tags updated successfully for question ID: " + id);
            } else {
                System.out.println("No question found with ID: " + id);
            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    }

    
    /**
     * Sets the specified tag in the QuestionTable database given a Boolean 
     * @param id ; the key to fetch a question 
     * @param tag ; the tag we want to update/set
     * @param isTag ; what we want the tag to be set to 
     * @throws SQLException
     */
	// 
	public void setQuestionTag(int id, String tag, Boolean isTag) throws SQLException {
		String query = "UPDATE Questions SET " + tag + " = ? WHERE id = ?"; //Sets all necessary columns to the passed values @ specified username
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setBoolean(1, isTag);
            pstmt.setInt(2, id);

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    }

	// This little guy doesn't  work. 
	/*
	public QuestionsList loadQuestionsTag(String tag) {
		QuestionsList questionsList = new QuestionsList();
		System.out.println("GOT HERE RAGH ");
		System.out.println("TAG IS THIS: " + tag);
		String query = "SELECT * FROM Questions WHERE tag = ?"; //maybe ==? 
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, tag);
			ResultSet rs = pstmt.executeQuery();  //This  ain't working. 
			while (rs.next()) {
				//Create New Question Object
				System.out.println("Here ya go: " + rs.getString("title"));
				Question question = new Question(rs.getInt("id"), rs.getString("title"), rs.getString("questionText"), rs.getString("userName"), rs.getBoolean("resolved"), rs.getBoolean("isSubQuestion"), rs.getInt("parentQuestionLinkId"), rs.getBoolean("hideUserName"), rs.getInt("preferredAnswerId"));
				//add QUestion Object to the QuestionsList to return
				questionsList.loadQuestion(question);
				System.out.println("Getting Question with id: " + rs.getInt("id"));
	        }
			
		} catch (SQLException e){
			System.out.println("The Load Quesitons Did not work");

			e.printStackTrace();
		}
		return questionsList;
	}
	*/
	
	 /**
	  * Loads all questions with a certain given tag 
	  * @param tag ; the tag we want to search by 
	  * @return QuestionsList ; A LinkedList of Questions that allow us to display each question!
	  */
	public QuestionsList loadQuestionsListTags(String tag) {
		QuestionsList questionsList = new QuestionsList();
		QuestionsList returnList = new QuestionsList();
		String query = "SELECT * FROM Questions";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				//Create New Question Object
				Question question = new Question(rs.getInt("id"), rs.getString("title"), rs.getString("questionText"), rs.getString("userName"), rs.getBoolean("resolved"), rs.getBoolean("isSubQuestion"), rs.getInt("parentQuestionLinkId"), rs.getBoolean("hideUserName"), rs.getInt("preferredAnswerId"));
				//add QUestion Object to the QuestionsList to return
				questionsList.loadQuestion(question);
				System.out.println("Getting Question with id: " + rs.getInt("id"));
	        } 
			for(int i = 0; i < questionsList.getSize(); i++) {
				System.out.println();
				System.out.println(getTag(questionsList.getQuestionFromIndex(i).getId()));
				
				if(tag.contains(getTag(questionsList.getQuestionFromIndex(i).getId()))) {
					returnList.loadQuestion(questionsList.getQuestionFromIndex(i));
					System.out.println("Added new: " + i);
				}
			}
			
		} catch (SQLException e){
			System.out.println("The Load Quesitons Did not work");

			e.printStackTrace();
		}
		return returnList;
	}
	
	
	/**
	 * Gets the tag given a Question's ID. 
	 * @param id ; the key to search for a question's tag
	 * @return String ; the name of the tag the question has. 
	 */
	public String getTag(int id) {
		String query = "SELECT tag FROM Questions WHERE id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	        	System.out.println(rs.getString("tag") + " is the tag.");
	            return rs.getString("tag"); // Return tag if the question Exists
	        }
	        else {
	        	System.out.println("Not working.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // If no question exists or an error occurs
	}
	
	/**
	 * Gets an ID based on the text 
	 * @param text ; the key to search for a question 
	 * @return int ; the id of a question 
	 */
	//get a ID based on the text 
	public int getQuestionID(String text) {
		String query = "SELECT id FROM Questions WHERE questionText = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, text);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt("id"); // Return id if question exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; // If no user exists or an error occurs
	}
	
	/**
	 * gets a Question based on text
	 * @param text ; the key to search for a question 
	 * @return Question ; just a question 
	 */
	public Question getQuestion(String text) {
        String query = "SELECT * FROM Questions WHERE questionText = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, text);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Question question = new Question(rs.getInt("id"), rs.getString("title"), rs.getString("questionText"), rs.getString("userName"), rs.getBoolean("resolved"), rs.getBoolean("isSubQuestion"), rs.getInt("parentQuestionLinkId"), rs.getBoolean("hideUserName"), rs.getInt("preferredAnswerId"));
                return question; // Return id if question exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // If no user exists or an error occurs
    }

	
	/**
	 * Sets an answer as a review or reverting back an answer
	 * @param answer ; the answer we want to insert
	 * @param review ; the change from answer to review or vice versa. 
	 */
	// setIsReview updates the boolean based on an answer and a given true/false 
	public void setIsReview(Answer answer, Boolean review){
		String query = "UPDATE Answers SET isReview = ? WHERE answerText = ?"; 
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setBoolean(1, review);
            pstmt.setString(2, answer.getAnswerText());

	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}



	// String ReviewerRequestsTable = "CREATE TABLE IF NOT EXISTS ReviewerRequests ("
	// 			+ "id INT PRIMARY KEY, "
	// 			+ "username VARCHAR(17), "
	// 			+ "approved BOOLEAN, "
	// 			+ "denied BOOLEAN)";
	// 	statement.execute(ReviewerRequestsTable);


	// 	String insertUser = "INSERT INTO cse360users (userName, password, role, isAdmin, isUser, isStaff, isInstructor, isReviewer, isStudent, name, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	
	/* try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3,  user.getRole());
			pstmt.setBoolean(4, user.getIsAdmin());
			pstmt.setBoolean(5, user.getIsUser());
			pstmt.setBoolean(6, user.getIsStaff());
			pstmt.setBoolean(7, user.getIsInstructor());
			pstmt.setBoolean(8, user.getIsReviewer());
			pstmt.setBoolean(9, user.getIsStudent());
            pstmt.setString(10, user.getName());
            pstmt.setString(11, user.getEmail());
			
			pstmt.executeUpdate();
		}
	*/

	
	
	// **************************
	/** This will add a user to the table given their userName. 
	 * This is also going to have both approve and denied set to false. 
	 * @param userName ; The Username of whoever made a request. 
	 */
	public void addRequest(String userName){
		String addUser = "INSERT INTO ReviewerRequests (userName, approved, pending) VALUES (?, ?, ?)";
			try (PreparedStatement pstmt = connection.prepareStatement(addUser)){
				pstmt.setString(1, userName); // set userName
				pstmt.setBoolean(2, false); // set approved boolean
				pstmt.setBoolean(3,  true); // set pending boolean

			pstmt.executeUpdate();
			System.out.println("Added user to request list");
		} catch (SQLException e){
			System.out.println("Adding user did not work.");

			e.printStackTrace();
		}
	}
	
	// Add a query here to search if userName is in the Request Database**********************

	
	// Get all pending requests
	 	/** Gets all pending requests
	 	 * @return ArrayList<String> ; the ArrayList of usenames in the form of Strings that wanted to be a reviewer. 
	 	 */
	 	public ArrayList<String> getPending(){
	 		// Create ArrayList
	 		ArrayList<String> pendingUserNames = new ArrayList<>();
	 
	 		String query = "SELECT * FROM ReviewerRequests WHERE pending = TRUE";
	 
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)){
	 			ResultSet rs = pstmt.executeQuery();
	 
	 			while(rs.next()){
	 				String userName = rs.getString("userName");
	 				pendingUserNames.add(userName);
	 			}
	 
	 		} catch (SQLException e){
	 			e.printStackTrace();
	 		}
	 		
	 		
	 		return pendingUserNames;
	 	}
	 
	 	// 
	 	/** Set approved and update pending once Instructor responds to request
	 	 * @param givenName ; the key to finding an entry in the ReviwerRequests DB 
	 	 * @param decision ; True or False that allows or rejects a user to become a reviewer. 
	 	 */
	 	public void decisionMade(String givenName, Boolean decision){
	 		// Find entry with userName
	 		String query = "UPDATE ReviewerRequests SET approved = ?, pending = ? WHERE userName = ?";
	 
	 		// Once found update following
	 			// approved to decision
	 			// pending to FALSE
	 		try(PreparedStatement pstmt = connection.prepareStatement(query)){
	 			pstmt.setBoolean(1, decision);
	 			pstmt.setBoolean(2, false);
	 			pstmt.setString(3, givenName);
	 
	 			pstmt.executeUpdate();
	 		} catch (SQLException e){
	 			e.printStackTrace();
	 		}
	 	}
	 
	 	// 
	 	/**Get decision for user to see 
	 	 * 0 = pending, 1 = denied, 2 = approved
	 	 * @param userName ; gets the decision of a username 
	 	 * @return int ; based on the pending,denied or approved state
	 	 */
	 	public int getDecision(String userName){
	 		int answer = 0;
	 
	 		String query = "SELECT * FROM ReviewerRequests WHERE userName = ?";
	 
	 		try(PreparedStatement pstmt = connection.prepareStatement(query)){
	 			pstmt.setString(1, userName);
	 			ResultSet rs = pstmt.executeQuery();
	 
	 			if(rs.next()){
	 				boolean pending = rs.getBoolean("pending");
	 				boolean approved = rs.getBoolean("approved");
	 
	 				if (pending){
	 					return 0;
	 				} else if (approved){
	 					return 2;
	 				} else {
	 					return 1;
	 				}
	 			}
	 			
	 		} catch (SQLException e){
	 			e.printStackTrace();
	 		}
	 
	 		return answer;
	 	}
	 
	 
	 	
	 // 
		/** Checks if a user already exists in the database based on their userName.
		 * @param userName ; the key to search the ReviewrRequests page if they exists
		 * @return Boolean ; true if they exists. Otherwise, false. 
		 */
		public boolean checkUserExists(String userName) {
		    String query = "SELECT COUNT(*) FROM ReviewerRequests WHERE userName = ?";
		    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
		        
		        pstmt.setString(1, userName);
		        ResultSet rs = pstmt.executeQuery();
		        
		        if (rs.next()) {
		            // If the count is greater than 0, the user exists
		            return rs.getInt(1) > 0;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return false; // If an error occurs, assume user doesn't exist
		}
	 	
	 	
	 	
	 	
	 	// *************



	// Load Questions By Username 
	/** loads all questions of a user 
	 * @param username ; the key to load all questions of a user
	 * @return QuestionsList ; a list of every question a user has. 
	 */
	public QuestionsList loadQuestionsByUsername(String username) {
		//QuestionsList questionsList = new QuestionsList();
		QuestionsList returnList = new QuestionsList();
		String query = "SELECT * FROM Questions WHERE userName = ? ";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				//Create New Question Object
				Question question = new Question(rs.getInt("id"), rs.getString("title"), rs.getString("questionText"), rs.getString("userName"), rs.getBoolean("resolved"), rs.getBoolean("isSubQuestion"), rs.getInt("parentQuestionLinkId"), rs.getBoolean("hideUserName"), rs.getInt("preferredAnswerId"));
				//add QUestion Object to the QuestionsList to return
				returnList.loadQuestion(question);
				System.out.println("Getting Question with id: " + rs.getInt("id"));
	        } 
			//commented out as we're using the "WHERE userName thing, but if that doesn't work, use the following:"
			/*for(int i = 0; i < questionsList.getSize(); i++) {
				System.out.println();
				System.out.println(getTag(questionsList.getQuestionFromIndex(i).getId()));
				
				if(tag.contains(getTag(questionsList.getQuestionFromIndex(i).getId()))) {
					returnList.loadQuestion(questionsList.getQuestionFromIndex(i));
					System.out.println("Added new: " + i);
				}
			}*/
			
		} catch (SQLException e){
			System.out.println("The Load Quesitons Did not work");

			e.printStackTrace();
		}
		return returnList;
	}

	// Load Answer By Username 
	/** loads all answer of a user 
	 * @param username ; the key to load all answer of a user
	 * @return AnswersList ; a list of every answer a user has. 
	 */
	public AnswersList loadAnswerByUsername(String username) {
		//QuestionsList questionsList = new QuestionsList();
		AnswersList returnList = new AnswersList();
		String query = "SELECT * FROM Answers WHERE userName = ? ";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				//Create New Question Object
				Answer answer = new Answer(rs.getInt("id"),rs.getString("userName"),  rs.getString("answerText"), rs.getInt("questionLinkID"), rs.getBoolean("hideUserName"), rs.getInt("answerLinkID"), rs.getInt("level"), rs.getString("highestRole"));
				//add Question Object to the QuestionsList to return
				returnList.loadAnswer(answer);
				System.out.println("Getting Answer with id: " + rs.getInt("id"));
	        } 
			//commented out as we're using the "WHERE userName thing, but if that doesn't work, use the following:"
			/*for(int i = 0; i < questionsList.getSize(); i++) {
				System.out.println();
				System.out.println(getTag(questionsList.getQuestionFromIndex(i).getId()));
				
				if(tag.contains(getTag(questionsList.getQuestionFromIndex(i).getId()))) {
					returnList.loadQuestion(questionsList.getQuestionFromIndex(i));
					System.out.println("Added new: " + i);
				}
			}*/
		} catch (SQLException e){
			System.out.println("The Load Answers Did not work");

			e.printStackTrace();
		}
		return returnList;
	}
	
	/** loads all reviews of a user 
	 * @param username ; the key to load all reviewers of a user
	 * @return AnswersList ; a list of every review a user has. 
	 */
	
	public AnswersList loadReviewsByUsername(String username) {
		AnswersList answersList = new AnswersList();
		AnswersList returnList = new AnswersList();
		String query = "SELECT * FROM Answers WHERE (userName, isReview) = (?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.setBoolean(2, true);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				//Create New Question Object
				Answer answer = new Answer(rs.getInt("id"),rs.getString("userName"),  rs.getString("answerText"), rs.getInt("questionLinkID"), rs.getBoolean("hideUserName"), rs.getInt("answerLinkID"), rs.getInt("level"),rs.getString("highestRole"));
				//add Question Object to the QuestionsList to return
					answer.setIsReview();
				returnList.loadAnswer(answer);
				System.out.println("Getting Answer with id: " + rs.getInt("id"));
	        } 
			
			for(int i = 0; i < answersList.getSize(); i++) {
				System.out.println();
				System.out.println(getTag(answersList.getAnswerFromIndex(i).getId()));
				
				if(answersList.getAnswerFromIndex(i).getIsReview() == true) {
					returnList.loadAnswer(returnList.getAnswerFromIndex(i));
					System.out.println("Added new: " + i);
				}
			}
		} catch (SQLException e){
			System.out.println("The Load Answers Did not work");

			e.printStackTrace();
		}
		return returnList;
	}
	
	//REVIEWER TABLES AND RANKING FUNCTIONS========================================================
	 	//=============================================================================================
	 
	 	//
	
	
	 	/** Will Add a new Reviewer to the ReviewerTable if they already aren't there
	 	 * @param userName ; adds the user to the reviewer table by username 
	 	 * @return ; true if it worked, false otherwise. 
	 	 */
	 	public boolean addNewReviewer(String userName){
	 		refreshReviewerTables();
	 		if(!alreadyInReviewerTable(userName)){
	 				String insertQuestion = "INSERT INTO ReviewerTable (userName) VALUES (?)";
	 					try (PreparedStatement pstmt = connection.prepareStatement(insertQuestion)) {
	 						pstmt.setString(1, userName);
	 						
	 						pstmt.executeUpdate();
	 						
	 						System.out.println("A new Ranked Reviewer "+ userName + " was Added");
	 						return true;
	 					
	 					} catch (SQLException e1) {
	 						System.out.println("A Ranked Reviewer was NOT Added");
	 
	 						e1.printStackTrace();
	 						return false;
	 					}
	 			} else {
	 				System.out.println("A Reviewer was Already in the System");
	 				return false;
	 			}
	 		
	 	}
	
	 	
	 	//
	 	/**Check existance of a given rating
	 	 * @param userName ; search key to base search off of 
	 	 * @param reviewerRating ; the rating we want to search by 
	 	 * @return True if the rating is already in the table. False otherwise. 
	 	 */
	 	public Boolean alreadyHasRating(String userName, ReviewerRating reviewerRating){ 
	 		String query = "SELECT * FROM ReviewerRating WHERE (userName, reviewerName) = (?,?)";
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			pstmt.setString(1, userName);
	 			pstmt.setString(2, reviewerRating.getReviewerName());
	 
	 			ResultSet rs = pstmt.executeQuery();
	 			while (rs.next()) {
	 				System.out.println("Rating Already in Table");
	 				return true;
	 	        }
	 			
	 		} catch (SQLException e){
	 			System.out.println("Checking for Existing Rating Failed");
	 
	 			e.printStackTrace();
	 		}
	 		return false;
	 	}
	
	 	
	 	/** Checks if a user is already in the reviewer table 
	 	 * @param reviewerName ; the key to search by 
	 	 * @return True if they're already in, false otherwise. 
	 	 */
	 	public Boolean alreadyInReviewerTable(String reviewerName){ 
	 		String query = "SELECT * FROM ReviewerTable WHERE userName = ?";
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			pstmt.setString(1, reviewerName);
	 
	 			ResultSet rs = pstmt.executeQuery();
	 			while (rs.next()) {
	 				System.out.println("Reviewer Already in Table");
	 				return true;
	 	        }
	 		} catch (SQLException e){
	 			System.out.println("Checking for Existing Reviewer Failed");
	 
	 			e.printStackTrace();
	 		}
	 		return false;
	 	}
	 
	 	/** Adds a new rating to the reviewer table 
	 	 * @param userName ; the user we want to add a new rating to 
	 	 * @param reviewerRating ; the updated review 
	 	 * @return true if it worked, false otherwise 
	 	 */
	 	public boolean addNewRating(String userName, ReviewerRating reviewerRating){
	 		refreshReviewerTables();
	 
	 		if(alreadyHasRating(userName, reviewerRating)){
	 			System.out.println("Reviewer was already in the System");
	 			return false;
	 		} else {
	 			String insertQuestion = "INSERT INTO ReviewerRating (userName, reviewerName, score, isTrusted) VALUES (?,?,?,?)";
	 				try (PreparedStatement pstmt = connection.prepareStatement(insertQuestion)) {
	 					pstmt.setString(1, userName);
	 					pstmt.setString(2, reviewerRating.getReviewerName());
	 					pstmt.setInt(3, reviewerRating.getScore());
	 					pstmt.setBoolean(4, reviewerRating.getIsTrusted());
	 
	 					
	 					pstmt.executeUpdate();
	 					
	 					System.out.println("A new Reviewer Rating was Added");
	 					return true;
	 				
	 				} catch (SQLException e1) {
	 					System.out.println("A Rating addition had an Error");
	 
	 					e1.printStackTrace();
	 					return false;
	 				}
	 		}
	 	}
	 
	 	
	 	//This little puppy will update the score of a given rating
	 	/** Updates the score of a given rating score 
	 	 * @param userName ; the username of the scorer 
	 	 * @param reviewerName ; the name of the reviewer we want to update 
	 	 * @param newScore ; the score we want to use to update 
	 	 */
	 	public void updateRatingScore(String userName, String reviewerName, int newScore){
	 		String query = "UPDATE ReviewerRating SET score = ? WHERE (userName, reviewerName) = (?, ?)"; 
	 	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			pstmt.setInt(1, newScore);
	 			pstmt.setString(2,  userName);
	 			pstmt.setString(3, reviewerName);
	 
	 	        pstmt.executeUpdate();
	 	        System.out.println("Rating Score Updated");
	 
	 	    } catch (SQLException e) {
	 	        e.printStackTrace();
	 	        System.out.println("Rating Update Malfunction.");
	 	    }
	 	}
	 
	 	//This little puppy will update the isTrusted of a given rating
	 	/**
	 	 * @param userName ; the username of the truster
	 	 * @param reviewerName ; the possibly trusted reviewer 
	 	 * @param isTrusted ; boolean, true if they're trusted. false otherwise. 
	 	 */
	 	public void updateIsTrusted(String userName, String reviewerName, Boolean isTrusted){
	 		String query = "UPDATE ReviewerRating SET isTrusted = ? WHERE (userName, reviewerName) = (?, ?)"; 
	 	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			pstmt.setBoolean(1, isTrusted);
	 			pstmt.setString(2,  userName);
	 			pstmt.setString(3, reviewerName);
	 
	 	        pstmt.executeUpdate();
	 	        System.out.println("Rating Trust Updated");
	 
	 	    } catch (SQLException e) {
	 	        e.printStackTrace();
	 	        System.out.println("Rating Trust Malfunction.");
	 	    }
	 	}
	 
	 	/**  gets the amount of users in the reviewer rating 
	 	 * @param userName ; gets the users from the rating list 
	 	 * @return int ; the number of people in the rating list count 
	 	 */
	 	public int ratingListCount(String userName) {
	 		int count = 0;
	 		String query = "SELECT * FROM ReviewerRating WHERE userName = ?";
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			pstmt.setString(1, userName);
	 			ResultSet rs = pstmt.executeQuery();
	 			while (rs.next()) {
	 				count++;
	 	        }
	 			System.out.println("Rating List Counted");
	 
	 		} catch (SQLException e){
	 			System.out.println("The Count Rating List did not work");
	 
	 			e.printStackTrace();
	 		}
	 		return count;
	 	}
	 
	 	
	 	
	 	//
	 	/** Returns an ArrayList<ReviewerRating> object will all RATINGS from the ReviewerRating Table with Specified UserName
	 	 * @param userName ; the ratings a user made for specific reviewers 
	 	 * @param databaseHelper ; the database :)) 
	 	 * @return ArrayList<ReviewerRating> object ; all the ratings from the reviewer rating table 
	 	 */
	 	public ArrayList<ReviewerRating> loadRatingList(String userName, DatabaseHelper databaseHelper) {
	 		refreshReviewerTables();
	 		ArrayList<ReviewerRating> ratingList = new ArrayList<>();
	 		String query = "SELECT * FROM ReviewerRating WHERE userName = ?";
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			pstmt.setString(1, userName);
	 			ResultSet rs = pstmt.executeQuery();
	 			while (rs.next()) {
	 				ReviewerRating rating = new ReviewerRating(rs.getString("reviewerName"), rs.getInt("score"), rs.getBoolean("isTrusted"), databaseHelper);
	 				ratingList.add(rating);
	 	        }
	 			System.out.println("Rating List Loaded");
	 
	 		} catch (SQLException e){
	 			System.out.println("The Load Rating List did not work");
	 
	 			e.printStackTrace();
	 		}
	 		return ratingList;
	 	}
	 	
	 	
	 	//
	 	/**Returns whether a given user, has a given user listed as trusted
	 	 * @param userName ; the user 
	 	 * @param reviewerName ; the reviewer a user may or may not trust 
	 	 * @return ; true if trust, false otherwise 
	 	 */
	 	public Boolean userTrustedReviewer(String userName, String reviewerName) {
	 		Boolean tf = false;
	 		ArrayList<ReviewerRating> ratingList = new ArrayList<>();
	 		String query = "SELECT * FROM ReviewerRating WHERE (userName, reviewerName) = (?, ?)";
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			pstmt.setString(1, userName);
	 			pstmt.setString(2, reviewerName);
	 			ResultSet rs = pstmt.executeQuery();
	 			while (rs.next()) {
	 				tf = rs.getBoolean("isTrusted");
	 	        }
	 			System.out.println("Boolean Trust for reviewer Loaded");
	 
	 		} catch (SQLException e){
	 			System.out.println("The Trust Boolean did not work");
	 
	 			e.printStackTrace();
	 		}
	 		return tf;
	 	}
	 	
	 
	 
	 	/** 
	 	 * @param userName the name of a user to check the database of 
	 	 * @param databaseHelper the data base :)) 
	 	 * @return  ArrayList<String> ; every reviewer that doesn't have a rated
	 	 */
	 	public ArrayList<String> notYetRatedList(String userName, DatabaseHelper databaseHelper){
	 		ArrayList<ReviewerRating> ratingList = loadRatingList(userName, databaseHelper);
	 		ArrayList<String> reviewersList = loadReviewersList();
	 		ArrayList<String> unRatedList = new ArrayList<>();
	 		Boolean tf = false;
	 		for(int i=0; i<reviewersList.size(); i++){
	 			tf = false;
	 			for(int j=0; j<ratingList.size(); j++){
	 				if(ratingList.get(j).getReviewerName().equals(reviewersList.get(i))){
	 					tf = true;
	 					break;
	 				} 
	 			}
	 			if(!tf){
	 				unRatedList.add(reviewersList.get(i));
	 			}
	 		}
	 		return unRatedList;
	 	}
	 	
	 	
	 	
	 	//
	 	/**Return A Listed of Globally Weighted Reviewers
	 	 * @param databaseHelper the data base :)) 
	 	 * @return ArrayList<ReviewerRating> ; a list of all the global reviews for each reviewer 
	 	 */
	 	public ArrayList<ReviewerRating> getGlobalRankingReviewers(DatabaseHelper databaseHelper){
	 		ArrayList<ReviewerRating> global = new ArrayList<>();
	 		String query = "SELECT * FROM ReviewerTable";														//For all reviews
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			ResultSet rs = pstmt.executeQuery();
	 			while (rs.next()) {
	 				String tempReviewerName = rs.getString("userName");											//Use their username
	 				int score = 0;
	 				
	 				//Sub Query
	 				String subQuery = "SELECT * FROM ReviewerRating WHERE reviewerName = ?";					//For all ratings with their name
	 		 		try (PreparedStatement subPstmt = connection.prepareStatement(subQuery)) {
	 		 			subPstmt.setString(1, tempReviewerName);
	 		 			ResultSet subRs = subPstmt.executeQuery();
	 		 			while (subRs.next()) {
	 		 				score += subRs.getInt("score");														// add up the score
	 		 	        }
	 		 			System.out.println("Score added");
	 		 
	 		 		} catch (SQLException e){
	 		 			System.out.println("The adding did not work");
	 		 
	 		 			e.printStackTrace();
	 		 		}
	 				ReviewerRating newRank = new ReviewerRating(tempReviewerName, score, false, databaseHelper);//Create a new obj JUST to store their name and score
	 				
	 				Boolean addToEnd = true;
	 				for(int i=0; i<global.size(); i++) {														//If their score is greater than the current one, insert it there.
	 					if(newRank.getScore() > global.get(i).getScore()) {										//Effectively sorting it as we go
	 						global.add(i, newRank);
							addToEnd = false;
							break;
	 					}
	 				} 
					if(addToEnd){
						global.add(newRank);
					}
	 				
	 	        } 
	 			} catch (SQLException e){
	 	 			System.out.println("Global Outside Did not work");
	 	 			e.printStackTrace();
	 	        }
	 		return global;
	 		
	 	}
	 
	 	//
	 	/**Returns an ArrayList<String> object will all Reviewers from the ReviewerTable database
	 	 * @return ArrayList<String> ; the list of reviewer names in the reviewer table 
	 	 */
	 	public ArrayList<String> loadReviewersList() {
	 		refreshReviewerTables();
	 		ArrayList<String> reviewersList = new ArrayList<>();
	 		String query = "SELECT * FROM ReviewerTable";
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			ResultSet rs = pstmt.executeQuery();
	 			while (rs.next()) {
	 				reviewersList.add(rs.getString("userName"));
	 				
	 				System.out.println("Loading Reviewer " + rs.getString("userName"));
	 	        } 
	 			} catch (SQLException e){
	 	 			System.out.println("The Load Reviewers Did not work");
	 	 			e.printStackTrace();
	 	        }
	 		return reviewersList;
	 	}
	 	
	 // 
	     /**Deletes a Reviewer from the ReviewerTable && the ReviewerRating database
	     * @param userName ; the username of a reviewer we want to delete 
	     * @throws SQLException
	     */
	    public void deleteReviewer(String userName) throws SQLException {
	 		refreshReviewerTables();
	 		//DELETE FROM THE REVIEWERTABLE
	         String delete = "DELETE FROM ReviewerTable WHERE userName = ?";
	         try (PreparedStatement pstmt = connection.prepareStatement(delete)){
	             pstmt.setString(1, userName); 
	             
	             
	             pstmt.executeUpdate();//deletes Question from DB
	             System.out.println("Reviewer " + userName +" has been Deleted from the ReviewerTable");
	         } catch (SQLException e) {
	 	        e.printStackTrace();
	 	        System.out.println("Reviewer " + userName +" has not been Deleted from the ReviewerTable");
	 	    }
	 
	 		//DELETE FROM THE REVIEWERRATING
	 		delete = "DELETE FROM ReviewerRating WHERE reviewerName = ?";
	         try (PreparedStatement pstmt = connection.prepareStatement(delete)){
	             pstmt.setString(1, userName); 
	             
	             
	             pstmt.executeUpdate();//deletes Question from DB
	             System.out.println("Reviewer " + userName +" has been Deleted from the ReviewerRating");
	         } catch (SQLException e) {
	 	        e.printStackTrace();
	 	        System.out.println("Reviewer " + userName +" has not been Deleted from the ReviewerRating");
	 	    }
	     }
	 
	 
	 
	 	//
	 	/**
	 	 * This little guy refreshed the REVIEWERRATING AND REVEWIERTABLE and deletes any that aren't supposed to be there
	 	 */
	 	public void refreshReviewerTables(){	
	 		//REFRESH THE REVIEWERTABLE
	 		String query = "SELECT * FROM ReviewerTable";
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			ResultSet rs = pstmt.executeQuery();
	 			while (rs.next()) {
	 				String userName = rs.getString("userName");
	 				if(!getIsReviewer("cse360users", userName, "")){
	 					deleteReviewer(userName);
	 					System.out.println("Reviewer " + userName + " deleted on Reviewer List Refresh");
	 				}				
	 	        }
	 			
	 		} catch (SQLException e){
	 			System.out.println("The Refresh Did not work");
	 
	 			e.printStackTrace();
	 		}
	 
	 		//REFRESH THE REVIEWERRATING TABLE
	 		query = "SELECT * FROM ReviewerRating";
	 		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	 			ResultSet rs = pstmt.executeQuery();
	 			while (rs.next()) {
	 				String userName = rs.getString("reviewerName");
	 				if(!getIsReviewer("cse360users", userName, "")){
	 					deleteReviewer(userName);
	 					System.out.println("Reviewer " + userName + " deleted on Rating List Refresh");
	 				}
	 	       
	 			}} catch (SQLException e){
	 				System.out.println("The Refresh Did not work");
	 				e.printStackTrace();
	 	 		}
	 		}
	
	
	
	
	// 
	/**Gathers every conversation a user has 
	 * @param user ; the key to search the coversation table for 
	 * @return ArrayList<String> ; a list of every conversation a user has 
	 */
	public ArrayList<String> getAllConversations(User user){
		ArrayList<String> conversations= new ArrayList<String>();
		// if the User is a Student
		if(user.getIsStudent() == true && user.getIsAdmin() == false) {
			System.out.println("Checking this Student's Conversations...");
			String query = "SELECT revUsername FROM Conversations WHERE stuUsername = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, user.getUserName());
		        ResultSet rs = pstmt.executeQuery();
		        while (rs.next()) {
					//Create Convo String
					String convo = rs.getString("revUsername");
					System.out.println(convo + " is a convo.");
					//Adds Convo to Conversations
					conversations.add(convo);
		        } 
		        
		        System.out.println("All done!");
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		// if the User is a Reviewer
		else {
			System.out.println("Checking this Reviewers's Conversations...");
			String query = "SELECT stuUsername FROM Conversations WHERE revUsername = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(query)) {
				pstmt.setString(1, user.getUserName());
				System.out.println("Username is " + user.getUserName());
		        ResultSet rs = pstmt.executeQuery();
		        while (rs.next()) {
					//Create Convo String
					String convo = rs.getString("stuUsername");
					System.out.println(convo + " is a convo.");
					//Adds Convo to Conversations
					conversations.add(convo);
		        } 
		        System.out.println("All done!");
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		 System.out.println("the size of the ArrayList is: " + conversations.size());
		return conversations; 
	}
	
	// 
	// IDK if this works but we'll hope so 
	/** gets the connectionID from conversations based on usernames to access the PM database messages easier.  
	 * @param user1 ; first user (student) 
	 * @param user2 ; second user (reviewer) 
	 * @return int ; the connection id. -1 if it doesn't exist. 
	 */
	public int getConnectionID(String user1, String user2) {
        String query = "SELECT connectionID FROM Conversations WHERE "
        		+ "(stuUsername = ? AND revUsername = ?) "
        		+ "OR (stuUsername = ? AND revUsername = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        	pstmt.setString(1, user1);
            pstmt.setString(2, user2);
            pstmt.setString(3, user2);
            pstmt.setString(4, user1); 
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
            	int id = rs.getInt("connectionID");
            	return id; // Return id if convo exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // If no convo exists 
    }
	
	// Creates a newMessage using a PM. 
	// IDK if this works but we'll hope so
	/**Creates a newMessage using a PM. 
	 * @param pm ; a private message object that is used to insert into the PM table 
	 * @return Boolean ; true if works, false otherwise 
	 */
	public Boolean newMessage(PrivateMessage pm) {
		String insertQuestion = "INSERT INTO PMs (stuUsername, revUsername, connectionID, message, time, sender) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertQuestion)) {
			int time = (int) (System.currentTimeMillis() / 1000);
			pm.setTime(time);
			pstmt.setString(1, pm.getStuUsername());
			pstmt.setString(2,  pm.getRevUsername());
			pstmt.setInt(3,  pm.getConnectionID());
			pstmt.setString(4, pm.getMessage());
			pstmt.setInt(5, time);
			pstmt.setString(6, pm.getSender());
			
			
			pstmt.executeUpdate();
			
			System.out.println("A new PM was Added");
			return true;
		
		} catch (SQLException e1) {
			System.out.println("The Add PM did not work");

			e1.printStackTrace();
			return false;
		}
	}
	
	// 
	/** loadsAllMessages using connectionID
	 * @param connectionID ; the id of the conversation we access 
	 * @return ArrayList<PrivateMessage> ; every message in the conversation 
	 */
	public ArrayList<PrivateMessage> loadAllMessages(int connectionID){
		ArrayList<PrivateMessage> returnList = new ArrayList<PrivateMessage>(); 
		String query = "SELECT stuUsername, revUsername, message, time, sender FROM PMs WHERE connectionID = ? ORDER BY time ASC";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, connectionID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				//Create new PM object using constructor2
				PrivateMessage message = new PrivateMessage(rs.getString("stuUsername"),rs.getString("revUsername"),  connectionID, rs.getString("message"), rs.getInt("time"), rs.getString("sender"));
				//add PM Object to the returnList
				returnList.add(message);
	        } 
			
		} catch (SQLException e){
			System.out.println("The Load All Messages Did not work");

			e.printStackTrace();
		}
		return returnList;
	}
	
	/** makes a new convo between 2 users 
	 * @param user1 user 1 
	 * @param user2 user 2 
	 * @return boolean ; true if it worked, false otherwise
	 */
	public Boolean newConversations(String user1, String user2) {
        String query = "INSERT INTO Conversations (stuUsername, revUsername) VALUES (?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user1);
            pstmt.setString(2,  user2);

            pstmt.executeUpdate();

            System.out.println("A new conversation was Added");
            return true;

        } catch (SQLException e1) {
            System.out.println("The add conversations did not work");

            e1.printStackTrace();
            return false;
        }
    }
	
	
	

	
	
}

	
	