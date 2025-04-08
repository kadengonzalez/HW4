package databasePart1;
//Original Code By Professor Lynn Robert Carter. Original has been Editied by Bracken Peterson Jan 2025
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

/**
 * The SetupAdmin class handles the setup process  for creating an administrator account.
 * This is intended to be used by the first user to initialize the system with admin credentials.
 */
public class AdminSetupPage {
	
    private final DatabaseHelper databaseHelper;

    public AdminSetupPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

 /**
  * Prompts the user to set up an admin account.
  */
    public void show(Stage primaryStage) {
    	// Input fields for userName and password
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter Admin Username");
        userNameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);

        TextField emailField = new TextField();  //changed 
        emailField.setPromptText("Enter Admin Email");
        emailField.setMaxWidth(250);

        TextField nameField = new TextField();  //changed 
        nameField.setPromptText("Enter Admin Name");
        nameField.setMaxWidth(250);
        
        //Label to keep track of Username errors
        Label usernameErrorLabel = new Label();
        usernameErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        //Label to keep track of Password errors
        Label passwordErrorLabel = new Label();
        passwordErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        

        Button setupButton = new Button("Setup");
        
        setupButton.setOnAction(a -> {
        	// Retrieve user input
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String email    = emailField.getText(); //changed 
            String name     = nameField.getText(); //changed 
            
            //check usrname and pwd, and update respective labels
            usernameCheck(userName, usernameErrorLabel);
            passwordCheck(password, passwordErrorLabel); 
            
            try {
            	// If the errorLabel for UserName Specifically String is empty
            	if (usernameErrorLabel.getText() == "") { 
            		
            		//make sure there are no password errors
            		if(passwordErrorLabel.getText() == "") {
            			
            			// Create a new User object with admin role and register in the database
            			String role = "admin";
            			User user=new User(userName, password, name, email, role); //changed 
            			//add ALL ROLES to Admin to user's roleList
            			user.setIsAdmin(true);
            			user.setIsUser(true);
            			user.setIsStaff(true);
            			user.setIsInstructor(true);
            			user.setIsReviewer(true);
            			user.setIsStudent(true);
            			
            			databaseHelper.register(user);
                        databaseHelper.addNewReviewer(user.getUserName()); //Adds the User to the ReviewerTable so they can be ranked
            			System.out.println("Administrator setup completed.");
                
            			// Navigate to the User Login Page to fulfill User Story
            			new UserLoginPage(databaseHelper).show(primaryStage);
            		}
            		
            	}
            	
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        });

        VBox layout = new VBox(10, userNameField, passwordField, nameField, emailField, setupButton, usernameErrorLabel, passwordErrorLabel); //changed 
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Scene scene = new Scene(layout, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());
        primaryStage.setScene(scene); // Might need adjustment 
        primaryStage.setTitle("Administrator Setup");
        primaryStage.show();
    }
    
    /**
     * Checks if a valid username is given. 
     * @param userName The entered username as a string.
     * @param errorLabel A label if there is an error.
     */
    public void usernameCheck(String userName, Label errorLabel) {
    	//get the error, and update the label
    	String errMessage = UserNameRecognizer.checkForValidUserName(userName);//Sends Current UserName through the Validation Check
    	updateErrorLabel(errMessage, errorLabel); //updates the label according to the error returned in the previous line.
    }
    
    /**
     * Checks if a valid password is given. 
     * @param password The entered password as a string.
     * @param passwordErrorLabel A label if there is an error.
     */
    public void passwordCheck(String password, Label passwordErrorLabel) {
    	String errMessage = PasswordEvaluator.evaluatePassword(password);//get potential error message from password check
    	if(errMessage != "") {
    		errMessage = ("Please Follow Password Requirments: " + errMessage); //added the txt so the user knows that the erros are for the pwd
    	}
    	updateErrorLabel(errMessage, passwordErrorLabel); //updates the label according to the error returned in the previous line.
    }
    
    /**
     * Updates the error label. 
     * @param errMessage The error message as a string.
     * @param AnErrorLabel The new error label.
     */
    public void updateErrorLabel(String errMessage, Label AnErrorLabel) {
    	//if the error is blank, the potential previous error should go away
    	if(errMessage == "") {
    		AnErrorLabel.setText(""); //setting the label to blank
    	}
    	//if the error is not "" it should appear until fixed
    	else {
    		AnErrorLabel.setText(errMessage); //setting the label to the error message
    	}
    }
    
} //end of class


