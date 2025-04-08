package databasePart1;
//Original Code By Professor Lynn Robert Carter. Original has been Editied by Bracken Peterson Jan 2025
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This page displays a simple welcome message for the user.
 */
// hi

import java.sql.SQLException;
import databasePart1.*;

/**
 * Responsible for creating UI and functionality allowing user to complete
 * all required tasks. 
 */
public class UserHomePage {
	
    private final DatabaseHelper databaseHelper;

    /**
	 * Constructor for the UserHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public UserHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * Creates UI and all needed functionality for the user
     * to complete required tasks. 
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     */
    public void show(Stage primaryStage, User user) {
    	// create a VBox for the UserHomePage
    	VBox layout = new VBox(20);
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    layout.setId("standard-page-vbox-layout"); //Configure CSS
	
		// create a log out button	    
		Button logout = new Button("Logout"); 
		logout.setOnAction( a-> {
				UserLoginPage userLoginPage = new UserLoginPage(databaseHelper); 
				userLoginPage.show(primaryStage);
		});
		
		
		// create a button to send user to welcome page so they can select different role
		Button switchRole = new Button ("Switch Roles");
		
		switchRole.setOnAction( a-> {
				WelcomeLoginPage welcomeLoginPage = new WelcomeLoginPage(databaseHelper); 
				welcomeLoginPage.show(primaryStage, user);
		});

    	// add logout button to VBox
        layout.getChildren().addAll(logout);		
	    
	    // Label to display Hello user
	    Label userLabel = new Label("Hello, User!");
	    userLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

		// add remaining elements to the VBox
	    layout.getChildren().addAll(userLabel, switchRole);
	    Scene userScene = new Scene(layout, 800, 400);

		userScene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());


	    // Set the scene to primary stage
	    primaryStage.setScene(userScene);
	    primaryStage.setTitle("User Page");
	    
	    primaryStage.show();    	
    }
}