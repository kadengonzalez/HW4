package databasePart1;

import java.sql.SQLException;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Allows admin to delete other user accounts from the system. 
 */
public class DeleteUserPage {
	
	
	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the DeleteUserPage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public DeleteUserPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    
    /**
     * Creates UI and functionality to allow user to 
     * input a userName and delete them from system. 
     * 
     * @param primaryStage Passes the current stage being used.
     */
	public void show(Stage primaryStage) {
		// Create layout for the page
		VBox layout = new VBox();
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    
		// Create elements for the stage
	    TextField inputTextField = new TextField();
	    
	    Button collectButton = new Button("Search");
	    
	    Label responseLabel = new Label();
	    
	    Label tempLabel = new Label();
	    
		Button confirmationButton = new Button("Yes, delete user");
		confirmationButton.setVisible(false); // Hide button until confirmed that user exists

		Label confirmationLabel = new Label();
		confirmationLabel.setText("Are you sure");
		confirmationLabel.setVisible(false); // Hide label until confirmed that the user exists
		
		
		// collectButton functionality
	    collectButton.setOnAction(e -> {
	    	// Collect user input
	    	String userInput = inputTextField.getText(); 
	    	
	    	// Create Variable
	    	boolean inDataBase; 
	    	
	    	// Deleting the User
			try {
				// Check if the user exits before attempting to delete them (aka remove them from database)
				inDataBase = databaseHelper.userExists(userInput);
				
				// User was found
				if (inDataBase) {
					// Inform admin user was found
		    		responseLabel.setText(userInput + " is in the data base:");
		    		
					// Display confirmation label and button
					confirmationLabel.setVisible(true);
		    		confirmationButton.setVisible(true);
					
		    		// On confirmation
		    		confirmationButton.setOnAction(fv ->{  
		    			// Inform admin that deletion process is underway
		    			tempLabel.setText("Deleting " + userInput);
		    			
		    			// Remove user from DataBase
						try {
							databaseHelper.deleteUser(userInput);  // Delete user from the data base
						} catch (SQLException e1) {
							e1.printStackTrace();
						}   
						
						// Reset page so admin can delete another user
						inputTextField.setText("");
						responseLabel.setText("");
						confirmationLabel.setVisible(false);
						confirmationButton.setVisible(false);
						responseLabel.setVisible(false);
		    		});
		    	} else {
		    		// Inform admin that user is not in the database
		    		responseLabel.setText(userInput + " is not a user");
		    	}
			} catch (SQLException e1) {
				// Catch any errors that could occur
				e1.printStackTrace();
			} 	
	    });
	    
	    
	    // Add all elements to the page
	    layout.getChildren().add(inputTextField);
	    layout.getChildren().add(collectButton);
	    layout.getChildren().add(responseLabel);
		layout.getChildren().add(confirmationLabel);
	    layout.getChildren().add(confirmationButton);
	    layout.getChildren().add(tempLabel);
	    
	    
	    // Configure page and make visible
	    Scene deleteUserScene = new Scene(layout, 400, 200);
	    deleteUserScene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());
	    primaryStage.setScene(deleteUserScene);
	    primaryStage.setTitle("Delete User Page");
	    primaryStage.show();
	}
}