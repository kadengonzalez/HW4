package databasePart1;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Contains all functionality and creates UI for admin to perform all necessary duties. 
 */
public class AdminHomePage {

	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the AdminHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public AdminHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    
    /**
     * Creates UI for admin page. 
     * Displays buttons to send admin to different pages
     * that are necessary for admin duties. 
     * 
     * @param user Passes the current user object.
     * @param primaryStage Passes the current stage being used.
     */
    public void show(User user, Stage primaryStage ) {
    	// Create a layout for the page
    	VBox layout = new VBox(10);
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    
	    // Create welcome message for admin
	    Label welcomeLabel = new Label("Hello, Admin! \n What would you like to do");
	    welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-alignment: center;");
	    
	    
		// Create Logout Button
	    Button logoutButton = new Button("Logout");
	    
	    logoutButton.setOnAction( a-> {
				UserLoginPage userLoginPage = new UserLoginPage(databaseHelper); 
				userLoginPage.show(primaryStage);
		});

		
		// Button to Switch Roles
		Button switchRoleButton = new Button ("Switch Roles");
		
		switchRoleButton.setOnAction( a-> {   
				WelcomeLoginPage welcomeLoginPage = new WelcomeLoginPage(databaseHelper); 
				welcomeLoginPage.show(primaryStage, user);
		});
		
	   
	    // Button to send admin to ChangePasswordPage
	    Button changeUserPasswordButton = new Button("Change Password");
	    
	    changeUserPasswordButton.setOnAction(e -> {
	    	System.out.println("Admin wants to change user password");
	    	ChangePasswordPage changePasswordPage = new ChangePasswordPage(databaseHelper);
	    	changePasswordPage.show(primaryStage);
	    });
	    
	    
	    // Button to send admin to ListUsersPage
	    Button listUsersButton = new Button("List Users");
	    
	    listUsersButton.setOnAction(e -> {
	    	System.out.println("Admin wants to list users");
	    	ListUsersPage listUsersPage = new ListUsersPage(databaseHelper);
	    	listUsersPage.show(primaryStage);
	    });
	    
	    
	    // Button to send admin to UpdateRolesPage
	    Button updateRolesButton = new Button("Update Roles");
	    
	    updateRolesButton.setOnAction(e -> {	    	
	    	UpdateRolesPage updateRolesPage = new UpdateRolesPage(databaseHelper);
	    	updateRolesPage.show(primaryStage);
	    });
	    
	    
	    // Button to send admin to DeleteUserPage
	    Button deleteUserButton = new Button ("Delete User");
	    
	    deleteUserButton.setOnAction(e -> {	    	
	    	DeleteUserPage deleteUserPage = new DeleteUserPage(databaseHelper);
	    	Stage newWindow = new Stage();
	    	deleteUserPage.show(newWindow);
	    });
	    
	    
	    // Add all components to VBox
	    layout.getChildren().addAll(switchRoleButton, welcomeLabel, changeUserPasswordButton, 
	    		                    listUsersButton, updateRolesButton, deleteUserButton, logoutButton);
	    

	    // Make page visible to admin
	    Scene adminScene = new Scene(layout, 800, 400);
	    adminScene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());
	    primaryStage.setScene(adminScene);
	    primaryStage.setTitle("Admin Page");
    }
}