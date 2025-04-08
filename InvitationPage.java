package databasePart1;

//Original Code By Professor Lynn Robert Carter.
import databasePart1.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * InvitePage class represents the  page where an admin can generate an invitation code.
 * The invitation code is displayed upon clicking a button.
 */

public class InvitationPage {

	/**
     * Displays the Invite Page in the provided primary stage.
     * 
     * @param databaseHelper An instance of DatabaseHelper to handle database operations.
     * @param primaryStage   The primary stage where the scene will be displayed.
     */
    public void show(DatabaseHelper databaseHelper,Stage primaryStage, User user) {
    	VBox layout = new VBox();
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    // Label to display the title of the page
	    Label userLabel = new Label("Invite");
	    userLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

		//Give the user a prompt as to what to do
		Label descriptionLabel = new Label("What roles would you like to assign them?\nPlease Enter the number days this code valid for.");
	    descriptionLabel.setStyle("-fx-font-size: 10px;");

		//Checkboxes to allow Admin to assign different roles to this specific Invite Code
		CheckBox checkAdmin = new CheckBox("Is an Admin");
        CheckBox checkUser = new CheckBox("Is an User");
        checkUser.setSelected(true);
        checkUser.setDisable(true);
        CheckBox checkStaff = new CheckBox("Is a Staff");
		CheckBox checkInstructor = new CheckBox("Is an Instructor");
        CheckBox checkReviewer = new CheckBox("Is an Reviewer");
        CheckBox checkStudent = new CheckBox("Is a Student");
	    
        // Button to generate the invitation code
	    Button showCodeButton = new Button("Generate Invitation Code");
	    
	    // Label to display the generated invitation code
	    Label inviteCodeLabel = new Label(""); ;
        inviteCodeLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic;");
        
        
        Button switchRole = new Button ("Switch Roles");  //changed 
		switchRole.setOnAction( a-> {   
				WelcomeLoginPage welcomeLoginPage = new WelcomeLoginPage(databaseHelper); 
				welcomeLoginPage.show(primaryStage, user);
		});
		
		//Error Label for not selecting a checkbox
		Label errorLabel = new Label("Please Select At Least One Role"); ;
		errorLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic; -fx-text-fill: red;");
		errorLabel.setVisible(false);
        
        showCodeButton.setOnAction(a -> {
			//if at least one checkbox is marked TRUE
			if(checkAdmin.isSelected() || checkUser.isSelected() || checkStaff.isSelected() ||  checkInstructor.isSelected() || checkReviewer.isSelected() || checkStudent.isSelected() ){
				double deadline = System.currentTimeMillis();
				deadline = (double)(deadline / 60000);
				deadline += (double)(10080); //add 7 days to dealine
				
				
				errorLabel.setVisible(false); //make the error go away
        		// Generate the invitation code using the databaseHelper and set it to the label
           		String invitationCode = databaseHelper.generateInvitationCode(deadline, checkAdmin.isSelected(), checkUser.isSelected(), checkStaff.isSelected(), checkInstructor.isSelected(), checkReviewer.isSelected(), checkStudent.isSelected()); //EDIT
            	inviteCodeLabel.setText(invitationCode);
			}
			else {
				errorLabel.setVisible(true);
			}
        });
	    

        layout.getChildren().addAll(userLabel, descriptionLabel, checkAdmin, checkUser, checkStaff, checkInstructor, checkReviewer, checkStudent, showCodeButton, inviteCodeLabel, switchRole, errorLabel);
	    Scene inviteScene = new Scene(layout, 800, 400);
	    inviteScene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

	    // Set the scene to primary stage
	    primaryStage.setScene(inviteScene);
	    primaryStage.setTitle("Invite Page");
    	
    }
}