package databasePart1;
//Original Code By Professor Lynn Robert Carter. Original has been Editied by Bracken Peterson Jan 2025
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;

import databasePart1.*;

/**
 * The WelcomeLoginPage class displays a welcome screen for authenticated users.
 * It allows users to navigate to their respective pages based on their role or quit the application.
 */
public class WelcomeLoginPage {
	
	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the ReviewerHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public WelcomeLoginPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    /**
     * This page displays buttons for each role that is available. 
     * Only roles that a user has access to will be displayed. 
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     */
    public void show( Stage primaryStage, User user) {
    	
    	VBox layout = new VBox(5);
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    Label welcomeLabel = new Label("Welcome!!");
	    welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

	    //ADMIN role button
	    Button adminButton = new Button("Continue to your Admin Page");
	    if(!user.getIsAdmin()) { adminButton.setVisible(false); } //set button to invisible if user doesnt have this role
	    adminButton.setOnAction(a -> {
	    	String role = user.getRole();
	    	System.out.println(role);
	    	new AdminHomePage(databaseHelper).show(user, primaryStage);
	    }); 	  
	    
	    //USER role button
	    Button userButton = new Button("Continue to your User Page");
	    if(!user.getIsUser()) { userButton.setVisible(false); } //set button to invisible if user doesnt have this role
	    userButton.setOnAction(a -> {
	    	String role = user.getRole();
	    	System.out.println(role);
	    	new UserHomePage(databaseHelper).show(primaryStage, user);
	    });
	    
	    //STAFF role button
	    Button staffButton = new Button("Continue to your Staff Page");
	    if(!user.getIsStaff()) { staffButton.setVisible(false); } //set button to invisible if user doesnt have this role
	    staffButton.setOnAction(a -> {
	    	String role = user.getRole();
	    	System.out.println(role);
	    	new StaffHomePage(databaseHelper).show(primaryStage, user);
	    });
	    
	    //INSTRUCTOR role button
	    Button instructorButton = new Button("Continue to your Instructor Page");
	    if(!user.getIsInstructor()) { instructorButton.setVisible(false); } //set button to invisible if user doesnt have this role
	    instructorButton.setOnAction(a -> {
	    	String role = user.getRole();
	    	System.out.println(role);
	    	new InstructorHomePage(databaseHelper).show(primaryStage, user);
	    });
	    
	    //REVIEWER role button
	    Button reviewerButton = new Button("Continue to your Reviewer Page");
	    if(!user.getIsReviewer()) { reviewerButton.setVisible(false); } //set button to invisible if user doesnt have this role
	    reviewerButton.setOnAction(a -> {
	    	String role = user.getRole();
	    	System.out.println(role);
	    	new ReviewerHomePage(databaseHelper).show(primaryStage, user);
	    });
	    
	    //STUDENT role button
	    Button studentButton = new Button("Continue to your Student Page");
	    if(!user.getIsStudent()) { studentButton.setVisible(false); } //set button to invisible if user doesnt have this role
	    studentButton.setOnAction(a -> {
	    	String role = user.getRole();
	    	System.out.println(role);
	    	new StudentHomePage(databaseHelper).show(primaryStage, user);
	    }); 	    
	    
	    // Button to quit the application
	    Button quitButton = new Button("Quit");
	    quitButton.setOnAction(a -> {
	    	databaseHelper.closeConnection();
	    	Platform.exit(); // Exit the JavaFX application
	    });
	    
	    // "Invite" button for admin to generate invitation codes
	    //if ("admin".equals(user.getRole())) {
		if (databaseHelper.getIsAdmin("cse360users", user.getUserName(), "")) {
            Button inviteButton = new Button("Invite");
            inviteButton.setOnAction(a -> {
                new InvitationPage().show(databaseHelper, primaryStage, user);
            });
            layout.getChildren().add(inviteButton);
        }

		Button logout = new Button("Logout"); //Changed 
		logout.setOnAction( a-> {
				UserLoginPage userLoginPage = new UserLoginPage(databaseHelper); 
				userLoginPage.show(primaryStage);
		});

		// Button to navigate to the user's respective page based on their role
		int count = 0; 
		if(databaseHelper.getIsAdmin("cse360users", user.getUserName(), "")) {count++;}
		if(databaseHelper.getIsUser("cse360users", user.getUserName(), "")) {count++;}
		if(databaseHelper.getIsStaff("cse360users", user.getUserName(), "")) {count++;}
		if(databaseHelper.getIsInstructor("cse360users", user.getUserName(), "")) {count++;}
		if(databaseHelper.getIsReviewer("cse360users", user.getUserName(), "")) {count++;}
		if(databaseHelper.getIsStudent("cse360users", user.getUserName(), "")) {count++;}
		System.out.println(count);
		if(count == 1){
			count = 0; 
			System.out.println("Inside If STatement");
			if(databaseHelper.getIsAdmin("cse360users", user.getUserName(), "")) {new AdminHomePage(databaseHelper).show(user, primaryStage);}
			if(databaseHelper.getIsStaff("cse360users", user.getUserName(), "")) {new StaffHomePage(databaseHelper).show(primaryStage, user);}
			if(databaseHelper.getIsInstructor("cse360users", user.getUserName(), "")) {new InstructorHomePage(databaseHelper).show(primaryStage, user);}
			if(databaseHelper.getIsReviewer("cse360users", user.getUserName(), "")) {new ReviewerHomePage(databaseHelper).show(primaryStage, user);}
			if(databaseHelper.getIsStudent("cse360users", user.getUserName(), "")) {
				new StudentHomePage(databaseHelper).show(primaryStage, user);
			}
			else{
				// click the userButton automatically
				Platform.runLater(() -> {
					userButton.fire();
				});
			}

		}
		count = 0; 

	

	    layout.getChildren().addAll(welcomeLabel,adminButton, userButton, staffButton, instructorButton, reviewerButton, studentButton, quitButton, logout);
	    Scene welcomeScene = new Scene(layout, 800, 400);
        welcomeScene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

	    // Set the scene to primary stage
	    primaryStage.setScene(welcomeScene);
	    primaryStage.setTitle("Welcome Page");
		
    }
}