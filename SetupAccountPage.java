package databasePart1; 
//Original Code By Professor Lynn Robert Carter. Original has been Editied by Bracken Peterson Jan 2025
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import java.lang.classfile.Label;
import java.sql.SQLException;

import databasePart1.*;

/**
 * SetupAccountPage class handles the account setup process for new users.
 * Users provide their userName, password, and a valid invitation code to register.
 */
public class SetupAccountPage {
	
    private final DatabaseHelper databaseHelper;
    // DatabaseHelper to handle database operations.
    public SetupAccountPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * Displays the Setup Account page in the provided stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
    public void show(Stage primaryStage) {
    	
    	// Input fields for userName, password, and invitation code, email and name
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter Username");
        userNameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);
        
        TextField inviteCodeField = new TextField();
        inviteCodeField.setPromptText("Enter InvitationCode");
        inviteCodeField.setMaxWidth(250);

        TextField emailField = new TextField();  //changed 
        emailField.setPromptText("Enter Email");
        emailField.setMaxWidth(250);

        TextField nameField = new TextField();  //changed 
        nameField.setPromptText("Enter Name");
        nameField.setMaxWidth(250);
        
        // Label to display error messages for invalid input or registration issues
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        //Passwords own Label to keep track of errors
        Label passwordErrorLabel = new Label();
        passwordErrorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        Button setupButton = new Button("Setup");
        
        setupButton.setOnAction(a -> {
        	// Retrieve user input
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String code = inviteCodeField.getText();
            String email = emailField.getText(); //changed 
            String name = nameField.getText(); //changed 
            
            //check usrname and pwd, and update respective labels
            usernameCheck(userName, errorLabel);
            passwordCheck(password, passwordErrorLabel); 
            
            try {
            	
            	// If the errorLabel for UserName Specifically String is empty
            	if (errorLabel.getText() == "") { 
            		
            		//make sure there are no password errors
            		if(passwordErrorLabel.getText() == "") {
            		
            			// Check if the user already exists
            			if(!databaseHelper.doesUserExist(userName)) {
            		
                            double currentTime = (double)(System.currentTimeMillis() / 60000);
            				// Validate the invitation code
            				if(databaseHelper.validateInvitationCode(code, currentTime)) {
            			
            					// Create a new user and register them in the database
            					User user = new User(userName, password, name, email, "User"); //changed 
            					databaseHelper.register(user);
            					String tableName = "InvitationCodes";
                                databaseHelper.updateUserRoles(user.getUserName(), databaseHelper.getIsAdmin(tableName, "", code), databaseHelper.getIsUser(tableName, "", code), databaseHelper.getIsStaff(tableName, "", code), databaseHelper.getIsInstructor(tableName, "", code), databaseHelper.getIsReviewer(tableName, "", code), databaseHelper.getIsStudent(tableName, "", code));
                                if(databaseHelper.getIsReviewer(tableName, "", code)){
                                    databaseHelper.addNewReviewer(user.getUserName()); //Adds the User to the ReviewerTable so they can be ranked
                                }
                                
            					
                                // Navigate to the User Login Page to fulfill User Story
                                new UserLoginPage(databaseHelper).show(primaryStage);
            				}
            				else {
            					errorLabel.setText("Please enter a valid invitation code");
            				}
            			}
            			else {
            				errorLabel.setText("This useruserName is taken!!.. Please use another to setup an account");
            			}
            		}
            	}	
            	
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(userNameField, passwordField, inviteCodeField, emailField, nameField,  setupButton, errorLabel, passwordErrorLabel); //changed
        Scene scene = new Scene(layout, 800, 400);
		scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Account Setup");
        primaryStage.show();
    }
    
    /**
     * This checks if the userName being passed is valid 
     * and updates the errorLabel if it is not valid with the 
     * new error message. 
     * 
     * @param userName This is the name of the current user. 
     * @param errorLabel This is the label to display any errors. 
     */
    public void usernameCheck(String userName, Label errorLabel) {
    	//get the error, and update the label
    	String errMessage = UserNameRecognizer.checkForValidUserName(userName);//Sends Current UserName through the Validation Check
    	updateErrorLabel(errMessage, errorLabel); //updates the label according to the error returned in the previous line.
    }
    
    
    //Check if a valid password has been chosen
    /**
     * This checks if the password being passed is valid
     * and updates passwordErrorLabel if it is not valid with 
     * the new error message. 
     * 
     * @param password This is the password being checked. 
     * @param passwordErrorLabel This is the label to display any errors. 
     */
    public void passwordCheck(String password, Label passwordErrorLabel) {
    	String errMessage = PasswordEvaluator.evaluatePassword(password);//get potential error message from password check
    	if(errMessage != "") {
    		errMessage = ("Please Follow Password Requirments: " + errMessage); //added the txt so the user knows that the erros are for the pwd
    	}
    	updateErrorLabel(errMessage, passwordErrorLabel); //updates the label according to the error returned in the previous line.
    }
    
    //Helper Function to update the error label for username/password creation
    /**
     * This is responsible for updating the label with the error message
     * that is being passed. 
     * 
     * @param errMessage The text of the error message. 
     * @param AnErrorLabel Label for where text is to be displayed. 
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
    
    
    
}
