package databasePart1;

import java.sql.SQLException;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Contains all functionality and creates UI for updating the roles of a user. 
 */
public class UpdateRolesPage {
	
	private final DatabaseHelper databaseHelper;

	
	/**
	 * Constructor for the UpdateRolesPage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public UpdateRolesPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    
    /**
     * Creates functionality and UI that allows admin to search for user.
     * If user exists, will display current roles in buttons. 
     * Admin can then add or remove roles using buttons. 
     * 
     * @param primaryStage Passes the current stage being used.
     */
	public void show(Stage primaryStage) {
		// create new window
		Stage newStage = new Stage();
		Pane pane = new Pane();
		DisplayUtilities displayUtilities = new DisplayUtilities();
		
		
		// Create input field
		TextField inputField = new TextField();
		inputField.setLayoutX(55);
		inputField.setLayoutY(25);
		
		
		// Create response label
		Label searchResponseLabel = displayUtilities.createLabel("", 70, 75);
		
		
		// Create search button
		Button searchButton = displayUtilities.createButton("SEARCH", 230, 25);
		
		
		// Create button for every role a person could have
		Button isAdmin = displayUtilities.createButton("Admin", 100, 150);
	
		Button isReviewer = displayUtilities.createButton("Reviewer", 250, 150);

		Button isUser = displayUtilities.createButton("User", 400, 150);

		Button isStudent = displayUtilities.createButton("Student", 100, 200);

		Button isInstructor = displayUtilities.createButton("Instructor", 250, 200);

		Button isStaff = displayUtilities.createButton("Staff", 400, 200);

		
		
		// Action listener for the searchButton
		searchButton.setOnAction(e -> {
			// Collect name provided by admin
			String userInput = inputField.getText();
			
			// Determine if user exists
			boolean userFound;
			try {
				userFound = databaseHelper.userExists(userInput);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				userFound = false;
				e1.printStackTrace();
			}  
		
			// Initialize all buttons to grey
			displayUtilities.setButtonGrey(isAdmin);
			
			displayUtilities.setButtonGrey(isReviewer);
			
			displayUtilities.setButtonGrey(isUser);
			
			displayUtilities.setButtonGrey(isStudent);
			
			displayUtilities.setButtonGrey(isInstructor);
			
			displayUtilities.setButtonGrey(isStaff);
			
			
			// Set button colors if user is found
			if (userFound) { 
				searchResponseLabel.setText(userInput +"'s roles: ");
				
				String tableName = "cse360users";
				
				// Set buttons to green if user possesses that role
				if(databaseHelper.getIsAdmin(tableName, userInput, "")) { displayUtilities.setButtonGreen(isAdmin); }	
				if(databaseHelper.getIsReviewer(tableName, userInput, "")) { displayUtilities.setButtonGreen(isReviewer); }
				if(databaseHelper.getIsUser(tableName, userInput, "")) { displayUtilities.setButtonGreen(isUser); }
				if(databaseHelper.getIsStudent(tableName, userInput, "")) { displayUtilities.setButtonGreen(isStudent); }
				if(databaseHelper.getIsInstructor(tableName, userInput, "")) { displayUtilities.setButtonGreen(isInstructor); }
				if(databaseHelper.getIsStaff(tableName, userInput, "")) { displayUtilities.setButtonGreen(isStaff); }
				
				// Set buttons to red if user does not possess that role
				if(!databaseHelper.getIsAdmin(tableName, userInput, "")) { displayUtilities.setButtonRed(isAdmin); }	
				if(!databaseHelper.getIsReviewer(tableName, userInput, "")) { displayUtilities.setButtonRed(isReviewer); }
				if(!databaseHelper.getIsUser(tableName, userInput, "")) { displayUtilities.setButtonRed(isUser); }
				if(!databaseHelper.getIsStudent(tableName, userInput, "")) { displayUtilities.setButtonRed(isStudent); }
				if(!databaseHelper.getIsInstructor(tableName, userInput, "")) { displayUtilities.setButtonRed(isInstructor); }
				if(!databaseHelper.getIsStaff(tableName, userInput, "")) { displayUtilities.setButtonRed(isStaff); }
				
				
				// Update admin role on click
				isAdmin.setOnAction(i -> {
					// If person is admin
					if (!databaseHelper.getIsAdmin(tableName, userInput, "")) {
							
						try {
							databaseHelper.setUserRoles(userInput, "isAdmin", true);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// Set button green
						displayUtilities.setButtonGreen(isAdmin);
					}
				});


				// Update reviewer role on click
				isReviewer.setOnAction(i -> {
						if (databaseHelper.getIsReviewer(tableName, userInput, "")) { // If they are reviewer
							
							try {
								databaseHelper.setUserRoles(userInput, "isReviewer", false); // Remove reviewer role
								databaseHelper.refreshReviewerTables(); //Deletes the reviewer from the 2 tables.
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} // REMOVE ACCESS FROM DATABASE

							displayUtilities.setButtonRed(isReviewer); // Update button color
						} else { // If they are not reviewer
							try {
								databaseHelper.setUserRoles(userInput, "isReviewer", true); // Add reviewer role
								databaseHelper.addNewReviewer(userInput); //Adds the User to the ReviewerTable so they can be ranked
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// ADD ACCESS TO DATA BASE

							displayUtilities.setButtonGreen(isReviewer); // Update button color
						}

				});

				// Update user role on click
				isUser.setOnAction(b -> {
						if (databaseHelper.getIsUser(tableName, userInput, "")) {
							try {
								databaseHelper.setUserRoles(userInput, "isUser", false);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// REMOVE ACCESS FROM DATABASE

							displayUtilities.setButtonRed(isUser);
						}
						else {
							try {
								databaseHelper.setUserRoles(userInput, "isUser", true);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// ADD ACCESS TO DATA BASE

							displayUtilities.setButtonGreen(isUser);
						}

				});

				// student button
				isStudent.setOnAction(d -> {
						if (databaseHelper.getIsStudent(tableName, userInput, "")) {
							try {
								databaseHelper.setUserRoles(userInput, "isStudent", false);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// REMOVE ACCESS FROM DATABASE

							displayUtilities.setButtonRed(isStudent);
						}
						else {
							try {
								databaseHelper.setUserRoles(userInput, "isStudent", true);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// ADD ACCESS TO DATA BASE

							displayUtilities.setButtonGreen(isStudent);
						}

				});

				// instructor 
				isInstructor.setOnAction(f -> {
						if (databaseHelper.getIsInstructor(tableName, userInput, "")) {
							try {
								databaseHelper.setUserRoles(userInput, "isInstructor", false);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// REMOVE ACCESS FROM DATABASE

							displayUtilities.setButtonRed(isInstructor);
						}
						else {
							try {
								databaseHelper.setUserRoles(userInput, "isInstructor", true);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// ADD ACCESS TO DATA BASE

							displayUtilities.setButtonGreen(isInstructor);
						}

				});

				// staff
				isStaff.setOnAction(g -> {
						if (databaseHelper.getIsStaff(tableName, userInput, "")) {
							try {
								databaseHelper.setUserRoles(userInput, "isStaff", false);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// REMOVE ACCESS FROM DATABASE

							displayUtilities.setButtonRed(isStaff);
						}
						else {
							try {
								databaseHelper.setUserRoles(userInput, "isStaff", true);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}// ADD ACCESS TO DATA BASE

							displayUtilities.setButtonGreen(isStaff);
						}

				});
				
				
			} else if (!userFound) {
				searchResponseLabel.setText(userInput + " not found");
			}
		});
		
		
		// Add all children to window
		pane.getChildren().add(inputField);
		pane.getChildren().add(searchResponseLabel);
		pane.getChildren().add(searchButton);
		pane.getChildren().add(isAdmin);
		pane.getChildren().add(isReviewer);
		pane.getChildren().add(isUser);
		pane.getChildren().add(isStudent);
		pane.getChildren().add(isInstructor);
		pane.getChildren().add(isStaff);

		Scene scene = new Scene(pane, 750, 400);
		scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());
		newStage.setScene(scene);;
		newStage.setTitle("UpdateRolesPage");
		newStage.show();
	}


	//set the location of the buttons
	public void setButtonLocation(Button button, double xVal, double yVal) {
		button.setLayoutX(xVal);
		button.setLayoutY(yVal);
	}
}