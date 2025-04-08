package databasePart1;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * For creating a one time password(s) allowing any user 
 * to login with and reset their password. 
 */
public class ChangePasswordPage {
	
	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the ChangePasswordPage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public ChangePasswordPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    /**
     * Creates UI and functionality to allow the user to enter a one 
     * time password for any user to utilize. 
     * 
     * @param primaryStage Passes the current stage being used.
     */
	public void show(Stage primaryStage) {
		// Create a layout for the page 
		Stage newStage = new Stage();
		Pane pane = new Pane();
		DisplayUtilities displayUtilities = new DisplayUtilities();
		
		
		// Display instructions to user		
		Label instructionLabel = displayUtilities.createLabel("Please enter a one time password for any user: ", 20, 20);
		
		
		// Create input field for user input
		TextField inputField = displayUtilities.createTextField(20, 50);
		
		
		// Create label for expressing success		
		Label resultLabel = displayUtilities.createLabel("", 200, 200);
		
		
		//Submit password button		
		Button submitButton = displayUtilities.createButton("Enter", 175, 50);
		
		submitButton.setOnAction(e -> {
			String userInput = inputField.getText();
			databaseHelper.createOneTimePassword(userInput);
			resultLabel.setText("Password created! You can close this window.");
		});
		
		
		// Add all elements to the layout		
		pane.getChildren().addAll(resultLabel, submitButton, inputField, instructionLabel);
		
		
		// Make page visible
		Scene scene = new Scene(pane, 750, 400);
		scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());
		newStage.setScene(scene);
		newStage.setTitle("UpdateRolesPage");
		newStage.show();
	}
}