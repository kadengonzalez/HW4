package databasePart1;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import databasePart1.*;

/**
 * Responsible for allowing staff member to carry out all duties. 
 */
public class StaffHomePage {

	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the StaffHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public StaffHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    /**
     * Creates functionality and UI for the staff user.
     * Allows them to carry out all duties, or directs 
     * them to appropriate pages. 
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
		Label helloLabel = displayUtilities.createLabel("Hello Staff", 300, 100);
		
		
		/**

		 * Button that enables staff to see all discussions and responses

		 * within the discussion board.

		 */
		Button viewDiscussionsButton = new Button("View Discussions");
		viewDiscussionsButton.setOnAction(e -> new ViewDiscussions(databaseHelper).show(primaryStage, user, new Question(0, "", "", "", false, false, 0, false, 0), false));
		
		
		/**

		 * Button that enables staff to see all private messages

		 * for monitoring and response.

		 */
		Button viewMessagesButton = new Button("View Private Messages");
		viewMessagesButton.setOnAction(e -> new ConversationListPage(databaseHelper).show(user));
		
		
		/**

		 * Button that enables staff to send private messages

		 * to instructors and other staff.

		 */
		Button createMessageButton = new Button("Create Private Message");
		createMessageButton.setOnAction(e -> new CreatePrivateMessage(databaseHelper, user).show(primaryStage));

		// Create logout button
		Button logoutButton = new Button("Logout"); 
		
		logoutButton.setOnAction( a-> {
				UserLoginPage userLoginPage = new UserLoginPage(databaseHelper);
				userLoginPage.show(primaryStage);
		});

		
		// Add all elements to page		
		layout.getChildren().addAll(helloLabel, logoutButton, viewDiscussionsButton, viewMessagesButton, createMessageButton);
		
		
		// Configure and set window visible
		Scene scene = new Scene(layout, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("StaffHomePage");
        primaryStage.show();
	}
}