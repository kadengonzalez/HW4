package databasePart1;

//Original Code By Professor Lynn Robert Carter.  Original has been Editied by Bracken Peterson Jan 2025
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

/**
 * The UserLoginPage class provides a login interface for users to access their accounts.
 * It validates the user's credentials and navigates to the appropriate page upon successful login.
 */
public class UserLoginPage {
	
    private final DatabaseHelper databaseHelper;

    /**
	 * Constructor for the UserLoginPage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public UserLoginPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * Creates interface for allowing a user to login to the system. 
     * Displays an error if login information is not correct. 
     * 
     * @param primaryStage Passes the current stage being used.
     */
    public void show(Stage primaryStage) {
    	//Intsruction Label
    	Label instructionLabel = new Label();
        instructionLabel.setText("Please enter your login information:");
        instructionLabel.setId("discussion-question");
        
    	// Input field for the user's userName, password
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter Username");
        userNameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);
        
        // Label to display any error messages
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        Button loginButton = new Button("Login");
        
        // create an action listener for the login button
        loginButton.setOnAction(a -> {
        	// Retrieve user inputs
            String userName = userNameField.getText();            
            String password = passwordField.getText();
            
            // try to log in the user
            try {
            	User user = new User(userName, password, "", "", "");
            	WelcomeLoginPage welcomeLoginPage = new WelcomeLoginPage(databaseHelper);
            	
            	String role = databaseHelper.getUserRole(userName);
            	
            	if(role!=null) {
            		user.setRole(role);

                    //If they use a OTP with a valid username, set them to the update password page
                    if(databaseHelper.oneTimeLogin(password)){ 
                        new UpdatePasswordPage(databaseHelper).show(primaryStage, user);
                    }

                    else {
                        //if they didn't use a valid OTP try logging them in normally
            		    if(databaseHelper.login(user)) { 

                            // Retrieve the user's role from the database using userName
                            Boolean isAdmin = databaseHelper.getIsAdmin("cse360users", userName, "");
                            Boolean isUser = databaseHelper.getIsUser("cse360users", userName, "");
                            Boolean isStaff = databaseHelper.getIsStaff("cse360users", userName, "");
                            Boolean isInstructor = databaseHelper.getIsInstructor("cse360users", userName, "");
                            Boolean isReviewer = databaseHelper.getIsReviewer("cse360users", userName, "");
                            Boolean isStudent = databaseHelper.getIsStudent("cse360users", userName, "");
                            
                            //Setting the Users Info from the SQL Query
                            user.setIsAdmin(isAdmin);
                            user.setIsUser(isUser);
                            user.setIsStaff(isStaff);
                            user.setIsInstructor(isInstructor);
                            user.setIsReviewer(isReviewer);
                            user.setIsStudent(isStudent);

                            welcomeLoginPage.show(primaryStage,user);
            		    }
                        else {
                            // Display an error if the login fails
                            errorLabel.setText("Error logging in");
                        }
                    }
            	}
            	else {
            		// Display an error if the account does not exist
                    errorLabel.setText("user account doesn't exists");
            	}
            	
            // catch any SQL errors that could occur
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            } 
        });

        // Settings for VBox and display it for user
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(instructionLabel, userNameField, passwordField, loginButton, errorLabel);
        Scene scene = new Scene(layout, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("User Login");
        primaryStage.show();
    }
}
