package databasePart1;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import databasePart1.*;

/**
 * Responsible for creating functionality and allowing Instructor
 * to carry out all their duties. 
 */
public class InstructorHomePage {

	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the InstructorHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public InstructorHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    /**
     * Creates functionality and UI for all Instructor needs. 
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     */
	public void show(Stage primaryStage, User user) {
		DisplayUtilities displayUtilities = new DisplayUtilities();
		
		//Set Up the Layout
		VBox layout = new VBox(10);
		layout.setId("standard-page-vbox-layout"); //Configure CSS
		
		
		// Create hello label		
		Label helloLabel = displayUtilities.createLabel("Hello Instructor", 200, 100);
		
		
		// Create logout button
		Button logoutButton = new Button("Logout"); 
		
		logoutButton.setOnAction( a-> {
				UserLoginPage userLoginPage = new UserLoginPage(databaseHelper); 
				userLoginPage.show(primaryStage);
		});

		Button viewMessagesButton = new Button("View Private Messages");
		viewMessagesButton.setOnAction(e -> new ConversationListPage(databaseHelper).show(user));
		
		
		Button createMessageButton = new Button("Create Private Message");
		createMessageButton.setOnAction(e -> new CreatePrivateMessage(databaseHelper, user).show(primaryStage));

		
		// Responding to Reviewer Requests Functionality ************************
		Button viewReviewerRequests = new Button ("View Reviewer Requests");
		viewReviewerRequests.setOnAction(b->{
			Stage newStage = new Stage();
			ViewReviewerRequestsPage viewRequestsPage = new ViewReviewerRequestsPage(databaseHelper);
			viewRequestsPage.show(primaryStage, newStage);
		});
		
		

		// Add all elements to layout
		layout.getChildren().addAll(helloLabel, logoutButton, viewReviewerRequests, viewMessagesButton, createMessageButton);
		
		
		// Configure and set window visible
		Scene scene = new Scene(layout, 800, 400);
		scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("UpdateRolesPage");
		primaryStage.show();
	}
}
